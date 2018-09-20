package com.caronasfei.web.controller.periodo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.caronasfei.db.periodo.Periodo;
import com.caronasfei.dto.periodo.RespostaPeriodoDTO;
import com.caronasfei.service.periodo.PeriodoServico;

@Controller
public class PeriodoAPIController {

	@Autowired
	private PeriodoServico periodoServico;

	@GetMapping("/periodos")
	@ResponseBody
	public RespostaPeriodoDTO listaPeriodos() {

		List<Periodo> periodos = this.periodoServico.findAll();

		return RespostaPeriodoDTO.novo(periodos);

	}

}
