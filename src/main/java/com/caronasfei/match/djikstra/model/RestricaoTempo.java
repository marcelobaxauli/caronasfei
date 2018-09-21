package com.caronasfei.match.djikstra.model;

import java.util.Date;

public class RestricaoTempo {

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

	@Override
	public String toString() {

		return String.format("leave = %s, arrive = %s", this.departTime, this.arriveTime);
	}

}
