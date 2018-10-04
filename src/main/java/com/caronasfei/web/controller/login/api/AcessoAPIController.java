package com.caronasfei.web.controller.login.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.caronasfei.db.usuario.Usuario;
import com.caronasfei.dto.login.LoginEntradaDTO;
import com.caronasfei.dto.web.padrao.RespostaPadraoDTO;
import com.caronasfei.service.usuario.UsuarioServico;
import com.caronasfei.sessao.SessaoWeb;
import com.caronasfei.web.url.PagesUrl;

@Controller
public class AcessoAPIController {

	@Autowired
	private UsuarioServico usuarioServico;

	@Autowired
	private SessaoWeb sessao;

	@PostMapping("/login")
	@ResponseBody
	public RespostaPadraoDTO login(@RequestBody LoginEntradaDTO entrada) {

		Usuario usuario = this.usuarioServico.login(entrada.getEmail(), entrada.getSenha());

		RespostaPadraoDTO resposta = new RespostaPadraoDTO();

		if (usuario == null) {
			resposta.setSucesso(false);
		} else {
			resposta.setProximaUrl(PagesUrl.HOME.getUrl());
			this.sessao.setUsuario(usuario);
			resposta.setSucesso(true);
		}

		return resposta;
	}
	
	@PostMapping("/logoff")
	@ResponseBody
	public RespostaPadraoDTO logoff() {
		
		RespostaPadraoDTO resposta = new RespostaPadraoDTO();

		this.sessao.setUsuario(null);
		resposta.setSucesso(true);
		resposta.setProximaUrl(PagesUrl.HOME.getUrl());
		
		return resposta;
		
	}

}
