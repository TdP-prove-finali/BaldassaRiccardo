package it.polito.tdp.turniinfermieri.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.turniinfermieri.model.Ferie;
import it.polito.tdp.turniinfermieri.model.Infermiere;

public class TurniInfermieriDAO {
	
	public List<Infermiere> getInfermieri() {
		String sql = "SELECT * FROM infermiere" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Infermiere> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Infermiere(res.getInt("id_infermiere"),
							res.getString("nome"), 
							res.getString("cognome"), 
							res.getDate("data_nascita").toLocalDate(),
							res.getInt("trimestre_ferie_lunghe")));
				} catch (Throwable t) {
					t.printStackTrace();
					System.out.println(res.getInt("id"));
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}

	public void modificaInfermiere(Infermiere infermiere) {

		String sql = "UPDATE infermiere SET trimestre_ferie_lunghe = ? WHERE id_infermiere = ?" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, infermiere.getTrimestre_ferie_lunghe());
			st.setInt(2, infermiere.getId_infermiere());
			
			st.executeUpdate();
			
			conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public List<Ferie> getFerie() {
		String sql = "SELECT * FROM ferie" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Ferie> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Ferie(res.getInt("id_ferie"),
							res.getInt("id_infermiere"), 
							res.getDate("data").toLocalDate()));
				} catch (Throwable t) {
					t.printStackTrace();
					System.out.println(res.getInt("id"));
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}


}
