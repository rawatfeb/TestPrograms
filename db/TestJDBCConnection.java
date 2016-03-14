package db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class TestJDBCConnection {

	
	public static void main(String...args) throws Throwable{
		
		
		
		testConnCopConnection();
		testJDBCConnection();
		
		
		System.exit(0);
		
		
		
	}

	private static void testJDBCConnection() throws Exception {
		Connection con=null;
		Statement stmt =null;
try{
	String driver="oracle.jdbc.driver.OracleDriver";
	try {
		Class.forName(driver);
	} catch (Exception e) {
		System.out.println("could not load the class");
		e.printStackTrace();
	}
	
	String url="jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS_LIST=(ADDRESS=(PROTOCOL=TCP)(HOST=mccurtain.westlan.com)(PORT=1521)))(CONNECT_DATA=(SERVICE_NAME=nvp09a.westlan.com)))";
	String userid="persistr";
	String password="n0vu5r";
	
	try {
		con = DriverManager.getConnection(url,userid,password);
	} catch (Exception e) {
		System.out.println("could not get the DB connection");
		e.printStackTrace();
	}
	
	
	 stmt = con.createStatement();
	String query="select * from persist0.persist where LAST_USED > '06-MAR-15 12.00.28.273605000 PM' ";
	ResultSet rs = stmt.executeQuery(query);
	System.out.println(rs.getRow());
	while(rs.next()){
		System.out.print(rs.getString("SERVICE_NAME")+"\t\t");
		System.out.print(rs.getString("CLIENT_HOST")+"\t\t");
		System.out.println(rs.getString("NOVUS_ENGINE"));
	}
	System.out.println(rs.getRow());


}catch(Exception e){e.printStackTrace();}
finally{
	stmt.close();	
	con.close();
}	
	}
	

	private static void testConnCopConnection() {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
