package it.polito.tdp.turniinfermieri.model;

import java.time.LocalDate;
import java.util.Map;

public class TestModel {

	public static void main(String[] args) {

		Model model = new Model();
		Map<LocalDate, DataTurni> prova = model.generaOrario();
		
		for (DataTurni dt : prova.values()) {
			//if (dt.getData().getYear() == 2019)
				System.out.println(dt);
		}
		
	}
	
}
