package com.caronasfei.web.controller.avaliasugestao.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.caronasfei.assembler.sugestao.SugestaoTrajetoAssembler;
import com.caronasfei.db.intencao.IntencaoCarona;
import com.caronasfei.db.intencao.IntencaoCarona.IntencaoCaronaEstado;
import com.caronasfei.db.sugestao.SugestaoTrajeto;
import com.caronasfei.db.sugestao.SugestaoTrajetoMotorista;
import com.caronasfei.db.sugestao.SugestaoTrajetoPassageiro;
import com.caronasfei.db.usuario.Usuario;
import com.caronasfei.dto.consultasugestao.ConfirmaTrajetoPassageiroDTO;
import com.caronasfei.dto.consultasugestao.SubstituiPassageiroTrajetoDTO;
import com.caronasfei.dto.consultasugestao.SugestaoTrajetoDTO;
import com.caronasfei.dto.consultasugestao.VisualizaSugestaoTrajetoDTO;
import com.caronasfei.dto.sugestao.AcaoMotoristaPassageiroDTO;
import com.caronasfei.dto.web.padrao.RespostaPadraoDTO;
import com.caronasfei.service.exception.DomainSecurityException;
import com.caronasfei.service.intencao.IntencaoCaronaServico;
import com.caronasfei.service.sugestao.SugestaoTrajetoServico;
import com.caronasfei.sessao.SessaoWeb;

@Controller
public class SugestaoTrajetoAPIController {

	private static final Logger logger = LogManager.getLogger(SugestaoTrajetoAPIController.class);

	@Autowired
	private SessaoWeb sessaoWeb;

	@Autowired
	private IntencaoCaronaServico intencaoCaronaServico;

	@Autowired
	private SugestaoTrajetoServico sugestaoTrajetoServico;

	@Autowired
	private SugestaoTrajetoAssembler sugestaoTrajetoAssembler;

	@GetMapping("/trajeto/sugestao")
	@ResponseBody
	public RespostaPadraoDTO<SugestaoTrajetoDTO> getSugestoes() {

		RespostaPadraoDTO<SugestaoTrajetoDTO> resposta = new RespostaPadraoDTO<SugestaoTrajetoDTO>();

		Usuario usuario = this.sessaoWeb.getUsuario();
		// ao invés de pegar toda vez pela sessão, usar o id
		// do usuario como entrada da chamada...

		if (usuario == null || !usuario.isCadastrado()) {
			resposta.setSucesso(false);
			resposta.setMensagem("Não autorizado");

			return resposta;
		}

		IntencaoCarona intencaoCarona = this.intencaoCaronaServico.findByUsuario(usuario);

		if (intencaoCarona.getEstado() != IntencaoCaronaEstado.ATIVA) {
			resposta.setSucesso(false);
			resposta.setMensagem("Nenhuma intencao de carona ativa");

			return resposta;
		}

		SugestaoTrajeto sugestaoTrajeto = this.sugestaoTrajetoServico.findByIntencaoCarona(intencaoCarona);

		if (sugestaoTrajeto == null) {
			resposta.setSucesso(false);
			resposta.setMensagem("Nenhuma intencao de carona ativa");

			return resposta;
		}

		SugestaoTrajetoDTO sugestaoTrajetoDto = this.sugestaoTrajetoAssembler.toDTO(sugestaoTrajeto);

		resposta.setSucesso(true);
		resposta.setDado(sugestaoTrajetoDto);

		return resposta;

	}

	@PostMapping("/motorista/aceitarpassageiro")
	@ResponseBody
	public RespostaPadraoDTO aceitarPassageiro(@RequestBody AcaoMotoristaPassageiroDTO aceitarPassageiroDTO) {

		RespostaPadraoDTO resposta = new RespostaPadraoDTO();

		int idMotorista = aceitarPassageiroDTO.getIdMotorista();
		int idPassageiro = aceitarPassageiroDTO.getIdPassageiro();

		SugestaoTrajetoMotorista motorista = this.sugestaoTrajetoServico.findMotoristaById(idMotorista);
		SugestaoTrajetoPassageiro passageiro = this.sugestaoTrajetoServico.findPassageiroById(idPassageiro);

		Usuario usuarioLogado = this.sessaoWeb.getUsuario();

		try {
			this.sugestaoTrajetoServico.aceitarPassageiro(motorista, passageiro, usuarioLogado);
			resposta.setSucesso(true);
		} catch (DomainSecurityException e) {
			logger.error(e);
			resposta.setSucesso(false);
			resposta.setMensagem("Requisição inválida.");
		}

		return resposta;

	}

	@PostMapping("/motorista/rejeitarpassageiro")
	@ResponseBody
	public RespostaPadraoDTO rejeitarPassageiro(@RequestBody AcaoMotoristaPassageiroDTO rejeitarPassageiroDTO) {

		RespostaPadraoDTO resposta = new RespostaPadraoDTO();

		int idMotorista = rejeitarPassageiroDTO.getIdMotorista();
		int idPassageiro = rejeitarPassageiroDTO.getIdPassageiro();

		SugestaoTrajetoMotorista motorista = this.sugestaoTrajetoServico.findMotoristaById(idMotorista);
		SugestaoTrajetoPassageiro passageiro = this.sugestaoTrajetoServico.findPassageiroById(idPassageiro);

		Usuario usuarioLogado = this.sessaoWeb.getUsuario();

		try {
			this.sugestaoTrajetoServico.rejeitarPassageiro(motorista, passageiro, usuarioLogado);
			resposta.setSucesso(true);
		} catch (DomainSecurityException e) {
			logger.error(e);
			resposta.setSucesso(false);
			resposta.setMensagem("Requisição inválida.");
		}

		return resposta;

	}

