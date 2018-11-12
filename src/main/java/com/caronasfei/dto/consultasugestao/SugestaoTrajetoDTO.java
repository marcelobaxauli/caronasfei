package com.caronasfei.dto.consultasugestao;

import java.util.List;

import com.caronasfei.dto.intencao.endereco.EnderecoDTO;

public class SugestaoTrajetoDTO {

	private MotoristaSaidaDTO motorista;
	private List<SugestaoTrajetoPassageiroSaidaDTO> passageiros;
	private EnderecoDTO destino;

	public MotoristaSaidaDTO getMotorista() {
		return motorista;
	}

	public void setMotorista(MotoristaSaidaDTO motorista) {
		this.motorista = motorista;
	}

	public List<SugestaoTrajetoPassageiroSaidaDTO> getPassageiros() {
		return passageiros;
	}

	public void setPassageiros(List<SugestaoTrajetoPassageiroSaidaDTO> passageiros) {
		this.passageiros = passageiros;
	}

	public EnderecoDTO getDestino() {
		return destino;
	}

	public void setDestino(EnderecoDTO destino) {
		this.destino = destino;
	}

}
