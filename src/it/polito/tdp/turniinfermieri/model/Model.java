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

}
