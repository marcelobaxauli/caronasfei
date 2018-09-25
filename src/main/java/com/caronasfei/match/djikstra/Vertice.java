package com.caronasfei.match.djikstra;

public class Vertice {

	private No targetNode;

	private double timeCost;

	private int i;
	private int j;

	public No getTargetNode() {
		return targetNode;
	}

	public void setTargetNode(No targetNode) {
		this.targetNode = targetNode;
	}

	public double getTimeCost() {
		return timeCost;
	}

	public void setTimeCost(double cost) {
		this.timeCost = cost;
	}

	public int getI() {
		return i;
	}

	public void setI(int i) {
		this.i = i;
	}

	public int getJ() {
		return j;
	}

	public void setJ(int j) {
		this.j = j;
	}

}
