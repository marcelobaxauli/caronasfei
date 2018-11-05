package com.caronasfei.dto.consultasugestao;

import java.util.List;

import com.caronasfei.dto.intencao.endereco.EnderecoDTO;

public class SugestaoTrajetoDTO {

	private MotoristaSaidaDTO motorista;
	private List<PassageiroSaidaDTO> passageiros;
	private EnderecoDTO destino;

	public MotoristaSaidaDTO getMotorista() {
		return motorista;
	}

	public void setMotorista(MotoristaSaidaDTO motorista) {
		this.motorista = motorista;
	}

	public List<PassageiroSaidaDTO> getPassageiros() {
		return passageiros;
	}

	public void setPassageiros(List<PassageiroSaidaDTO> passageiros) {
		this.passageiros = passageiros;
	}

	public EnderecoDTO getDestino() {
		return destino;
	}

	public void setDestino(EnderecoDTO destino) {
		this.destino = destino;
	}

}
