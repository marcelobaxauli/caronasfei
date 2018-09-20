package com.caronasfei.web.controller.curso;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.caronasfei.db.curso.Curso;
import com.caronasfei.dto.curso.RespostaCursoDTO;
import com.caronasfei.service.curso.CursoServico;

@Controller
public class CursoAPIController {

	@Autowired
	private CursoServico cursoServico;

	@GetMapping("/cursos")
	@ResponseBody
	public RespostaCursoDTO listCursos() {

		List<Curso> cursos = this.cursoServico.findAll();

		return RespostaCursoDTO.novo(cursos);

	}

}
