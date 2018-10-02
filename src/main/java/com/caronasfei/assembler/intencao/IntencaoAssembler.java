package com.caronasfei.assembler.intencao;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.caronasfei.assembler.exception.ValidacaoException;
import com.caronasfei.assembler.intencao.endereco.EnderecoAssembler;
import com.caronasfei.assembler.intencao.horario.HorarioAssembler;
import com.caronasfei.db.intencao.IntencaoCarona;
import com.caronasfei.db.intencao.IntencaoCarona.AcaoCarona;
import com.caronasfei.db.intencao.IntencaoCarona.DirecaoCarona;
import com.caronasfei.db.intencao.IntencaoCarona.IntencaoCaronaEstado;
import com.caronasfei.db.intencao.endereco.Endereco;
import com.caronasfei.db.intencao.endereco.Endereco.OpcaoEndereco;
import com.caronasfei.db.intencao.horario.Horario;
import com.caronasfei.db.usuario.Usuario;
import com.caronasfei.dto.intencao.IntencaoCaronaDTO;
import com.caronasfei.dto.intencao.endereco.EnderecoDTO;
import com.caronasfei.service.endereco.EnderecoServico;
import com.caronasfei.service.intencao.IntencaoCaronaServico;

@Component
@Scope("prototype")
public class IntencaoAssembler {

	@Autowired
	private IntencaoCaronaServico intencaoService;
	
	@Autowired
	private EnderecoAssembler enderecoAssembler;

	@Autowired
	private EnderecoServico enderecoServico;
	
	@Autowired
	private HorarioAssembler horarioAssembler;

	@Transactional(readOnly = true)
	public IntencaoCarona toIntencao(IntencaoCaronaDTO intencaoDTO, Usuario usuario) throws ValidacaoException {

		String acaoCodigo = intencaoDTO.getAcao();
		AcaoCarona acaoCarona = AcaoCarona.getByCodigo(acaoCodigo);

		if (acaoCarona == null) {
			throw new ValidacaoException("Acao carona inválida.");
		}

		Integer numeroAssentos = null;
		Integer detour = null;
		if (acaoCarona == AcaoCarona.OFERECER_CARONA) {
			numeroAssentos = intencaoDTO.getNumeroAssentos();			
			detour = intencaoDTO.getDetour();
		}
		

		if (acaoCarona == AcaoCarona.OFERECER_CARONA && numeroAssentos == null) {
			throw new ValidacaoException(
					"Intencao de pedir carona mas o numero de assentos do carro nao foi informado");
		}

		String opcaoLocalPartida = intencaoDTO.getOpcaoLocalPartida();
		String opcaoLocalDestino = intencaoDTO.getOpcaoLocalDestino();

		if (!((Endereco.OpcaoEndereco.FEI_SBC.getCodigo().equals(opcaoLocalPartida)
				&& Endereco.OpcaoEndereco.OUTRO.getCodigo().equals(opcaoLocalDestino))
				|| (Endereco.OpcaoEndereco.OUTRO.getCodigo().equals(opcaoLocalPartida)
						&& Endereco.OpcaoEndereco.FEI_SBC.getCodigo().equals(opcaoLocalDestino)))) {
			throw new ValidacaoException("Opção de endereço inválido");
		}

		EnderecoDTO endereco = intencaoDTO.getEndereco();

		// verificar endereco junto ao Google?
		// qual o máximo de radius permitido?
		// o cara pode criar uma intenção com local de partida/chegada no Acre? (muito
		// longe)?

		Endereco enderecoPartida = null;
		Endereco enderecoDestino = null;

		DirecaoCarona direcaoCarona = null;

		if (Endereco.OpcaoEndereco.OUTRO.getCodigo().equals(opcaoLocalPartida)) {
			enderecoPartida = this.enderecoAssembler.toEndereco(endereco, intencaoDTO.getOpcaoLocalPartida());
			enderecoDestino = this.enderecoServico.findEnderecoById(Endereco.ID_ENDERECO_FEI_SBC);
			direcaoCarona = DirecaoCarona.IDA_FEI;
		} else if (Endereco.OpcaoEndereco.OUTRO.getCodigo().equals(opcaoLocalDestino)) {
			enderecoDestino = this.enderecoAssembler.toEndereco(endereco, intencaoDTO.getOpcaoLocalDestino());
			enderecoPartida = this.enderecoServico.findEnderecoById(Endereco.ID_ENDERECO_FEI_SBC);
			direcaoCarona = DirecaoCarona.VOLTA_FEI;
		}

		String opcaoHorarioPartida = intencaoDTO.getOpcaoHorarioPartida();
		String horarioPartidaRaw = intencaoDTO.getHorarioPartida();

		Horario horarioPartida = this.horarioAssembler.toHorario(opcaoHorarioPartida, horarioPartidaRaw);

		String opcaoHorarioChegada = intencaoDTO.getOpcaoHorarioChegada();
		String horarioChegadaRaw = intencaoDTO.getHorarioChegada();

		Horario horarioChegada = this.horarioAssembler.toHorario(opcaoHorarioChegada, horarioChegadaRaw);

		IntencaoCarona intencao = new IntencaoCarona();
		intencao.setAcaoCarona(acaoCarona);
		intencao.setDataCriacao(new Date());
		intencao.setDirecaoCarona(direcaoCarona);
		intencao.setEstado(IntencaoCaronaEstado.ATIVA);
		if (acaoCarona == AcaoCarona.OFERECER_CARONA) {
			intencao.setNumeroAssentos(numeroAssentos);
			intencao.setDetour(detour);
		}
		intencao.setDataCancelamento(null);
		intencao.setUsuario(usuario);
		intencao.setHorarioPartida(horarioPartida);
		intencao.setHorarioChegada(horarioChegada);
		intencao.setEnderecoPartida(enderecoPartida);
		intencao.setEnderecoDestino(enderecoDestino);
		
		return intencao;

	}

