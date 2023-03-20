package padroesdeprojeto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.sql.Date;

public class ContatoDAO {
	private Connection connection;
	
	public ContatoDAO() {
		this.connection = new ConnectionFactory().getConnection();
	}
	
	public void insert(Contato c) {
		String sql = "insert into contatos (nome,email,endereco,dataNascimento) "
					+ "values (?,?,?,?)";
	
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);

	        stmt.setString(1,c.getNome());
	        stmt.setString(2,c.getEmail());
	        stmt.setString(3,c.getEndereco());
	        stmt.setDate(4, new Date(c.getDataNascimento().getTimeInMillis()));
	        
	        stmt.execute();
	        stmt.close();
	    } 
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public ResultSet getAll() {
		try {
			PreparedStatement stmt = this.connection.prepareStatement("select * from contatos");
			ResultSet rs = stmt.executeQuery();
		
			List<Contato> lista = new ArrayList<Contato>();
		
			while(rs.next()) {
				Contato c = new Contato();
				c.setNome(rs.getString("nome"));
				c.setEndereco(rs.getString("endereco"));
				c.setEmail(rs.getString("email"));
			
				Calendar data = Calendar.getInstance();
				data.setTime(rs.getDate("dataNascimento"));
				c.setDataNascimento(data);
			
				lista.add(c);
			}
			//rs.close();
			//stmt.close();
			
			return rs;
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	public void update(Contato contato) {
	    String sql = "update contatos set nome=?, email=?, endereco=?," +
	            "dataNascimento=? where id=?";
	    try {
	        PreparedStatement stmt = connection.prepareStatement(sql);
	        stmt.setString(1, contato.getNome());
	        stmt.setString(2, contato.getEmail());
	        stmt.setString(3, contato.getEndereco());
	        stmt.setDate(4, new Date(contato.getDataNascimento()
	                .getTimeInMillis()));
	        stmt.setLong(5, contato.getId());
	        stmt.execute();
	        stmt.close();
	    } catch (SQLException e) {
	        throw new RuntimeException(e);
	    }
	}
	
	public void delete(Contato contato) {
	    try {
	        PreparedStatement stmt = connection.prepareStatement("delete " +
	                "from contatos where id=?");
	        stmt.setLong(1, contato.getId());
	        stmt.execute();
	        stmt.close();
	    } catch (SQLException e) {
	        throw new RuntimeException(e);
	    }
	}
}