	@PostMapping("/passageiro/aceitarcarona")
	@ResponseBody
	public RespostaPadraoDTO aceitarCaronaPassageiro(
			@RequestBody ConfirmaTrajetoPassageiroDTO confirmaTrajetoPassageiroDTO) {

		RespostaPadraoDTO resposta = new RespostaPadraoDTO();

		int trajetoId = confirmaTrajetoPassageiroDTO.getTrajetoId();
		int passageiroId = confirmaTrajetoPassageiroDTO.getPassageiroId();

		SugestaoTrajeto sugestaoTrajeto = this.sugestaoTrajetoServico.findById(trajetoId);
		SugestaoTrajetoPassageiro sugestaoTrajetoPassageiro = this.sugestaoTrajetoServico
				.findPassageiroById(passageiroId);

		try {
			this.sugestaoTrajetoServico.aceitarTrajetoPassageiro(sugestaoTrajeto, sugestaoTrajetoPassageiro);
			resposta.setSucesso(true);
			// resposta.nextUrl ?
		} catch (DomainSecurityException e) {
			logger.error(e);
			resposta.setSucesso(false);
			resposta.setMensagem("Requisição inválida.");
		}

		return resposta;

	}

	@PostMapping("/passageiro/rejeitarcarona")
	@ResponseBody
	public RespostaPadraoDTO rejeitarCaronaPassageiro(
			@RequestBody ConfirmaTrajetoPassageiroDTO confirmaTrajetoPassageiroDTO) {

		RespostaPadraoDTO resposta = new RespostaPadraoDTO();

		int trajetoId = confirmaTrajetoPassageiroDTO.getTrajetoId();
		int passageiroId = confirmaTrajetoPassageiroDTO.getPassageiroId();

		SugestaoTrajeto sugestaoTrajeto = this.sugestaoTrajetoServico.findById(trajetoId);
		SugestaoTrajetoPassageiro sugestaoTrajetoPassageiro = this.sugestaoTrajetoServico
				.findPassageiroById(passageiroId);

		try {
			this.sugestaoTrajetoServico.rejeitarTrajetoPassageiro(sugestaoTrajeto, sugestaoTrajetoPassageiro);
			resposta.setSucesso(true);
			// resposta.nextUrl ?
		} catch (DomainSecurityException e) {
			logger.error(e);
			resposta.setSucesso(false);
			resposta.setMensagem("Requisição inválida.");
		}

		return resposta;

	}

	@GetMapping("/sugestao/visualizado")
	@ResponseBody
	public RespostaPadraoDTO visualizarSugestaoTrajeto(VisualizaSugestaoTrajetoDTO visualizaSugestaoTrajetoDTO) {

		RespostaPadraoDTO resposta = new RespostaPadraoDTO();

		int trajetoId = visualizaSugestaoTrajetoDTO.getTrajetoId();

		SugestaoTrajeto sugestaoTrajeto = this.sugestaoTrajetoServico.findById(trajetoId);
		this.sugestaoTrajetoServico.visualizarSugestao(sugestaoTrajeto);

		resposta.setSucesso(true);
		// resposta.nextUrl ?

		return resposta;

	}

	@PostMapping("/sugestao/substituir")
	public RespostaPadraoDTO substituirPassageiro(SubstituiPassageiroTrajetoDTO substituiPassageiroDTO) {

		RespostaPadraoDTO resposta = new RespostaPadraoDTO();

		Usuario usuario = this.sessaoWeb.getUsuario();
		int idSugestaoTrajeto = substituiPassageiroDTO.getIdSugestaoTrajeto();
		int idSugestaoTrajetoMotorista = substituiPassageiroDTO.getIdSugestaoTrajetoMotorista();
		int idSugestaoTrajetoPassageiro = substituiPassageiroDTO.getIdSugestaoTrajetoPassageiro();

		try {
			SugestaoTrajetoMotorista sugestaoTrajetoMotorista = this.sugestaoTrajetoServico
					.findMotoristaById(idSugestaoTrajetoMotorista);
			SugestaoTrajetoPassageiro sugestaoTrajetoPassageiro = this.sugestaoTrajetoServico
					.findPassageiroById(idSugestaoTrajetoPassageiro);

			this.sugestaoTrajetoServico.substituirPassageiro(sugestaoTrajetoMotorista, sugestaoTrajetoPassageiro,
					usuario);
			resposta.setSucesso(true);
			// resposta.nextUrl ?
		} catch (DomainSecurityException e) {
			logger.error(e);
			resposta.setSucesso(false);
			resposta.setMensagem("Requisição inválida.");
		}

		return resposta;

	}

}
