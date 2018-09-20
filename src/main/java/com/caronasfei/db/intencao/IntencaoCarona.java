package com.caronasfei.db.intencao;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.caronasfei.db.intencao.endereco.Endereco;
import com.caronasfei.db.intencao.horario.Horario;
import com.caronasfei.db.usuario.Usuario;

@Entity
@Table(name = "intencao_carona")
public class IntencaoCarona {

	public enum IntencaoCaronaEstado {
		ATIVA, CANCELADA, FINALIZADA // intenção cumpriu seu papel e ação da carona ocorreu até sua última etapa.
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_intencao_carona")
	private long id;

	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;

	@Column(name = "data_criacao")
	private Date dataCriacao;

	@Column(name = "data_cancelamento")
	private Date dataCancelamento;

	@Column(name = "estado")
	@Enumerated(EnumType.STRING)
	private IntencaoCaronaEstado estado;

	@Column(name = "acao_carona")
	@Enumerated(EnumType.STRING)
	private AcaoCarona acaoCarona;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_local_partida")
	private Endereco enderecoPartida;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_local_destino")
	private Endereco enderecoDestino;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_horario_partida")
	private Horario horarioPartida;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_horario_chegada")
	private Horario horarioChegada;

	public IntencaoCarona() {
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public AcaoCarona getAcaoCarona() {
		return acaoCarona;
	}

	public void setAcaoCarona(AcaoCarona acaoCarona) {
		this.acaoCarona = acaoCarona;
	}

	public Endereco getEnderecoPartida() {
		return enderecoPartida;
	}

	public void setEnderecoPartida(Endereco enderecoPartida) {
		this.enderecoPartida = enderecoPartida;
	}

	public Endereco getEnderecoDestino() {
		return enderecoDestino;
	}

	public void setEnderecoDestino(Endereco enderecoDestino) {
		this.enderecoDestino = enderecoDestino;
	}

	public Horario getHorarioPartida() {
		return horarioPartida;
	}

	public void setHorarioPartida(Horario horarioPartida) {
		this.horarioPartida = horarioPartida;
	}

	public Horario getHorarioChegada() {
		return horarioChegada;
	}

	public void setHorarioChegada(Horario horarioChegada) {
		this.horarioChegada = horarioChegada;
	}

	public Date getDataCancelamento() {
		return dataCancelamento;
	}

	public void setDataCancelamento(Date dataCancelamento) {
		this.dataCancelamento = dataCancelamento;
	}

	public IntencaoCaronaEstado getEstado() {
		return estado;
	}

	public void setEstado(IntencaoCaronaEstado estado) {
		this.estado = estado;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IntencaoCarona other = (IntencaoCarona) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public enum AcaoCarona {

		PEDIR_CARONA("pedir_carona"), OFERECER_CARONA("oferecer_carona");

		private String codigo;

		private AcaoCarona(String codigo) {
			this.codigo = codigo;
		}

		public String getCodigo() {
			return codigo;
		}

		public void setCodigo(String codigo) {
			this.codigo = codigo;
		}

		public static AcaoCarona getByCodigo(String codigo) {

			AcaoCarona[] values = AcaoCarona.values();

			for (AcaoCarona value : values) {

				if (value.getCodigo().equals(codigo)) {
					return value;
				}

			}

			return null;
		}

	}

}
