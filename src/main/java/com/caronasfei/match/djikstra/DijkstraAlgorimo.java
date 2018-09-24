package com.caronasfei.match.djikstra;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.caronasfei.match.djikstra.model.RestricaoTempo;

public class DijkstraAlgorimo implements Algoritmo {

	private Grafo grafo;

	private List<RestricaoTempo> restricaoTempoLista;

	private int custoTempo[][];

	public DijkstraAlgorimo(Grafo grafo, int custo[][], List<RestricaoTempo> restricaoTempoLista) {
		this.grafo = grafo;
		this.restricaoTempoLista = restricaoTempoLista;
		this.custoTempo = custo;
	}

	@Override
	public void rodar() {

		Date tempoInicio = new Date();
		System.out.println(tempoInicio + ": inicio...");

		Set<Integer> nosVisitados = new HashSet<Integer>();

		No ultimoNo = this.grafo.getLastNode();
		No primeiroNo = this.grafo.getFirstNode();

		No proximoNo = null;
		do {

			proximoNo = this.grafo.getNoMinimoCusto();

			if (proximoNo != null) {
				proximoNo.spanCosts(nosVisitados);
			}

			nosVisitados.add(proximoNo.getNumber());

		} while (nosVisitados.size() != this.grafo.getCurrentSize());

		Date dataFim = new Date();
		System.out.println(tempoInicio + ": termino.");

		System.out.println("tempo de processamento: " + ((dataFim.getTime() - tempoInicio.getTime())) + " milisegundos");

		this.printSequence(primeiroNo);
		System.out.println("\nScore: " + primeiroNo.getCurrentBestScore());
		System.out.println("\nTempo: " + primeiroNo.getCurrentTime());
		System.out.println("Custo da travessia direta: " + this.custoTempo[0][this.custoTempo.length - 1] / 60 / 1000);

		System.out.println("Pool de opções:");
		this.exibePossiveisPassageiros();
	}

	public void printSequence(No node) {

		if (node != null) {
			this.printSequence(node.getProximoNo());
			RestricaoTempo timeRestriction = this.restricaoTempoLista.get(node.getNumber());
			Date depart = new Date(timeRestriction.getDepartTime());
			Date arrive = new Date(timeRestriction.getArriveTime());
			System.out.print(node.getNumber() + "(" + depart + ", " + arrive + ")" + "\t");
		}

	}

	public void exibePossiveisPassageiros() {

		RestricaoTempo restricaoTempoOrigem = this.restricaoTempoLista.get(0);

		for (int j = 1; j < this.restricaoTempoLista.size() - 1; j++) {

			RestricaoTempo restricaoTempoAtual = this.restricaoTempoLista.get(j);

			if (restricaoTempoAtual.getDepartTime() <= restricaoTempoOrigem.getDepartTime()
					&& restricaoTempoAtual.getArriveTime() >= restricaoTempoOrigem.getArriveTime()
					&& restricaoTempoOrigem.getArriveTime() > restricaoTempoAtual.getDepartTime()) {
				// System.out.println(j + ": " + currentTimeRestriction + " dist(" +
				// this.cost[0][j] / 60 / 1000 + ")");
			}
		}
	}

}
