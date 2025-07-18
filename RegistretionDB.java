package Nvdia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.cj.Query;

public class RegistretionDB {
 
	public static void main(String[] args) {
		
	 try {
		 
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/qsp");
		Statement s = c.createStatement();
		
		ResultSet ex = s.executeQuery("INSERT INTO registretion VALUES ('usernamefield' , mobilenofield ,'cities')");
		
		
	} catch (ClassNotFoundException | SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
	
}
