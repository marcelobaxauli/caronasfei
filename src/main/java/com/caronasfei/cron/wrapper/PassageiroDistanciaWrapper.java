package com.caronasfei.cron.wrapper;

import com.caronasfei.db.intencao.IntencaoCarona;

public class PassageiroDistanciaWrapper implements Comparable<PassageiroDistanciaWrapper> {

	private Double distancia;
	private IntencaoCarona passageiro;

	public Double getDistancia() {
		return distancia;
	}

	public void setDistancia(Double distancia) {
		this.distancia = distancia;
	}

	public IntencaoCarona getPassageiro() {
		return passageiro;
	}

	public void setPassageiro(IntencaoCarona passageiro) {
		this.passageiro = passageiro;
	}

	public int compareTo(PassageiroDistanciaWrapper o) {
		return this.distancia.compareTo(o.distancia);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(distancia);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((passageiro == null) ? 0 : passageiro.hashCode());
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
		PassageiroDistanciaWrapper other = (PassageiroDistanciaWrapper) obj;
		if (Double.doubleToLongBits(distancia) != Double.doubleToLongBits(other.distancia))
			return false;
		if (passageiro == null) {
			if (other.passageiro != null)
				return false;
		} else if (!passageiro.equals(other.passageiro))
			return false;
		return true;
	}

}
