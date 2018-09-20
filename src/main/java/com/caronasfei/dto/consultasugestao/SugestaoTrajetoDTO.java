package com.caronasfei.dto.consultasugestao;

import java.util.List;

public class SugestaoTrajetoDTO {

	private MotoristaSaidaDTO motorista;
	private List<PassageiroSaidaDTO> passageiros;

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

}
