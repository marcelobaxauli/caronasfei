package com.caronasfei.dto.intencao;

import com.caronasfei.dto.intencao.endereco.EnderecoDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

public class IntencaoCaronaDTO {

	@JsonProperty("acao")
	private String acao;

	@JsonProperty("opc_local_partida")
	private String opcaoLocalPartida;

	@JsonProperty("opc_local_destino")
	private String opcaoLocalDestino;

	@JsonProperty("local")
	private EnderecoDTO endereco;

	@JsonProperty("opc_horario_partida")
	private String opcaoHorarioPartida;

	@JsonProperty("opc_horario_chegada")
	private String opcaoHorarioChegada;

	@JsonProperty("horario_partida")
	private String horarioPartida;

	@JsonProperty("horario_chegada")
	private String horarioChegada;

	public String getAcao() {
		return acao;
	}

	public void setAcao(String acao) {
		this.acao = acao;
	}

	public String getOpcaoLocalPartida() {
		return opcaoLocalPartida;
	}

	public void setOpcaoLocalPartida(String opcaoLocalPartida) {
		this.opcaoLocalPartida = opcaoLocalPartida;
	}

	public String getOpcaoLocalDestino() {
		return opcaoLocalDestino;
	}

	public void setOpcaoLocalDestino(String opcaoLocalDestino) {
		this.opcaoLocalDestino = opcaoLocalDestino;
	}

	public EnderecoDTO getEndereco() {
		return endereco;
	}

	public void setEndereco(EnderecoDTO endereco) {
		this.endereco = endereco;
	}

	public String getOpcaoHorarioPartida() {
		return opcaoHorarioPartida;
	}

	public void setOpcaoHorarioPartida(String opcaoHorarioPartida) {
		this.opcaoHorarioPartida = opcaoHorarioPartida;
	}

	public String getOpcaoHorarioChegada() {
		return opcaoHorarioChegada;
	}

	public void setOpcaoHorarioChegada(String opcaoHorarioChegada) {
		this.opcaoHorarioChegada = opcaoHorarioChegada;
	}

	public String getHorarioPartida() {
		return horarioPartida;
	}

	public void setHorarioPartida(String horarioPartida) {
		this.horarioPartida = horarioPartida;
	}

	public String getHorarioChegada() {
		return horarioChegada;
	}

	public void setHorarioChegada(String horarioChegada) {
		this.horarioChegada = horarioChegada;
	}

}
