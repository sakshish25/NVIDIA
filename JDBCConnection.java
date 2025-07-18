package Nvdia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class JDBCConnection {

	
		Connection con;
		Statement s;
		
		public JDBCConnection()
		{
			try
			{
				Class.forName("com.mysql.cj.jdbc.Driver");
				
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/qsp", "root", "Sql@#123@#");
				s = con.createStatement();		
				System.out.println("Connection Successfull");
			}
			
			catch(ClassNotFoundException | SQLException e)
			{
				System.out.println("Database Connection failed: "+e.getMessage());
				e.printStackTrace();
			}
		}
	}
