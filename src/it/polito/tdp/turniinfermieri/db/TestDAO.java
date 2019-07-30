package it.polito.tdp.turniinfermieri.db;

import java.time.LocalDate;
import java.time.Month;

import it.polito.tdp.turniinfermieri.model.Ferie;

public class TestDAO {

	public static void main(String[] args) {

		
			
			TurniInfermieriDAO dao = new TurniInfermieriDAO();
			System.out.println(dao.getFerie());
			//dao.modificaFerieInfermiere(new Ferie(1, 1, LocalDate.of(2019, Month.SEPTEMBER, 01)));
			System.out.println(dao.getFerie());

			

	
		
	}

}
