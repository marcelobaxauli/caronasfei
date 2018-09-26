package com.caronasfei.match.djikstra;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.caronasfei.db.intencao.IntencaoCarona;
import com.caronasfei.db.intencao.IntencaoCarona.AcaoCarona;
import com.caronasfei.db.intencao.endereco.Endereco;
import com.caronasfei.match.djikstra.model.RestricaoTempo;

public class No {

	private List<Vertice> outputVertexes = new LinkedList<Vertice>();

	private No proximoNo;

	private int number;

	private RestricaoTempo timeRestriction;

	private int numeroPassageirosAtual;
	private long currentTime;

	private int currentBestScore;

	private IntencaoCarona intencaoCarona;

	private Endereco endereco;

	private Grafo graph;

	public No(int number, Grafo graph) {

		this.number = number;
		this.graph = graph;

	}

	public No getProximoNo() {
		return proximoNo;
	}

	public void setNextNode(No nextNode) {
		this.proximoNo = nextNode;
	}

	public List<Vertice> getOutputVertexes() {
		return outputVertexes;
	}

	public void setOutputVertexes(List<Vertice> outputVertexes) {
		this.outputVertexes = outputVertexes;
	}

	public void addOutputVertex(Vertice vertex) {
		this.outputVertexes.add(vertex);
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public IntencaoCarona getIntencaoCarona() {
		return intencaoCarona;
	}

	public void setIntencaoCarona(IntencaoCarona intencaoCarona) {
		this.intencaoCarona = intencaoCarona;
	}

	public boolean isInTimeRestriction(Date visitTime, Date maximumTime) {

		if (this.number == this.graph.getCurrentSize()) {
			return true;
		}

		return this.timeRestriction.isInTimeRestriction(visitTime, maximumTime);
	}

	public RestricaoTempo getTimeRestriction() {
		return this.timeRestriction;
	}

	public void setTimeRestriction(RestricaoTempo timeRestriction) {
		this.timeRestriction = timeRestriction;
	}

	public int getCurrentNumberOfPassengers() {
		return numeroPassageirosAtual;
	}

	public void setCurrentNumberOfPassengers(int currentNumberOfPassengers) {
		this.numeroPassageirosAtual = currentNumberOfPassengers;
	}

	// span costs to forward adjacent vertices
	public long getCurrentTime() {
		return currentTime;
	}

	public void setCurrentTime(long currentTime) {
		this.currentTime = currentTime;
	}

	public int getCurrentBestScore() {
		return currentBestScore;
	}

	public void setCurrentBestScore(int currentBestScore) {
		this.currentBestScore = currentBestScore;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public void spanCosts(Map<Integer, No> visitedNodes) {

		if (this.intencaoCarona.getAcaoCarona() == AcaoCarona.OFERECER_CARONA
				&& this.numeroPassageirosAtual >= this.intencaoCarona.getNumeroAssentos()) {
			return;
		}

		for (Vertice outputVertex : this.outputVertexes) {

			if (outputVertex.getI() >= this.graph.getCurrentSize()
					|| outputVertex.getJ() >= this.graph.getCurrentSize()) {
				break;
			}

			long estimatedTimeCost = (long) (this.currentTime + outputVertex.getTimeCost());

			int adjacentNodeScore = this.graph.getObjectiveValue(this.numeroPassageirosAtual + 1,
					estimatedTimeCost / 1000 / 60);

			No noDestino = outputVertex.getTargetNode();

			// TODO preciso verificar o car capacity da intenção carona motorista do nó
			// adjacente.
			// Se for passageiro nem precisa verificar.

			if (visitedNodes.get(noDestino.getNumber()) != null
					&& outputVertex.getTargetNode().isInTimeRestriction(
							new Date(this.graph.getRideDepart().getTime() + estimatedTimeCost),
							this.graph.getRideArriveTime())
					&& (noDestino.getIntencaoCarona().getAcaoCarona() == AcaoCarona.OFERECER_CARONA
							&& this.numeroPassageirosAtual + 1 <= noDestino.getIntencaoCarona().getNumeroAssentos())
					&& adjacentNodeScore < noDestino.getCurrentBestScore()) {

				noDestino.setCurrentBestScore(adjacentNodeScore);
				noDestino.setNextNode(this);
				noDestino.setCurrentTime(estimatedTimeCost);
				noDestino.setCurrentNumberOfPassengers(this.numeroPassageirosAtual + 1);
			}

		}

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + number;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		No other = (No) obj;
		if (number != other.number)
			return false;
		return true;
	}

}
