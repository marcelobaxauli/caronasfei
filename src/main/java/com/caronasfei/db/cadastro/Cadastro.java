package com.caronasfei.db.cadastro;

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
@Table(name = "cadastro")
public class Cadastro {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_cadastro")
	private int id;

	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "id_cadastro_etapa")
	private CadastroEtapa cadastroEtapa;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public CadastroEtapa getCadastroEtapa() {
		return cadastroEtapa;
	}

	public void setCadastroEtapa(CadastroEtapa cadastroEtapa) {
		this.cadastroEtapa = cadastroEtapa;
	}

}
