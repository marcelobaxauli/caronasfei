package com.caronasfei.db.usuario;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.caronasfei.db.perfil.Perfil;

@Entity
@Table(name = "usuario_perfil")
public class UsuarioPerfil {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_usuario_perfil")
	private int id;

	@OneToOne
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;

	@OneToOne
	@JoinColumn(name = "id_perfil")
	private Perfil perfil;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

}
