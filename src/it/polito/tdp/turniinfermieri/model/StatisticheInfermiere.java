package it.polito.tdp.turniinfermieri.model;

public class StatisticheInfermiere {

	private Infermiere infermiere;
	private int numeroRiposi;
	private int numeroMattine;
	private int numeroPomeriggi;
	private int numeroNotti;
	private int numeroRiposiFestivita;
	
	public StatisticheInfermiere(Infermiere infermiere, int numeroRiposi, int numeroMattine, int numeroPomeriggi,
			int numeroNotti, int numeroRiposiFestivita) {
		super();
		this.infermiere = infermiere;
		this.numeroRiposi = numeroRiposi;
		this.numeroMattine = numeroMattine;
		this.numeroPomeriggi = numeroPomeriggi;
		this.numeroNotti = numeroNotti;
		this.numeroRiposiFestivita = numeroRiposiFestivita;
	}

	public Infermiere getInfermiere() {
		return infermiere;
	}

	public void setInfermiere(Infermiere infermiere) {
		this.infermiere = infermiere;
	}

	public int getNumeroRiposi() {
		return numeroRiposi;
	}

	public void setNumeroRiposi(int numeroRiposi) {
		this.numeroRiposi = numeroRiposi;
	}

	public int getNumeroMattine() {
		return numeroMattine;
	}

	public void setNumeroMattine(int numeroMattine) {
		this.numeroMattine = numeroMattine;
	}

	public int getNumeroPomeriggi() {
		return numeroPomeriggi;
	}

	public void setNumeroPomeriggi(int numeroPomeriggi) {
		this.numeroPomeriggi = numeroPomeriggi;
	}

	public int getNumeroNotti() {
		return numeroNotti;
	}

	public void setNumeroNotti(int numeroNotti) {
		this.numeroNotti = numeroNotti;
	}

	@Override
	public String toString() {
		return String.format(
				"%s - riposi: %s, mattine: %s, pomeriggi: %s, notti: %s",
				infermiere, numeroRiposi, numeroMattine, numeroPomeriggi, numeroNotti);
	}

	public int getNumeroRiposiFestivita() {
		return numeroRiposiFestivita;
	}

	public void setNumeroRiposiFestivita(int numeroRiposiFestivita) {
		this.numeroRiposiFestivita = numeroRiposiFestivita;
	}
	
	public int getNumeroRiposiFeriali() {
		return (numeroRiposi - numeroRiposiFestivita);
	}
	
	
}
