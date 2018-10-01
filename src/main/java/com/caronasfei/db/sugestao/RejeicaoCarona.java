package com.caronasfei.db.sugestao;

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

import com.caronasfei.db.usuario.Usuario;

@Entity
@Table(name = "rejeicao_carona")
public class RejeicaoCarona {

	public enum RejeicaoCaronaSentido {
		REJEICAO_MOTORISTA, // motorista rejeitou a carona
		REJEICAO_PASSAGEIRO // passageiro rejeitou
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_rejeicao_carona")
	private int id;

	@OneToOne
	@JoinColumn(name = "id_usuario_motorista")
	private Usuario usuarioMotorista;

	@OneToOne
	@JoinColumn(name = "id_usuario_passageiro")
	private Usuario usuarioPassageiro;

	@Column(name = "sentido")
	@Enumerated(EnumType.STRING)
	private RejeicaoCaronaSentido sentido;

	public Usuario getUsuarioMotorista() {
		return usuarioMotorista;
	}

	public void setUsuarioMotorista(Usuario usuarioMotorista) {
		this.usuarioMotorista = usuarioMotorista;
	}

	public Usuario getUsuarioPassageiro() {
		return usuarioPassageiro;
	}

	public void setUsuarioPassageiro(Usuario usuarioPassageiro) {
		this.usuarioPassageiro = usuarioPassageiro;
	}

	public RejeicaoCaronaSentido getSentido() {
		return sentido;
	}

	public void setSentido(RejeicaoCaronaSentido sentido) {
		this.sentido = sentido;
	}

}
