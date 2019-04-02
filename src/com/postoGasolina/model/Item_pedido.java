package com.postoGasolina.model;

import java.math.BigDecimal;

public class Item_pedido {
	private int idItem;
	private Produto_loja produto_loja;
	private int id_pedido;
	private BigDecimal preco_unitario;
	private BigDecimal quantidade;
	private String tipo_produto;
	private BigDecimal total;

	public Item_pedido(Builder builder) {
		this.produto_loja = builder.produto_loja;
		this.id_pedido = builder.id_pedido;
		this.preco_unitario = builder.preco_unitario;
		this.quantidade = builder.quantidade;
		this.tipo_produto = builder.tipo_produto;
		this.total = builder.total;
		this.idItem = builder.idItem;
	}

	public static class Builder {

		private int idItem;
		private Produto_loja produto_loja;
		private int id_pedido;
		private BigDecimal preco_unitario;
		private BigDecimal quantidade;
		private String tipo_produto;
		private BigDecimal total;

		public Builder idItem(int idItem) {
			this.idItem = idItem;
			return this;
		}

		public Builder produtoLoja(Produto_loja produto_loja) {
			this.produto_loja = produto_loja;
			return this;
		}

		public Builder idPedido(int id_pedido) {
			this.id_pedido = id_pedido;
			return this;
		}

		public Builder precoUnitario(BigDecimal preco_unitario) {
			this.preco_unitario = preco_unitario;
			return this;
		}

		public Builder quantidade(BigDecimal quantidade) {
			this.quantidade = quantidade;
			return this;
		}

		public Builder tipoProduto(String tipo_produto) {
			this.tipo_produto = tipo_produto;
			return this;
		}

		public Builder total(BigDecimal total) {
			this.total = total;
			return this;
		}

		public Item_pedido build() {
			return new Item_pedido(this);
		}

	}
	
	public Produto_loja getProduto_loja() {
		return produto_loja;
	}

	public BigDecimal getPreco_unitario() {
		return preco_unitario;
	}

	public BigDecimal getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(BigDecimal quantidade) {
		this.quantidade = quantidade;
	}

	public String getTipo_produto() {
		return tipo_produto;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public int getIdItem() {
		return idItem;
	}

}
