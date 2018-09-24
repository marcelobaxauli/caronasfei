package com.caronasfei.util.http;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.caronasfei.util.http.OSRMAPI.Coordenadas;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { OSRMAPI.class })
public class OSRMAPITest {

	@Autowired
	private OSRMAPI osrmAPI;

	@Test
	public void teste() throws ParseException {
		String resposta = this.osrmAPI.getTempo(Coordenadas.converte(-46.573334, -23.624267),
				Coordenadas.converte(-46.580434, -23.726300));

		JSONParser parser = new JSONParser();
		JSONObject json = (JSONObject) parser.parse(resposta);
		JSONArray routes = (JSONArray) json.get("routes");
		JSONObject route = (JSONObject) routes.get(0);
		Double segundos = (Double) route.get("duration"); // segundos

		Assert.assertEquals(segundos, 1630.1, 0);

	}

}
