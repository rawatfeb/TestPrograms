package db;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.hsqldb.server.Server;



public class TestHSQLDB {

	
	
	public static void main(String...args) throws Exception{
		Connection c=null;
		try {
	       // Class.forName("org.hsqldb.jdbcDriver" );  
	        Class.forName("org.hsqldb.jdbcDriver").newInstance();	        
	    } catch (Exception e) {
	        System.out.println("ERROR: failed to load HSQLDB JDBC driver.");
	        e.printStackTrace();
	        return;
	    }
	   try {
		 c = DriverManager.getConnection("jdbc:hsqldb:hsql://10.30.144.151:9001/nibdb");
//		   Connection c = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/nibdb", "sa", "");
	   } catch (SQLException e) {
			System.out.println("could not get the connection");
			e.printStackTrace();
		}	
		Statement stmt = c.createStatement();
		//String sql="create table nibdb(nibname varchar(30), nibtype varchar(30))";
		//stmt.execute(sql);
		
		String sql="create schema LOAD";
		stmt.execute(sql);
		
		String insertSql="insert into nibdb values ('NIBANZ1','OLD')";		
		try {
			stmt.execute(insertSql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		String selectQuery="select * from nibdb";
		 ResultSet rs=null;
		try {
			rs = stmt.executeQuery(selectQuery);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 try {
			while(rs.next()){
				 System.out.println(rs.getString("nibname"));
				 System.out.println(rs.getString("nibtype"));
			 }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		System.exit(0);
	
	}
	
	public static Server startDB(String dbURL){
		  Server hsqlServer=new Server();
		  hsqlServer.setLogWriter(new PrintWriter(System.out));
		  hsqlServer.setDatabaseName(0,"testDB");
		  hsqlServer.setDatabasePath(0,dbURL);		//file:/tmp/batchtest
		  hsqlServer.start();
		  return hsqlServer;
		}
	
}
