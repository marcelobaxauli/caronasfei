package com.caronasfei.dto.consultasugestao;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VisualizaSugestaoTrajetoDTO {
	@JsonProperty("id_trajeto")
	private int trajetoId;

	public int getTrajetoId() {
		return trajetoId;
	}

	public void setTrajetoId(int trajetoId) {
		this.trajetoId = trajetoId;
	}

}
