package com.caronasfei.match.djikstra;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.caronasfei.match.djikstra.model.FuncaoObjetivo;
import com.caronasfei.match.djikstra.model.RestricaoTempo;

public class Grafo {

	private static final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

	private Map<Integer, No> nos;

	private List<No> minimoCustoNosListaReduzida;

	private No primeiroNo;
	private No ultimoNo;

	private Date tempoDePartida;
	private Date tempodeChegada;

	private int tamanhoAtual;

	private int capacidadeCarro;

	private FuncaoObjetivo funcaoObjetivo;

	public Grafo(int maxNos) {

		this.nos = new HashMap<Integer, No>(maxNos, 1);

		this.funcaoObjetivo = new FuncaoObjetivo(3, 4 * 60, 30);

		for (int i = 0; i < maxNos; i++) {
			No novoNo = new No(i, this);

			this.nos.put(i, novoNo);

		}

		for (int i = maxNos - 1; i >= 0; i--) {
			for (int j = 0; j < maxNos - 1; j++) {

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
				System.out.println(sdf.format(new Date()) + ": i = " + i + 1000);
			}
		}

	}

	public void configura(int custo[][], int n, int capacidadeCarro, List<RestricaoTempo> restricoesTempoLista) {

		this.minimoCustoNosListaReduzida = new LinkedList<No>();

		for (int i = 0; i < n; i++) {
			No node = this.nos.get(i);

			if (i == n - 1) {
				node.setCurrentBestScore(0);
			} else {
				node.setCurrentBestScore(Integer.MAX_VALUE);
			}
			node.setTimeRestriction(restricoesTempoLista.get(i));
			node.setCurrentTime(0);
			this.minimoCustoNosListaReduzida.add(node);
		}

		this.tamanhoAtual = n;
		this.capacidadeCarro = capacidadeCarro;

		this.primeiroNo = this.nos.get(0);
		this.ultimoNo = this.nos.get(n - 1);

		for (int i = 0; i < n; i++) {

			No sourceNode = this.nos.get(i);

			for (Vertice vertex : sourceNode.getOutputVertexes()) {

				if (vertex.getI() >= n || vertex.getJ() >= n) {
					break;
				}

				vertex.setTimeCost(custo[vertex.getI()][vertex.getJ()]);

			}

		}

		// último nó não possui vertices de saída
		No sourceNode = this.nos.get(n - 1);
		sourceNode.setTimeRestriction(restricoesTempoLista.get(n - 1));

		this.tempoDePartida = new Date(this.nos.get(0).getTimeRestriction().getDepartTime());
		this.tempodeChegada = new Date(this.nos.get(0).getTimeRestriction().getArriveTime());

	}

	public No getNoMinimoCusto() {

		this.minimoCustoNosListaReduzida.sort(new Comparator<No>() {

			@Override
			public int compare(No o1, No o2) {
				return o1.getCurrentBestScore() - o2.getCurrentBestScore();
			}

		});
		return this.minimoCustoNosListaReduzida.remove(0);
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

	public int getObjectiveValue(int numberOfPassengers, int timeCost) {
		return this.funcaoObjetivo.getObjectiveFunctionValue(numberOfPassengers, timeCost);
	}

}
