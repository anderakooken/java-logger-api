package com.maissabor.services.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.maissabor.services.ServicesApplication;
import com.maissabor.services.models.Functions;


/**
 * @apiNote Classe de conexão com o banco de dados, com funções para as manipulações dos dados.
 */
public class Query {
    private volatile Connection con; // Váriavel de Conexão.
	private volatile boolean open = false; // Váriavel para saber se a conexão está aberta ou não
	private volatile String url,user,pw; // Váriavel de url, usuário e senha
	private final Logger LOGGER = LoggerFactory.getLogger(Query.class); //Váriavel de Log
	private boolean logSQL = false; //Váriavel para saber se é para mostrar as consultas ou não

	/**
	 * @apiNote Instância sem parametro. Conecta no banco padrão no arquivo server.json.
	 */
	public Query() {
		try {
            var array = Functions.JsonReader(ServicesApplication.class.getResourceAsStream("json/config.json").readAllBytes()).getJSONArray("fontes");
            String server = Functions.JsonReader(ServicesApplication.class.getResourceAsStream("json/server.json").readAllBytes()).getString("server");
            for (int i = 0; i < array.length(); i++) {
                JSONObject json = array.getJSONObject(i);
                if(json.getString("titulo").equals(server)){
                    this.url = json.getString("url");
					this.user = json.getString("usuario");
					this.pw = json.getString("senha");
                }
            }
            
        } catch (IOException e) {
            // TODO Auto-generated catch block
            LOGGER.error("Erro ao tentar se conectar com o banco de dados", e);
        }
	}

	/**
	 * @apiNote Instância recebendo como parametro database a se conectado.
	 * @param database
	 */
	public Query(String database) {
		try {
            var array = Functions.JsonReader(ServicesApplication.class.getResourceAsStream("json/config.json").readAllBytes()).getJSONArray("fontes");        
            for (int i = 0; i < array.length(); i++) {
                JSONObject json = array.getJSONObject(i);
                if(json.getString("titulo").equals(database)){
                    this.url = json.getString("url");
					this.user = json.getString("usuario");
					this.pw = json.getString("senha");
                }
            }
            
        } catch (IOException e) {
            // TODO Auto-generated catch block
            LOGGER.error("Erro ao tentar se conectar com o banco de dados", e);
        }
	}

	/**
	 * @apiNote Função para abrir e fechar a conexão. Recebe um valor booleano com parametro.
	 * @param isopen
	 */
	public void isOpen(boolean isopen){
		open = isopen;
		if(isopen){
			con = ConnectionFactory.getConnection(url,user,pw);
		}else{
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				LOGGER.error("Erro ao fechar a conexão com o banco de dados :: isOpen()", e);
			}
		}
	}

	/**
	 * @apiNote Verifica se a conexão está aberta
	 */
	private void VerifyConnection(){
		if(open){
			try {
				if(con == null || con.isClosed()){
					con = ConnectionFactory.getConnection(url,user,pw);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				LOGGER.error("Erro ao verificar a conexão :: VerifyConnection()", e);
			}
		}
	}

	/**
	 * @apiNote Recebe parametros - {SQL,Type} - para consulta e retorna uma Lista de Objetos
	 * @apiNote 0 - SQL (String)
	 * @apiNote 1 - Tipo (Ex: "Hashmap")
	 * @param parametros
	 * @return
	 */
    public List<Object> query(Object[] parametros) {
		List<Object> lista = new ArrayList<>();
		String sql = (String)parametros[0];
		String type = ((String)parametros[1]).toLowerCase();

		if(logSQL){
			LOGGER.info(sql);
		}
		
		VerifyConnection();
		try (PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery()) {
			
				if(type.equals("hashmap")){
					
					List<HashMap<String,Object>> maps = new ArrayList<>();
					

					ResultSetMetaData rsm = rs.getMetaData();

					while (rs.next()) {
						
						HashMap<String,Object> map = new HashMap<>();
						for (int i = 1; i < rsm.getColumnCount(); i++) {
							map.put(rsm.getColumnName(i), rs.getString(rsm.getColumnName(i)));
						}

						maps.add(map);
					}

					lista = new ArrayList<>();
					lista.add(maps);
			
				}

		
		} catch (NumberFormatException | SQLException e) {
			// TODO Auto-generated catch block
			LOGGER.error("Erro ao fechar a consultar no banco de dados :: query()", e);
		} 

		
		return lista;

	}

	/***
	 * @apiNote Recebe uma String SQL para Cadastrar, Editar ou Deletar valores no banco. Retorna um valor booleano para verificar se está tudo ok.
	 * 
	 * @category Sem retorno, ligação com banco de dados.
	 * @exception SQLException
	 ***/
	public boolean CED(String sql) {

		boolean value = false;

		if (sql == null) {
			return value;
		}

		VerifyConnection();

		if(logSQL){
			LOGGER.info(sql);
		}
		
	
		try(PreparedStatement ps = con.prepareStatement(sql)){

			ps.execute();

			value = true;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			LOGGER.error("Erro no banco de dados :: CED()", e);
			
			value = false;
		}
		return value;
	}

/***
	 * @apiNote Recebe uma consulta e retorna uma lista de valores;
	 * 
	 * @return List<String>
	 * @category Com retorno, ligação com banco de dados.
	 * @exception SQLException
	 ***/
	public List<String> search(String sql) {

		List<String> dados = new ArrayList<>();
		
		VerifyConnection();
		
		if(logSQL){
			LOGGER.info(sql);
		}

		try(PreparedStatement ps = con.prepareStatement(sql)){
			ResultSet rs = ps.executeQuery();

			while (rs.next() && rs.getString(1) != null) {
				dados.add(rs.getString(1));
			}
			rs.close();
			if (dados.isEmpty()) {
				dados.add("");
			} /*
				 * else if(Functions.isNull(dados.get(0))){
				 * dados.set(0, "");
				 * }
				 */
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			LOGGER.error("Erro ao executar o comando no banco de dados! :: Search()", e);
		}

		return dados;
	}

	/***
	 * 
	 * @apiNote Recebe uma String SQL podendo só retornar números. Transforma o valor em String e retorna.
	 * @return String
	 * @category Com retorno, ligação com banco de dados.
	 * @exception SQLException
	 ***/
	public String Count(String sql) {
		String valor = "0";

		if (sql == null) {
			return valor;
		}
	
		VerifyConnection();
	
		if(logSQL){
			LOGGER.info(sql);
		}
		
		
		try(PreparedStatement ps = con.prepareStatement(sql)){
			
			
			ResultSet rs = ps.executeQuery();
			if (rs.next() && rs.getString(1) != null) {
				valor = rs.getString(1);
				try {
					Integer.parseInt(valor);
				} catch (NumberFormatException e) {
					LOGGER.error("Count só pode receber números :: Count()", e);
				}
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			LOGGER.error("Erro ao executar o comando no banco de dados! :: Count()", e);
		}

		return valor;
	}

	/**
	 * @apiNote Recebe um valor boolean para saber se mostra ou não as consultas SQL executadas.
	 * @param show
	 */
	public void showLog(boolean show){
		this.logSQL = show;
	}

}
