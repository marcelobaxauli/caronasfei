package com.caronasfei.web.controller.intencao.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.caronasfei.assembler.exception.ValidacaoException;
import com.caronasfei.assembler.intencao.IntencaoAssembler;
import com.caronasfei.db.intencao.IntencaoCarona;
import com.caronasfei.db.usuario.Usuario;
import com.caronasfei.dto.intencao.IntencaoCaronaDTO;
import com.caronasfei.dto.web.padrao.RespostaPadraoDTO;
import com.caronasfei.service.intencao.IntencaoCaronaServico;
import com.caronasfei.sessao.SessaoWeb;
import com.caronasfei.web.url.PagesUrl;

@Controller
public class IntencaoCaronaAPIController {

	@Autowired
	private SessaoWeb sessaoWeb;

	@Autowired
	private IntencaoAssembler intencaoAssembler;

	@Autowired
	private IntencaoCaronaServico intencaoService;

	@GetMapping("/intencaocarona")
	@ResponseBody
	public RespostaPadraoDTO<IntencaoCaronaDTO> getIntencaoCarona() {

		Usuario usuario = this.sessaoWeb.getUsuario();

		IntencaoCarona intencaoCarona = this.intencaoService.findByUsuario(usuario);

		if (intencaoCarona == null) {
			// fzr alguma coisa aqui
			// redirecionar?
		}

		IntencaoCaronaDTO intencaoCaronaDTO = this.intencaoAssembler.toIntencaoCaronaDTO(intencaoCarona);

		RespostaPadraoDTO<IntencaoCaronaDTO> resposta = new RespostaPadraoDTO<IntencaoCaronaDTO>();
		resposta.setSucesso(true);
		resposta.setDado(intencaoCaronaDTO);

		return resposta;

	}

	@PostMapping("/intencaocarona")
	@ResponseBody
	public RespostaPadraoDTO cadastrarIntencao(@RequestBody IntencaoCaronaDTO intencaoDTO) {

		RespostaPadraoDTO resposta = new RespostaPadraoDTO();

		try {
			IntencaoCarona intencao = this.intencaoAssembler.toIntencao(intencaoDTO, this.sessaoWeb.getUsuario());

			this.intencaoService.cadastrarIntencao(intencao);

			resposta.setSucesso(true);
			resposta.setProximaUrl(PagesUrl.AGUARDA_SUGESTAO.getUrl());

		} catch (ValidacaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return resposta;

	}

}
