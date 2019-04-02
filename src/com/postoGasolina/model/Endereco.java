package com.postoGasolina.model;

public class Endereco {
	
	//Endereço
	private int id_endereco;
	private String cep;
	private String endereco;
	private String numero;
	private String complemento;
	private String bairro;
	private String cidade;
	private String estado;
	
	public Endereco(int id_endereco, String estado){
		this.id_endereco = id_endereco;
		this.estado = estado;
	}

	public Endereco(Builder builder) {
		this.id_endereco = builder.id_endereco;
		this.cep = builder.cep;
		this.endereco = builder.endereco;
		this.numero = builder.numero;
		this.complemento = builder.complemento;
		this.bairro = builder.bairro;
		this.cidade = builder.cidade;
		this.estado = builder.estado;
	}

	public static class Builder {

		private int id_endereco;
		private String cep;
		private String endereco;
		private String numero;
		private String complemento;
		private String bairro;
		private String cidade;
		private String estado;

		public Builder idEndereco(int id_endereco) {
			this.id_endereco = id_endereco;
			return this;
		}

		public Builder cep(String cep) {
			this.cep = cep;
			return this;
		}

		public Builder endereco(String endereco) {
			this.endereco = endereco;
			return this;
		}

		public Builder numero(String numero) {
			this.numero = numero;
			return this;
		}

		public Builder complemento(String complemento) {
			this.complemento = complemento;
			return this;
		}

		public Builder bairro(String bairro) {
			this.bairro = bairro;
			return this;
		}

		public Builder cidade(String cidade) {
			this.cidade = cidade;
			return this;
		}

		public Builder estado(String estado) {
			this.estado = estado;
			return this;
		}

		public Endereco build() {
			return new Endereco(this);
		}

	}

	@Override
	public String toString() {
		return "Endereco [id_endereco=" + id_endereco + ", cep=" + cep + ", endereco=" + endereco + ", numero=" + numero
				+ ", complemento=" + complemento + ", bairro=" + bairro + ", cidade=" + cidade + ", estado=" + estado
				+ "]";
	}

	public int getId_endereco() {
		return id_endereco;
	}

	public void setId_endereco(int id_endereco) {
		this.id_endereco = id_endereco;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

}