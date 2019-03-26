package com.postoGasolina.main;

import java.io.IOException;

import com.postoGasolina.model.Fluxo_caixa;

import insidefx.undecorator.Undecorator;
import insidefx.undecorator.UndecoratorScene;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class Main extends Application {

	private static Stage stage;
	private final HostServices services = this.getHostServices();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Main.stage = primaryStage;
		//carregarTelaLogin();
		new Tela().carregarTelaInicial(stage);
	}

	public Stage getStage() {
		return stage;
	}

}
