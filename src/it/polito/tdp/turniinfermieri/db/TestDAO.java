package it.polito.tdp.turniinfermieri.db;



public class TestDAO {

	public static void main(String[] args) {

		
			
			TurniInfermieriDAO dao = new TurniInfermieriDAO();
			System.out.println(dao.getFerie());
			//dao.modificaFerieInfermiere(new Ferie(1, 1, LocalDate.of(2019, Month.SEPTEMBER, 01)));
			System.out.println(dao.getFerie());

			

	
		
	}

}
