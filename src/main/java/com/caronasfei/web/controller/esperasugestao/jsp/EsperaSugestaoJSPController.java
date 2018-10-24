package com.caronasfei.web.controller.esperasugestao.jsp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.caronasfei.db.intencao.IntencaoCarona;
import com.caronasfei.db.sugestao.SugestaoTrajeto;
import com.caronasfei.db.usuario.Usuario;
import com.caronasfei.service.intencao.IntencaoCaronaServico;
import com.caronasfei.service.sugestao.SugestaoTrajetoServico;
import com.caronasfei.sessao.SessaoWeb;

@Controller
public class EsperaSugestaoJSPController {

	@Autowired
	private SessaoWeb sessaoWeb;

	@Autowired
	private IntencaoCaronaServico intencaoCaronaService;
	
	@Autowired
	private SugestaoTrajetoServico sugestaoTrajetoServico;
	
	@GetMapping("/esperasugestao")
	public String esperaSugestaoPagina() {

		Usuario usuario = sessaoWeb.getUsuario();
		
		if (usuario == null) {
			return "redirect:login";
		}
		
		if (!usuario.isCadastrado()) {
			// retornar para etapa restante no cadastro
		}

		IntencaoCarona intencaoCarona = this.intencaoCaronaService.findByUsuario(usuario);
		
		if (intencaoCarona == null) {
			return "redirect:/cadastrointencao";
		}

		SugestaoTrajeto sugestaoTrajeto = this.sugestaoTrajetoServico.findByIntencaoCarona(intencaoCarona);
		
		if (sugestaoTrajeto == null) {			
			return "esperasugestao/index";
		}

		return "redirect:/trajeto/avalia";

	}

}
