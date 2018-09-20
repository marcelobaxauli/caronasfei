package com.caronasfei.dto.curso;

import java.util.ArrayList;
import java.util.List;

import com.caronasfei.db.curso.Curso;

public class RespostaCursoDTO {

	private List<CursoDTO> cursos;

	public List<CursoDTO> getCursos() {
		return cursos;
	}

	public void setCursos(List<CursoDTO> cursos) {
		this.cursos = cursos;
	}

	public static RespostaCursoDTO novo(List<Curso> cursos) {
		RespostaCursoDTO resposta = new RespostaCursoDTO();

		List<CursoDTO> cursosDTO = new ArrayList<CursoDTO>();

		for (Curso curso : cursos) {

			CursoDTO cursoDTO = new CursoDTO();
			cursoDTO.setNome(curso.getNome());
			cursoDTO.setCodigo(curso.getCodigo());

			cursosDTO.add(cursoDTO);
		}

		resposta.setCursos(cursosDTO);

		return resposta;
	}

}
