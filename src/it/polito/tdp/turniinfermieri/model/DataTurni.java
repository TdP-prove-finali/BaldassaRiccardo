package it.polito.tdp.turniinfermieri.model;

import java.time.LocalDate;
import java.util.Map;

public class DataTurni {

	private LocalDate data;
	private Map<Infermiere, String> turni;
	
	public DataTurni(LocalDate data, Map<Infermiere, String> turni) {
		super();
		this.data = data;
		this.turni = turni;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public Map<Infermiere, String> getTurni() {
		return turni;
	}

	public void setTurni(Map<Infermiere, String> turni) {
		this.turni = turni;
	}

	@Override
	public String toString() {
		return String.format("DataTurni [data=%s, turni=%s]", data, turni);
	}
	

	
}