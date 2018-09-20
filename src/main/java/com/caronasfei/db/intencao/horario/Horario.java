package com.caronasfei.db.intencao.horario;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "horario")
public class Horario {

	private static final SimpleDateFormat sdf = new SimpleDateFormat("hh : mm a");

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_horario")
	private int id;

	@OneToOne
	@JoinColumn(name = "id_horario_tipo")
	private HorarioTipo horarioTipo;

	private Date horario;

	public HorarioTipo getHorarioTipo() {
		return horarioTipo;
	}

	public void setHorarioTipo(HorarioTipo horarioTipo) {
		this.horarioTipo = horarioTipo;
	}

	public Date getHorario() {
		return horario;
	}

	public void setHorario(Date horario) {
		this.horario = horario;
	}

	public String getHorarioFormatado() {
		if (this.horario == null) {
			return "";
		}
		
		return Horario.sdf.format(this.horario);
	}

}
