package com.caronasfei.match.djikstra;

import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.caronasfei.db.intencao.IntencaoCarona;
import com.caronasfei.db.intencao.IntencaoCarona.AcaoCarona;
import com.caronasfei.db.intencao.IntencaoCarona.DirecaoCarona;
import com.caronasfei.db.intencao.endereco.Endereco;
import com.caronasfei.db.sugestao.SugestaoTrajeto;
import com.caronasfei.db.sugestao.SugestaoTrajetoMotorista;
import com.caronasfei.db.sugestao.SugestaoTrajetoPassageiro;
import com.caronasfei.match.djikstra.model.FuncaoObjetivo;
import com.caronasfei.match.djikstra.model.RestricaoTempo;
import com.caronasfei.util.http.Coordenadas;
import com.caronasfei.util.http.OSRMAPI;

@Component
@Scope("singleton")
public class Grafo {

	private static final Logger LOGGER = LogManager.getLogger(Grafo.class);

	@Autowired
	private OSRMAPI osrmApi;

	private Map<Integer, No> nos;

	private List<No> instanciaNos;

	private No primeiroNo;
	private No ultimoNo;

	private Date tempoDePartida;
	private Date tempodeChegada;

	private int tamanhoAtual;

	private FuncaoObjetivo funcaoObjetivo;

	// @PostConstruct
	public void init(int maxNumeroNos) {

		Date inicio = new Date();

		this.nos = new HashMap<Integer, No>(maxNumeroNos, 1);

		this.funcaoObjetivo = new FuncaoObjetivo(3, 4 * 60, 30);

		for (int i = 0; i < maxNumeroNos; i++) {

			No novoNo = new No(i, this);
			this.nos.put(i, novoNo);

		}

		for (int i = maxNumeroNos - 1; i > 0; i--) {
			for (int j = 0; j < maxNumeroNos - 1; j++) {

				if (i != j) {
					No noOrigem = nos.get(i);
					No noDestino = nos.get(j);

					Vertice novoVertice = new Vertice();
					novoVertice.setTargetNode(noDestino);
					novoVertice.setI(i);
					novoVertice.setJ(j);

					noOrigem.addOutputVertex(novoVertice);
				}

			}

			if (i % 1000 == 0) {
				LOGGER.info((maxNumeroNos - i) + " nós instanciados...");
			}
		}

		Date fim = new Date();

		long tempoInicializacao = fim.getTime() - inicio.getTime();
		LOGGER.info("tempo de inicialização do grafo: {} milisegundos", tempoInicializacao);

	}

	public void instancia(List<IntencaoCarona> intencoesCarona, Endereco destino) {

		Date inicio = new Date();

		this.instanciaNos = new LinkedList<No>();

		this.tamanhoAtual = this.instanciaNos.size();

		this.preencheNos(intencoesCarona, destino);
		this.preencheArestas();
		
		this.primeiroNo = this.nos.get(0);
		this.ultimoNo = this.nos.get(this.instanciaNos.size() - 1);

		this.tempoDePartida = new Date(this.nos.get(0).getTimeRestriction().getDepartTime());
		this.tempodeChegada = new Date(this.nos.get(0).getTimeRestriction().getArriveTime());

		Date fim = new Date();

		long tempoInicializacao = fim.getTime() - inicio.getTime();
		LOGGER.info("tempo de preenchimento do grafo: {} milisegundos", tempoInicializacao);

	}

	public void fixaNosConfirmados(List<SugestaoTrajeto> sugestoesTrajetoComSubstituicao) {
		// fixa os nós já confirmados

		// procura por motorista primeiro

		for (SugestaoTrajeto sugestaoTrajeto : sugestoesTrajetoComSubstituicao) {

			SugestaoTrajetoMotorista motorista = sugestaoTrajeto.getMotorista();
			List<SugestaoTrajetoPassageiro> passageiros = sugestaoTrajeto.getPassageiros();

			No noAnterior = null;
			for (No no : this.instanciaNos) {
				if (no.getIntencaoCarona().equals(motorista.getIntencaoCarona())) {
					no.setFixo(true);
					no.setSugestaoTrajetoUsuario(motorista);
					noAnterior = no;
					break;
				}
			}

			for (SugestaoTrajetoPassageiro passageiro : passageiros) {
				if (passageiro.isNoFixo()) {
					for (No no : this.instanciaNos) {
						if (no.getIntencaoCarona().equals(passageiro.getIntencaoCarona())) {
							no.setFixo(true);
							no.setSugestaoTrajetoUsuario(passageiro);
							noAnterior.setNextNode(no);
							noAnterior = no;
							break;
						}
					}
				}
			}

		}

	}

