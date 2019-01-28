package com.caronasfei.dto.consultasugestao;

import com.caronasfei.dto.intencao.endereco.EnderecoDTO;

public class MotoristaSaidaDTO {

	// id da tabela SugestaoTrajetoUsuario. Não da do usuario em si.
	private long id;
	private String nome;
	// foto?
	private String curso;
	private String periodo;
	private String estado;
	private String horarioPartida;
	private String horarioChegada;
	private EnderecoDTO enderecoPartida;
	private EnderecoDTO enderecoDestino;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	// horario de saida?
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCurso() {
		return curso;
	}

	public void setCurso(String curso) {
		this.curso = curso;
	}

	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

	public EnderecoDTO getEnderecoPartida() {
		return enderecoPartida;
	}

	public void setEnderecoPartida(EnderecoDTO enderecoPartida) {
		this.enderecoPartida = enderecoPartida;
	}

	public EnderecoDTO getEnderecoDestino() {
		return enderecoDestino;
	}

	public void setEnderecoDestino(EnderecoDTO enderecoDestino) {
		this.enderecoDestino = enderecoDestino;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
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
