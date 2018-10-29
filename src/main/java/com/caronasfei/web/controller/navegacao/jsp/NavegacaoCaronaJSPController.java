package com.caronasfei.web.controller.navegacao.jsp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.caronasfei.db.intencao.IntencaoCarona;
import com.caronasfei.db.intencao.IntencaoCarona.AcaoCarona;
import com.caronasfei.db.intencao.IntencaoCarona.IntencaoCaronaEstado;
import com.caronasfei.db.sugestao.SugestaoTrajeto;
import com.caronasfei.db.sugestao.SugestaoTrajetoMotorista;
import com.caronasfei.db.sugestao.SugestaoTrajetoMotorista.SugestaoTrajetoMotoristaEstado;
import com.caronasfei.db.sugestao.SugestaoTrajetoPassageiro;
import com.caronasfei.db.usuario.Usuario;
import com.caronasfei.service.intencao.IntencaoCaronaServico;
import com.caronasfei.service.sugestao.SugestaoTrajetoServico;
import com.caronasfei.sessao.SessaoWeb;

@Controller
public class NavegacaoCaronaJSPController {

	@Autowired
	private SessaoWeb sessaoWeb;
	
	@Autowired
	private IntencaoCaronaServico intencaoCaronaServico;
	
	@Autowired
	private SugestaoTrajetoServico sugestaoTrajetoServico;
	
	@GetMapping("/trajeto/navegacao")
	public String navegacao() {
		
		Usuario usuarioLogado = sessaoWeb.getUsuario();

		if (usuarioLogado == null) {			
			return "redirect:login";			
		}
		
		IntencaoCarona intencaoCarona = this.intencaoCaronaServico.findByUsuario(usuarioLogado);
		
		if (intencaoCarona == null || intencaoCarona.getEstado() != IntencaoCaronaEstado.ATIVA) {
			return "redirect:cadastrointencao";
		}

		SugestaoTrajeto sugestaoTrajeto = this.sugestaoTrajetoServico.findSugestaoTrajetoByIntencaoCaronaMotorista(intencaoCarona);
		
		if (sugestaoTrajeto == null) {
			return "redirect:esperasugestao";
			
		}

		if (intencaoCarona.getAcaoCarona() == AcaoCarona.OFERECER_CARONA) {
			return "/navegacao/index_motorista";			
		} else if (intencaoCarona.getAcaoCarona() == AcaoCarona.PEDIR_CARONA) {
			return "/navegacao/index_passageiro";			
		} else {
			throw new IllegalStateException("Acao de carona nao mapeada: " + intencaoCarona.getAcaoCarona());
		}
		
		
	}
	
}
