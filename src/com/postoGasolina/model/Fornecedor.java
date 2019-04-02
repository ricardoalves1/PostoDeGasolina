package com.postoGasolina.model;

import java.time.LocalDate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Fornecedor {
	
	private int id_fornecedor;
	private Endereco endereco;
	private String razao_social;
	private String cnpj;
	private String ie;
	private String email;
	private String site;
	private String status;
	private String observacoes;
	private LocalDate data_situacao;
	
	private ObservableList<Telefone> listaTelefone = FXCollections.observableArrayList();
	
	@Override
	public String toString() {
		return razao_social;
	}

	public Fornecedor(int id_fornecedor, Endereco endereco, String razao_social, String cnpj, String ie, String email,
			String site, String status, String observacoes, LocalDate data_situacao, ObservableList<Telefone> listaTelefone) {
		this.id_fornecedor = id_fornecedor;
		this.endereco = endereco;
		this.razao_social = razao_social;
		this.cnpj = cnpj;
		this.ie = ie;
		this.email = email;
		this.site = site;
		this.status = status;
		this.observacoes = observacoes;
		this.data_situacao = data_situacao;
		this.listaTelefone = listaTelefone;
	}

	public int getId_fornecedor() {
		return id_fornecedor;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public String getRazao_social() {
		return razao_social;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
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

	public String getSite() {
		return site;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getObservacoes() {
		return observacoes;
	}

	public LocalDate getData_situacao() {
		return data_situacao;
	}

	public ObservableList<Telefone> getListaTelefone() {
		return listaTelefone;
	}

}
