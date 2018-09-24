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

	// isso deveria ser configuração externa (via config .properties)
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

	public void instancia(IntencaoCarona motorista, List<IntencaoCarona> passageiros, Endereco destino) {

		this.instanciaNos = new LinkedList<No>();

		// primeiro nó
		// nó motorista
		No primeiroNo = this.nos.get(0);
		primeiroNo.setCurrentBestScore(0);
		primeiroNo.setCurrentTime(motorista.getHorarioPartida().getHorario().getTime());
		if (motorista.getDirecaoCarona() == DirecaoCarona.IDA_FEI) {
			primeiroNo.setEndereco(motorista.getEnderecoPartida());
		} else if (motorista.getDirecaoCarona() == DirecaoCarona.VOLTA_FEI) {
			primeiroNo.setEndereco(motorista.getEnderecoDestino());
		}
		primeiroNo.setTimeRestriction(RestricaoTempo.converte(motorista.getHorarioPartida().getHorario(),
				motorista.getHorarioChegada().getHorario()));
		
		this.instanciaNos.add(primeiroNo);

		// passageiros
		for (int i = 0; i < passageiros.size(); i++) {

			IntencaoCarona passageiro = passageiros.get(i);
			
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
		No ultimoNo = this.nos.get(passageiros.size() + 1);
		ultimoNo.setCurrentBestScore(Integer.MAX_VALUE);
		ultimoNo.setCurrentTime(0);
		// ultimo no nao tem restricao de tempo especifico
		ultimoNo.setTimeRestriction(RestricaoTempo.converte(null, null));
		ultimoNo.setEndereco(destino);
		this.instanciaNos.add(ultimoNo);

		this.tamanhoAtual = this.instanciaNos.size();
		this.capacidadeCarro = capacidadeCarro;

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

}
