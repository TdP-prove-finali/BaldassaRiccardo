package it.polito.tdp.turniinfermieri.model;

import java.time.LocalDate;

public class Ferie {

	private int id_ferie;
	private int id_infermiere;
	private LocalDate data;
	
	public Ferie(int id_ferie, int id_infermiere, LocalDate data) {
		super();
		this.id_ferie = id_ferie;
		this.id_infermiere = id_infermiere;
		this.data = data;
	}

	public int getId_ferie() {
		return id_ferie;
	}

	public void setId_ferie(int id_ferie) {
		this.id_ferie = id_ferie;
	}

	public int getId_infermiere() {
		return id_infermiere;
	}

	public void setId_infermiere(int id_infermiere) {
		this.id_infermiere = id_infermiere;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}
	
	
	
}
