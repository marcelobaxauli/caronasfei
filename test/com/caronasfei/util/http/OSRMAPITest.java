package com.caronasfei.util.http;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { OSRMAPI.class })
public class OSRMAPITest {

	@Autowired
	private OSRMAPI osrmAPI;

	@Test
	public void teste() throws ParseException {
		Double segundos = this.osrmAPI.getTempo(Coordenadas.converte(-46.573334, -23.624267),
				Coordenadas.converte(-46.580434, -23.726300));

		Assert.assertEquals(segundos, 1640.3, 0);

	}

	@Test
	public void removeTest() {
		
		List<Integer> ints = new ArrayList<Integer>();
		
		ints.add(4);
		ints.add(4);
		ints.add(4);
		ints.add(4);
		ints.add(4);
		
		for (int  i = 0; i < 50; i++) {
			ints.remove(0);
			System.out.println("removed");
		}
		
	}
	
}
