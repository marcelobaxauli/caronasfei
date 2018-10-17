package com.caronasfei.match.djikstra;

public class Vertice {

	private No noOrigem;

	// em milisegundos
	private long custoTransito;

	private int i;
	private int j;

	public No getNoOrigem() {
		return noOrigem;
	}

	public void setNoOrigem(No noOrigem) {
		this.noOrigem = noOrigem;
	}

	public long getCustoTransito() {
		return custoTransito;
	}

	public void setCustoTransito(long custo) {
		this.custoTransito = custo;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + i;
		result = prime * result + j;
		result = prime * result + ((noOrigem == null) ? 0 : noOrigem.hashCode());
		long temp;
		temp = Double.doubleToLongBits(custoTransito);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		Vertice other = (Vertice) obj;
		if (i != other.i)
			return false;
		if (j != other.j)
			return false;
		if (noOrigem == null) {
			if (other.noOrigem != null)
				return false;
		} else if (!noOrigem.equals(other.noOrigem))
			return false;
		if (Double.doubleToLongBits(custoTransito) != Double.doubleToLongBits(other.custoTransito))
			return false;
		return true;
	}

}
