package com.caronasfei.match.djikstra;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.caronasfei.match.djikstra.model.RestricaoTempo;

public class No {

	private List<Vertice> outputVertexes = new LinkedList<Vertice>();

	private No proximoNo;

	private int number;

	private RestricaoTempo timeRestriction;

	private int currentNumberOfPassengers;
	private int currentTime;

	private int currentBestScore;

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
		return currentNumberOfPassengers;
	}

	public void setCurrentNumberOfPassengers(int currentNumberOfPassengers) {
		this.currentNumberOfPassengers = currentNumberOfPassengers;
	}

	// span costs to forward adjacent vertices
	public int getCurrentTime() {
		return currentTime;
	}

	public void setCurrentTime(int currentTime) {
		this.currentTime = currentTime;
	}

	public int getCurrentBestScore() {
		return currentBestScore;
	}

	public void setCurrentBestScore(int currentBestScore) {
		this.currentBestScore = currentBestScore;
	}

	public void spanCosts(Set<Integer> visitedNodes) {

		if (this.currentNumberOfPassengers >= this.graph.getCarCapacity()) {
			return;
		}

		for (Vertice outputVertex : this.outputVertexes) {

			if (outputVertex.getI() >= this.graph.getCurrentSize()
					|| outputVertex.getJ() >= this.graph.getCurrentSize()) {
				break;
			}

			int estimatedTimeCost = this.currentTime + outputVertex.getTimeCost();

			int adjacentNodeScore = this.graph.getObjectiveValue(this.currentNumberOfPassengers + 1,
					estimatedTimeCost / 1000 / 60);

			if (!visitedNodes.contains(outputVertex.getTargetNode().getNumber())
					&& outputVertex.getTargetNode().isInTimeRestriction(
							new Date(this.graph.getRideDepart().getTime() + estimatedTimeCost),
							this.graph.getRideArriveTime())
					&& (outputVertex.getTargetNode().getNumber() == this.graph.getCurrentSize() - 1
							|| (this.currentNumberOfPassengers + 1 <= this.graph.getCarCapacity()))
					&& adjacentNodeScore < outputVertex.getTargetNode().getCurrentBestScore()) {

				outputVertex.getTargetNode().setCurrentBestScore(adjacentNodeScore);
				outputVertex.getTargetNode().setNextNode(this);
				outputVertex.getTargetNode().setCurrentTime(estimatedTimeCost);
				outputVertex.getTargetNode().setCurrentNumberOfPassengers(this.currentNumberOfPassengers + 1);
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
