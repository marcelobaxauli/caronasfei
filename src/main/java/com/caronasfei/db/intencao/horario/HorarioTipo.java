package com.caronasfei.db.intencao.horario;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "horario_tipo")
public class HorarioTipo {

	public enum HorarioTipoCodigo {
		QUALQUER_HORARIO("qualquer"), HORARIO_FIXO("horario_fixo"), DE("de"), ATE("ate");

		private String codigo;

		private HorarioTipoCodigo(String codigo) {
			this.codigo = codigo;
		}

		public String getCodigo() {
			return codigo;
		}

		public void setCodigo(String codigo) {
			this.codigo = codigo;
		}

		public static HorarioTipoCodigo getByCodigo(String codigo) {

			HorarioTipoCodigo[] values = HorarioTipoCodigo.values();

			for (HorarioTipoCodigo value : values) {

				if (value.getCodigo().equals(codigo)) {
					return value;
				}

			}

			return null;

		}

	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_horario_tipo")
	private int id;

	@Column(name = "cd_horario_tipo")
	private String codigo;

	@Column(name = "ds_horario_tipo")
	private String descricao;

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public HorarioTipoCodigo getHorarioTipoCodigo() {
		return HorarioTipoCodigo.getByCodigo(this.codigo);
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
