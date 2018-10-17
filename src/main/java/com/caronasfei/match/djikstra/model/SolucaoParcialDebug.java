package com.caronasfei.match.djikstra.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.caronasfei.match.djikstra.No;

public class SolucaoParcialDebug {

	private static final SimpleDateFormat sdf = new SimpleDateFormat("kk:mm:ss");
	
	public static void print(No noCaminho, No noCandidato, int numeroPassageiros, 
			double custoTransitoMinutos, long custoEstimadoNoCandidato, int noCandidatoScore, boolean solucaoParcial) {
		
		SolucaoParcialDebug.printDoInicio(noCaminho);
		
		String bairroNodo = noCandidato.getEndereco().getBairro();
		String cidadeNodo = noCandidato.getEndereco().getCidade();

		Date horarioMin = noCandidato.getIntencaoCarona().getHorarioPartida().getHorario();
		Date horarioMax = noCandidato.getIntencaoCarona().getHorarioChegada().getHorario();
		
		System.out.printf(" %s(%s) [%s,%s]", bairroNodo, cidadeNodo, sdf.format(horarioMin), sdf.format(horarioMax));				
		
		System.out.printf(" %d passageiros", numeroPassageiros);		
		
		System.out.printf(" %.2f transito", custoTransitoMinutos / 1000 / 60);
		
		System.out.printf(" %d acumulado", custoEstimadoNoCandidato / 1000 / 60);

		System.out.printf(" -> %d score ", noCandidatoScore);
		
		if (solucaoParcial) {
			System.out.print("*");
		}
		
		System.out.println();
		
	}
	
	public static void printDoInicio(No no) {
		
		if (no != null) {

			if (no.getNoAnterior() != null) {
				SolucaoParcialDebug.printDoInicio(no.getNoAnterior());				
			}
			
			String bairroOrigem = no.getEndereco().getBairro();
			String cidadeOrigem = no.getEndereco().getCidade();
			
			Date horarioMin = no.getIntencaoCarona().getHorarioPartida().getHorario();
			Date horarioMax = no.getIntencaoCarona().getHorarioChegada().getHorario();
			
			long horarioEstimado = no.getHorarioEstimado();
			
			long custoTransito = 0;
			if (no.getVerticeSelecionado() != null) {				
				custoTransito = no.getVerticeSelecionado().getCustoTransito();
			}
			
			Date horarioProjetado = new Date(horarioEstimado + custoTransito);
			
			System.out.printf("%s(%s) [%s,%s] (%s)->", bairroOrigem, cidadeOrigem, sdf.format(horarioMin), sdf.format(horarioMax), sdf.format(horarioProjetado));				
			
		}
		
	}
	
}
