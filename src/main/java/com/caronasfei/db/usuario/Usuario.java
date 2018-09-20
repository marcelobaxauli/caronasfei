package com.caronasfei.db.usuario;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.caronasfei.db.cadastro.Cadastro;
import com.caronasfei.db.cadastro.CadastroEtapa.CadastroEtapaCodigo;
import com.caronasfei.db.curso.CursoPeriodo;
import com.caronasfei.db.perfil.Perfil;

@Entity
@Table(name = "usuario")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_usuario")
	private Long id;

	@Column(name = "nome")
	private String nome;

	@Lob
	@Column(name = "foto")
	private byte[] foto;

	@Column(name = "email")
	private String email;

	@Column(name = "senha")
	private String senha;

	@Column(name = "numero_celular")
	private String numeroCelular;

	@OneToOne
	@JoinColumn(name = "id_curso_periodo")
	private CursoPeriodo cursoPeriodo;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_perfil")
	private Perfil perfil;

	@Column(name = "cidade")
	private String cidade;

	// flag que indica se o usuário já
	// confirmou suas credenciais FEI
	@Column(name = "autenticado_fei")
	private boolean autenticadoFei;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_cadastro")
	private Cadastro cadastro;

	public long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public byte[] getFoto() {
		return foto;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getNumeroCelular() {
		return numeroCelular;
	}

	public void setNumeroCelular(String numeroCelular) {
		this.numeroCelular = numeroCelular;
	}

	public CursoPeriodo getCursoPeriodo() {
		return cursoPeriodo;
	}

	public void setCursoPeriodo(CursoPeriodo cursoPeriodo) {
		this.cursoPeriodo = cursoPeriodo;
	}

	public boolean isAutenticadoFei() {
		return autenticadoFei;
	}

	public void setAutenticadoFei(boolean autenticadoFei) {
		this.autenticadoFei = autenticadoFei;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public Cadastro getCadastro() {
		return cadastro;
	}

	public void setCadastro(Cadastro cadastro) {
		this.cadastro = cadastro;
	}

	public boolean isCadastrado() {

		if (this.cadastro != null && this.cadastro.getCadastroEtapa() != null
				&& this.cadastro.getCadastroEtapa().getCodigo().equals(CadastroEtapaCodigo.COMPLETO.getCodigo())) {
			return true;
		}

		return false;

	}

}
