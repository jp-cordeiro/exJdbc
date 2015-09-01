package exJdbc;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;

public class ConectMySql {
	
public static void main(String[] args) {
	
	//Inatanciando a conexao com o banco e deixando-a vazia para receber parametros de conexao.
	Connection conexao = null;
	
	try{
		
		//Registrando a classe JDBC e os parametros de copnexão em tempo de execução.
		String url = "jdbc:mysql://localhost/agenda";
		String senha = "root";
		String usuario = "root";
		
		//O metodo getConnection recebe os parâmetros deifinidos.
		conexao = (Connection) DriverManager.getConnection(url,usuario,senha);
		System.out.println("Conectou!");
		conexao.close();
	}catch(SQLException e){
		System.out.println("Ocorreu um erro!");
	}
}

}
