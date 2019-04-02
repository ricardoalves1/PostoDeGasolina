package com.postoGasolina.controller;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.postoGasolina.model.Item_pedido;
import com.postoGasolina.model.Produto_loja;
import com.postoGasolina.util.NumeroTextField;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public abstract class CompraVenda implements Initializable {

    @FXML
    JFXButton btnAdicionar;

    @FXML
    BorderPane borderPaneTabela;

    @FXML
    ImageView btnExcluir;

    @FXML
    JFXButton btnDesconto;

    @FXML
    JFXButton btnFechamento;

    NumeroTextField campoPreco = new NumeroTextField(BigDecimal.ZERO,
            NumberFormat.getCurrencyInstance(new Locale("pt", "BR")));
    NumeroTextField campoTotal = new NumeroTextField(BigDecimal.ZERO,
            NumberFormat.getCurrencyInstance(new Locale("pt", "BR")));
    NumeroTextField campoDesconto = new NumeroTextField(BigDecimal.ZERO,
            NumberFormat.getCurrencyInstance(new Locale("pt", "BR")));

    JFXTextField campoQuantidade = new JFXTextField("0");

    @FXML
    GridPane gridPaneBottom;

    @FXML
    GridPane gridPaneTop;

    int idTipoCombustivel;

    ComboBox<Produto_loja> comboBoxProduto = new ComboBox<>();

    ObservableList<Item_pedido> lista_itensPedido = FXCollections.observableArrayList();
    static int idItemPedido;
    static int idtabela = 1;
    BigDecimal totalCalculado = new BigDecimal("0");

    static JFXTextField mensagem = new JFXTextField("0");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            carregarComponentes();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            carregarTabela();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnNovo(ActionEvent event) {
        limparcamposCompleto();
    }

    abstract void limparcamposCompleto();

    BigDecimal carregarTotal() {
        totalCalculado = new BigDecimal("0");

        lista_itensPedido.forEach(item -> {
            totalCalculado = totalCalculado.add(item.getTotal());
        });

        totalCalculado = totalCalculado.subtract(campoDesconto.getNumber());

        return totalCalculado;
    }

    abstract void carregarComponentes() throws SQLException, ClassNotFoundException;

    public void limparcampos() {

        // campoPesquisar.setText("");
        campoQuantidade.setText("0");
        comboBoxProduto.setValue(null);
        campoTotal.setNumber(BigDecimal.ZERO);
        campoPreco.setNumber(BigDecimal.ZERO);

    }

    abstract void carregarTabela();

    //JFXTreeTableView<CompraVendaClass>
    void tabela(JFXTreeTableView<CompraVendaClass> treeTableView) {

        // Criando as colunas da tabela
        JFXTreeTableColumn<CompraVendaClass, String> colunaId = new JFXTreeTableColumn<>("ID");
        colunaId.setPrefWidth(150);
        JFXTreeTableColumn<CompraVendaClass, String> colunaNome = new JFXTreeTableColumn<>("DESCRIÇÃO");
        colunaNome.setPrefWidth(300);
        JFXTreeTableColumn<CompraVendaClass, String> colunaQuantidade = new JFXTreeTableColumn<>("QUANTIDADE");
        colunaQuantidade.setPrefWidth(200);
        JFXTreeTableColumn<CompraVendaClass, String> colunaPreco = new JFXTreeTableColumn<>("PREÇO");
        colunaPreco.setPrefWidth(150);
        JFXTreeTableColumn<CompraVendaClass, String> colunaTotal = new JFXTreeTableColumn<>("TOTAL");
        colunaTotal.setPrefWidth(150);

        colunaId.setCellValueFactory((TreeTableColumn.CellDataFeatures<CompraVendaClass, String> param) -> {
            if (colunaId.validateValue(param))
                return param.getValue().getValue().id;
            else
                return colunaId.getComputedValue(param);
        });
        colunaNome.setCellValueFactory((TreeTableColumn.CellDataFeatures<CompraVendaClass, String> param) -> {
            if (colunaNome.validateValue(param))
                return param.getValue().getValue().nome;
            else
                return colunaNome.getComputedValue(param);
        });
        colunaQuantidade.setCellValueFactory((TreeTableColumn.CellDataFeatures<CompraVendaClass, String> param) -> {
            if (colunaQuantidade.validateValue(param))
                return param.getValue().getValue().quantidade;
            else
                return colunaQuantidade.getComputedValue(param);
        });

        colunaPreco.setCellValueFactory((TreeTableColumn.CellDataFeatures<CompraVendaClass, String> param) -> {
            if (colunaPreco.validateValue(param))
                return param.getValue().getValue().preco;
            else
                return colunaPreco.getComputedValue(param);
        });
        colunaTotal.setCellValueFactory((TreeTableColumn.CellDataFeatures<CompraVendaClass, String> param) -> {
            if (colunaTotal.validateValue(param))
                return param.getValue().getValue().total;
            else
                return colunaTotal.getComputedValue(param);
        });

        ObservableList<CompraVendaClass> lista_ItensTabela = FXCollections.observableArrayList();
        lista_itensPedido.forEach(itens -> {
            if (itens.getProduto_loja().getTipo_produto().equals("combustivel")) {

                lista_ItensTabela.add(new CompraVendaClass(itens.getIdItem(),
                        itens.getProduto_loja().getCombustivel().getTipoCombustivel().getNome(), itens.getQuantidade(),
                        itens.getPreco_unitario(), itens.getTotal(),
                        itens.getProduto_loja().getCombustivel().getId_combustivel()));
            } else if (itens.getProduto_loja().getTipo_produto().equals("produto")) {
                lista_ItensTabela
                        .add(new CompraVendaClass(itens.getIdItem(), itens.getProduto_loja().getProduto().getDescricao(),
                                itens.getQuantidade(), itens.getPreco_unitario(), itens.getTotal(),
                                itens.getProduto_loja().getProduto().getId_produto()));
            }
        });

        treeTableView
                .setRoot(new RecursiveTreeItem<CompraVendaClass>(lista_ItensTabela, RecursiveTreeObject::getChildren));

        treeTableView.getColumns().setAll(colunaId, colunaNome, colunaQuantidade, colunaPreco, colunaTotal);
        treeTableView.setShowRoot(false);

    }

    // criando uma classe anonima para ser consumida pela tabela
    protected class CompraVendaClass extends RecursiveTreeObject<CompraVendaClass> {

        StringProperty id;
        StringProperty nome;
        StringProperty quantidade;
        StringProperty preco;
        StringProperty total;
        StringProperty idProduto;

        public CompraVendaClass(int id, String nome, BigDecimal quantidade, BigDecimal preco, BigDecimal total,
                          int idProduto) {
            super();
            this.id = new SimpleStringProperty(String.valueOf(id));
            this.nome = new SimpleStringProperty(nome);
            this.quantidade = new SimpleStringProperty(String.valueOf(quantidade));
            this.preco = new SimpleStringProperty(String.valueOf(
                    new NumeroTextField(preco, NumberFormat.getCurrencyInstance(new Locale("pt", "BR"))).getText()));
            this.total = new SimpleStringProperty(String.valueOf(
                    new NumeroTextField(total, NumberFormat.getCurrencyInstance(new Locale("pt", "BR"))).getText()));
            this.idProduto = new SimpleStringProperty(String.valueOf(idProduto));
        }

        // retorna o id do funcionario com isso posso alterar e remover qualquer
        // funcionario do banco de dados
        @Override
        public String toString() {
            return String.valueOf(id.getValue());
        }
    }

}
