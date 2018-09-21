package com.caronasfei.match;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.caronasfei.db.intencao.IntencaoCarona;
import com.caronasfei.service.intencao.IntencaoCaronaServico;

@Component
@Scope("singleton")
public class MatchCaronas {

	@Autowired
	private IntencaoCaronaServico intencaoServico;

	public void init() {
		// constrói grafo grande
		
		
		
	}

	public void gerarSugestoes() {
		// roda algoritmo e grava as sugestões de carona encontradas no banco...

		List<IntencaoCarona> intencaoMotoristas = this.intencaoServico.findAllMotoristaNotInSugestaoAtiva();
		List<IntencaoCarona> intencaoPassageiros = this.intencaoServico.findAllPassageirosNotInSugestaoAtiva();

	}

}
