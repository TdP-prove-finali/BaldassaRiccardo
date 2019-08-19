package it.polito.tdp.turniinfermieri.model;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TestModel {

	public static void main(String[] args) {

		Model model = new Model();
		Map<LocalDate, Map<Infermiere, String>> prova = model.generaOrario();
	//	List<Infermiere> inf = model.getInfermieri();
		//System.out.println(prova);
		
		//for (LocalDate l : prova.keySet()) {
		//	System.out.println("" + l + prova.get(l));
		//}
		
	//	LocalDate data = LocalDate.of(2019, Month.SEPTEMBER, 1);
		//LocalDate fine = LocalDate.of(2020, Month.SEPTEMBER, 1);
		
	//	while(data.isBefore(fine)){

	//		System.out.println(prova.get(data).get(inf.get(0)));

		//	data = data.plusDays(1);
	//	}

		
		
	}
	
}
