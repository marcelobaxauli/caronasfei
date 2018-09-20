package com.caronasfei.db.curso;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.caronasfei.db.periodo.Periodo;

@Entity
@Table(name = "curso_periodo")
public class CursoPeriodo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_curso_periodo")
	private long id;

	@OneToOne
	@JoinColumn(name = "id_curso")
	private Curso curso;

	@OneToOne
	@JoinColumn(name = "id_periodo")
	private Periodo periodo;

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public Periodo getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Periodo periodo) {
		this.periodo = periodo;
	}

}
