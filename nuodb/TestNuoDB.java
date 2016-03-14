package nuodb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestNuoDB {
	public static void main(String... args) throws Exception {
		testConnect();
	}

	private static void testConnect() throws SQLException {
		DriverManager.registerDriver(new com.nuodb.jdbc.Driver());

		//jdbc:com.nuodb://hostname[:port]/dbname

		Connection connection = DriverManager.getConnection("jdbc:com.nuodb://localhost/testDB", "dba", "dba");
		Statement stmt = connection.createStatement();
		String createsql = "create table testDB.NAMES(id varchar(10),name varchar(30))";
		String insertsql = "insert into testDB.NAMES values ('swat','swat')";
		String deletesql = "delete from testDB.NAMES where name='bir'";
		String selectsql = "select * from testDB.NAMES";
		 ResultSet rset = stmt.executeQuery(selectsql);
		try  {
			while(rset.next()){
			System.out.println(rset.getString("NAME"));
			}
		} catch (Exception e) {
			throw e;
		}
		connection.close();
	}
}
