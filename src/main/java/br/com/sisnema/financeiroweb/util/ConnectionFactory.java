package br.com.sisnema.financeiroweb.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe responsavel por retornar conexões com o banco de dados
 */
public class ConnectionFactory {

	/**
	 * Método que retorna uma conexão ativa com 
	 * o banco de dados
	 * 
	 * @param prmBanco - Contem os parametros necessários 
	 * 					 para se abrir uma conexão: 
	 * 					 URL, USER, PASS
	 * 
	 * @return - Conexão com o Banco de dados
	 * @author <a href='mailto:contato@sisnema.com.br'>Sisnema Informática</a>
	 * @since 16/08/2012
	 * @throws SQLException
	 */
	public static Connection getConnectionMysql() throws SQLException{
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			return DriverManager.getConnection( "jdbc:mysql://localhost:3306/financeiroweb",
												"root",
												"sisnema"
											  );
			
		} catch (ClassNotFoundException e) {
			System.out.println("Driver mapeado incorretamente.");
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static Connection getConnectionPostgres() throws SQLException{
		try {
			Class.forName("org.postgresql.Driver");
			
			return DriverManager.getConnection( "jdbc:postgresql:financeiroweb",
					"postgres",
					"sisnema"
					);
			
		} catch (ClassNotFoundException e) {
			System.out.println("Driver mapeado incorretamente.");
			e.printStackTrace();
		}
		
		return null;
	}
}
