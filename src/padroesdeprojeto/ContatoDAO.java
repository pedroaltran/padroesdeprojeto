package padroesdeprojeto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;

import java.sql.Date;

public class ContatoDAO {
	private Connection connection;
	
	public ContatoDAO() {
		this.connection = new ConnectionFactory().getConnection();
	}
	
	public void insert(Contato c) {
		String sql = "insert into contatos (nome,email,endereço,dataNascimento) "
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
	
	public List<Contato> getAll() {
	    List<Contato> contatos = new ArrayList<>();
	    try {
	        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM contatos");
	        ResultSet rs = stmt.executeQuery();
	        while (rs.next()) {
	            Contato contato = new Contato();
	            contato.setId(rs.getLong("id"));
	            contato.setNome(rs.getString("nome"));
	            contato.setEmail(rs.getString("email"));
	            contato.setEndereco(rs.getString("endereço"));
	            Calendar data = Calendar.getInstance();
	            data.setTime(rs.getDate("dataNascimento"));
	            contato.setDataNascimento(data);
	            contatos.add(contato);
	        }
	        stmt.close();
	        rs.close();
	    } catch (SQLException e) {
	        throw new RuntimeException(e);
	    }
	    return contatos;
	}
	

	
	public void update(Contato contato) {
	    String sql = "update contatos set nome=?, email=?, endereço=?," +
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

	public void delete(Long id) {
	    try {
	        PreparedStatement stmt = connection.prepareStatement("delete " +
	                "from contatos where id=?");
	        stmt.setLong(1, id);
	        stmt.execute();
	        stmt.close();
	    } catch (SQLException e) {
	        throw new RuntimeException(e);
	    }
	}

}
