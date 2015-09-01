package exJdbc;

import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

public class ContatoCrud {

	public void salvar(Contato contato) {
		// Recebe a conexão gerada pelo método
		Connection conexao = this.geraConexao();
		// Statement criado como vazio.
		PreparedStatement insereSt = null;

		// String sql criada para realizar a operação do método, os "?" são os
		// parametros passados pelo Statement criado com na suas respectivas
		// ordens
		String sql = "INSERT INTO contato (nome,telefone,email,dt_cad,obs) VALUES (?,?,?,?,?)";

		try {
			// Statement recebe o script sql da string do comando
			insereSt = (PreparedStatement) conexao.prepareStatement(sql);

			// Statement set nas posições o valores para serem comitados no
			// banco
			insereSt.setString(1, contato.getNome());
			insereSt.setString(2, contato.getTelefone());
			insereSt.setString(3, contato.getEmail());
			insereSt.setDate(4, (Date) contato.getDataCadastro());
			insereSt.setString(5, contato.getObs());

			// Executa o commit no BD
			insereSt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("Erro ao incluir contato. Mensagem: "
					+ e.getMessage());
		} finally {
			try {
				// Fecha o Statement e a Conexão
				insereSt.close();
				conexao.close();
			} catch (Throwable e) {
				System.out
						.println("Erro ao fechar operação de inserção. Mensagem: "
								+ e.getMessage());
			}
		}
	}

	public void atualizar(Contato contato) {

		// Recebe a conexão gerada pelo método
		Connection conexao = this.geraConexao();
		// Statement criado como vazio.
		PreparedStatement atualizaSt = null;

		// String sql criada para realizar a operação do método, os "?" são os
		// parametros passados pelo Statement criado com na suas respectivas
		// ordens
		// A data de cadastro não é alterada
		String sql = "UPDATE contato SET nome =?,telefone=?,email=?,obs=? WHERW codigo=?)";

		try {
			// Statement recebe o script sql da string do comando
			atualizaSt = (PreparedStatement) conexao.prepareStatement(sql);

			// Statement set nas posições o valores para serem comitados no
			// banco
			atualizaSt.setString(1, contato.getNome());
			atualizaSt.setString(2, contato.getTelefone());
			atualizaSt.setString(3, contato.getEmail());
			atualizaSt.setDate(4, (Date) contato.getDataCadastro());
			atualizaSt.setString(5, contato.getObs());

			// Executa o commit no BD
			atualizaSt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("Erro ao atualizar contato. Mensagem: "
					+ e.getMessage());
		} finally {
			try {
				// Fecha o Statement e a Conexão
				atualizaSt.close();
				conexao.close();
			} catch (Throwable e) {
				System.out
						.println("Erro ao fechar operação de atualizar. Mensagem: "
								+ e.getMessage());
			}
		}
	}

	public void excluir(Contato contato) {

		// Recebe a conexão gerada pelo método
		Connection conexao = this.geraConexao();
		// Statement criado como vazio.
		PreparedStatement excluiSt = null;

		// String sql criada para realizar a operação do método, os "?" são os
		// parametros passados pelo Statement criado com na suas respectivas
		// ordens
		String sql = "DELETE FROM contato WHERE codigo=?";

		try {
			// Statement recebe o script sql da string do comando
			excluiSt = (PreparedStatement) conexao.prepareStatement(sql);

			// Statement set nas posições o valores para serem comitados no
			// banco
			excluiSt.setInt(1, contato.getCodigo());

			// Executa o commit no BD
			excluiSt.executeQuery();

		} catch (SQLException e) {
			System.out.println("Erro ao incluir contato. Mensagem: "
					+ e.getMessage());
		} finally {
			try {
				// Fecha o Statement e a Conexão
				excluiSt.close();
				conexao.close();
			} catch (Throwable e) {
				System.out
						.println("Erro ao fechar operação de inserção. Mensagem: "
								+ e.getMessage());
			}
		}
	}

