package com.caronasfei.web.url;

public enum PagesUrl {

	PREFIXO("/caronasfei"),
	HOME(PREFIXO.getUrl() + "/"),
	LOGIN(PREFIXO.getUrl() + "/login"),
	DADOS_PESSOAIS(PREFIXO.getUrl() + "/registro/dadospessoais"), 
	AUTENTICACAO_FEI(PREFIXO.getUrl() + "/registro/autenticacaofei"), 
	PERFIL_USUARIO(PREFIXO.getUrl() + "/registro/perfil"),
	AGUARDA_SUGESTAO(PREFIXO.getUrl() + "/esperasugestao");

	private final String url;

	private PagesUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}	
	
}
