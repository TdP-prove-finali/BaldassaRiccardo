package it.polito.tdp.turniinfermieri.model;

import java.time.LocalDate;

public class Infermiere implements Comparable<Infermiere>{
	
	private int id_infermiere;
	private String nome;
	private String cognome;
	private LocalDate data_nascita;
	private int trimestre_ferie_lunghe;
	private int numero_riposi;
	private int numero_mattine;
	private int numero_pomeriggi;
	private int numero_notti;

	
	public Infermiere(int id_infermiere, String nome, String cognome, LocalDate data_nascita,
			int trimestre_ferie_lunghe) {
		super();
		this.id_infermiere = id_infermiere;
		this.nome = nome;
		this.cognome = cognome;
		this.data_nascita = data_nascita;
		this.trimestre_ferie_lunghe = trimestre_ferie_lunghe;
		this.numero_riposi = 0;
		this.numero_mattine = 0;
		this.numero_pomeriggi = 0;
		this.numero_notti = 0;
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
		return String.format("%s %s %d", nome, cognome, trimestre_ferie_lunghe);
	}

	public int getNumero_riposi() {
		return numero_riposi;
	}

	public void setNumero_riposi(int numero_riposi) {
		this.numero_riposi = numero_riposi;
	}

	@Override
	public int compareTo(Infermiere altro) {
		return altro.getNumero_riposi() - this.getNumero_riposi();
	}

	public int getNumero_mattine() {
		return numero_mattine;
	}

	public void setNumero_mattine(int numero_mattine) {
		this.numero_mattine = numero_mattine;
	}

	public int getNumero_pomeriggi() {
		return numero_pomeriggi;
	}

	public void setNumero_pomeriggi(int numero_pomeriggi) {
		this.numero_pomeriggi = numero_pomeriggi;
	}

	public int getNumero_notti() {
		return numero_notti;
	}

	public void setNumero_notti(int numero_notti) {
		this.numero_notti = numero_notti;
	}

}
