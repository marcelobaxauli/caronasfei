package com.caronasfei.dto.consultasugestao;

import com.caronasfei.dto.intencao.endereco.EnderecoDTO;

public class SugestaoTrajetoPassageiroSaidaDTO {

	// id da tabela SugestaoTrajetoPassageiro. Não da do usuario em si.
	private long id;
	private String nome;
	// foto?
	private String curso;
	private String periodo;
	private String estado;
	private EnderecoDTO enderecoPartida;
	private EnderecoDTO enderecoDestino;
	private double distanciaMotorista;
	// horario de saida?
	private int ordemCarona;
	
	public String getNome() {
		return nome;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public double getDistanciaMotorista() {
		return distanciaMotorista;
	}

	public void setDistanciaMotorista(double distanciaMotorista) {
		this.distanciaMotorista = distanciaMotorista;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public int getOrdemCarona() {
		return ordemCarona;
	}

	public void setOrdemCarona(int ordemCarona) {
		this.ordemCarona = ordemCarona;
	}
}
