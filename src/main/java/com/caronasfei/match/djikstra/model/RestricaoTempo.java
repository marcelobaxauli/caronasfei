package com.caronasfei.match.djikstra.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.caronasfei.db.intencao.horario.Horario;
import com.caronasfei.match.djikstra.No;
import com.caronasfei.match.djikstra.Vertice;

public class RestricaoTempo {

	private static final Logger LOGGER = LogManager.getLogger(RestricaoTempo.class);
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("kk:mm:ss");
	
	private Date horarioMinimo;
	private Date horarioMaximo;

	public RestricaoTempo(long leaveTime, long arriveTime) {
		super();
		this.horarioMinimo = new Date(leaveTime);
		this.horarioMaximo = new Date(arriveTime);
	}

	public long getHorarioMinimo() {
		return horarioMinimo.getTime();
	}

	public void setHorarioMinimo(long departTime) {
		this.horarioMinimo = new Date(departTime);
	}

	public long getHorarioMaximo() {
		return horarioMaximo.getTime();
	}

	public void setHorarioMaximo(long horarioMaximo) {
		this.horarioMaximo = new Date(horarioMaximo);
	}

	public boolean isInTimeRestriction(Date visitTime, Date maximumTime) {
		return  visitTime.getTime() >= this.horarioMinimo.getTime() && this.horarioMaximo.getTime() >= maximumTime.getTime() 
				&& this.horarioMinimo.getTime() < maximumTime.getTime();
	}
	
	public static RestricaoTempo converte(Date inicio, Date fim) {
		// retorna a restricao de tempo da intenção de carona informada
		
		Date limiteBase = null;
		Date limiteTopo = null;
		try {
			// primeiro minuto do dia		
			limiteBase = RestricaoTempo.sdf.parse("00:01:00");
			// ultimo minuto do dia
			limiteTopo = RestricaoTempo.sdf.parse("23:59:00");
		} catch (ParseException e) {
			LOGGER.error("Erro criando limite base de horario", e);
		}
		
		if (inicio != null) {
			limiteBase = inicio; 
		}
		
		if (fim != null) {
			limiteTopo = fim;
		}
		
		return new RestricaoTempo(limiteBase.getTime(), limiteTopo.getTime());
		
	}
	
	public static boolean isCaminhoValido(long horarioEstimadoNoCandidato, No noCandidato) {
		
		RestricaoTempo restricaoTempo = noCandidato.getRestricaoTempo();

		long horarioMinimoCandidato = restricaoTempo.getHorarioMinimo();
		long horarioMaximoCandidato = restricaoTempo.getHorarioMaximo();

		return horarioEstimadoNoCandidato >= horarioMinimoCandidato && horarioEstimadoNoCandidato <= horarioMaximoCandidato;

	}

	@Override
	public String toString() {

		return String.format("leave = %s, arrive = %s", this.horarioMinimo, this.horarioMaximo);
	}

}
