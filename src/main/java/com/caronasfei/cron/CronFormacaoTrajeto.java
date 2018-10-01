package com.caronasfei.cron;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.caronasfei.match.MatchCaronasIda;
import com.caronasfei.service.endereco.EnderecoServico;
import com.caronasfei.service.intencao.IntencaoCaronaServico;
import com.caronasfei.service.sugestao.SugestaoTrajetoServico;

@Component
@EnableScheduling
public class CronFormacaoTrajeto {

	@Autowired
	private MatchCaronasIda matchCaronasIda;

	@Scheduled(fixedRate = 30000)
	public void executar() {
		this.matchCaronasIda.encontraMatches();
	}

}
