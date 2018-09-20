package com.caronasfei.web.controller.registrar.jsp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import com.caronasfei.service.curso.CursoServico;
import com.caronasfei.service.periodo.PeriodoServico;
import com.caronasfei.sessao.SessaoWeb;

@Controller
// @RequestMapping("/registrar")
public class RegistroJSPController {

	@Autowired
	private CursoServico cursoServico;

	@Autowired
	private PeriodoServico periodoService;

	@Autowired
	private SessaoWeb sessaoWeb;

	// MAPEAMENTO DE PAGINA JSP:

	@GetMapping("/registro")
	public String index() {

		return "/registrar/index";
	}

	@GetMapping("/registro/dadospessoais")
	public String primeiroPasso(ModelMap model) {

		return "/registrar/dadospessoais/index";
	}

	@GetMapping("/registro/autenticacaofei")
	public String autenticacaoFei(ModelMap model) {

		return "/registrar/autenticacaofei/index";
	}

	@GetMapping("/registro/perfil")
	public String perfil(ModelMap model) {

		return "/registrar/perfil/index";
	}

}
