package com.caronasfei.util.http;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Scope("prototype")
public class OSRMAPI {

	private static final Logger LOGGER = LogManager.getLogger(OSRMAPI.class);
	
	// TODO: obter o endereço da API OSRM por aquivo. Assim da pra mudar pra
	// localhost qnd baixar e instalar o backend
	private String enderecoBaseApiUrl = "http://localhost:5000/route/v1/driving";

	public Double getTempo(Coordenadas origem, Coordenadas destino) {

		String enderecoApiUrl = this.enderecoBaseApiUrl + "/" + origem.toString() + ";" + destino.toString();

		RestTemplate restTemplate = new RestTemplate();
		String resposta = restTemplate.getForObject(enderecoApiUrl, String.class);

		JSONParser parser = new JSONParser();
		JSONObject json = null;
		try {
			json = (JSONObject) parser.parse(resposta);
		} catch (ParseException e) {
			LOGGER.error("Erro na conversão do json de resposta",  e);
		}
		JSONArray routes = (JSONArray) json.get("routes");
		JSONObject route = (JSONObject) routes.get(0);
		Number segundosObg = (Number) route.get("duration"); // segundos
		double segundos = segundosObg.doubleValue();

		return segundos;

	}

}
