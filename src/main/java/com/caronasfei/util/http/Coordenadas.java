package com.caronasfei.util.http;

public class Coordenadas {

	private double longitude;
	private double latitude;

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public static Coordenadas converte(double longitude, double latitude) {
		Coordenadas coordenadas = new Coordenadas();
		coordenadas.setLongitude(longitude);
		coordenadas.setLatitude(latitude);

		return coordenadas;
	}

	public String toString() {
		return this.longitude + "," + this.latitude;
	}

}
