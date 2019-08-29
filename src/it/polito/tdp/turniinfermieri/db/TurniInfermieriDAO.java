package it.polito.tdp.turniinfermieri.db;

import java.sql.Connection;
import java.sql.Date;
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
							res.getDate("data_nascita").toLocalDate()));
				} catch (Throwable t) {
					t.printStackTrace();
					System.out.println(res.getInt("id_infermiere"));
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
					System.out.println(res.getInt("id_ferie"));
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

	public void modificaFerieInfermiere(Ferie ferie) {

		String sql = "UPDATE ferie SET data = ? WHERE id_ferie = ?" ;
		try {
			Connection conn = DBConnect.getConnection() ;
			
			PreparedStatement st = conn.prepareStatement(sql) ;
			if (ferie.getData() != null) {
				// aggiunta di 12 ore alla data da modificare perché altrimenti nel db prendeva il valore del giorno precedente
				final long hours12 = 12L * 60L * 60L * 1000L;
				Date d = Date.valueOf(ferie.getData());
				Date newData = new Date(d.getTime() + hours12);
			
				st.setDate(1, newData);
			}
			else
				st.setDate(1, null);
			
			st.setInt(2, ferie.getId_ferie());
			
			st.executeUpdate();
			
			conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public List<Integer> getVincoli() {
		String sql = "SELECT valore FROM vincoli" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Integer> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(res.getInt("valore"));
				} catch (Throwable t) {
					t.printStackTrace();
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
