package com.caronasfei.web.controller.intencao.jsp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.caronasfei.service.intencao.IntencaoCaronaServico;
import com.caronasfei.sessao.SessaoWeb;

@Controller
public class IntencaoCaronaJSPController {

	@Autowired
	private SessaoWeb sessao;

	@Autowired
	private IntencaoCaronaServico intencaoServico;

	@GetMapping("/cadastrointencao")
	public String cadastrarIntencao() {

		if (this.sessao.getUsuario() == null) {
			return "redirect:login";
		}

		if (!this.sessao.getUsuario().isCadastrado()) {
			// redireciona pra onde ta faltando do cadastro
		}
		
		if (this.intencaoServico.temIntencaoCadastrada(this.sessao.getUsuario())) {
			return "redirect:esperasugestao";
		}

		return "/intencao/cadastrar";

	}

}
