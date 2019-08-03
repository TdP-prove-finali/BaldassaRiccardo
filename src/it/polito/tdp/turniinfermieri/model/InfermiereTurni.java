package it.polito.tdp.turniinfermieri.model;

public class InfermiereTurni {
	
	private Infermiere infermiere;
	private String turno;
	
	public InfermiereTurni(Infermiere infermiere, String turno) {
		super();
		this.infermiere = infermiere;
		this.turno = turno;
	}

	public Infermiere getInfermiere() {
		return infermiere;
	}

	public void setInfermiere(Infermiere infermiere) {
		this.infermiere = infermiere;
	}

	public String getTurno() {
		return turno;
	}

	public void setTurno(String turno) {
		this.turno = turno;
	}
	

}
