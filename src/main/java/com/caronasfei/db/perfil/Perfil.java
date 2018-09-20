package com.caronasfei.db.perfil;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "perfil")
public class Perfil {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_perfil")
	private int id;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_perfil_passageiro")
	private PerfilPassageiro perfilPassageiro;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_perfil_motorista")
	private PerfilMotorista perfilMotorista;

	public PerfilPassageiro getPerfilPassageiro() {
		return perfilPassageiro;
	}

	public void setPerfilPassageiro(PerfilPassageiro perfilPassageiro) {
		this.perfilPassageiro = perfilPassageiro;
	}

	public PerfilMotorista getPerfilMotorista() {
		return perfilMotorista;
	}

	public void setPerfilMotorista(PerfilMotorista perfilMotorista) {
		this.perfilMotorista = perfilMotorista;
	}

}
