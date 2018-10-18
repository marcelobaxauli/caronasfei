package com.caronasfei.match.djikstra.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.caronasfei.match.djikstra.No;

public class SolucaoParcialDebug {

	private static final SimpleDateFormat sdf = new SimpleDateFormat("kk:mm:ss");
	
	public static void print(No noCaminho, No noCandidato, int numeroPassageiros, 
			double custoTransito, long horarioEstimadoNoCandidato, int noCandidatoScore, boolean solucaoParcial) {
		
		SolucaoParcialDebug.printDoInicio(noCaminho, horarioEstimadoNoCandidato);
		
		String bairroNodo = noCandidato.getEndereco().getBairro();
		String cidadeNodo = noCandidato.getEndereco().getCidade();
		
		Date horarioMin = new Date(noCandidato.getRestricaoTempo().getHorarioMinimo());
		Date horarioMax = new Date(noCandidato.getRestricaoTempo().getHorarioMaximo());
		
		System.out.printf(" %s(%s) [%s,%s]", bairroNodo, cidadeNodo, sdf.format(horarioMin), sdf.format(horarioMax));				
		
		System.out.printf(" %d passageiros", numeroPassageiros);		
		
		System.out.printf(" %.2f transito", custoTransito / 1000 / 60);
		
		System.out.printf(" %d horario estimado", horarioEstimadoNoCandidato / 1000 / 60);

		System.out.printf(" -> %d score ", noCandidatoScore);
		
		if (solucaoParcial) {
			System.out.print("*");
		}
		
		System.out.println();
		
	}
	
	public static void printDoInicio(No no, long horarioIncidente) {
		
		if (no != null) {

			if (no.getNoAnterior() != null) {
				SolucaoParcialDebug.printDoInicio(no.getNoAnterior(), no.getHorarioEstimado());				
			}
			
			String bairroOrigem = no.getEndereco().getBairro();
			String cidadeOrigem = no.getEndereco().getCidade();
			
			Date horarioMin = new Date(no.getRestricaoTempo().getHorarioMinimo());
			Date horarioMax = new Date(no.getRestricaoTempo().getHorarioMaximo());
			
			long horarioEstimado = no.getHorarioEstimado();
			
			long custoTransito = 0;
			if (no.getVerticeIncidente() != null) {				
				custoTransito = no.getVerticeIncidente().getCustoTransito();
			}
			
			Date horarioProjetado = new Date(horarioEstimado + custoTransito);
			
			System.out.printf("%s(%s) [%s,%s] (%s)->", bairroOrigem, cidadeOrigem, sdf.format(horarioMin), sdf.format(horarioMax), sdf.format(horarioIncidente));				
			
		}
		
	}
	
}
