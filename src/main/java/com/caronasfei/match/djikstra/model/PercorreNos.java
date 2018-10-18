package com.caronasfei.match.djikstra.model;

import com.caronasfei.db.intencao.endereco.Endereco;
import com.caronasfei.match.djikstra.No;
import com.caronasfei.match.djikstra.Vertice;

public class PercorreNos {

	public static void preencheScoreCaminho(No no, int score, Endereco destino) {

		if (no != null && !no.getEndereco().equals(destino)) {

			no.setCaminhoScore(score);
			Vertice verticeSelecionado = no.getVerticeIncidente();

			if (verticeSelecionado != null && verticeSelecionado.getNoOrigem() != null) {

				PercorreNos.preencheScoreCaminho(verticeSelecionado.getNoOrigem(), score, destino);

			}

		}

	}

	public static void destacaMotorista(No no) {

		No ultimoNo = null;
		while (no.getVerticeIncidente() != null) {
			ultimoNo = no;
			no.setCaminhoScore(null);
			no = no.getVerticeIncidente().getNoOrigem();
		}

		ultimoNo.setVerticeIncidente(null);

	}

	public static boolean isMelhorCaminhoScore(No no, int score) {

		No noAtual = no;
		boolean caminhoMelhorScore = true;
		do {
			if (noAtual.getCaminhoScore() != null && noAtual.getCaminhoScore() < score) {
				caminhoMelhorScore = false;
				break;
			}

			if (noAtual.getVerticeIncidente() != null) {
				noAtual = noAtual.getVerticeIncidente().getNoOrigem();
			}
		} while (noAtual.getVerticeIncidente() != null);

		return caminhoMelhorScore;

	}
	
	public static void destravaDependencias(No no) {

		// percorre os nós passageiros e destaca os motoristas que possuem passageiro 
		// em comum mas com score menor que o score encontrado
		
		// este método considera que a checagem de score do caminho foi feito antes.
		// quando chamado ele executa o destaque de todos os motoristas que possuem nós
		// passageiros em comum a este caminho.
		
		No noAtual = no;
		do {
			if (noAtual.getCaminhoScore() != null) {
				PercorreNos.destacaMotorista(noAtual);
			}

			if (noAtual.getVerticeIncidente() != null) {
				noAtual = noAtual.getVerticeIncidente().getNoOrigem();
			}
		} while (noAtual.getVerticeIncidente() != null);

	}


}
