package com.postoGasolina.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Cliente_Gasto {

	private int id;
	private String cliente;
	private BigDecimal gastoMensal;
	private BigDecimal  gastoAnual;
	private BigDecimal gastoTotal;
	private LocalDate dataNascimento;
	private String tipo_cliente;
	private String email;
	
	public Cliente_Gasto(int id, String cliente, BigDecimal gastoMensal, BigDecimal gastoAnual, BigDecimal gastoTotal,
			LocalDate dataNascimento, String tipo_cliente, String email) {
		this.id = id;
		this.cliente = cliente;
		this.gastoMensal = gastoMensal;
		this.gastoAnual = gastoAnual;
		this.gastoTotal = gastoTotal;
		this.dataNascimento = dataNascimento;
		this.tipo_cliente = tipo_cliente;
		this.email = email;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public BigDecimal getGastoMensal() {
		return gastoMensal;
	}

	public BigDecimal getGastoAnual() {
		return gastoAnual;
	}

	public BigDecimal getGastoTotal() {
		return gastoTotal;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public String getTipo_cliente() {
		return tipo_cliente;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}