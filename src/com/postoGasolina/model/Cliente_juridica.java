package com.postoGasolina.model;

import java.time.LocalDate;

import org.apache.commons.lang3.text.WordUtils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Cliente_juridica {

	private int id_cliente_juridica;
	private Endereco endereco;
	private String nome;
	private String cnpj;
	private String situacao;
	private LocalDate data_situacao;
	private String ie;
	private String email;
	private String observacao;
	
	private ObservableList<Telefone> listaTelefone = FXCollections.observableArrayList();

	public Cliente_juridica(Builder builder) {
		this.id_cliente_juridica = builder.id_cliente_juridica;
		this.endereco = builder.endereco;
		this.nome = builder.nome;
		this.cnpj = builder.cnpj;
		this.situacao = builder.situacao;
		this.data_situacao = builder.data_situacao;
		this.ie = builder.ie;
		this.email = builder.email;
		this.observacao = builder.observacao;
		this.listaTelefone = builder.listaTelefone;
	}

	public static class Builder {

		private int id_cliente_juridica;
		private Endereco endereco;
		private String nome;
		private String cnpj;
		private String situacao;
		private LocalDate data_situacao;
		private String ie;
		private String email;
		private String observacao;
		private ObservableList<Telefone> listaTelefone = FXCollections.observableArrayList();

		public Builder idCliente(int id_cliente_juridica) {
			this.id_cliente_juridica = id_cliente_juridica;
			return this;
		}

		public Builder endereco(Endereco endereco) {
			this.endereco = endereco;
			return this;
		}

		public Builder nome(String nome) {
			this.nome = WordUtils.capitalize(nome.toLowerCase());
			return this;
		}

		public Builder cnpj(String cnpj) {
			this.cnpj = cnpj;
			return this;
		}

		public Builder situacao(String situacao) {
			this.situacao = situacao;
			return this;
		}

		public Builder dataSituacao(LocalDate data_situacao) {
			this.data_situacao = data_situacao;
			return this;
		}

		public Builder ie(String ie) {
			this.ie = ie;
			return this;
		}

		public Builder email(String email) {
			this.email = email;
			return this;
		}

		public Builder observacao(String observacao) {
			this.observacao = observacao;
			return this;
		}

		public Builder telefone(ObservableList<Telefone> listaTelefone) {
			this.listaTelefone = listaTelefone;
			return this;
		}

		public Cliente_juridica build() {
			return new Cliente_juridica(this);
		}

	}

	public int getId_cliente_juridica() {
		return id_cliente_juridica;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getSituacao() {
		return situacao;
	}

	public LocalDate getData_situacao() {
		return data_situacao;
	}

	public String getIe() {
		return ie;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public ObservableList<Telefone> getListaTelefone() {
		return listaTelefone;
	}

}