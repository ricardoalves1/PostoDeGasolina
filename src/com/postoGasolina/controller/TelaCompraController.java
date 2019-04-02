package com.postoGasolina.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.Locale;

import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import com.postoGasolina.dao.CompraDao;
import com.postoGasolina.dao.FornecedoresDao;
import com.postoGasolina.main.Tela;
import com.postoGasolina.model.Fluxo_caixa;
import com.postoGasolina.model.Fluxo_caixa2;
import com.postoGasolina.model.Fornecedor;
import com.postoGasolina.model.Item_pedido;
import com.postoGasolina.model.Pedido_compra;
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

public class TelaCompraController extends CompraVenda implements Initializable {

	@FXML
	private ImageView btnFornecedor;

	private NumeroTextField campoTotalCompra = new NumeroTextField(BigDecimal.ZERO,
			NumberFormat.getCurrencyInstance(new Locale("pt", "BR")));

	@FXML
	private JFXTreeTableView<CompraVendaClass> treeTableViewCompra;

	ComboBox<Fornecedor> comboBoxFornecedor = new ComboBox<>();

	@FXML
	private JFXTextField campoResponsavel;

	private JFXSnackbar s;

	@FXML
	void mouseEventCompra(MouseEvent event) { 
		if (!lista_itensPedido.isEmpty()) {
			campoDesconto.setNumber(RecebePedidoCompra.getDesconto());
		}
		if(mensagem.getText().equals("1")){
			s = new JFXSnackbar(borderPaneTabela);
			s.show("Desconto adicionado com sucesso", 4000);
			mensagem.setText("0");
		}
	}

	@Override
	void limparcamposCompleto() {
		lista_itensPedido.clear();
		carregarTabela();
		RecebePedidoCompra.setDesconto(BigDecimal.ZERO);
		limparcampos();
		campoTotalCompra.setNumber(BigDecimal.ZERO);
		campoDesconto.setNumber(BigDecimal.ZERO);
		comboBoxFornecedor.setValue(null);
		campoResponsavel.setText("");
	}

	@FXML
	void btnAdicionar(ActionEvent event) {

		if (comboBoxProduto.getValue() != null) {
			if (!campoQuantidade.getText().isEmpty() && !campoQuantidade.getText().equals("0")) {

				Item_pedido itemPedido = new Item_pedido.Builder()
						.idPedido(0)
						.produtoLoja(comboBoxProduto.getSelectionModel().getSelectedItem())
						.precoUnitario(campoPreco.getNumber())
						.quantidade(new BigDecimal(campoQuantidade.getText()))
						.tipoProduto(comboBoxProduto.getSelectionModel().getSelectedItem().getTipo_produto())
						.total(campoPreco.getNumber().multiply(new BigDecimal(campoQuantidade.getText())))
						.idItem(idtabela++)
						.build();

				lista_itensPedido.add(itemPedido);

				limparcampos();
				carregarTabela();

				carregarTotalCompra();
				
				s = new JFXSnackbar(borderPaneTabela);
				s.show("Produto adicionado com sucesso", 4000);

			} else {
				s = new JFXSnackbar(borderPaneTabela);
				s.show("Informa quantidade", 4000);
			}
		} else {
			s = new JFXSnackbar(borderPaneTabela);
			s.show("Seleciona um produto", 4000);
		}

	}

	private void carregarTotalCompra() {
		campoTotalCompra.setNumber(carregarTotal());
	}

	@FXML
	void btnDesconto(ActionEvent event) {
		RecebePedidoCompra.setTotal_pagar(campoTotalCompra.getNumber());
		new Tela().carregarTelaDesconto2();
	}

