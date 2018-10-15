package com.caronasfei.match.djikstra.model;

import com.caronasfei.db.intencao.endereco.Endereco;
import com.caronasfei.match.djikstra.No;
import com.caronasfei.match.djikstra.Vertice;

public class PercorreNos {

	public static void preencheScoreCaminho(No no, int score, Endereco destino) {

		if (no != null && !no.getEndereco().equals(destino)) {

			no.setCaminhoScore(score);
			Vertice verticeSelecionado = no.getVerticeSelecionado();

			if (verticeSelecionado != null && verticeSelecionado.getNoDestino() != null) {

				PercorreNos.preencheScoreCaminho(verticeSelecionado.getNoDestino(), score, destino);

			}

		}

	}

	public static void destacaMotorista(No no) {

		No ultimoNo = null;
		while (no.getVerticeSelecionado() != null) {
			ultimoNo = no;
			no.setCaminhoScore(null);
			no = no.getVerticeSelecionado().getNoDestino();
		}

		ultimoNo.setVerticeSelecionado(null);

	}

	public static boolean isMelhorCaminhoScore(No no, int score) {

		No noAtual = no;
		boolean caminhoMelhorScore = true;
		do {
			if (noAtual.getCaminhoScore() != null && noAtual.getCaminhoScore() < score) {
				caminhoMelhorScore = false;
				break;
			}

			if (noAtual.getVerticeSelecionado() != null) {
				noAtual = noAtual.getVerticeSelecionado().getNoDestino();
			}
		} while (noAtual.getVerticeSelecionado() != null);

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

			if (noAtual.getVerticeSelecionado() != null) {
				noAtual = noAtual.getVerticeSelecionado().getNoDestino();
			}
		} while (noAtual.getVerticeSelecionado() != null);

	}


}
