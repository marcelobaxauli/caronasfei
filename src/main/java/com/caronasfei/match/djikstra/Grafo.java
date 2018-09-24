package com.caronasfei.match.djikstra;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.caronasfei.db.intencao.IntencaoCarona;
import com.caronasfei.db.intencao.IntencaoCarona.DirecaoCarona;
import com.caronasfei.db.intencao.endereco.Endereco;
import com.caronasfei.match.djikstra.model.FuncaoObjetivo;
import com.caronasfei.match.djikstra.model.RestricaoTempo;

public class Grafo {

	private static final Logger LOGGER = LogManager.getLogger(Grafo.class);

	private static final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

	// TODO: isso deveria ser configuração externa (via config .properties)
	private static final int MAXIMO_NUMERO_NOS = 3000;

	private Map<Integer, No> nos;

	private List<No> instanciaNos;

	private No primeiroNo;
	private No ultimoNo;

	private Date tempoDePartida;
	private Date tempodeChegada;

	private int tamanhoAtual;

	private int capacidadeCarro;

	private FuncaoObjetivo funcaoObjetivo;

	@PostConstruct
	public void init() {

		this.nos = new HashMap<Integer, No>(MAXIMO_NUMERO_NOS, 1);

		this.funcaoObjetivo = new FuncaoObjetivo(3, 4 * 60, 30);

		for (int i = 0; i < MAXIMO_NUMERO_NOS; i++) {

			No novoNo = new No(i, this);
			this.nos.put(i, novoNo);

		}

		for (int i = MAXIMO_NUMERO_NOS - 1; i >= 0; i--) {
			for (int j = 0; j < MAXIMO_NUMERO_NOS - 1; j++) {

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
				LOGGER.info((MAXIMO_NUMERO_NOS - i) + " nós instanciados...");
			}
		}

	}

	public void instancia(List<IntencaoCarona> intencoesCarona, Endereco destino) {

		this.instanciaNos = new LinkedList<No>();

		this.tamanhoAtual = this.instanciaNos.size();
		this.capacidadeCarro = capacidadeCarro;

		this.preencheNos(intencoesCarona, destino);
		this.preencheArestas();

		this.primeiroNo = this.nos.get(0);
		this.ultimoNo = this.nos.get(this.instanciaNos.size() - 1);

		this.tempoDePartida = new Date(this.nos.get(0).getTimeRestriction().getDepartTime());
		this.tempodeChegada = new Date(this.nos.get(0).getTimeRestriction().getArriveTime());

	}

	public No getNoMinimoCusto() {

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

	public int getCarCapacity() {
		return capacidadeCarro;
	}

	public void setCarCapacity(int carCapacity) {
		this.capacidadeCarro = carCapacity;
	}

	public int getObjectiveValue(int numberOfPassengers, long timeCost) {
		return this.funcaoObjetivo.getObjectiveFunctionValue(numberOfPassengers, timeCost);
	}

	private void preencheNos(List<IntencaoCarona> intencoesCarona, Endereco destino) {

		// intencoes de carona - motoristas e passageiros
		for (int i = 0; i < intencoesCarona.size(); i++) {

			IntencaoCarona passageiro = intencoesCarona.get(i);

			No node = this.nos.get(i);
			node.setCurrentBestScore(Integer.MAX_VALUE);
			node.setCurrentTime(0);
			node.setIntencaoCarona(passageiro);
			node.setTimeRestriction(RestricaoTempo.converte(passageiro.getHorarioPartida().getHorario(),
					passageiro.getHorarioChegada().getHorario()));

			if (passageiro.getDirecaoCarona() == DirecaoCarona.IDA_FEI) {
				node.setEndereco(passageiro.getEnderecoPartida());
			} else if (passageiro.getDirecaoCarona() == DirecaoCarona.VOLTA_FEI) {
				node.setEndereco(passageiro.getEnderecoDestino());
			}

			this.instanciaNos.add(node);
		}

		// último nó não possui vertices de saída
		// último nó vai ser a FEI / agr no inicio
		No ultimoNo = this.nos.get(intencoesCarona.size());
		ultimoNo.setCurrentBestScore(0);
		ultimoNo.setCurrentTime(0);
		// ultimo no nao tem restricao de tempo especifico
		ultimoNo.setTimeRestriction(RestricaoTempo.converte(null, null));
		ultimoNo.setEndereco(destino);
		this.instanciaNos.add(ultimoNo);

	}

	private void preencheArestas() {

		for (int i = 0; i < this.instanciaNos.size() - 1; i++) {

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

				// TODO pesquisa no OSRM quanto tempo demora pra ir do endereco de origem até o
				// endereco de destino, e preenche a aresta com este tempo

			}

		}

	}

}