	@FXML
	void btnFechamento(ActionEvent event) {
		try {
			new RecebePedidoCompra(0, comboBoxFornecedor.getValue(), new Fluxo_caixa(), campoTotalCompra.getNumber(),
					campoDesconto.getNumber(), campoResponsavel.getText(), lista_itensPedido);

			if (comboBoxFornecedor.getValue() != null && !campoResponsavel.getText().isEmpty() && !lista_itensPedido.isEmpty()) {
				try {

					new CompraDao()
							.finalizar(
									new Pedido_compra(
											0,
											RecebePedidoCompra.getFornecedor(),
											new Fluxo_caixa2(),
											RecebePedidoCompra.getCampoResponsavel(),
											RecebePedidoCompra.getTotal_pagar(),
											RecebePedidoCompra.getDesconto(),
											RecebePedidoCompra.getItens_pedido()
									)
							);

					limparcamposCompleto();
					
					s = new JFXSnackbar(borderPaneTabela);
					s.show("Compra registrada com sucesso", 4000);

				} catch (Exception e) {
					e.printStackTrace();
				}

			} else {
				s = new JFXSnackbar(borderPaneTabela);
				s.show("Campos obrigatórios não informado", 4000);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@FXML
	void BtnExcluirMouseClicked(MouseEvent event) {
		System.out.println(treeTableViewCompra.getSelectionModel().getSelectedIndex() != -1);
		if (treeTableViewCompra.getSelectionModel().getSelectedIndex() != -1) {

			try {
				System.out.println("entrou");
				int id = Integer
						.valueOf(treeTableViewCompra.getSelectionModel().getSelectedItem().getValue().toString());

				
				for (int i = 0; i < lista_itensPedido.size(); ++i) {
					if (lista_itensPedido.get(i).getIdItem() == id) {
						lista_itensPedido.remove(i);

						carregarTabela();
						carregarTotalCompra();
						
						s = new JFXSnackbar(borderPaneTabela);
						s.show("Produto removido com sucesso", 4000);
						break;
					}
				}
			} catch (Exception e) {
				//
			}

		} else {
			s = new JFXSnackbar(borderPaneTabela);
			s.show("Seleciona produto na tabela", 4000);
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

		campoTotalCompra.getStylesheets().add(style);
		campoTotalCompra.getStyleClass().add("format-campo");
		campoTotalCompra.setUnFocusColor(Color.TRANSPARENT);
		campoTotalCompra.setFocusColor(Color.TRANSPARENT);
		campoTotalCompra.setAlignment(Pos.CENTER);
		campoTotalCompra.setEditable(false);
		campoTotalCompra.setFocusTraversable(false);
		gridPaneBottom.add(campoTotalCompra, 2, 0);
		gridPaneBottom.setMargin(campoTotalCompra, new Insets(0, 0, 0, 0));

		style = getClass().getResource("/com/postoGasolina/style/TelaGerenciarFuncionario.css").toExternalForm();

		campoPreco.getStylesheets().add(style);
		campoPreco.getStyleClass().add("formata-campo");
		campoPreco.setUnFocusColor(Color.WHITE);
		campoPreco.setFocusColor(Color.WHITE);
		campoPreco.setEditable(true);
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
		
		campoPreco.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				try {
					if (!newValue.isEmpty()) {
						campoTotal.setNumber(campoPreco.getNumber().multiply(new BigDecimal(campoQuantidade.getText())));
					} 
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});

		// COMBOBOX PESQUISAR
		comboBoxFornecedor.setItems(new FornecedoresDao().listar());

		style = getClass().getResource("/com/postoGasolina/style/TelaGerenciarFuncionarioComboBox.css")
				.toExternalForm();
		comboBoxFornecedor.getStylesheets().add(style);
		comboBoxFornecedor.getStyleClass().add("formata-campo");
		comboBoxFornecedor.setPrefWidth(385);
		comboBoxFornecedor.setPromptText("Pesquisar Fornecedor ...");
		comboBoxFornecedor.setPadding(new Insets(0, 0, 0, -10));
		comboBoxFornecedor.setFocusTraversable(false);
	
		new AutoShowComboBoxHelper(comboBoxFornecedor);
		
		gridPaneTop.add(comboBoxFornecedor, 0, 1);
		gridPaneTop.setMargin(comboBoxFornecedor, new Insets(11, 0, 0, 50));

		comboBoxFornecedor.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if (comboBoxFornecedor.isFocused()) {

				comboBoxFornecedor.setPromptText("");

				try {
					comboBoxFornecedor.setItems(new FornecedoresDao().listar());
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} else {
				comboBoxFornecedor.setPromptText("Pesquisar Fornecedor ...");
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
									btnAdicionar.setDisable(false);
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
										btnAdicionar.setDisable(true);
									} else {
										campoQuantidade.setEditable(true);
										btnAdicionar.setDisable(false);
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
				carregarTotalCompra();
				campoDesconto.setNumber(RecebePedidoCompra.getDesconto());
			}
		});

	}

	void carregarTabela() {
		atualizarTabela();
		tabela(treeTableViewCompra);
	}


	void atualizarTabela() {
		try {
			treeTableViewCompra = FXMLLoader
					.load(getClass().getClassLoader().getResource("com/postoGasolina/view/TreeTableviewModelo.fxml"));
			borderPaneTabela.setCenter(treeTableViewCompra);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static class RecebePedidoCompra {

		private static int id_pedido_compra;
		private static Fornecedor fornecedor;
		private static Fluxo_caixa fluxoCaixa;
		private static BigDecimal total_pagar;
		private static BigDecimal desconto;
		private static String campoResponsavel;
		private static int limparCompra;
		private static ObservableList<Item_pedido> itens_pedido = FXCollections.observableArrayList();

		public RecebePedidoCompra(int id_pedido_compra, Fornecedor fornecedor, Fluxo_caixa fluxoCaixa,
								BigDecimal total_pagar, BigDecimal desconto, String campoResponsavel,
								ObservableList<Item_pedido> itens_pedido)
		{
			RecebePedidoCompra.id_pedido_compra = id_pedido_compra;
			RecebePedidoCompra.fornecedor = fornecedor;
			RecebePedidoCompra.fluxoCaixa = fluxoCaixa;
			RecebePedidoCompra.total_pagar = total_pagar;
			RecebePedidoCompra.desconto = desconto;
			RecebePedidoCompra.campoResponsavel = campoResponsavel;
			RecebePedidoCompra.itens_pedido = itens_pedido;
		}

		public static Fornecedor getFornecedor() {
			return fornecedor;
		}

		public static BigDecimal getTotal_pagar() {
			return total_pagar;
		}

		public static void setTotal_pagar(BigDecimal total_pagar) {
			RecebePedidoCompra.total_pagar = total_pagar;
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
				RecebePedidoCompra.desconto = desconto;
			} else {
				RecebePedidoCompra.desconto = BigDecimal.ZERO;
			}
		}

		public static String getCampoResponsavel() {
			return campoResponsavel;
		}

		public static ObservableList<Item_pedido> getItens_pedido() {
			return itens_pedido;
		}

	}

	@FXML
	void adicionarFornecedor(MouseEvent event) {
		new Tela().carregarTelaFornecedorUtilitaria();
	}

}