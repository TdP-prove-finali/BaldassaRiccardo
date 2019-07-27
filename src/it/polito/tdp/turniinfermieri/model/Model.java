package it.polito.tdp.turniinfermieri.model;

import java.util.List;

import it.polito.tdp.turniinfermieri.db.TurniInfermieriDAO;

public class Model {
	
	private TurniInfermieriDAO dao;
	private List<Infermiere> infermieri;
	
	public Model() {
		dao = new TurniInfermieriDAO();
		infermieri = dao.getInfermieri();
	}

	public List<Infermiere> getInfermieri() {
		return infermieri;
	}
	
	public boolean trimestriAccettabili(List<Infermiere> infermieri) {

		int cont_1 = 0;
		int cont_2 = 0;
		int cont_3 = 0;
		int cont_4 = 0;
		
		for (Infermiere i : infermieri) {
			if (i.getTrimestre_ferie_lunghe() == 1)
				cont_1++;
			else if (i.getTrimestre_ferie_lunghe() == 2)
				cont_2++;
			else if (i.getTrimestre_ferie_lunghe() == 3)
				cont_3++;
			else if (i.getTrimestre_ferie_lunghe() == 4)
				cont_4++;
			else
				return false;
		}

		if (cont_1 == 3 && cont_2 == 2 && cont_3 == 3 && cont_4 == 3)
			return true;
		
		return false;
	}

}
