package it.polito.tdp.metrodeparis.model;

public class Connessione {
	
	private Fermata fermata1;
	private Fermata fermata2;
	private double peso;
	private int idConnessione;
	
	public Connessione(int idConnessione, Fermata fermata1, Fermata fermata2, double peso) {
		this.fermata1 = fermata1;
		this.fermata2 = fermata2;
		this.peso = peso;
		this.idConnessione = idConnessione;
	}

	public Fermata getFermata1() {
		return fermata1;
	}

	public Fermata getFermata2() {
		return fermata2;
	}

	public double getPeso() {
		return peso;
	}

	public int getIdConnessione() {
		return idConnessione;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idConnessione;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Connessione other = (Connessione) obj;
		if (idConnessione != other.idConnessione)
			return false;
		return true;
	}
	
	

}
