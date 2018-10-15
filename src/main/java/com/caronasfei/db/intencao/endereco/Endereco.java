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

	public static final int ID_ENDERECO_FEI_SBC = 1;

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bairro == null) ? 0 : bairro.hashCode());
		result = prime * result + ((cep == null) ? 0 : cep.hashCode());
		result = prime * result + ((cidade == null) ? 0 : cidade.hashCode());
		result = prime * result + ((estado == null) ? 0 : estado.hashCode());
		result = prime * result + id;
		long temp;
		temp = Double.doubleToLongBits(latitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(longitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + numero;
		result = prime * result + ((opcaoEndereco == null) ? 0 : opcaoEndereco.hashCode());
		result = prime * result + ((rua == null) ? 0 : rua.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Endereco other = (Endereco) obj;
		if (bairro == null) {
			if (other.bairro != null)
				return false;
		} else if (!bairro.equals(other.bairro))
			return false;
		if (cep == null) {
			if (other.cep != null)
				return false;
		} else if (!cep.equals(other.cep))
			return false;
		if (cidade == null) {
			if (other.cidade != null)
				return false;
		} else if (!cidade.equals(other.cidade))
			return false;
		if (estado == null) {
			if (other.estado != null)
				return false;
		} else if (!estado.equals(other.estado))
			return false;
		if (id != other.id)
			return false;
		if (Double.doubleToLongBits(latitude) != Double.doubleToLongBits(other.latitude))
			return false;
		if (Double.doubleToLongBits(longitude) != Double.doubleToLongBits(other.longitude))
			return false;
		if (numero != other.numero)
			return false;
		if (opcaoEndereco != other.opcaoEndereco)
			return false;
		if (rua == null) {
			if (other.rua != null)
				return false;
		} else if (!rua.equals(other.rua))
			return false;
		return true;
	}

}
