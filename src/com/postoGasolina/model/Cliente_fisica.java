package com.postoGasolina.model;

import java.sql.SQLException;

import com.postoGasolina.dao.ClienteFisicaDao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Cliente_fisica {

	private int id_cliente_fisica;
	private Pessoa pessoa;
	private Endereco endereco;
	private String pai;
	private String mae;
	private String email;
	private String informacao;
	private ObservableList<Telefone> listaTelefone = FXCollections.observableArrayList();

	public Cliente_fisica(Builder builder) {
		this.id_cliente_fisica = builder.id_cliente_fisica;
		this.pessoa = builder.pessoa;
		this.endereco = builder.endereco;
		this.pai = builder.pai;
		this.mae = builder.mae;
		this.email = builder.email;
		this.informacao = builder.informacao;
		this.listaTelefone = builder.listaTelefone;
	}

	public static class Builder {

		private int id_cliente_fisica;
		private Pessoa pessoa;
		private Endereco endereco;
		private String pai;
		private String mae;
		private String email;
		private String informacao;
		private ObservableList<Telefone> listaTelefone = FXCollections.observableArrayList();

		public Builder id(int id_cliente_fisica) {
			this.id_cliente_fisica = id_cliente_fisica;
			return this;
		}

		public Builder pessoa(Pessoa pessoa) {
			this.pessoa = pessoa;
			return this;
		}

		public Builder endereco(Endereco endereco) {
			this.endereco = endereco;
			return this;
		}

		public Builder pai(String pai) {
			this.pai = pai;
			return this;
		}

		public Builder mae(String mae) {
			this.mae = mae;
			return this;
		}

		public Builder email(String email) {
			this.email = email;
			return this;
		}

		public Builder informacao(String informacao) {
			this.informacao = informacao;
			return this;
		}

		public Builder telefone(ObservableList<Telefone> listaTelefone) {
			this.listaTelefone = listaTelefone;
			return this;
		}

		public Cliente_fisica build() {
			return new Cliente_fisica(this);
		}

	}

	@Override
	public String toString() {
		return "Cliente_fisica [id_cliente_fisica=" + id_cliente_fisica + ", pessoa=" + pessoa + ", endereco="
				+ endereco + ", pai=" + pai + ", mae=" + mae + ", email=" + email + ", informacao=" + informacao + "]";
	}

	public int getId_cliente_fisica() {
		return id_cliente_fisica;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public String getPai() {
		return pai;
	}

	public String getMae() {
		return mae;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getInformacao() {
		return informacao;
	}

	public ObservableList<Telefone> getListaTelefone() {
		return listaTelefone;
	}

	public static byte cadastrar(Cliente_fisica clienteFisica) {
		// Validaçoes

		try {
			new ClienteFisicaDao().cadastrar(clienteFisica);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static byte alterar(Cliente_fisica clienteFisica) {
		// Validaçoes

		try {
			new ClienteFisicaDao().alterar(clienteFisica);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

}