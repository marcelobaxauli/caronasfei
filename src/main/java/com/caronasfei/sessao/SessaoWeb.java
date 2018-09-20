package com.caronasfei.sessao;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import com.caronasfei.db.usuario.Usuario;

@Component
@SessionScope
public class SessaoWeb {

	private Usuario usuario;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
