package com.caronasfei.web.controller.avaliasugestao.jsp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.caronasfei.db.intencao.IntencaoCarona;
import com.caronasfei.db.intencao.IntencaoCarona.IntencaoCaronaEstado;
import com.caronasfei.db.sugestao.SugestaoTrajeto;
import com.caronasfei.db.sugestao.SugestaoTrajetoPassageiro;
import com.caronasfei.db.usuario.Usuario;
import com.caronasfei.service.intencao.IntencaoCaronaServico;
import com.caronasfei.service.sugestao.SugestaoTrajetoServico;
import com.caronasfei.sessao.SessaoWeb;

@Controller
public class AvaliaSugestaoTrajetoJSPController {

	@Autowired
	private SessaoWeb sessaoWeb;
	
	@Autowired
	private IntencaoCaronaServico intencaoCaronaServico;
	
	@Autowired
	private SugestaoTrajetoServico sugestaoTrajetoServico;
	
	@GetMapping("/trajeto/avalia")
	public String index() {
			
		// checagem copiada da esperasugestao/ConsultaSugestaoAPIController

		Usuario usuarioLogado = sessaoWeb.getUsuario();

		if (usuarioLogado == null) {			
			return "redirect:login";			
		}
		
		IntencaoCarona intencaoCarona = this.intencaoCaronaServico.findByUsuario(usuarioLogado);
		
		if (intencaoCarona == null || intencaoCarona.getEstado() != IntencaoCaronaEstado.ATIVA) {
			return "redirect:cadastrointencao";
		}
		
		if (intencaoCarona.getAcaoCarona() == IntencaoCarona.AcaoCarona.OFERECER_CARONA) {
			SugestaoTrajeto sugestaoTrajeto = this.sugestaoTrajetoServico.findSugestaoTrajetoByIntencaoCaronaMotorista(intencaoCarona);
			
			if (sugestaoTrajeto == null) {
				return "redirect:esperasugestao";
			}
			
			return "avaliasugestao/index_motorista";
		} else if (intencaoCarona.getAcaoCarona() == IntencaoCarona.AcaoCarona.PEDIR_CARONA) {
			SugestaoTrajeto sugestaoTrajeto = this.sugestaoTrajetoServico.findSugestaoTrajetoByIntencaoCaronaPassageiro(intencaoCarona);
			
			if (sugestaoTrajeto == null) {
				return "redirect:esperasugestao";
			}
			
			SugestaoTrajetoPassageiro passageiro = this.sugestaoTrajetoServico.getPassageiro(intencaoCarona, sugestaoTrajeto);
			
			if (passageiro == null) {
				return "redirect:esperasugestao";				
			}

			return "avaliasugestao/index_passageiro";
		}

		
		return "";
		
	}
	
}
