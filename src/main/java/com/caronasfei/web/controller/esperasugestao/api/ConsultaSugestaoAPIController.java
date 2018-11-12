package com.caronasfei.web.controller.esperasugestao.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.caronasfei.assembler.sugestao.SugestaoTrajetoAssembler;
import com.caronasfei.db.intencao.IntencaoCarona;
import com.caronasfei.db.intencao.IntencaoCarona.IntencaoCaronaEstado;
import com.caronasfei.db.sugestao.SugestaoTrajeto;
import com.caronasfei.db.sugestao.SugestaoTrajetoPassageiro;
import com.caronasfei.db.usuario.Usuario;
import com.caronasfei.dto.consultasugestao.MotoristaSaidaDTO;
import com.caronasfei.dto.consultasugestao.SugestaoTrajetoPassageiroSaidaDTO;
import com.caronasfei.dto.consultasugestao.SugestaoTrajetoDTO;
import com.caronasfei.dto.consultasugestao.SugestaoTrajetoPerfilPassageiroDTO;
import com.caronasfei.dto.web.padrao.RespostaPadraoDTO;
import com.caronasfei.service.intencao.IntencaoCaronaServico;
import com.caronasfei.service.sugestao.SugestaoTrajetoServico;
import com.caronasfei.sessao.SessaoWeb;

@Controller
public class ConsultaSugestaoAPIController {

	@Autowired
	private SessaoWeb sessaoWeb;

	@Autowired
	private IntencaoCaronaServico intencaoCaronaServico;

	@Autowired
	private SugestaoTrajetoServico sugestaoTrajetoServico;

	@Autowired
	private SugestaoTrajetoAssembler sugestaoTrajetoAssembler;

	@GetMapping("/consultasugestaocarona")
	@ResponseBody
	public RespostaPadraoDTO consultaSugestaoCarona() {

		Usuario usuarioLogado = sessaoWeb.getUsuario();

		if (usuarioLogado == null) {
			RespostaPadraoDTO resposta = new RespostaPadraoDTO();
			resposta.setSucesso(false);
			resposta.setMensagem("Não autorizado.");
			
			return resposta;			
		}
		
		IntencaoCarona intencaoCarona = intencaoCaronaServico.findByUsuario(usuarioLogado);
		
		if (intencaoCarona.getEstado() != IntencaoCaronaEstado.ATIVA) {
			// retornar lista vazia?
			RespostaPadraoDTO resposta = new RespostaPadraoDTO();
			resposta.setSucesso(false);
			resposta.setMensagem("Nenhuma intencao de carona ativa encontrada.");
			
			return resposta;
		}
		
		RespostaPadraoDTO resposta = new RespostaPadraoDTO();
		resposta.setSucesso(true);
		if (intencaoCarona.getAcaoCarona() == IntencaoCarona.AcaoCarona.OFERECER_CARONA) {
			SugestaoTrajeto sugestaoTrajeto = this.sugestaoTrajetoServico.findSugestaoTrajetoByIntencaoCaronaMotorista(intencaoCarona);
			
			SugestaoTrajetoDTO perfilMotoristaDTO = this.sugestaoTrajetoAssembler.toDTO(sugestaoTrajeto);
			resposta.setDado(perfilMotoristaDTO);
		} else if (intencaoCarona.getAcaoCarona() == IntencaoCarona.AcaoCarona.PEDIR_CARONA) {
			SugestaoTrajeto sugestaoTrajeto = this.sugestaoTrajetoServico.findSugestaoTrajetoByIntencaoCaronaPassageiro(intencaoCarona);
			
			SugestaoTrajetoPassageiro passageiro = this.sugestaoTrajetoServico.getPassageiro(intencaoCarona, sugestaoTrajeto);
			
			MotoristaSaidaDTO motoristaSaidaDTO = this.sugestaoTrajetoAssembler.toMotoristaSaidaDTO(sugestaoTrajeto.getMotorista());
			SugestaoTrajetoPassageiroSaidaDTO passageiroSaidaDTO = this.sugestaoTrajetoAssembler.toPassageiroSaidaDTO(passageiro);

			SugestaoTrajetoPerfilPassageiroDTO perfilPassageiroDTO = new SugestaoTrajetoPerfilPassageiroDTO();
			perfilPassageiroDTO.setMotoristaDTO(motoristaSaidaDTO);
			perfilPassageiroDTO.setPassageiroDTO(passageiroSaidaDTO);
			resposta.setDado(perfilPassageiroDTO);
		}

		return resposta;

	}

}
