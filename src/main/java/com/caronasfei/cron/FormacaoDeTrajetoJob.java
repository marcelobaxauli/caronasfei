package com.caronasfei.cron;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.caronasfei.db.intencao.IntencaoCarona;
import com.caronasfei.service.intencao.IntencaoCaronaServico;

public class FormacaoDeTrajetoJob implements Runnable {

	private static final Logger LOGGER = LogManager.getLogger(FormacaoDeTrajetoJob.class);

	// Cada nova intencao de carona registrada gera um evento generico aqui nesta
	// fila
	private BlockingQueue<Object> filaEventos = new LinkedBlockingQueue<Object>();

	@Autowired
	private IntencaoCaronaServico intencaoServico;

	public void run() {

		while (true) {

			try {
				this.filaEventos.take();

				List<IntencaoCarona> motoristasLivres = this.intencaoServico.findAllMotoristaNotInSugestaoAtiva();

				List<IntencaoCarona> passageirosLivres = this.intencaoServico.findAllPassageirosNotInSugestaoAtiva();

				for (IntencaoCarona motorista : motoristasLivres) {
					
					
					
				}
				
			} catch (InterruptedException e) {
				LOGGER.error("Erro esperando por novo evento", e);
			}

		}

	}

}
