package com.caronasfei.dto.periodo;

import java.util.ArrayList;
import java.util.List;

import com.caronasfei.db.periodo.Periodo;

public class RespostaPeriodoDTO {

	private List<PeriodoDTO> periodos;

	public List<PeriodoDTO> getPeriodos() {
		return periodos;
	}

	public void setPeriodos(List<PeriodoDTO> periodos) {
		this.periodos = periodos;
	}

	public static RespostaPeriodoDTO novo(List<Periodo> periodos) {
		RespostaPeriodoDTO resposta = new RespostaPeriodoDTO();

		List<PeriodoDTO> periodosDTO = new ArrayList<PeriodoDTO>();

		for (Periodo periodo : periodos) {

			PeriodoDTO periodoDTO = new PeriodoDTO();
			periodoDTO.setNome(periodo.getNome());
			periodoDTO.setCodigo(periodo.getCodigo());

			periodosDTO.add(periodoDTO);
		}

		resposta.setPeriodos(periodosDTO);

		return resposta;
	}

}
