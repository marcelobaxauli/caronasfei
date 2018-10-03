package com.caronasfei.dto.intencao;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IntencaoCaronaDTO {

	@JsonProperty("id")
	private long id;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

}