	@Transactional(readOnly = true)
	public IntencaoCaronaDTO toIntencaoCaronaDTO(IntencaoCarona intencaoCarona) {

		String acaoCaronaCodigo = intencaoCarona.getAcaoCarona().getCodigo();
		
		Endereco enderecoPartida = intencaoCarona.getEnderecoPartida();
		Endereco enderecoDestino = intencaoCarona.getEnderecoDestino();

		String opcEnderecoPartida = null;
		String opcEnderecoDestino = null;

		Endereco local = null;
		if (enderecoPartida.getOpcaoEndereco() == OpcaoEndereco.FEI_SBC) {
			opcEnderecoPartida = Endereco.OpcaoEndereco.FEI_SBC.getCodigo();
			opcEnderecoDestino = Endereco.OpcaoEndereco.OUTRO.getCodigo();

			local = enderecoDestino;
		} else {
			opcEnderecoPartida = Endereco.OpcaoEndereco.OUTRO.getCodigo();
			opcEnderecoDestino = Endereco.OpcaoEndereco.FEI_SBC.getCodigo();

			local = enderecoPartida;
		}

		Horario horarioPartida = intencaoCarona.getHorarioPartida();
		Horario horarioChegada = intencaoCarona.getHorarioChegada();

		String opcHorarioPartida = horarioPartida.getHorarioTipo().getCodigo();
		String opcHorarioChegada = horarioChegada.getHorarioTipo().getCodigo();

		String horarioPartidaDescricao = horarioPartida.getHorarioFormatado();
		String horarioChegadaDescricao = horarioChegada.getHorarioFormatado();

		EnderecoDTO enderecoDTO = new EnderecoDTO();
		String enderecoFormatado = local.getRua() + ", " + local.getNumero() + " - " + local.getBairro() + " - "
				+ local.getCep() + ". " + local.getCidade();
		enderecoDTO.setRua(local.getRua());
		enderecoDTO.setNumero(local.getNumero());
		enderecoDTO.setBairro(local.getBairro());
		enderecoDTO.setCep(local.getCep());
		enderecoDTO.setCidade(local.getCidade());
		enderecoDTO.setLatitude(local.getLatitude());
		enderecoDTO.setLongitude(local.getLongitude());
		enderecoDTO.setFormatado(enderecoFormatado);

		IntencaoCaronaDTO intencaoDTO = new IntencaoCaronaDTO();
		intencaoDTO.setAcao(acaoCaronaCodigo);
		intencaoDTO.setOpcaoHorarioPartida(opcHorarioPartida);
		intencaoDTO.setOpcaoHorarioChegada(opcHorarioChegada);
		if (intencaoCarona.getAcaoCarona() == AcaoCarona.OFERECER_CARONA) {
			intencaoDTO.setNumeroAssentos(intencaoCarona.getNumeroAssentos());
		}
		intencaoDTO.setHorarioPartida(horarioPartidaDescricao);
		intencaoDTO.setHorarioChegada(horarioChegadaDescricao);
		intencaoDTO.setOpcaoLocalPartida(opcEnderecoPartida);
		intencaoDTO.setOpcaoLocalDestino(opcEnderecoDestino);
		intencaoDTO.setEndereco(enderecoDTO);

		return intencaoDTO;

	}

}
