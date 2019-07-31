package it.polito.tdp.turniinfermieri.model;

import java.time.LocalDate;

public class Orario {
	
	private int id_orario;
	private int id_infermiere;
	private LocalDate data;
	private String turno;
	
	public Orario(int id_orario, int id_infermiere, LocalDate data, String turno) {
		super();
		this.id_orario = id_orario;
		this.id_infermiere = id_infermiere;
		this.data = data;
		this.turno = turno;
	}

	public int getId_orario() {
		return id_orario;
	}

	public void setId_orario(int id_orario) {
		this.id_orario = id_orario;
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

	public String getTurno() {
		return turno;
	}

	public void setTurno(String turno) {
		this.turno = turno;
	}
	
	

}
