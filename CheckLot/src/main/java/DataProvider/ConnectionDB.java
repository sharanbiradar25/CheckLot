package DataProvider;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ConnectionDB {
	
	List<String> lots = new ArrayList<String>();
	static List<String> sellerCodes = new ArrayList<String>();
	static Connection connection;
	static Statement stm;
	static ResultSet result;
	static String value = null;

	public void getConnection() throws SQLException, ClassNotFoundException {
		Class.forName("com.ibm.as400.access.AS400JDBCDriver");
		connection = DriverManager.getConnection("jdbc:as400://c-casdb-qa1.copart.com;libraries=RNQDB001;", "SHBIRADAR","copart007");
		stm = connection.createStatement();
	}

	public List<String> getLots(String column)
			throws IOException, ClassNotFoundException, SQLException {
		GetData g=new GetData();

		List<String> queries = new ArrayList<String>();
		List<String> info = new ArrayList<String>(); 
		g.getData();
		queries = g.queries;
		getConnection();
		for (int i = 0; i < queries.size(); i++) {
			result = stm.executeQuery(queries.get(i));
			if (result != null) {
				while (result.next()) {
					value = result.getString(column);
					info.add(value);
					break;
					
				}
			}
		}
		connection.close();
		return info;
	}
	public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException{
		ConnectionDB d=new ConnectionDB();
		d.getConnection();
		//System.out.println(d.getLots("LTLOTNBR").size());
		
	}
}
