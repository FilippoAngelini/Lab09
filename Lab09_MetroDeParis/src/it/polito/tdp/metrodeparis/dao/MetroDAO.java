package it.polito.tdp.metrodeparis.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.metrodeparis.model.Connessione;
import it.polito.tdp.metrodeparis.model.Fermata;

public class MetroDAO {

	public List<Fermata> getAllFermate() {

		final String sql = "SELECT id_fermata, nome, coordx, coordy FROM fermata ORDER BY nome ASC";
		List<Fermata> fermate = new ArrayList<Fermata>();

		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Fermata f = new Fermata(rs.getInt("id_Fermata"), rs.getString("nome"), new LatLng(rs.getDouble("coordx"), rs.getDouble("coordy")));
				fermate.add(f);
			}

			st.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore di connessione al Database.");
		}

		return fermate;
	}
	
	public List<Connessione> listConnessioni() {
		
		final String sql = "SELECT id_connessione, fermata1.id_fermata as id1, fermata1.nome as nome1, fermata1.coordX as x1, fermata1.coordy as y1, fermata2.id_fermata as id2, fermata2.nome as nome2, fermata2.coordX as x2, fermata2.coordy as y2, velocita FROM fermata as fermata1, fermata as fermata2, connessione, linea WHERE connessione.id_stazP=fermata1.id_fermata AND connessione.id_stazA=fermata2.id_fermata AND connessione.id_linea=linea.id_linea";
		List<Connessione> connessioni = new ArrayList<Connessione>();
		
		

		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				LatLng coords1 = new LatLng(rs.getDouble("x1"), rs.getDouble("y1"));
				LatLng coords2 = new LatLng(rs.getDouble("x2"), rs.getDouble("y2"));
				Fermata f1 = new Fermata(rs.getInt("id1"), rs.getString("nome1"), coords1);
				Fermata f2 = new Fermata(rs.getInt("id2"), rs.getString("nome2"), coords2);
				
				int velocita = rs.getInt("velocita");
				
				double distanza = LatLngTool.distance(coords1, coords2, LengthUnit.KILOMETER);
				
				double peso = distanza/velocita;
				
				Connessione c = new Connessione(rs.getInt("id_connessione"),f1,f2, peso);
				
				connessioni.add(c);
				
			}

			st.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore di connessione al Database.");
		}

		return connessioni;
	}

}
