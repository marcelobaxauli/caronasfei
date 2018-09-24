package com.caronasfei.match.djikstra.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RestricaoTempo {

	private static final Logger LOGGER = LogManager.getLogger(RestricaoTempo.class);
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("kk:mm:ss");
	
	private Date departTime;
	private Date arriveTime;

	public RestricaoTempo(long leaveTime, long arriveTime) {
		super();
		this.departTime = new Date(leaveTime);
		this.arriveTime = new Date(arriveTime);
	}

	public long getDepartTime() {
		return departTime.getTime();
	}

	public void setDepartTime(long departTime) {
		this.departTime = new Date(departTime);
	}

	public long getArriveTime() {
		return arriveTime.getTime();
	}

	public void setArriveTime(long arriveTime) {
		this.arriveTime = new Date(arriveTime);
	}

	public boolean isInTimeRestriction(Date visitTime, Date maximumTime) {
		return  visitTime.getTime() >= this.departTime.getTime() && this.arriveTime.getTime() >= maximumTime.getTime() 
				&& this.departTime.getTime() < maximumTime.getTime();
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

	@Override
	public String toString() {

		return String.format("leave = %s, arrive = %s", this.departTime, this.arriveTime);
	}

}
