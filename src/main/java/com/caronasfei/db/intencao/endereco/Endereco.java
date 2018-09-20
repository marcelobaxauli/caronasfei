package com.caronasfei.db.intencao.endereco;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "endereco")
public class Endereco {

	public enum OpcaoEndereco {

		OUTRO("outro"), FEI_SBC("fei");

		private String codigo;

		private OpcaoEndereco(String codigo) {
			this.codigo = codigo;
		}

		public String getCodigo() {
			return codigo;
		}

		public static OpcaoEndereco fromCodigo(String codigo) {

			OpcaoEndereco[] values = OpcaoEndereco.values();

			for (OpcaoEndereco value : values) {

				if (value.getCodigo().equals(codigo)) {
					return value;
				}

			}

			return null;

		}

	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_endereco")
	private int id;

	@Enumerated(EnumType.STRING)
	@Column(name = "opcao_endereco")
	private OpcaoEndereco opcaoEndereco;

	private String rua;

	private int numero;

	private String bairro;

	private String cidade;

	private String cep;

	private String estado;

	private double latitude;

	private double longitude;

	public String getRua() {
		return rua;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String getCidade() {
		return cidade;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public OpcaoEndereco getOpcaoEndereco() {
		return opcaoEndereco;
	}

	public void setOpcaoEndereco(OpcaoEndereco opcaoEndereco) {
		this.opcaoEndereco = opcaoEndereco;
	}

	public String getEnderecoFormatado() {

		String endereco = this.getRua() + " " + this.getNumero() + ", " + this.getBairro() + ", " + this.getCidade()
				+ ", " + this.getCep();

		return endereco;

	}

}
