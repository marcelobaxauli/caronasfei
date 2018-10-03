package com.caronasfei.web.controller.intencao.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.caronasfei.assembler.intencao.IntencaoAssembler;
import com.caronasfei.db.intencao.IntencaoCarona;
import com.caronasfei.db.usuario.Usuario;
import com.caronasfei.dto.intencao.IntencaoCaronaDTO;
import com.caronasfei.dto.intencao.NovaIntencaoCaronaDTO;
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
	public RespostaPadraoDTO<NovaIntencaoCaronaDTO> getIntencaoCarona() {

		Usuario usuario = this.sessaoWeb.getUsuario();

		IntencaoCarona intencaoCarona = this.intencaoService.findByUsuario(usuario);

		if (intencaoCarona == null) {
			// fzr alguma coisa aqui
			// redirecionar?
		}

		NovaIntencaoCaronaDTO intencaoCaronaDTO = this.intencaoAssembler.toIntencaoCaronaDTO(intencaoCarona);

		RespostaPadraoDTO<NovaIntencaoCaronaDTO> resposta = new RespostaPadraoDTO<NovaIntencaoCaronaDTO>();
		resposta.setSucesso(true);
		resposta.setDado(intencaoCaronaDTO);

		return resposta;

	}

	@PostMapping("/intencaocarona")
	@ResponseBody
	public RespostaPadraoDTO cadastrarIntencao(@RequestBody NovaIntencaoCaronaDTO intencaoDTO) {

		RespostaPadraoDTO resposta = new RespostaPadraoDTO();

		try {

			this.intencaoService.cadastrarIntencao(intencaoDTO, this.sessaoWeb.getUsuario());

			resposta.setSucesso(true);
			resposta.setProximaUrl(PagesUrl.AGUARDA_SUGESTAO.getUrl());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return resposta;

	}
	
	@DeleteMapping("/intencaocarona")
	@ResponseBody
	public RespostaPadraoDTO deletarIntencao(@RequestBody IntencaoCaronaDTO intencaoCaronaDTO) {
		
		RespostaPadraoDTO resposta = new RespostaPadraoDTO();
		
		resposta.setSucesso(true);
		resposta.setProximaUrl(PagesUrl.HOME.getUrl());
		try {
			
			this.intencaoService.deletarIntencaoCarona(intencaoCaronaDTO);
			
		} catch (Exception e) {
			resposta.setSucesso(false);
			e.printStackTrace();
		}
		
		return resposta;
		
	}

}
