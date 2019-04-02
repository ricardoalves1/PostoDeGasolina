package com.postoGasolina.controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextField;
import com.postoGasolina.dao.ClienteFisicaDao;
import com.postoGasolina.util.CPF;
import com.postoGasolina.util.TextFieldFormatter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class TelaCadastroRapidoClientesController implements Initializable {
	@FXML
	private GridPane gridPaneBottom;

	@FXML
	private JFXButton btnSalvar;

	@FXML
	private ImageView imageAdicionar;
	
	@FXML
    private JFXTextField campoNome;

    @FXML
    private JFXTextField campoCpf;
    @FXML
    private BorderPane borderPane;
    
    private JFXSnackbar s;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		campoCpf.setOnKeyReleased(event -> {
			TextFieldFormatter ttf = new TextFieldFormatter();
			ttf.setCaracteresValidos("0123456789");
			ttf.setMask("###.###.###-##");
			ttf.setTf(campoCpf);
			ttf.formatter();
			try {
				if (campoCpf.getText().length() == 14 && campoCpf.getText().charAt(13) != ' ') {
					boolean cpf = new CPF(campoCpf.getText()).isCPF();
					if(!cpf){
						s = new JFXSnackbar(borderPane);
						s.show("CPF Inválido", 6000);
						campoCpf.setText(""); 
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

	}

	@FXML
	void btnSalvar(ActionEvent event) {
		if(!campoNome.getText().isEmpty() && !campoCpf.getText().isEmpty()){
			try {
				new ClienteFisicaDao().cadastrar(campoNome.getText(), campoCpf.getText());
				
				TelaVendaController.mensagem.setText("4");
				
				Stage stage = (Stage)gridPaneBottom.getScene().getWindow();
				stage.close();
				
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		} else {
		s = new JFXSnackbar(borderPane);
		s.show("Campos obrigatórios não informado", 4000);
		}
	}
}