package com.caronasfei.dto.consultasugestao;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ConfirmaTrajetoPassageiroDTO {

	@JsonProperty("id_trajeto")
	private int trajetoId;

	@JsonProperty("id_passageiro")
	private int passageiroId;

	public int getTrajetoId() {
		return trajetoId;
	}

	public void setTrajetoId(int trajetoId) {
		this.trajetoId = trajetoId;
	}

	public int getPassageiroId() {
		return passageiroId;
	}

	public void setPassageiroId(int passageiroId) {
		this.passageiroId = passageiroId;
	}

}
