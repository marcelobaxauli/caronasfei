package com.caronasfei.db.cadastro;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cadastro_etapa")
public class CadastroEtapa {

	public enum CadastroEtapaCodigo {

		DADOS_PESSOAIS("DAPE", "/caronasfei/registrar/dadospessoais"), 
		AUTENTICACAO_FEI("AUTFEI", "/caronasfei/registrar/autenticacaofei"), 
		PERFIL_USUARIO("PRFUSU", "/caronasfei/registrar/perfil"), 
		COMPLETO("COMPL", "/caronasfei/");

		private String codigo;
		private String url;

		private CadastroEtapaCodigo(String codigo, String url) {
			this.codigo = codigo;
			this.url = url;
		}

		public String getCodigo() {
			return codigo;
		}

		public void setCodigo(String codigo) {
			this.codigo = codigo;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_cadastro_etapa")
	private int id;

	@Column(name = "ds_cadastro_etapa")
	private String descricao;

	@Column(name = "cd_cadastro_etapa")
	private String codigo;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

}
