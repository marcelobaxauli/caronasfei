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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.caronasfei.db.intencao.IntencaoCarona;

@Entity
@Table(name = "sugestao_trajeto_usuario")
public class SugestaoTrajetoUsuario {

	// Só pode mudar o estado da sugestao_trajeto
	// para CONFIRMADO quando o motorista
	// confirmar pelo menos um passageiro.
	public enum SugestaoTrajetoUsuarioEstado {
		CONFIRMADO, NAO_CONFIRMADO, REJEITADO
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_sugestao_trajeto_usuario")
	private int id;

	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "id_intencao_carona")
	private IntencaoCarona intencaoCarona;

	@Enumerated(EnumType.STRING)
	@Column(name = "estado")
	private SugestaoTrajetoUsuarioEstado estado;

	public IntencaoCarona getIntencaoCarona() {
		return intencaoCarona;
	}

	public void setIntencaoCarona(IntencaoCarona intencaoCarona) {
		this.intencaoCarona = intencaoCarona;
	}

	public SugestaoTrajetoUsuarioEstado getEstado() {
		return estado;
	}

	public void setEstado(SugestaoTrajetoUsuarioEstado estado) {
		this.estado = estado;
	}

	public int getId() {
		return id;
	}

	
}
