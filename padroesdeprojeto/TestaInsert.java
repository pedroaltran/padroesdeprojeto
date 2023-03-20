package padroesdeprojeto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;

public class TestaInsert {
	public void insert() throws SQLException {
		Connection con = new ConnectionFactory().getConnection();
		
		try {
			String sql = "insert into contatos (nome, email, endereco, dataNascimento) " +
					"values (?,?,?,?)";
			PreparedStatement stmt = con.prepareStatement(sql);
		
			stmt.setString(1, "Fabiano");
			stmt.setString(2, "fgsantos@unaerp.br");
			stmt.setString(3, "Rua A, 123");
			stmt.setDate(4, new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
	
			stmt.execute();
			stmt.close();
			
			System.out.println("*** Gravou!");
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			con.close();	
		}
	}
}
