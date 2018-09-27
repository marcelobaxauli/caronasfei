package com.caronasfei.cron;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.caronasfei.db.intencao.IntencaoCarona;
import com.caronasfei.db.intencao.IntencaoCarona.DirecaoCarona;
import com.caronasfei.db.intencao.endereco.Endereco;
import com.caronasfei.db.sugestao.SugestaoTrajeto;
import com.caronasfei.match.MatchCaronasIda;
import com.caronasfei.match.djikstra.No;
import com.caronasfei.service.endereco.EnderecoServico;
import com.caronasfei.service.intencao.IntencaoCaronaServico;
import com.caronasfei.service.sugestao.SugestaoTrajetoServico;

@Component
@EnableScheduling
public class CronFormacaoTrajeto {

	@Autowired
	private MatchCaronasIda matchCaronasIda;

	@Autowired
	private IntencaoCaronaServico intencaoServico;

	@Autowired
	private EnderecoServico enderecoServico;

	@Autowired
	private SugestaoTrajetoServico sugestaoTrajetoService;

	@Scheduled(fixedRate = 30000)
	public void executar() {
		this.executarIda();
	}

	@Transactional(propagation = Propagation.REQUIRED)
	private void executarIda() {
		
	}

}
