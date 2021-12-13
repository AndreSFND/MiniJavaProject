package com.java.andrehotel.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionHandler {

    private static final String url = "jdbc:mysql://localhost:3306/andrehotel";
    private static final String username = "andre";
    private static final String password = "root";

    public Connection connection;
    
    public ConnectionHandler() {

    	try {
    		
    		Class.forName("com.mysql.cj.jdbc.Driver");
    		Connection connection = DriverManager.getConnection(url, username, password);
    	
    		this.connection = connection;
    	
    	} catch (SQLException e) {
    	
    		throw new IllegalStateException("Cannot connect the database!", e);
    	
    	} catch (ClassNotFoundException e) {
    	
    		throw new IllegalStateException("Cannot find the driver in the classpath!", e);
    	
    	}
    
    }
    
    public ResultSet queryStatement(String sqlStatement) throws SQLException {
        
		Statement statement;
		
		statement = this.connection.createStatement();
		ResultSet resultSet = statement.executeQuery(sqlStatement);

		return resultSet;
    	
    }

    public void closeConnection() {

        try {
			this.connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    
    }
    
}
