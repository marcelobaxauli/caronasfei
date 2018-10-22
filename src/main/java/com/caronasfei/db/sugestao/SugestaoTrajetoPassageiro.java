package com.caronasfei.db.sugestao;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.caronasfei.db.intencao.IntencaoCarona;

@Entity
@Table(name = "sugestao_trajeto_passageiro")
public class SugestaoTrajetoPassageiro {

	// TODO: Fazer essa classe herdar os atributos intencaoCarona e estado da classe
	// SugestaoTrajetoUsuario. Vou fzr sem herança agora pra facilitar...

	// Primeiro o motorista precisa aceitar buscar o passageiro
	// na sugestão de carona: vai para o estado ACEITO_MOTORISTA.

	// Após isso o passageiro precisa aceitar a carona com o determinado
	// motorista. Após isso a estado vai para ACEITO (estado final).

	// precisava fazer esse estado virar uma tabela..
	// pra registrar os eventos de usuário (quando o motorista aceitar ou recusar
	// esse usuário / ou usuário rejeitar a carona) e gravar o horário de
	// acontecimento dos evento. Pode ser útil depois.
	public enum SugestaoTrajetoPassageiroEstado {
		NAO_CONFIRMADO, // NEM MOTORISTA NEM PASSAGEIRO CONFIRMARAM
		CONFIRMADO_MOTORISTA, // MOTORISTA CONFIRMOU
		CONFIRMADO, // MOTORISTA E PASSAGEIRO CONFIRMARAM
		REJEITADO_MOTORISTA, // MOTORISTA REJEITOU
		REJEITADO_PASSAGEIRO, // PASSAGEIRO REJEITOU
		SUBSTITUICAO // PASSAGEIRO REJEITOU A CARONA OU FOI REJEITADO PELO MOTORISTA.
						// MOTORISTA OPTOU POR SUBSTITUIR ESTE PASSAGEIRO
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_sugestao_trajeto_passageiro")
	private int id;

	// Por enquanto só estou considerando a distancia para formar as sugestões de
	// trajeto.
	// A ideia no futuro é levar vários outros elementos em consideração (como por
	// exemplo número de amigos em comum no facebook,
	// preferencia de sexo, preferencia de curso, ranking interno do app e etc).
	// Por esses outros elementos serem obviamente multivalorados, a ideia é criar
	// uma nova tabela só para armazenar esses valores,
	// aí entraria uma foreign key aqui para ligar...
	@Column(name = "distancia")
	private double distanciaParaMotorista;

	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "id_intencao_carona")
	private IntencaoCarona intencaoCarona;

	@Enumerated(EnumType.STRING)
	@Column(name = "estado")
	private SugestaoTrajetoPassageiroEstado estado;

	@ManyToOne(optional=false)
	@JoinColumn(name = "id_sugestao_trajeto")
	private SugestaoTrajeto sugestaoTrajeto;

	public double getDistanciaParaMotorista() {
		return distanciaParaMotorista;
	}

	public void setDistanciaParaMotorista(double distanciaParaMotorista) {
		this.distanciaParaMotorista = distanciaParaMotorista;
	}

	public SugestaoTrajeto getSugestaoTrajeto() {
		return sugestaoTrajeto;
	}

	public void setSugestaoTrajeto(SugestaoTrajeto sugestaoTrajeto) {
		this.sugestaoTrajeto = sugestaoTrajeto;
	}

	public IntencaoCarona getIntencaoCarona() {
		return intencaoCarona;
	}

	public void setIntencaoCarona(IntencaoCarona intencaoCarona) {
		this.intencaoCarona = intencaoCarona;
	}

	public SugestaoTrajetoPassageiroEstado getEstado() {
		return estado;
	}

	public void setEstado(SugestaoTrajetoPassageiroEstado estado) {
		this.estado = estado;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isNoFixo() {
		return this.estado == SugestaoTrajetoPassageiroEstado.CONFIRMADO
				|| this.estado == SugestaoTrajetoPassageiroEstado.CONFIRMADO_MOTORISTA
				|| this.estado == SugestaoTrajetoPassageiroEstado.NAO_CONFIRMADO;
	}

}
