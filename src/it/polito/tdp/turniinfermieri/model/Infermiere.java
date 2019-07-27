package it.polito.tdp.turniinfermieri.model;

import java.time.LocalDate;

public class Infermiere {
	
	private int id_infermiere;
	private String nome;
	private String cognome;
	private LocalDate data_nascita;
	private int trimestre_ferie_lunghe;
	
	public Infermiere(int id_infermiere, String nome, String cognome, LocalDate data_nascita,
			int trimestre_ferie_lunghe) {
		super();
		this.id_infermiere = id_infermiere;
		this.nome = nome;
		this.cognome = cognome;
		this.data_nascita = data_nascita;
		this.trimestre_ferie_lunghe = trimestre_ferie_lunghe;
	}

	public int getId_infermiere() {
		return id_infermiere;
	}

	public void setId_infermiere(int id_infermiere) {
		this.id_infermiere = id_infermiere;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public LocalDate getData_nascita() {
		return data_nascita;
	}

	public void setData_nascita(LocalDate data_nascita) {
		this.data_nascita = data_nascita;
	}

	public int getTrimestre_ferie_lunghe() {
		return trimestre_ferie_lunghe;
	}

	public void setTrimestre_ferie_lunghe(int trimestre_ferie_lunghe) {
		this.trimestre_ferie_lunghe = trimestre_ferie_lunghe;
	}

	@Override
	public String toString() {
		return String.format("%s %s", nome, cognome);
	}

}
