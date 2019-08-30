package it.polito.tdp.turniinfermieri.model;

import java.time.LocalDate;
import java.util.Comparator;

public class Infermiere {
	
	private int id_infermiere;
	private String nome;
	private String cognome;
	private LocalDate data_nascita;
	private int numero_riposi;
	private int numero_mattine;
	private int numero_pomeriggi;
	private int numero_notti;
	private int numero_riposi_festivita;

	
	public Infermiere(int id_infermiere, String nome, String cognome, LocalDate data_nascita) {
		super();
		this.id_infermiere = id_infermiere;
		this.nome = nome;
		this.cognome = cognome;
		this.data_nascita = data_nascita;
		this.numero_riposi = 0;
		this.numero_mattine = 0;
		this.numero_pomeriggi = 0;
		this.numero_notti = 0;
		this.numero_riposi_festivita = 0;
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

	@Override
	public String toString() {
		return String.format("%s %s", nome, cognome);
	}

	public int getNumero_riposi() {
		return numero_riposi;
	}

	public void setNumero_riposi(int numero_riposi) {
		this.numero_riposi = numero_riposi;
	}

	static Comparator<Infermiere> riposiComparator() {
        return new Comparator<Infermiere>() {

			@Override
			public int compare(Infermiere inf1, Infermiere inf2) {
				return inf2.getNumero_riposi() - inf1.getNumero_riposi();
			}
        };
    }
	
	static Comparator<Infermiere> riposiFestivitaComparator() {
        return new Comparator<Infermiere>() {

			@Override
			public int compare(Infermiere inf1, Infermiere inf2) {
				return inf2.getNumero_riposi_festivita() - inf1.getNumero_riposi_festivita();
			}
        };
    }
	
	static Comparator<Infermiere> mattineComparator() {
        return new Comparator<Infermiere>() {

			@Override
			public int compare(Infermiere inf1, Infermiere inf2) {
				return inf1.getNumero_mattine() - inf2.getNumero_mattine();
			}
        };
    }
	
	static Comparator<Infermiere> pomeriggiComparator() {
        return new Comparator<Infermiere>() {

			@Override
			public int compare(Infermiere inf1, Infermiere inf2) {
				return inf1.getNumero_pomeriggi() - inf2.getNumero_pomeriggi();
			}
        };
    }
	
	static Comparator<Infermiere> nottiComparator() {
        return new Comparator<Infermiere>() {

			@Override
			public int compare(Infermiere inf1, Infermiere inf2) {
				return inf1.getNumero_notti() - inf2.getNumero_notti();
			}
        };
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

	public int getNumero_riposi_festivita() {
		return numero_riposi_festivita;
	}

	public void setNumero_riposi_festivita(int numero_riposi_festivita) {
		this.numero_riposi_festivita = numero_riposi_festivita;
	}

}
