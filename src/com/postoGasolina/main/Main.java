package com.postoGasolina.main;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	private static Stage stage;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		Main.stage = primaryStage;
		//carregarTelaLogin();
		new Tela().carregarTelaInicial(stage);
	}

	public Stage getStage() {
		return stage;
	}

}
