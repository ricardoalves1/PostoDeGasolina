package com.postoGasolina.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.postoGasolina.model.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ClienteJuridicaDao implements InterfaceDao<Cliente_juridica> {
	private Connection connection;
	private String sql;
	private PreparedStatement statement;
	private ResultSet rs;
	private ResultSet rs2;
	private ResultSet rs3;
	private ObservableList<Cliente_juridica> listaclientes;
	private int idEndereco = 0;
	private int id_cliente_fisica = 0; 

	@Override
	public void cadastrar(Cliente_juridica clienteJuridica) throws ClassNotFoundException, SQLException {
			// prepara conexão
			connection = ConexaoUtil.getInstance().getConnection();

			// ADICIONA ENDEREÇO
			sql = "insert into tb_endereco(cep, endereco, numero, complemento, bairro, uf, cidade)values(?,?,?,?,?,?,?)";

			statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, clienteJuridica.getEndereco().getCep());
			statement.setString(2, clienteJuridica.getEndereco().getEndereco());
			statement.setString(3, clienteJuridica.getEndereco().getNumero());
			statement.setString(4, clienteJuridica.getEndereco().getComplemento());
			statement.setString(5, clienteJuridica.getEndereco().getBairro());
			statement.setString(6, clienteJuridica.getEndereco().getEstado());
			statement.setString(7, clienteJuridica.getEndereco().getCidade());

			statement.execute();

			rs = statement.getGeneratedKeys();
			if (rs.next()) {
				idEndereco = rs.getInt(1);
			}

			// ADICIONA CLIENTE
			sql = "insert into tb_cliente_juridica(id_endereco_fk, nome, cnpj, ie, email, observacao, situacao, data_situacao)values(?,?,?,?,?,?,?,?)";
		
			statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			statement.setInt(1, idEndereco);
			statement.setString(2, clienteJuridica.getNome());
			statement.setString(3, clienteJuridica.getCnpj());
			statement.setString(4, clienteJuridica.getIe());
			statement.setString(5, clienteJuridica.getEmail());
			statement.setString(6, clienteJuridica.getObservacao());
			statement.setString(7, clienteJuridica.getSituacao());
			statement.setDate(8, Date.valueOf(clienteJuridica.getData_situacao()));

			statement.execute();

			rs = statement.getGeneratedKeys();
			if (rs.next()) {
				id_cliente_fisica = rs.getInt(1);
			}

			clienteJuridica.getListaTelefone().forEach(telefone -> {

				try {
					sql = "insert into tb_telefone_cliente_juridica(id_cliente_juridica_fk,telefone)values(?,?)";
					statement = connection.prepareStatement(sql);
					statement.setInt(1, id_cliente_fisica);
					statement.setString(2, telefone.getTelefone());
					statement.execute();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			});

			statement.close();
			connection.close();
			rs.close();
	}

	@Override
	public void alterar(Cliente_juridica clienteJuridica) throws SQLException, ClassNotFoundException {

			connection = ConexaoUtil.getInstance().getConnection();

			// Endereco
			sql = "update tb_endereco set cep=?, endereco=?, numero=?, complemento=?, bairro=?, uf=?, cidade=? where id_endereco=?";

			statement = connection.prepareStatement(sql);
			statement.setString(1, clienteJuridica.getEndereco().getCep());
			statement.setString(2, clienteJuridica.getEndereco().getEndereco());
			statement.setString(3, clienteJuridica.getEndereco().getNumero());
			statement.setString(4, clienteJuridica.getEndereco().getComplemento());
			statement.setString(5, clienteJuridica.getEndereco().getBairro());
			statement.setString(6, clienteJuridica.getEndereco().getEstado());
			statement.setString(7, clienteJuridica.getEndereco().getCidade());
			statement.setInt(8, clienteJuridica.getEndereco().getId_endereco());

			statement.execute();

			// ADICIONA CLIENTE
			sql = "update tb_cliente_juridica set nome=?, cnpj=?, ie=?, email=?, observacao=?, situacao=?, data_situacao=? where id_cliente_juridica=?";
		
			statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, clienteJuridica.getNome());
			statement.setString(2, clienteJuridica.getCnpj());
			statement.setString(3, clienteJuridica.getIe());
			statement.setString(4, clienteJuridica.getEmail());
			statement.setString(5, clienteJuridica.getObservacao());
			statement.setString(6, clienteJuridica.getSituacao());
			statement.setDate(7, Date.valueOf(clienteJuridica.getData_situacao()));
			statement.setInt(8, clienteJuridica.getId_cliente_juridica());

			statement.execute();

			statement.close();
			connection.close();

	}

	@Override
	public void remover(int id) throws ClassNotFoundException, SQLException {
		connection = ConexaoUtil.getInstance().getConnection();

		sql = "delete from tb_cliente_juridica where id_cliente_juridica=?";
		statement = connection.prepareStatement(sql);
		statement.setInt(1, id);
		statement.execute();
		
		sql = "delete from tb_endereco where id_endereco in(select id_endereco_fk from tb_cliente_juridica where id_cliente_juridica=?)";
		statement = connection.prepareStatement(sql);
		statement.setInt(1, id);
		statement.execute();
		sql = "delete from tb_telefone_cliente_juridica where id_cliente_juridica_fk=?";
		statement = connection.prepareStatement(sql);
		statement.setInt(1, id);
		statement.execute();

		connection.close();
		statement.close();

	}

	@Override
	public ObservableList<Cliente_juridica> listar() throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub

		listaclientes = FXCollections.observableArrayList();

		connection = ConexaoUtil.getInstance().getConnection();

		// cliente
		sql = "SELECT * FROM tb_cliente_juridica cliente inner join tb_endereco endereco on(cliente.id_endereco_fk = endereco.id_endereco)";
		statement = connection.prepareStatement(sql);

		rs = statement.executeQuery();
		while (rs.next()) {
			clienteJuridica();
		}

		connection.close();
		statement.close();
		rs.close();

		return listaclientes;
	}
	
	public ObservableList<Cliente_juridica> pesquisar(int id) throws ClassNotFoundException, SQLException {

		listaclientes = FXCollections.observableArrayList();

		connection = ConexaoUtil.getInstance().getConnection();

		// cliente
		sql = "SELECT * FROM tb_cliente_juridica cliente inner join tb_endereco endereco on(cliente.id_endereco_fk = endereco.id_endereco) where cliente.id_cliente_juridica=?";
		statement = connection.prepareStatement(sql);
		statement.setInt(1, id);
		
		rs = statement.executeQuery();
		while (rs.next()) {
			clienteJuridica();
		}

		connection.close();
		statement.close();
		rs.close();

		return listaclientes;
	}

	public void clienteJuridica() throws SQLException {

		ObservableList<Telefone> lista_telefones = FXCollections.observableArrayList();
		ResultSet rs2;
		sql = "select * from tb_telefone_cliente_juridica where id_cliente_juridica_fk=?";
		statement = connection.prepareStatement(sql);
		statement.setInt(1, rs.getInt("id_cliente_juridica"));
		rs2 = statement.executeQuery();

		while (rs2.next()) {
			lista_telefones.add(new Telefone(rs2.getInt("id_cliente_juridica_fk"), rs2.getString("telefone")));
		}

		Endereco endereco = new Endereco.Builder()
				.idEndereco(rs.getInt("id_endereco"))
				.cep(rs.getString("cep"))
				.endereco(rs.getString("endereco"))
				.complemento(rs.getString("complemento"))
				.numero(rs.getString("numero"))
				.bairro(rs.getString("bairro"))
				.cidade(rs.getString("cidade"))
				.estado(rs.getString("uf"))
				.build();

		Cliente_juridica cliente = new Cliente_juridica.Builder()
				.idCliente(rs.getInt("id_cliente_juridica"))
				.endereco(endereco)
				.nome(rs.getString("nome"))
				.cnpj(rs.getString("cnpj"))
				.situacao(rs.getString("situacao"))
				.dataSituacao(ConverterDate.toLocalDate(rs.getDate("data_situacao")))
				.ie(rs.getString("ie"))
				.email(rs.getString("email"))
				.observacao(rs.getString("observacao"))
				.telefone(lista_telefones)
				.build();

		listaclientes.add(cliente);

		rs2.close();

	}

	public void excluirTelefone(Telefone telefone) throws SQLException, ClassNotFoundException {

		sql = "delete from tb_telefone_cliente_juridica where ? and telefone = ?";

		alterarTelefone(telefone);

	}

	public void adicionarTelefone(Telefone telefone) throws ClassNotFoundException, SQLException {
		sql = "insert into tb_telefone_cliente_juridica(id_cliente_juridica_fk, telefone)values(?,?)";

		alterarTelefone(telefone);

	}

	public void alterarTelefone(Telefone telefone) throws ClassNotFoundException, SQLException {

		connection = ConexaoUtil.getInstance().getConnection();

		statement = connection.prepareStatement(sql);
		statement.setInt(1, telefone.getId_responsavel_telefone());
		statement.setString(2, telefone.getTelefone());
		statement.execute();

		connection.close();
		statement.close();

	}

	public ObservableList<Cliente_Gasto> listarGasto() throws ClassNotFoundException, SQLException{
		connection = ConexaoUtil.getInstance().getConnection();
		
		ObservableList<Cliente_Gasto> lista_clientes = FXCollections.observableArrayList();
		
		sql="SELECT cliente.email, cliente.id_cliente_juridica, cliente.nome, "
				+ "sum(venda.total_pagar) as gastoTotal FROM tb_pedido_venda venda "
				+ "join tb_cliente_juridica cliente on(cliente.id_cliente_juridica = venda.id_cliente_juridica_fk)"
				+ "group by cliente.nome;";
		
		statement = connection.prepareStatement(sql);
		rs =  statement.executeQuery();
		
		while(rs.next()){
			System.out.println(rs.getInt("id_cliente_juridica"));
			
			sql = "SELECT sum(total_pagar) as gastoMensal FROM tb_pedido_venda venda"
					+ "inner join tb_cliente_juridica cliente on(id_cliente_juridica_fk = cliente.id_cliente_juridica)"
					+ "inner join tb_fluxo_caixa caixa on(id_fluxo_caixa_fk = caixa.id_fluxo_caixa)"
					+ "where (MONTH(now()) = MONTH(caixa.data_hora_inicial)) and (cliente.id_cliente_juridica =?)"
					+ "group by cliente.nome;";
			
			
			statement = connection.prepareStatement(sql);
			statement.setInt(1, rs.getInt("id_cliente_juridica"));
			rs2 = statement.executeQuery();
			
			
			
			sql = "SELECT sum(total_pagar) as gastoAnual FROM tb_pedido_venda venda"
					+ "inner join tb_cliente_juridica cliente on(id_cliente_juridica_fk = cliente.id_cliente_juridica)"
					+ "inner join tb_fluxo_caixa caixa on(id_fluxo_caixa_fk = caixa.id_fluxo_caixa)"
					+ "where (year(now()) = year(caixa.data_hora_inicial)) and (cliente.id_cliente_juridica =?)"
					+ "group by cliente.nome;";
			
			statement = connection.prepareStatement(sql); 
			statement.setInt(1, rs.getInt("id_cliente_juridica"));
			rs3 =  statement.executeQuery();
			
			
			if(rs2.first() && rs3.first()){
			lista_clientes.add(new Cliente_Gasto(rs.getInt("id_cliente_juridica"),
					rs.getString("nome"), rs2.getBigDecimal("gastoMensal"), rs3.getBigDecimal("gastoAnual"),
					rs.getBigDecimal("gastoTotal"), 
								null, "cliente_juridica", rs.getString("email")));
			}
		}

		connection.close();
		statement.close();
		rs.close();
		return lista_clientes;
		
	}
}