	public List<Contato> listar() {
		Connection conexao = this.geraConexao();

		//Cria uma lista vazia do tipo Contato
		List<Contato> contatos = new ArrayList<Contato>();

		Statement consultaSt = null;
		ResultSet resultado = null;
		Contato contato = null;

		String sql = "SELECT * FROM contato";

		try {
			// A instrução CreateStatement é utilizada para realizar instruções
			// SQL que não recebem parametros "?"(não é preparada)
			consultaSt = (Statement) conexao.createStatement();

			// O método executeQuery difere do executeUpdate pois retorna um
			// resultado(ResultSet), por isso esse é o método para utilizarmos
			// em consulta a banco de dados
			resultado = consultaSt.executeQuery(sql);
			
			//Laço While onde será atribuido para um objeto Contato cada resultado encontrado no banco
			while (resultado.next()) {
				//Instancia um novo contato para receber os parâmetros passados pelo resultSet
				contato = new Contato();

				//Parametros retornados do banco sendo passados para cada atributo do obejto Contato
				contato.setCodigo(resultado.getInt("codigo"));
				contato.setNome(resultado.getString("nome"));
				contato.setTelefone(resultado.getString("telefone"));
				contato.setEmail(resultado.getString("email"));
				contato.setDataCadastro(resultado.getDate("dt_cad"));
				contato.setObs(resultado.getString("obs"));

				//Objeto adicionado a lista
				contatos.add(contato);
			}

		} catch (SQLException e) {
			System.out.println("Erro ao buscar código do contato. Mensagem: "
					+ e.getMessage());
		} finally {
			try {
				consultaSt.close();
				resultado.close();
				conexao.close();
			} catch (Throwable e) {
				System.out
						.println("Erro ao fechar operações de consulta. Mensagem: "
								+ e.getMessage());
			}
		}

		return contatos;
	}

	public Contato buscaContato(int valor) {
		//Cria a conexão
		Connection conexao = this.geraConexao();
		
		//Utiliza um PreparedStatement para ser possível inserir parametrôs para a consulta
		PreparedStatement contatoSt = null;
		//Cria um ResultSet para receber o resultado da consulta
		ResultSet resultado = null;
		//Cria um contato para receber os dados da consulta
		Contato contato = null;
		
		//Instrução sql para realizar uma consulta pelo código do contato
		String sql = "SELECT * FROM contato WHERE codigo=?";
		
		try{
			//Prepara o PreparedStatement para receber a String sql
			contatoSt = (PreparedStatement) conexao.prepareStatement(sql);
			//Seta o parametro na instrução
			contatoSt.setInt(1, valor);
			//Executa a consulta com o parametro establecido
			resultado = contatoSt.executeQuery(sql);
			
			//Quando resultado não tiver próximo atribua os valores em um objeto Contato
				if(resultado.next()){
					contato = new Contato();
					contato.setCodigo(resultado.getInt("codigo"));
					contato.setNome(resultado.getString("nome"));
					contato.setTelefone(resultado.getString("telefone"));
					contato.setEmail(resultado.getString("email"));
					contato.setDataCadastro(resultado.getDate("dt_cad"));
					contato.setObs(resultado.getString("obs"));
				}			
			
		}catch(SQLException e){
			System.out.println("Ocorreu um erro na consulta do contato. Mensagem: "+e.getMessage());
		}
		finally{
			try{
				contatoSt.close();
				resultado.close();
				conexao.close();
			}catch(Throwable e){
				System.out.println("Ocorreu um erro no encerramento da consulta. Mensagem: "+e.getMessage());
			}
		}

		return contato;
	}

	public Connection geraConexao() {
		Connection conexao = null;
		try{
			
			//Registrando a classe JDBC e os parametros de copnexão em tempo de execução.
			String url = "jdbc:mysql://localhost/agenda";
			String senha = "root";
			String usuario = "root";
			
			//O metodo getConnection recebe os parâmetros deifinidos.
			conexao = (Connection) DriverManager.getConnection(url,usuario,senha);

		}catch(SQLException e){
			System.out.println("Ocorreu um erro!");
		}
			

		return conexao;
	}

	public static void main(String[] args) {

		ContatoCrud crud = new ContatoCrud();
		
		//Criando um primeiro contato
		Contato fulano = new Contato();
		fulano.setNome("Fulano de Tal");
		fulano.setTelefone("123");
		fulano.setEmail("teste@teste.com");
		fulano.setDataCadastro(new Date(System.currentTimeMillis()));
		fulano.setObs("Novo Contato");
		
		crud.salvar(fulano);
		
		System.out.println("Contatos cadastrados: "+crud.listar().size());
	}
}
