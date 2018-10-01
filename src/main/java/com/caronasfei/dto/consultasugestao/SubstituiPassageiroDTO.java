package com.caronasfei.dto.consultasugestao;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SubstituiPassageiroDTO {

	@JsonProperty("id_sugestao")
	private int idSugestaoCarona;

	@JsonProperty("id_sugestao_motorista")
	private int idSugestaoCaronaMotorista;

	@JsonProperty("id_sugestao_passageiro")
	private int idSugestaoCaronaPassageiro;

	public int getIdSugestaoCarona() {
		return idSugestaoCarona;
	}

	public void setIdSugestaoCarona(int idSugestaoCarona) {
		this.idSugestaoCarona = idSugestaoCarona;
	}

	public int getIdSugestaoCaronaMotorista() {
		return idSugestaoCaronaMotorista;
	}

	public void setIdSugestaoCaronaMotorista(int idSugestaoCaronaMotorista) {
		this.idSugestaoCaronaMotorista = idSugestaoCaronaMotorista;
	}

	public int getIdSugestaoCaronaPassageiro() {
		return idSugestaoCaronaPassageiro;
	}

	public void setIdSugestaoCaronaPassageiro(int idSugestaoCaronaPassageiro) {
		this.idSugestaoCaronaPassageiro = idSugestaoCaronaPassageiro;
	}

}
