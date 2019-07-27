package it.polito.tdp.turniinfermieri.db;

import java.sql.Connection;



public class TestDAO {

	public static void main(String[] args) {

		
		try {
			Connection connection = DBConnect.getConnection();
			connection.close();
			System.out.println("Connection Test PASSED");
			
			TurniInfermieriDAO dao = new TurniInfermieriDAO();
			
			System.out.println(dao.getInfermieri()) ;

		} catch (Exception e) {
			throw new RuntimeException("Test FAILED", e);
		}
	
		
	}

}
