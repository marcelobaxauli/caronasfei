package com.caronasfei.web.controller.esperasugestao.jsp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.caronasfei.db.usuario.Usuario;
import com.caronasfei.sessao.SessaoWeb;

@Controller
public class EsperaSugestaoJSPController {

	@Autowired
	private SessaoWeb sessaoWeb;

	@GetMapping("/esperasugestao")
	public String esperaSugestaoPagina() {

		Usuario usuario = sessaoWeb.getUsuario();
		
		if (usuario == null) {
			return "redirect:login";
		}
		
		if (!usuario.isCadastrado()) {
			// retornar para etapa restante no cadastro
		}
		
		return "esperasugestao/index";

	}

}
