package com.caronasfei.web.controller.home.jsp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import com.caronasfei.db.cadastro.CadastroEtapa.CadastroEtapaCodigo;
import com.caronasfei.sessao.SessaoWeb;

@Controller
public class HomeJSPController {

	@Autowired
	private SessaoWeb sessao;

	@GetMapping("/")
	public String home(ModelMap model) {

		if (this.sessao != null && this.sessao.getUsuario() != null && this.sessao.getUsuario().getCadastro() != null
				&& this.sessao.getUsuario().getCadastro().getCadastroEtapa().getCodigo()
						.equals(CadastroEtapaCodigo.COMPLETO.getCodigo())) {

			model.addAttribute("usuario_nome", this.sessao.getUsuario().getNome());

			return "/home/home_autenticado";

		}

		return "/home/home_naoautenticado";

	}

}
