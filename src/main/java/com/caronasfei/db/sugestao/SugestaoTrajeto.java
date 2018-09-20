package com.caronasfei.db.sugestao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.caronasfei.db.sugestao.SugestaoTrajetoPassageiro.SugestaoTrajetoPassageiroEstado;

@Entity
@Table(name = "sugestao_trajeto")
public class SugestaoTrajeto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_sugestao_trajeto")
	private int id;

	// Por enquanto não preciso extender a SugestaoTrajetoUsuario para criar uma
	// SugestaoTrajetoMotorista...
	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "id_sugestao_trajeto_motorista")
	private SugestaoTrajetoUsuario motorista;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "sugestaoTrajeto", cascade = { CascadeType.PERSIST })
	private List<SugestaoTrajetoPassageiro> passageiros;

	public SugestaoTrajeto() {
		this.passageiros = new ArrayList<SugestaoTrajetoPassageiro>();
	}

	public SugestaoTrajetoUsuario getMotorista() {
		return motorista;
	}

	public void setMotorista(SugestaoTrajetoUsuario motorista) {
		this.motorista = motorista;
	}

	public List<SugestaoTrajetoPassageiro> getPassageiros() {
		return passageiros;
	}
	
	public void setPassageiros(List<SugestaoTrajetoPassageiro> passageiros) {
		this.passageiros = passageiros;
	}

	public void addPassageiro(SugestaoTrajetoPassageiro passageiro) {
		this.passageiros.add(passageiro);
	}

	public Integer getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
