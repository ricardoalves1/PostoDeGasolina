package com.postoGasolina.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.Locale;

import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTreeTableView;
import com.postoGasolina.dao.FuncionarioDao;
import com.postoGasolina.main.Tela;
import com.postoGasolina.model.Cliente;
import com.postoGasolina.model.Fluxo_caixa;
import com.postoGasolina.model.Funcionario;
import com.postoGasolina.model.Item_pedido;
import com.postoGasolina.model.Produto_loja;
import com.postoGasolina.util.AutoShowComboBoxHelper;
import com.postoGasolina.util.NumeroTextField;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class TelaVendaController extends CompraVenda implements Initializable {

	@FXML
	private ImageView btnCliente;

	private NumeroTextField campoTotalVenda = new NumeroTextField(BigDecimal.ZERO,
			NumberFormat.getCurrencyInstance(new Locale("pt", "BR")));

	@FXML
	private JFXTreeTableView<CompraVendaClass> treeTableViewVenda;

	private JFXSnackbar snackBar;

	ComboBox<Cliente> comboBoxCliente = new ComboBox<>();

	ComboBox<Funcionario> comboBoxFuncionario = new ComboBox<>();

	@FXML
	void mouseEventCompra(MouseEvent event) {
		if (!lista_itensPedido.isEmpty()) {
			campoDesconto.setNumber(RecebePedido.getDesconto());
		}
		if (RecebePedido.limparVenda == 1) {
			limparcamposCompleto();
			RecebePedido.setLimparVenda(0);
		}
		if(mensagem.getText().equals("1")){
			snackBar = new JFXSnackbar(borderPaneTabela);
			snackBar.show("Venda efetuada com sucesso", 4000);
			mensagem.setText("0");
		}
		if(mensagem.getText().equals("3")){
			snackBar = new JFXSnackbar(borderPaneTabela);
			snackBar.show("Desconto adicionado com sucesso", 4000);
			mensagem.setText("0");
		}
		if(mensagem.getText().equals("4")){
			snackBar = new JFXSnackbar(borderPaneTabela);
			snackBar.show("Cliente cadastrado com sucesso", 4000);
			mensagem.setText("0");
		}
	}

	@Override
	void limparcamposCompleto() {
		lista_itensPedido.clear();
		carregarTabela();
		RecebePedido.setDesconto(BigDecimal.ZERO);
		limparcampos();
		campoTotalVenda.setNumber(BigDecimal.ZERO);
		campoDesconto.setNumber(BigDecimal.ZERO);
		comboBoxCliente.setValue(null);
		comboBoxFuncionario.setValue(null);

	}

	@FXML
	void btnAdicionar(ActionEvent event) {

		if (comboBoxProduto.getValue() != null) {
			if (!campoQuantidade.getText().isEmpty() && !campoQuantidade.getText().equals("0")) {

				boolean qtdProduto;
				boolean qtdCombustivel;
				BigDecimal qtdAtualizado = new BigDecimal("0");
				qtdProduto = true;
				qtdCombustivel = true;

				if (comboBoxProduto.getValue().getTipo_produto().equals("combustivel")) {

					qtdAtualizado = comboBoxProduto.getValue().getCombustivel().getTipoCombustivel().getQuantidade();

					for (Item_pedido item : lista_itensPedido) {
						if (item.getTipo_produto().equals("combustivel")) {

							if (item.getProduto_loja().getCombustivel().getId_combustivel() == comboBoxProduto
									.getValue().getCombustivel().getId_combustivel()) {

								qtdAtualizado = qtdAtualizado.subtract(item.getQuantidade());

							}

						}
					}

					if (qtdAtualizado.compareTo(new BigDecimal(campoQuantidade.getText())) == -1) { /// é
						qtdCombustivel = false;

					}
				} else if (comboBoxProduto.getValue().getTipo_produto().equals("produto")) {

					qtdAtualizado = comboBoxProduto.getValue().getProduto().getEstoque_disponivel();

					for (Item_pedido item : lista_itensPedido) {
						if (item.getTipo_produto().equals("produto")) {

							if (!item.getProduto_loja().getProduto().isNao_controlar_estoque()) {
								if (item.getProduto_loja().getProduto().getId_produto() == comboBoxProduto.getValue()
										.getProduto().getId_produto()) {

									qtdAtualizado = qtdAtualizado.subtract(item.getQuantidade());

								}
							}
						}
					}

					if (qtdAtualizado.compareTo(new BigDecimal(campoQuantidade.getText())) == -1) { /// é
						/// menorr
						qtdProduto = false;
					}
				}

				if (qtdProduto && qtdCombustivel) {
					lista_itensPedido.add(
							new Item_pedido.Builder()
									.produtoLoja(comboBoxProduto.getSelectionModel().getSelectedItem())
									.idPedido(0)
									.precoUnitario(campoPreco.getNumber())
									.quantidade(new BigDecimal(campoQuantidade.getText()))
									.tipoProduto(comboBoxProduto.getSelectionModel().getSelectedItem().getTipo_produto())
									.total(campoPreco.getNumber().multiply(new BigDecimal(campoQuantidade.getText())))
									.idItem(idtabela++)
									.build()
					);

					limparcampos();
					carregarTabela();

					carregarTotalVenda();

					snackBar = new JFXSnackbar(borderPaneTabela);
					snackBar.show("Produto adicionado com sucesso", 4000);
				} else {
					snackBar = new JFXSnackbar(borderPaneTabela);
					snackBar.show("Essa quandidade não possui em estoque", 4000);

				}

			} else {

				snackBar = new JFXSnackbar(borderPaneTabela);
				snackBar.show("Informa quantidade", 4000);
			}
		} else {
			snackBar = new JFXSnackbar(borderPaneTabela);
			snackBar.show("Seleciona um produto", 4000);
		}

	}

	private void carregarTotalVenda() {

		campoTotalVenda.setNumber(carregarTotal());

	}

	@FXML
	void btnDesconto(ActionEvent event) {
		RecebePedido.setTotal_pagar(campoTotalVenda.getNumber());
		new Tela().carregarTelaDesconto();
	}

	@FXML
	void btnFechamento(ActionEvent event) {
		try {
			new RecebePedido(0, comboBoxFuncionario.getValue(), comboBoxCliente.getValue(), new Fluxo_caixa(),
					campoTotalVenda.getNumber(), campoDesconto.getNumber(), "", lista_itensPedido);

			new Tela().carregarTelaFecharVenda();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@FXML
	void BtnExcluirMouseClicked(MouseEvent event) {
		System.out.println(treeTableViewVenda.getSelectionModel().getSelectedIndex() != -1);
		if (treeTableViewVenda.getSelectionModel().getSelectedIndex() != -1) {

			try {
				int id = Integer
						.valueOf(treeTableViewVenda.getSelectionModel().getSelectedItem().getValue().toString());


				for (int i = 0; i < lista_itensPedido.size(); ++i) {
					if (lista_itensPedido.get(i).getIdItem() == id) {
						lista_itensPedido.remove(i);

						carregarTabela();
						carregarTotalVenda();

						snackBar = new JFXSnackbar(borderPaneTabela);
						snackBar.show("Produto removido com sucesso", 4000);

						break;
					}
				}
			} catch (Exception e) {
				//
			}

		}else {
			snackBar = new JFXSnackbar(borderPaneTabela);
			snackBar.show("Seleciona produto na tabela", 4000);
		}
	}

	@Override
	void carregarComponentes() throws SQLException, ClassNotFoundException {

		String style = getClass().getResource("/com/postoGasolina/style/TelaVenda.css").toExternalForm();

		campoDesconto.getStylesheets().add(style);
		campoDesconto.getStyleClass().add("format-campo");
		campoDesconto.setUnFocusColor(Color.TRANSPARENT);
		campoDesconto.setFocusColor(Color.TRANSPARENT);
		campoDesconto.setAlignment(Pos.CENTER);
		campoDesconto.setEditable(false);
		campoDesconto.setFocusTraversable(false);
		gridPaneBottom.add(campoDesconto, 1, 0);
		gridPaneBottom.setMargin(campoDesconto, new Insets(0, 0, 0, 0));

		campoTotalVenda.getStylesheets().add(style);
		campoTotalVenda.getStyleClass().add("format-campo");
		campoTotalVenda.setUnFocusColor(Color.TRANSPARENT);
		campoTotalVenda.setFocusColor(Color.TRANSPARENT);
		campoTotalVenda.setAlignment(Pos.CENTER);
		campoTotalVenda.setEditable(false);
		campoTotalVenda.setFocusTraversable(false);
		gridPaneBottom.add(campoTotalVenda, 2, 0);
		gridPaneBottom.setMargin(campoTotalVenda, new Insets(0, 0, 0, 0));

		style = getClass().getResource("/com/postoGasolina/style/TelaGerenciarFuncionario.css").toExternalForm();

		campoPreco.getStylesheets().add(style);
		campoPreco.getStyleClass().add("formata-campo");
		campoPreco.setUnFocusColor(Color.WHITE);
		campoPreco.setFocusColor(Color.WHITE);
		campoPreco.setEditable(false);
		campoPreco.setFocusTraversable(false);
		gridPaneTop.add(campoPreco, 1, 3);
		gridPaneTop.setMargin(campoPreco, new Insets(10, 270, 0, 120));

		campoTotal.getStylesheets().add(style);
		campoTotal.getStyleClass().add("formata-campo");
		campoTotal.setUnFocusColor(Color.WHITE);
		campoTotal.setFocusColor(Color.WHITE);
		campoTotal.setEditable(false);
		campoTotal.setFocusTraversable(false);
		gridPaneTop.add(campoTotal, 1, 3);
		gridPaneTop.setMargin(campoTotal, new Insets(10, 140, 0, 240));

		campoQuantidade.getStylesheets().add(style);
		campoQuantidade.getStyleClass().add("formata-campo");
		campoQuantidade.setUnFocusColor(Color.WHITE);
		campoQuantidade.setFocusColor(Color.WHITE);
		campoQuantidade.setFocusTraversable(false);
		gridPaneTop.add(campoQuantidade, 1, 3);
		gridPaneTop.setMargin(campoQuantidade, new Insets(10, 390, 0, 0));

		campoPreco.setLabelFloat(true);
		campoTotal.setLabelFloat(true);
		campoQuantidade.setLabelFloat(true);
		campoPreco.setPromptText("Preço");
		campoTotal.setPromptText("Total");
		campoQuantidade.setPromptText("Quantidade *");

		// COMBOBOX PESQUISAR

		comboBoxCliente.setItems(new Cliente().listar());

		style = getClass().getResource("/com/postoGasolina/style/TelaGerenciarFuncionarioComboBox.css")
				.toExternalForm();
		comboBoxCliente.getStylesheets().add(style);
		comboBoxCliente.getStyleClass().add("formata-campo");
		comboBoxCliente.setPrefWidth(385);
		comboBoxCliente.setPromptText("Pesquisar Cliente ...");
		comboBoxCliente.setPadding(new Insets(0, 0, 0, -10));
		comboBoxCliente.setFocusTraversable(false);
		new AutoShowComboBoxHelper(comboBoxCliente);
		gridPaneTop.add(comboBoxCliente, 0, 1);
		gridPaneTop.setMargin(comboBoxCliente, new Insets(11, 0, 0, 50));

		comboBoxCliente.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if (comboBoxCliente.isFocused()) {

				comboBoxCliente.setPromptText("");

				comboBoxCliente.setItems(new Cliente().listar());
			} else {
				comboBoxCliente.setPromptText("Pesquisar Cliente ...");
			}

		});

		comboBoxFuncionario.setItems(new FuncionarioDao().listar());

		style = getClass().getResource("/com/postoGasolina/style/TelaGerenciarFuncionarioComboBox.css")
				.toExternalForm();
		comboBoxFuncionario.getStylesheets().add(style);
		comboBoxFuncionario.getStyleClass().add("formata-campo");
		comboBoxFuncionario.setPrefWidth(367);
		comboBoxFuncionario.setPromptText("Pesquisar Funcionário ...");
		comboBoxFuncionario.setPadding(new Insets(0, 0, 0, -10));
		comboBoxFuncionario.setFocusTraversable(false);
		new AutoShowComboBoxHelper(comboBoxFuncionario);
		gridPaneTop.add(comboBoxFuncionario, 1, 1);
		gridPaneTop.setMargin(comboBoxFuncionario, new Insets(11, 0, 0, 0));

		comboBoxFuncionario.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if (comboBoxFuncionario.isFocused()) {
				comboBoxFuncionario.setPromptText("");
				try {
					comboBoxFuncionario.setItems(new FuncionarioDao().listar());
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} else {
				comboBoxFuncionario.setPromptText("Pesquisar Funcionário ...");
			}
		});

		comboBoxProduto.setItems(new Produto_loja().listar());

		style = getClass().getResource("/com/postoGasolina/style/TelaGerenciarFuncionarioComboBox.css")
				.toExternalForm();
		comboBoxProduto.getStylesheets().add(style);
		comboBoxProduto.getStyleClass().add("formata-campo");
		comboBoxProduto.setPrefWidth(385);
		comboBoxProduto.setPromptText("Pesquisar Produtos ...");
		comboBoxProduto.setPadding(new Insets(0, 0, 0, -10));
		comboBoxProduto.setFocusTraversable(false);
		new AutoShowComboBoxHelper(comboBoxProduto);
		gridPaneTop.add(comboBoxProduto, 0, 2);
		gridPaneTop.setMargin(comboBoxProduto, new Insets(60, 0, 0, 50));

		comboBoxProduto.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if (comboBoxProduto.isFocused()) {
				comboBoxProduto.setPromptText("");
				comboBoxProduto.setItems(new Produto_loja().listar());
				campoQuantidade.setText("0");
			} else {
				comboBoxProduto.setPromptText("Pesquisar Produtos ...");
			}

		});

		try {
			comboBoxProduto.valueProperty().addListener(new ChangeListener<Produto_loja>() {

				@Override
				public void changed(ObservableValue<? extends Produto_loja> observable, Produto_loja oldValue,
									Produto_loja newValue) {

					campoQuantidade.setText("0");
					campoPreco.setNumber(BigDecimal.ZERO);
					campoTotal.setNumber(BigDecimal.ZERO);

					if (newValue != null) {
						if (comboBoxProduto.getSelectionModel() != null) {
							if (comboBoxProduto.getSelectionModel().getSelectedIndex() != -1) {
								if (comboBoxProduto.getSelectionModel().getSelectedItem()
										.getTipo_produto().equals("combustivel")) {
									campoQuantidade.setEditable(true);
									BigDecimal preco = comboBoxProduto.getSelectionModel().getSelectedItem()
											.getCombustivel().getPreco_venda();
									campoPreco.setNumber(preco);
								} else if (comboBoxProduto.getSelectionModel().getSelectedItem()
										.getTipo_produto().equals("produto")) {
									BigDecimal preco = comboBoxProduto.getSelectionModel().getSelectedItem()
											.getProduto().getPreco_venda();
									campoPreco.setNumber(preco);
									if (comboBoxProduto.getSelectionModel().getSelectedItem().getProduto()
											.isNao_controlar_estoque()) {
										campoQuantidade.setEditable(false);
										campoQuantidade.setText("1");
									} else {
										campoQuantidade.setEditable(true);
									}
								}
							}
						}
					}

				}
			});
		} catch (Exception e2) {
			//
		}

		campoQuantidade.setOnKeyTyped(event -> {
			String input = event.getCharacter();
			String numeros = "0123456789.";
			if (!numeros.contains(input + "")) {
				event.consume();
			}
		});

		campoQuantidade.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				try {
					if (!newValue.isEmpty()) {
						final NumeroTextField preco = new NumeroTextField(campoPreco.getNumber());
						final NumeroTextField qtdAtualizado = new NumeroTextField(new BigDecimal(newValue));
						campoTotal.setNumber(preco.getNumber().multiply(qtdAtualizado.getNumber()));
					} else {
						campoTotal.setNumber(BigDecimal.ZERO);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});

		campoDesconto.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				carregarTotalVenda();
				campoDesconto.setNumber(RecebePedido.getDesconto());
			}
		});

	}

	@Override
	void carregarTabela() {

		atualizarTabela();
		tabela(treeTableViewVenda);

	}

	void atualizarTabela() {

		try {
			treeTableViewVenda = FXMLLoader
					.load(getClass().getClassLoader().getResource("com/postoGasolina/view/TreeTableviewModelo.fxml"));
			borderPaneTabela.setCenter(treeTableViewVenda);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static class RecebePedido {

		private static int id_pedido_venda;
		private static Funcionario funcionario;
		private static Cliente cliente;
		private static Fluxo_caixa fluxoCaixa;
		private static BigDecimal total_pagar;
		private static BigDecimal desconto;
		private static String forma_pagamento;
		private static int limparVenda;
		private static ObservableList<Item_pedido> itens_pedido = FXCollections.observableArrayList();


		public RecebePedido(int id_pedido_venda, Funcionario funcionario, Cliente cliente, Fluxo_caixa fluxoCaixa,
							BigDecimal total_pagar, BigDecimal desconto, String forma_pagamento,
							ObservableList<Item_pedido> itens_pedido) {
			RecebePedido.id_pedido_venda = id_pedido_venda;
			RecebePedido.funcionario = funcionario;
			RecebePedido.cliente = cliente;
			RecebePedido.fluxoCaixa = fluxoCaixa;
			RecebePedido.total_pagar = total_pagar;
			RecebePedido.desconto = desconto;
			RecebePedido.forma_pagamento = forma_pagamento;
			RecebePedido.itens_pedido = itens_pedido;
		}

		public static Funcionario getFuncionario() {
			return funcionario;
		}

		public static void setFuncionario(Funcionario funcionario) {
			RecebePedido.funcionario = funcionario;
		}

		public static Cliente getCliente() {
			return cliente;
		}

		public static void setCliente(Cliente cliente) {
			RecebePedido.cliente = cliente;
		}

		public static BigDecimal getTotal_pagar() {
			return total_pagar;
		}

		public static void setTotal_pagar(BigDecimal total_pagar) {
			RecebePedido.total_pagar = total_pagar;
		}

		public static BigDecimal getDesconto() {
			if (desconto != null) {
				return desconto;
			} else {
				return BigDecimal.ZERO;
			}
		}

		public static void setDesconto(BigDecimal desconto) {
			if (desconto != null) {
				RecebePedido.desconto = desconto;
			} else {
				RecebePedido.desconto = BigDecimal.ZERO;
			}
		}

		public static ObservableList<Item_pedido> getItens_pedido() {
			return itens_pedido;
		}

		public static int getLimparVenda() {
			return limparVenda;
		}

		public static void setLimparVenda(int limparVenda) {
			RecebePedido.limparVenda = limparVenda;
		}

	}

	@FXML
	void adicionarCliente(MouseEvent event) {
		new Tela().carregarTelaCadastroRapidoClientes();
	}
}