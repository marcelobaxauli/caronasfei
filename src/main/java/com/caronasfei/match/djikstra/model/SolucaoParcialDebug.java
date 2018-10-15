package com.caronasfei.match.djikstra.model;

import com.caronasfei.match.djikstra.No;

public class SolucaoParcialDebug {

	public static void print(No noCaminho, No noCandidato, int numeroPassageiros, 
			double custoTransitoMinutos, long custoEstimadoNoCandidato, int noCandidatoScore, boolean solucaoParcial) {
		
		SolucaoParcialDebug.printDoInicio(noCaminho);
		
		String bairroNodo = noCandidato.getEndereco().getBairro();
		String cidadeNodo = noCandidato.getEndereco().getCidade();

		System.out.printf(" <- %s(%s)", bairroNodo, cidadeNodo);				
		
		System.out.printf(" %d passageiros", numeroPassageiros);		
		
		System.out.printf(" %.2f transito", custoTransitoMinutos);
		
		System.out.printf(" %d acumulado", custoEstimadoNoCandidato);

		System.out.printf(" -> %d score ", noCandidatoScore);
		
		if (solucaoParcial) {
			System.out.print("*");
		}
		
		System.out.println();
		
	}
	
	public static void printDoInicio(No no) {
		
		if (no != null) {
			if (no.getVerticeSelecionado() != null) {
				SolucaoParcialDebug.printDoInicio(no.getVerticeSelecionado().getNoDestino());				
			}

			String bairroOrigem = no.getEndereco().getBairro();
			String cidadeOrigem = no.getEndereco().getCidade();
			
			if (no.getVerticeSelecionado() == null) {
				System.out.printf(" %s(%s) ", bairroOrigem, cidadeOrigem);				
			} else {
				System.out.printf("<- %s(%s)", bairroOrigem, cidadeOrigem);				
			}
			
			
			
		}
		
	}
	
}
