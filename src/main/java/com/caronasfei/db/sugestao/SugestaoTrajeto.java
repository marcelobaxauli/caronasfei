package com.caronasfei.db.sugestao;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderColumn;
import javax.persistence.Table;

@Entity
@Table(name = "sugestao_trajeto")
public class SugestaoTrajeto {

	public enum SugestaoTrajetoEstado {
		NORMAL, 
		INTENCAO_CANCELADA, 
		CANCELADA,
		CONFIRMADA // quando pelo menos um passageiro aceitou e
				   // o motorista confirma(finaliza) a carona
		
		// TODO: novos passageiros podem ser adicionados após a confirmação
		// da sugestao ter sido CONFIRMADA pelo motorista?
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_sugestao_trajeto")
	private int id;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "estado")
	private SugestaoTrajetoEstado estado;

	// Por enquanto não preciso extender a SugestaoTrajetoUsuario para criar uma
	// SugestaoTrajetoMotorista...
	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "id_sugestao_trajeto_motorista")
	private SugestaoTrajetoMotorista motorista;

	// salva a ordem, pq é a ordem que o motorista deve pegar cada passageiro.
	// fundamental manter a ordem aqui
	@OrderColumn(name = "ordem_carona")
	@OneToMany(fetch = FetchType.EAGER, 
			   mappedBy = "sugestaoTrajeto",
			   cascade = CascadeType.ALL)
	private List<SugestaoTrajetoPassageiro> passageiros;

	// indica se a sugestao foi visualizada
	@Column(name = "visualizada")
	private Boolean visualizada;

	// score desta sugestão de carona.
	// quando a sugestão de carona for visualizada por alguém não poderá mudar..
	@Column(name = "score")
	private Integer score;

	public SugestaoTrajeto() {
		this.passageiros = new LinkedList<SugestaoTrajetoPassageiro>();
	}

	public SugestaoTrajetoMotorista getMotorista() {
		return motorista;
	}

	public void setMotorista(SugestaoTrajetoMotorista motorista) {
		this.motorista = motorista;
	}

	public List<SugestaoTrajetoPassageiro> getPassageiros() {
		return passageiros;
	}

	public void addSugestaoTrajetoPassageiro(SugestaoTrajetoPassageiro passageiro) {
		this.passageiros.add(passageiro);
	}

	public Integer getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Boolean isVisualizada() {
		return visualizada;
	}

	public void setVisualizada(Boolean visualizada) {
		this.visualizada = visualizada;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public SugestaoTrajetoEstado getEstado() {
		return estado;
	}

	public void setEstado(SugestaoTrajetoEstado estado) {
		this.estado = estado;
	}

}
