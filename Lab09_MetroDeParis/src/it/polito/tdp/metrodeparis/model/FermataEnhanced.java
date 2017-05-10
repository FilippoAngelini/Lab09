package it.polito.tdp.metrodeparis.model;

import com.javadocmd.simplelatlng.LatLng;

public class FermataEnhanced extends Fermata{
	
	private int idLinea;

	public FermataEnhanced(int idFermata, String nome, LatLng coords, int idLinea) {
		super(idFermata, nome, coords);
		this.idLinea=idLinea;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + idLinea;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		FermataEnhanced other = (FermataEnhanced) obj;
		if (idLinea != other.idLinea)
			return false;
		return true;
	}

	
	
}
