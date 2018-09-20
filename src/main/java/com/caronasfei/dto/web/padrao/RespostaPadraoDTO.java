package com.caronasfei.dto.web.padrao;

public class RespostaPadraoDTO<T> {

	private String proximaUrl;
	private String mensagem;
	private boolean sucesso;
	private T dado;

	public String getProximaUrl() {
		return proximaUrl;
	}

	public void setProximaUrl(String proximaUrl) {
		this.proximaUrl = proximaUrl;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public boolean isSucesso() {
		return sucesso;
	}

	public void setSucesso(boolean sucesso) {
		this.sucesso = sucesso;
	}

	public T getDado() {
		return dado;
	}

	public void setDado(T dado) {
		this.dado = dado;
	}

}
