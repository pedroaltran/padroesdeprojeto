package padroesdeprojeto;

import java.sql.ResultSet;
import java.sql.SQLException;

public class App {

	public static void main(String[] args) throws SQLException {
		
		ResultSetPrinter rsp = new ResultSetPrinter();
		
		ContatoDAO dao = new ContatoDAO();
		
		ResultSet contatos = dao.getAll();
		
		rsp.printResultSet(contatos);
		
	}

}