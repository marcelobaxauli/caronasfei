package com.caronasfei.dto.autenticacaofei;

public class AutenticacaoFeiSaidaDTO {

	private boolean autenticacaoValida;
	private String proximaUrl;

	public boolean isAutenticacaoValida() {
		return autenticacaoValida;
	}

	public void setAutenticacaoValida(boolean autenticacaoValida) {
		this.autenticacaoValida = autenticacaoValida;
	}

	public String getProximaUrl() {
		return proximaUrl;
	}

	public void setProximaUrl(String proximaUrl) {
		this.proximaUrl = proximaUrl;
	}

}
