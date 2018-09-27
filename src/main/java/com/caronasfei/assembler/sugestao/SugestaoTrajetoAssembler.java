package com.caronasfei.assembler.sugestao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.caronasfei.assembler.intencao.endereco.EnderecoAssembler;
import com.caronasfei.db.curso.CursoPeriodo;
import com.caronasfei.db.intencao.IntencaoCarona;
import com.caronasfei.db.intencao.endereco.Endereco;
import com.caronasfei.db.sugestao.SugestaoTrajeto;
import com.caronasfei.db.sugestao.SugestaoTrajetoPassageiro;
import com.caronasfei.db.sugestao.SugestaoTrajetoMotorista;
import com.caronasfei.db.usuario.Usuario;
import com.caronasfei.dto.consultasugestao.MotoristaSaidaDTO;
import com.caronasfei.dto.consultasugestao.PassageiroSaidaDTO;
import com.caronasfei.dto.consultasugestao.SugestaoTrajetoDTO;
import com.caronasfei.dto.intencao.endereco.EnderecoDTO;

@Component
@Scope("prototype")
public class SugestaoTrajetoAssembler {

	@Autowired
	private EnderecoAssembler enderecoAssembler;

	public SugestaoTrajetoDTO toDTO(SugestaoTrajeto sugestaoTrajeto) {

		SugestaoTrajetoMotorista sugestaoTrajetoMotorista = sugestaoTrajeto.getMotorista();

		MotoristaSaidaDTO motoristaSaidaDTO = this.toMotoristaSaidaDTO(sugestaoTrajetoMotorista);

		List<SugestaoTrajetoPassageiro> passageiros = sugestaoTrajeto.getPassageiros();

		List<PassageiroSaidaDTO> passageiroListSaidaDTO = this.toPassageiroListSaidaDTO(passageiros);

		SugestaoTrajetoDTO sugestaoTrajetoPerfilMotoristaDTO = new SugestaoTrajetoDTO();
		sugestaoTrajetoPerfilMotoristaDTO.setMotorista(motoristaSaidaDTO);
		sugestaoTrajetoPerfilMotoristaDTO.setPassageiros(passageiroListSaidaDTO);

		return sugestaoTrajetoPerfilMotoristaDTO;

	}

	public MotoristaSaidaDTO toMotoristaSaidaDTO(SugestaoTrajetoMotorista sugestaoTrajetoMotorista) {
		IntencaoCarona intencaoCaronaMotorista = sugestaoTrajetoMotorista.getIntencaoCarona();
		Usuario usuarioMotorista = intencaoCaronaMotorista.getUsuario();

		MotoristaSaidaDTO motoristaSaidaDTO = new MotoristaSaidaDTO();
		motoristaSaidaDTO.setId(sugestaoTrajetoMotorista.getId());
		motoristaSaidaDTO.setNome(usuarioMotorista.getNome());
		CursoPeriodo cursoPeriodo = usuarioMotorista.getCursoPeriodo();
		motoristaSaidaDTO.setCurso(cursoPeriodo.getCurso().getNome());
		motoristaSaidaDTO.setPeriodo(cursoPeriodo.getPeriodo().getNome());
		motoristaSaidaDTO.setEstado(sugestaoTrajetoMotorista.getEstado().name());

		Endereco enderecoPartida = intencaoCaronaMotorista.getEnderecoPartida();
		Endereco enderecoDestino = intencaoCaronaMotorista.getEnderecoDestino();

		EnderecoDTO enderecoDestinoDTO = null;
		EnderecoDTO enderecoPartidaDTO = null;
		if (enderecoPartida == null) {
			// FEI_SBC
			enderecoDestinoDTO = this.enderecoAssembler.toEnderecoDTO(enderecoDestino);
			enderecoPartidaDTO = null;
		}

		if (enderecoDestino == null) {
			// FEI_SBC
			enderecoPartidaDTO = this.enderecoAssembler.toEnderecoDTO(enderecoPartida);
			enderecoDestinoDTO = null;
		}

		motoristaSaidaDTO.setEnderecoPartida(enderecoPartidaDTO);
		motoristaSaidaDTO.setEnderecoDestino(enderecoDestinoDTO);

		return motoristaSaidaDTO;

	}

