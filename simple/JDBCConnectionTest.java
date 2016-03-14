package simple;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class JDBCConnectionTest {

	//private static final String DB_URL = "jdbc:oracle:thin:@(description=(address_list=(load_balance=no)(address=(protocol=tcp)(port=1521)(host=walthill-vip.westlan.com))(address=(protocol=tcp)(port=1521)(host=wanchese-vip.westlan.com)))(connect_data=(service_name=wlp21a.westlan.com)))";
	private static final String DB_URL = "jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS_LIST=(LOAD_BALANCE=NO)(ADDRESS=(PROTOCOL=TCP)(HOST=dunlevy-vip)(PORT=1521))(ADDRESS=(PROTOCOL=TCP)(HOST=dunlap-vip)(PORT=1521)))(CONNECT_DATA=(SERVICE_NAME=mdp02a.westlan.com)))";
	private static final String USER = "docr";
	private static final String PASS = "n0vu5r";

	public static void main(String[] args) throws Exception {
	getDatabaseConnection();
}

	private static void getDatabaseConnection() throws Exception{
		 try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw e;
		}
		 System.out.println("Connecting to database...");
		 Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);
		  System.out.println("Creating statement..."+conn);
	       Statement stmt = conn.createStatement();
	       System.out.println("Creating statement..."+stmt);
	        ResultSet rs = stmt.executeQuery("select * from wwlmetadoc. where GUID='I48107d26031e11e5b86bd602cb8781fa'");  //WCSPAC2 //wcsfed3  //wcsfs2    //metadoc: wwlmetadoc
	     while(rs.next()){
	    	 System.out.println(rs.getString(1)+" "+rs.getString(2));
	     }
	      stmt.close();
	      conn.close();
	      System.out.println("Done");
	}
	
}
