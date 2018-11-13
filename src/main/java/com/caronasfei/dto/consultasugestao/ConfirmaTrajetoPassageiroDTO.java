package com.caronasfei.dto.consultasugestao;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ConfirmaTrajetoPassageiroDTO {

	@JsonProperty("id_sugestao")
	private int sugestaoId;

	@JsonProperty("id_passageiro")
	private int passageiroId;

	public int getSugestaoId() {
		return sugestaoId;
	}

	public void setSugestaoId(int sugestaoId) {
		this.sugestaoId = sugestaoId;
	}

	public int getPassageiroId() {
		return passageiroId;
	}

	public void setPassageiroId(int passageiroId) {
		this.passageiroId = passageiroId;
	}

}
