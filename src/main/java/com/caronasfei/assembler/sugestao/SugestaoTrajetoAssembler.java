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
import com.caronasfei.db.sugestao.SugestaoTrajetoMotorista;
import com.caronasfei.db.sugestao.SugestaoTrajetoPassageiro;
import com.caronasfei.db.usuario.Usuario;
import com.caronasfei.dto.consultasugestao.MotoristaSaidaDTO;
import com.caronasfei.dto.consultasugestao.SugestaoTrajetoDTO;
import com.caronasfei.dto.consultasugestao.SugestaoTrajetoPassageiroSaidaDTO;
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

		List<SugestaoTrajetoPassageiroSaidaDTO> passageiroListSaidaDTO = this.toPassageiroListSaidaDTO(passageiros);

		SugestaoTrajetoDTO sugestaoTrajetoDTO = new SugestaoTrajetoDTO();
		sugestaoTrajetoDTO.setId(sugestaoTrajeto.getId());
		sugestaoTrajetoDTO.setMotorista(motoristaSaidaDTO);
		sugestaoTrajetoDTO.setPassageiros(passageiroListSaidaDTO);
		sugestaoTrajetoDTO.setDestino(this.enderecoAssembler.toEnderecoDTO(sugestaoTrajeto.getEnderecoDestino()));
		
		return sugestaoTrajetoDTO;

	}

	public MotoristaSaidaDTO toMotoristaSaidaDTO(SugestaoTrajetoMotorista sugestaoTrajetoMotorista) {
		IntencaoCarona intencaoCaronaMotorista = sugestaoTrajetoMotorista.getIntencaoCarona();
		Usuario usuarioMotorista = intencaoCaronaMotorista.getUsuario();

		MotoristaSaidaDTO motoristaSaidaDTO = new MotoristaSaidaDTO();
		motoristaSaidaDTO.setId(sugestaoTrajetoMotorista.getId());
		motoristaSaidaDTO.setNome(usuarioMotorista.getNome());
		CursoPeriodo cursoPeriodo = usuarioMotorista.getCursoPeriodo();
		motoristaSaidaDTO.setCurso(cursoPeriodo.getCurso().getNome());
		motoristaSaidaDTO.setHorarioPartida(sugestaoTrajetoMotorista.getIntencaoCarona().getHorarioPartida().getHorarioFormatado());
		motoristaSaidaDTO.setPeriodo(cursoPeriodo.getPeriodo().getNome());
		motoristaSaidaDTO.setEstado(sugestaoTrajetoMotorista.getEstado().name());

		Endereco enderecoPartida = intencaoCaronaMotorista.getEnderecoPartida();
		Endereco enderecoDestino = intencaoCaronaMotorista.getEnderecoDestino();

		EnderecoDTO enderecoDestinoDTO = this.enderecoAssembler.toEnderecoDTO(enderecoDestino);
		EnderecoDTO enderecoPartidaDTO = this.enderecoAssembler.toEnderecoDTO(enderecoPartida);

		motoristaSaidaDTO.setEnderecoPartida(enderecoPartidaDTO);
		motoristaSaidaDTO.setEnderecoDestino(enderecoDestinoDTO);

		return motoristaSaidaDTO;

	}

	public SugestaoTrajetoPassageiroSaidaDTO toPassageiroSaidaDTO(SugestaoTrajetoPassageiro passageiro) {

		IntencaoCarona intencaoCaronaPassageiro = passageiro.getIntencaoCarona();
		Usuario usuarioPassageiro = intencaoCaronaPassageiro.getUsuario();

		SugestaoTrajetoPassageiroSaidaDTO passageiroSaidaDTO = new SugestaoTrajetoPassageiroSaidaDTO();
		passageiroSaidaDTO.setId(passageiro.getId());
		passageiroSaidaDTO.setNome(usuarioPassageiro.getNome());
		CursoPeriodo cursoPeriodo = usuarioPassageiro.getCursoPeriodo();
		passageiroSaidaDTO.setCurso(cursoPeriodo.getCurso().getNome());
		passageiroSaidaDTO.setEstado(passageiro.getEstado().name());
		passageiroSaidaDTO.setPeriodo(cursoPeriodo.getPeriodo().getNome());
		passageiroSaidaDTO.setDistanciaMotorista(passageiro.getDistanciaParaMotorista());
		passageiroSaidaDTO.setOrdemCarona(passageiro.getOrdemCarona());

		Endereco enderecoPartida = intencaoCaronaPassageiro.getEnderecoPartida();
		Endereco enderecoDestino = intencaoCaronaPassageiro.getEnderecoDestino();

		EnderecoDTO enderecoDestinoDTO = this.enderecoAssembler.toEnderecoDTO(enderecoDestino);
		EnderecoDTO enderecoPartidaDTO = this.enderecoAssembler.toEnderecoDTO(enderecoPartida);

		passageiroSaidaDTO.setEnderecoPartida(enderecoPartidaDTO);
		passageiroSaidaDTO.setEnderecoDestino(enderecoDestinoDTO);

		return passageiroSaidaDTO;

	}
	
	public List<SugestaoTrajetoPassageiroSaidaDTO> toPassageiroListSaidaDTO(List<SugestaoTrajetoPassageiro> passageiros) {

		List<SugestaoTrajetoPassageiroSaidaDTO> passageiroSaidaDTOList = new ArrayList<SugestaoTrajetoPassageiroSaidaDTO>();
		for (SugestaoTrajetoPassageiro sugestaoTrajetoPassageiro : passageiros) {

			IntencaoCarona intencaoCaronaPassageiro = sugestaoTrajetoPassageiro.getIntencaoCarona();
			Usuario usuarioPassageiro = intencaoCaronaPassageiro.getUsuario();

			SugestaoTrajetoPassageiroSaidaDTO passageiroSaidaDTO = new SugestaoTrajetoPassageiroSaidaDTO();
			passageiroSaidaDTO.setId(sugestaoTrajetoPassageiro.getId());
			passageiroSaidaDTO.setNome(usuarioPassageiro.getNome());
			CursoPeriodo cursoPeriodo = usuarioPassageiro.getCursoPeriodo();
			passageiroSaidaDTO.setCurso(cursoPeriodo.getCurso().getNome());
			passageiroSaidaDTO.setPeriodo(cursoPeriodo.getPeriodo().getNome());
			passageiroSaidaDTO.setDistanciaMotorista(sugestaoTrajetoPassageiro.getDistanciaParaMotorista());
			passageiroSaidaDTO.setEstado(sugestaoTrajetoPassageiro.getEstado().name());
			passageiroSaidaDTO.setOrdemCarona(sugestaoTrajetoPassageiro.getOrdemCarona());
			
			Endereco enderecoPartida = intencaoCaronaPassageiro.getEnderecoPartida();
			Endereco enderecoDestino = intencaoCaronaPassageiro.getEnderecoDestino();

			EnderecoDTO enderecoDestinoDTO = this.enderecoAssembler.toEnderecoDTO(enderecoDestino);
			EnderecoDTO enderecoPartidaDTO = this.enderecoAssembler.toEnderecoDTO(enderecoPartida);

			passageiroSaidaDTO.setEnderecoPartida(enderecoPartidaDTO);
			passageiroSaidaDTO.setEnderecoDestino(enderecoDestinoDTO);

			passageiroSaidaDTOList.add(passageiroSaidaDTO);

		}

		return passageiroSaidaDTOList;

	}

}
