package it.polito.tdp.turniinfermieri.model;

import java.time.LocalDate;
import java.util.Map;

public class TestModel {

	public static void main(String[] args) {

		Model model = new Model();
		Map<LocalDate, Map<Infermiere, String>> prova = model.generaOrario();
		
		for (LocalDate l : prova.keySet()) {
			System.out.println("" + l + prova.get(l));
		}
		
	}
	
}
