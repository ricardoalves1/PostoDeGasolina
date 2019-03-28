package com.postoGasolina.model;

import java.sql.SQLException;

import com.postoGasolina.dao.ClienteFisicaDao;
import com.postoGasolina.dao.ClienteJuridicaDao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Cliente {
	
	private Cliente_fisica cliente_fisica;
	private Cliente_juridica cliente_juridica;
	
	private String tipoCliente;
	
	public Cliente(){
		
	}

	public Cliente(Cliente_fisica cliente_fisica, String tipoCliente) {
		this.cliente_fisica = cliente_fisica;
		this.tipoCliente = tipoCliente;
	}

	public Cliente(Cliente_juridica cliente_juridica, String tipoCliente) {
		this.cliente_juridica = cliente_juridica;
		this.tipoCliente = tipoCliente;
	}

	public Cliente(Object cliente, String tipoCliente) {
		if (cliente instanceof Cliente_fisica) {
			new Cliente((Cliente_fisica) cliente, tipoCliente);
		}
		else {
			new Cliente((Cliente_juridica) cliente, tipoCliente);
		}
	}
	
	@Override
	public String toString() {
		return
			tipoCliente.equals("cliente_fisica") ? cliente_fisica.getPessoa().getNome() :
			tipoCliente.equals("cliente_juridica") ? cliente_juridica.getNome() : "";
	}

	public Cliente_fisica getCliente_fisica() {
		return cliente_fisica;
	}

	public Cliente_juridica getCliente_juridica() {
		return cliente_juridica;
	}

	public String getTipoCliente() {
		return tipoCliente;
	}

	public ObservableList<Cliente> listar(){
		ObservableList<Cliente> lista_clientes = FXCollections.observableArrayList();
		
		
		try {
			//listando cliente física
			new ClienteFisicaDao().listar().forEach(clienteFisica ->{
				lista_clientes.add(new Cliente(clienteFisica,"cliente_fisica"));
			});
			
			//listando cliente jurídica
			new ClienteJuridicaDao().listar().forEach(clienteJuridica ->{
				lista_clientes.add(new Cliente(clienteJuridica, "cliente_juridica"));
			});
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return lista_clientes;
	}

}
