package com.caronasfei.util.http;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Scope("prototype")
public class OSRMAPI {

	// TODO: obter o endereço da API OSRM por aquivo. Assim da pra mudar pra
	// localhost qnd baixar e instalar o backend
	private String enderecoBaseApiUrl = "http://router.project-osrm.org/route/v1/driving";

	public String getTempo(Coordenadas origem, Coordenadas destino) {

		String enderecoApiUrl = this.enderecoBaseApiUrl + "/" + origem.toString() + ";" + destino.toString();
		
		RestTemplate restTemplate = new RestTemplate();
		String result = restTemplate.getForObject(enderecoApiUrl, String.class);
		
		return result;

	}

	static class Coordenadas {

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

}