	public Set<Integer> getMotoristaNodesNumbers() {

		Set<Integer> nosMotoristas = new HashSet<Integer>();
		for (No no : this.instanciaNos) {
			if (no.getIntencaoCarona().getAcaoCarona() == AcaoCarona.OFERECER_CARONA) {
				nosMotoristas.add(no.getNumber());
			}
		}

		return nosMotoristas;

	}

	public No getNoMinimoCusto() {

		if (this.instanciaNos.isEmpty()) {
			return null;
		}

		this.instanciaNos.sort(new Comparator<No>() {

			@Override
			public int compare(No o1, No o2) {
				return o1.getCurrentBestScore() - o2.getCurrentBestScore();
			}

		});

		return this.instanciaNos.remove(0);
	}

	public No getFirstNode() {
		return primeiroNo;
	}

	public void setFirstNode(No firstNode) {
		this.primeiroNo = firstNode;
	}

	public No getLastNode() {
		return ultimoNo;
	}

	public void setLastNode(No lastNode) {
		this.ultimoNo = lastNode;
	}

	public Date getRideDepart() {
		return this.tempoDePartida;
	}

	public Date getRideArriveTime() {
		return this.tempodeChegada;
	}

	public int getCurrentSize() {
		return tamanhoAtual;
	}

	public void setCurrentSize(int currentSize) {
		this.tamanhoAtual = currentSize;
	}

	public int getObjectiveValue(int numberOfPassengers, long timeCost) {
		return this.funcaoObjetivo.getObjectiveFunctionValue(numberOfPassengers, timeCost);
	}

	private void preencheNos(List<IntencaoCarona> intencoesCarona, Endereco destino) {

		// intencoes de carona - motoristas e passageiros
		for (int i = 0; i < intencoesCarona.size(); i++) {

			IntencaoCarona intencaoCarona = intencoesCarona.get(i);

			No node = this.nos.get(i);
			node.setCurrentBestScore(Integer.MAX_VALUE);
			node.setCurrentTime(0);
			node.setIntencaoCarona(intencaoCarona);
			node.setTimeRestriction(RestricaoTempo.converte(intencaoCarona.getHorarioPartida().getHorario(),
					intencaoCarona.getHorarioChegada().getHorario()));

			if (intencaoCarona.getDirecaoCarona() == DirecaoCarona.IDA_FEI) {
				node.setEndereco(intencaoCarona.getEnderecoPartida());
			} else if (intencaoCarona.getDirecaoCarona() == DirecaoCarona.VOLTA_FEI) {
				node.setEndereco(intencaoCarona.getEnderecoDestino());
			}

			this.instanciaNos.add(node);
		}

		// último nó não possui vertices de saída
		// último nó vai ser a FEI / agr no inicio
		No ultimoNo = this.nos.get(intencoesCarona.size() - 1);
		ultimoNo.setCurrentBestScore(0);
		ultimoNo.setCurrentTime(0);
		// ultimo no nao tem restricao de tempo especifico
		ultimoNo.setTimeRestriction(RestricaoTempo.converte(null, null));
		ultimoNo.setEndereco(destino);
		this.instanciaNos.add(ultimoNo);

	}

	private void preencheArestas() {

		Date inicio = new Date();

		for (int i = this.instanciaNos.size() - 1; i > 0; i--) {

			No noOrigem = this.instanciaNos.get(i);
			List<Vertice> arestasSaida = noOrigem.getOutputVertexes();

			for (Vertice aresta : arestasSaida) {

				// TODO acho que só precisa verificar o J
				if (aresta.getI() >= this.instanciaNos.size() || aresta.getJ() >= this.instanciaNos.size()) {
					break;
				}

				No noDestino = aresta.getTargetNode();

				Endereco enderecoOrigem = noOrigem.getEndereco();
				Endereco enderecoDestino = noDestino.getEndereco();

				Double segundosDistancia = this.osrmApi.getTempo(
						Coordenadas.converte(enderecoOrigem.getLongitude(), enderecoOrigem.getLatitude()),
						Coordenadas.converte(enderecoDestino.getLongitude(), enderecoDestino.getLatitude()));

				aresta.setTimeCost(segundosDistancia);

			}

		}

		Date fim = new Date();
		long tempoInicializacao = fim.getTime() - inicio.getTime();
		LOGGER.info("tempo de preenchimento das arestas: {} milisegundos", tempoInicializacao);

	}

}
