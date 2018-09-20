package com.caronasfei.web.controller.registrar.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.caronasfei.db.curso.Curso;
import com.caronasfei.db.curso.CursoPeriodo;
import com.caronasfei.db.periodo.Periodo;
import com.caronasfei.db.usuario.Usuario;
import com.caronasfei.dto.autenticacaofei.AutenticacaoFeiEntradaDTO;
import com.caronasfei.dto.novousuario.NovoUsuarioEntradaDTO;
import com.caronasfei.dto.web.padrao.RespostaPadraoDTO;
import com.caronasfei.service.CursoPeriodoService;
import com.caronasfei.service.curso.CursoServico;
import com.caronasfei.service.periodo.PeriodoServico;
import com.caronasfei.service.usuario.UsuarioServico;
import com.caronasfei.sessao.SessaoWeb;
import com.caronasfei.web.url.PagesUrl;

@Controller
// @RequestMapping("/registrar")
public class RegistroAPIController {

	@Autowired
	private UsuarioServico userServico;

	@Autowired
	private CursoServico cursoServico;

	@Autowired
	private PeriodoServico periodoService;

	@Autowired
	private CursoPeriodoService cursoPeriodoService;

	@Autowired
	private SessaoWeb sessaoWeb;

	// MEAPEAMENTO DE API:

	@PostMapping("/registro/dadospessoais")
	@ResponseBody
	public RespostaPadraoDTO registrarUsuario(@RequestBody NovoUsuarioEntradaDTO requisicao) {

		RespostaPadraoDTO resposta = new RespostaPadraoDTO();

		try {
			Curso curso = this.cursoServico.findByCodigo(requisicao.getCurso());
			Periodo periodo = this.periodoService.findByCodigo(requisicao.getPeriodo());

			CursoPeriodo cursoPeriodo = this.cursoPeriodoService.findByCursoAndPeriodo(curso, periodo);

			Usuario usuario = new Usuario();
			usuario.setId(null);
			usuario.setNome(requisicao.getNome());
			usuario.setCursoPeriodo(cursoPeriodo);
			usuario.setAutenticadoFei(false);
			usuario.setFoto(requisicao.getFoto());
			usuario.setEmail(requisicao.getEmail());
			usuario.setSenha(requisicao.getSenha());
			usuario.setNumeroCelular(requisicao.getCelular());
			usuario.setCidade(requisicao.getCidade());

			this.userServico.criarUsuario(usuario);
			this.sessaoWeb.setUsuario(usuario);

			resposta.setSucesso(true);
			resposta.setMensagem(null);
			resposta.setProximaUrl(PagesUrl.AUTENTICACAO_FEI.getUrl());

		} catch (Exception e) {

			resposta.setSucesso(false);
			resposta.setMensagem(e.getMessage());
			resposta.setProximaUrl(null);

			e.printStackTrace();

		}

		return resposta;
	}

	@PostMapping("/registro/autenticacaofei")
	@ResponseBody
	public RespostaPadraoDTO autenticarFei(@RequestBody AutenticacaoFeiEntradaDTO requisicao) {

		String usuarioFei = requisicao.getUsuario();
		String senhaFei = requisicao.getSenha();

		// verifica autenticacao junto ao site da fei...

		boolean autenticacaoValida = true;

		if (autenticacaoValida) {
			Usuario usuario = this.sessaoWeb.getUsuario();
			Usuario usuarioAtualizado = this.userServico.atualizaComoAutenticadoFei(usuario);	
			this.sessaoWeb.setUsuario(usuarioAtualizado);
		}

		RespostaPadraoDTO resposta = new RespostaPadraoDTO();
		resposta.setSucesso(autenticacaoValida);
		if (!autenticacaoValida) {
			resposta.setMensagem("Credenciais não são válidas.");
		} else {
			resposta.setProximaUrl(PagesUrl.HOME.getUrl());
		}

		return resposta;
	}

}
