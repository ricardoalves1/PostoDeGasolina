package com.postoGasolina.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConexaoUtil {
	private static ConexaoUtil conexaoUtil = new ConexaoUtil();

	private ConexaoUtil() {}

	public static ConexaoUtil getInstance(){
		return conexaoUtil;
	}
	
	public Connection getConnection() throws ClassNotFoundException, SQLException{
		Class.forName("com.mysql.jdbc.Driver");
		
		String url = "jdbc:mysql://localhost/db_posto_gasolina";
        String usuario = "root";
        String senha = "";

		return DriverManager.getConnection(url, usuario, senha);
	}

}