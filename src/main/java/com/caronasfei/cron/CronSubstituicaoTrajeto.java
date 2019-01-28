package com.caronasfei.cron;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.caronasfei.match.MatchCaronasIda;

@Component
@EnableScheduling
public class CronSubstituicaoTrajeto {
	
	@Autowired
	private MatchCaronasIda matchCaronasIda;

	@Scheduled(fixedRate = 5000)
	public void executar() {
		this.matchCaronasIda.substituiMatches();
	}

}