	public PassageiroSaidaDTO toPassageiroSaidaDTO(SugestaoTrajetoPassageiro passageiro) {

		IntencaoCarona intencaoCaronaPassageiro = passageiro.getIntencaoCarona();
		Usuario usuarioPassageiro = intencaoCaronaPassageiro.getUsuario();

		PassageiroSaidaDTO passageiroSaidaDTO = new PassageiroSaidaDTO();
		passageiroSaidaDTO.setId(passageiro.getId());
		passageiroSaidaDTO.setNome(usuarioPassageiro.getNome());
		CursoPeriodo cursoPeriodo = usuarioPassageiro.getCursoPeriodo();
		passageiroSaidaDTO.setCurso(cursoPeriodo.getCurso().getNome());
		passageiroSaidaDTO.setPeriodo(cursoPeriodo.getPeriodo().getNome());
		passageiroSaidaDTO.setDistanciaMotorista(passageiro.getDistanciaParaMotorista());

		Endereco enderecoPartida = intencaoCaronaPassageiro.getEnderecoPartida();
		Endereco enderecoDestino = intencaoCaronaPassageiro.getEnderecoDestino();

		EnderecoDTO enderecoDestinoDTO = null;
		EnderecoDTO enderecoPartidaDTO = null;
		if (enderecoPartida == null) {
			// FEI_SBC
			enderecoDestinoDTO = this.enderecoAssembler.toEnderecoDTO(enderecoDestino);
			enderecoPartidaDTO = null;
		}

		if (enderecoDestino == null) {
			// FEI_SBC
			enderecoPartidaDTO = this.enderecoAssembler.toEnderecoDTO(enderecoPartida);
			enderecoDestinoDTO = null;
		}

		passageiroSaidaDTO.setEnderecoPartida(enderecoPartidaDTO);
		passageiroSaidaDTO.setEnderecoDestino(enderecoDestinoDTO);

		return passageiroSaidaDTO;

	}

	public List<PassageiroSaidaDTO> toPassageiroListSaidaDTO(List<SugestaoTrajetoPassageiro> passageiros) {

		List<PassageiroSaidaDTO> passageiroSaidaDTOList = new ArrayList<PassageiroSaidaDTO>();
		for (SugestaoTrajetoPassageiro sugestaoTrajetoPassageiro : passageiros) {

			IntencaoCarona intencaoCaronaPassageiro = sugestaoTrajetoPassageiro.getIntencaoCarona();
			Usuario usuarioPassageiro = intencaoCaronaPassageiro.getUsuario();

			PassageiroSaidaDTO passageiroSaidaDTO = new PassageiroSaidaDTO();
			passageiroSaidaDTO.setId(sugestaoTrajetoPassageiro.getId());
			passageiroSaidaDTO.setNome(usuarioPassageiro.getNome());
			CursoPeriodo cursoPeriodo = usuarioPassageiro.getCursoPeriodo();
			passageiroSaidaDTO.setCurso(cursoPeriodo.getCurso().getNome());
			passageiroSaidaDTO.setPeriodo(cursoPeriodo.getPeriodo().getNome());
			passageiroSaidaDTO.setDistanciaMotorista(sugestaoTrajetoPassageiro.getDistanciaParaMotorista());
			passageiroSaidaDTO.setEstado(sugestaoTrajetoPassageiro.getEstado().name());
			
			Endereco enderecoPartida = intencaoCaronaPassageiro.getEnderecoPartida();
			Endereco enderecoDestino = intencaoCaronaPassageiro.getEnderecoDestino();

			EnderecoDTO enderecoDestinoDTO = null;
			EnderecoDTO enderecoPartidaDTO = null;
			if (enderecoPartida == null) {
				// FEI_SBC
				enderecoDestinoDTO = this.enderecoAssembler.toEnderecoDTO(enderecoDestino);
				enderecoPartidaDTO = null;
			}

			if (enderecoDestino == null) {
				// FEI_SBC
				enderecoPartidaDTO = this.enderecoAssembler.toEnderecoDTO(enderecoPartida);
				enderecoDestinoDTO = null;
			}

			passageiroSaidaDTO.setEnderecoPartida(enderecoPartidaDTO);
			passageiroSaidaDTO.setEnderecoDestino(enderecoDestinoDTO);

			passageiroSaidaDTOList.add(passageiroSaidaDTO);

		}

		return passageiroSaidaDTOList;

	}

}
