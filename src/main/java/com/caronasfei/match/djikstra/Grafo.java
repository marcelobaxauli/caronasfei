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
import com.caronasfei.db.sugestao.SugestaoTrajetoPassageiro.SugestaoTrajetoPassageiroEstado;
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
	public void init(int maxNumeroNos, IntencaoCarona intencaoMotorista) {

		Date inicio = new Date();

		Double segundosDistancia = this.osrmApi.getTempo(
				Coordenadas.converte(intencaoMotorista.getEnderecoPartida().getLongitude(),
						intencaoMotorista.getEnderecoPartida().getLatitude()),
				Coordenadas.converte(intencaoMotorista.getEnderecoDestino().getLongitude(),
						intencaoMotorista.getEnderecoDestino().getLatitude()));

		this.nos = new HashMap<Integer, No>(maxNumeroNos, 1);

		double minutosDistancia = segundosDistancia / 60.0;
		int detourReferencia = (int) (minutosDistancia + intencaoMotorista.getDetour());

		this.funcaoObjetivo = new FuncaoObjetivo(intencaoMotorista.getNumeroAssentos(), 5 * 60, detourReferencia);

		for (int i = 0; i < maxNumeroNos; i++) {

			No novoNo = new No(i, this);
			this.nos.put(i, novoNo);

		}

		for (int i = 0; i < maxNumeroNos - 1; i++) {
			for (int j = 1; j < maxNumeroNos; j++) {

				if (i != j) {
					No noOrigem = nos.get(i);
					No noDestino = nos.get(j);

					Vertice novoVerticeSaida = new Vertice();
					novoVerticeSaida.setNoDestino(noDestino);
					novoVerticeSaida.setI(i);
					novoVerticeSaida.setJ(j);

					noOrigem.addVerticeDeSaida(novoVerticeSaida);
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

		this.tamanhoAtual = intencoesCarona.size() + 1;

		this.preencheNos(intencoesCarona, destino);
		this.preencheArestas();

		this.primeiroNo = this.nos.get(0);
		this.ultimoNo = this.nos.get(this.instanciaNos.size() - 1);

		this.tempoDePartida = new Date(this.nos.get(0).getRestricaoTempo().getHorarioMinimo());
		this.tempodeChegada = new Date(this.nos.get(0).getRestricaoTempo().getHorarioMaximo());

		Date fim = new Date();

		long tempoInicializacao = fim.getTime() - inicio.getTime();
		LOGGER.info("tempo de preenchimento do grafo: {} milisegundos", tempoInicializacao);

	}

	public void fixaNosConfirmados(SugestaoTrajeto sugestaoTrajeto, Map<Integer, No> nosVisitados) {
		// fixa os nós já confirmados

		// procura por motorista primeiro

		SugestaoTrajetoMotorista motorista = sugestaoTrajeto.getMotorista();
		List<SugestaoTrajetoPassageiro> passageiros = sugestaoTrajeto.getPassageiros();
		int numeroPassageiros = 0;
		long custoTransitoAcumulado = 0l;

		No noAnterior = null;
		for (No no : this.instanciaNos) {
			if (no.getIntencaoCarona().equals(motorista.getIntencaoCarona())) {
				no.setFixo(true);
				no.setSugestaoTrajetoUsuario(motorista);
				nosVisitados.put(no.getNumber(), no);
				noAnterior = no;
				break;
			}
		}

		boolean noSugestaoMarker = false;
		for (SugestaoTrajetoPassageiro passageiro : passageiros) {
			for (No no : this.instanciaNos) {
				if (no.getIntencaoCarona() != null && no.getIntencaoCarona().equals(passageiro.getIntencaoCarona())) {

					Set<Vertice> verticesDeSaida = noAnterior.getVerticesDeSaida();

					for (Vertice verticeSaida : verticesDeSaida) {

						if (verticeSaida.getNoDestino().equals(no)) {

							no.setFixo(true);
							no.setSugestaoTrajetoUsuario(passageiro);
							no.setNoAnterior(noAnterior);
							no.setCurrentNumberOfPassengers(++numeroPassageiros);
							noAnterior = no;

							// TODO: neste ponto eu já sei que a instancia é um SugestaoTrajetoPassageiro?
							// ERROU!! na verdade pode ser o nó destino (FEI por exemplo)
							if (noAnterior.getSugestaoTrajetoUsuario() instanceof SugestaoTrajetoPassageiro) {
								
								SugestaoTrajetoPassageiro sugestaoTrajetoPassageiroAnterior = (SugestaoTrajetoPassageiro) noAnterior
										.getSugestaoTrajetoUsuario();

								if (sugestaoTrajetoPassageiroAnterior.getEstado() == SugestaoTrajetoPassageiroEstado.SUBSTITUICAO) {
									noSugestaoMarker = true;
									no.setFixo(false);
								}
								
								if (!noSugestaoMarker) {

									// TODO: daqui em diante nao tem score nos nós

									custoTransitoAcumulado += verticeSaida.getCustoTransito();

									// TODO só somar o numero de passageiros se o nó candidato for de passageiro
									// fera.
									int noCandidatoScore = this.getObjectiveValue(numeroPassageiros,
											custoTransitoAcumulado);

									no.setScore(noCandidatoScore);

									
								}
								
							}


						}

					}
				}
			}
		}

	}

	public Set<Integer> getMotoristaNodesNumbers() {

		Set<Integer> nosMotoristas = new HashSet<Integer>();
		for (No no : this.instanciaNos) {
			if (no.getIntencaoCarona() != null
					&& no.getIntencaoCarona().getAcaoCarona() == AcaoCarona.OFERECER_CARONA) {
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
				return o1.getScore() - o2.getScore();
			}

		});

		return this.instanciaNos.remove(0);
	}

	public No getPrimeiroNo() {
		return primeiroNo;
	}

	public void setPrimeiroNo(No firstNode) {
		this.primeiroNo = firstNode;
	}

	public No getUltimoNo() {
		return ultimoNo;
	}

	public void setUltimoNo(No lastNode) {
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
		return this.funcaoObjetivo.getObjectiveFunctionValue(numberOfPassengers, timeCost / 1000 / 60);
	}

	private void preencheNos(List<IntencaoCarona> intencoesCarona, Endereco destino) {

		boolean primeiroNo = true;
		// intencoes de carona - motoristas e passageiros
		for (int i = 0; i < intencoesCarona.size(); i++) {

			IntencaoCarona intencaoCarona = intencoesCarona.get(i);

			No nodo = this.nos.get(i);
			if (primeiroNo) {
				primeiroNo = false;
				nodo.setScore(0);
				nodo.setHorarioEstimado(intencaoCarona.getHorarioPartida().getHorario().getTime());
			} else {
				nodo.setScore(Integer.MAX_VALUE);
				nodo.setHorarioEstimado(0);
			}
			nodo.setIntencaoCarona(intencaoCarona);
			nodo.setRestricaoTempo(RestricaoTempo.converte(intencaoCarona.getHorarioPartida().getHorario(),
					intencaoCarona.getHorarioChegada().getHorario()));

			if (intencaoCarona.getDirecaoCarona() == DirecaoCarona.IDA_FEI) {
				nodo.setEndereco(intencaoCarona.getEnderecoPartida());
			} else if (intencaoCarona.getDirecaoCarona() == DirecaoCarona.VOLTA_FEI) {
				nodo.setEndereco(intencaoCarona.getEnderecoDestino());
			}

			this.instanciaNos.add(nodo);
		}

		// último nó não possui vertices de saída
		// último nó vai ser a FEI / agr no inicio

		No ultimoNo = this.nos.get(intencoesCarona.size());
		ultimoNo.setScore(Integer.MAX_VALUE);
		ultimoNo.setHorarioEstimado(0);
		// ultimo no nao tem restricao de tempo especifico
		ultimoNo.setRestricaoTempo(RestricaoTempo.converte(null, null));
		ultimoNo.setEndereco(destino);
		this.instanciaNos.add(ultimoNo);

	}

	private void preencheArestas() {

		Date inicio = new Date();

		for (int i = 0; i < this.instanciaNos.size() - 1; i++) {

			No noOrigem = this.instanciaNos.get(i);
			Set<Vertice> verticesSaida = noOrigem.getVerticesDeSaida();

			for (Vertice vertice : verticesSaida) {

				// TODO acho que só precisa verificar o J
				if (vertice.getI() >= this.instanciaNos.size() || vertice.getJ() >= this.instanciaNos.size()) {
					break;
				}

				No noDestino = vertice.getNoDestino();

				Endereco enderecoOrigem = noOrigem.getEndereco();
				Endereco enderecoDestino = noDestino.getEndereco();

				Double segundosDistancia = this.osrmApi.getTempo(
						Coordenadas.converte(enderecoOrigem.getLongitude(), enderecoOrigem.getLatitude()),
						Coordenadas.converte(enderecoDestino.getLongitude(), enderecoDestino.getLatitude()));

				vertice.setCustoTransito((long) (segundosDistancia * 1000));

			}

		}

		Date fim = new Date();
		long tempoInicializacao = fim.getTime() - inicio.getTime();
		// LOGGER.info("tempo de preenchimento das arestas: {} milisegundos",
		// tempoInicializacao);

	}

}
