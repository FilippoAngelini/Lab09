package it.polito.tdp.metrodeparis.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graphs;
import org.jgrapht.WeightedGraph;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.metrodeparis.dao.MetroDAO;

public class Model {
	
	WeightedGraph<Fermata,DefaultWeightedEdge> graph;
	MetroDAO dao;
	Map<Fermata,Fermata> map;
	
	public Model(){
		dao = new MetroDAO();
		graph = this.getGrafo();
		map = new HashMap <Fermata,Fermata>();
	}

	private WeightedGraph<Fermata, DefaultWeightedEdge> getGrafo() {
		
		if(graph==null)
			this.creaGrafo();
		
		return graph;
	}

	private void creaGrafo() {
		
		this.graph = new SimpleWeightedGraph<Fermata,DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		Graphs.addAllVertices(graph, dao.getAllFermate());
		System.out.println(graph);
				
		for(Connessione c : dao.listConnessioni()) {
			DefaultWeightedEdge e = graph.addEdge(c.getFermata1(), c.getFermata2());
			if (e != null)
				graph.setEdgeWeight(e, c.getPeso());
		}
		
		System.out.println(graph);
	}

	public String getPercorso(Fermata partenza, Fermata arrivo) {
		
		DijkstraShortestPath<Fermata,DefaultWeightedEdge> path = new DijkstraShortestPath<Fermata,DefaultWeightedEdge>(graph,partenza,arrivo);
		
		map.put(partenza, null);

		List<Fermata> ris = new ArrayList<Fermata>();
		
		double tempoTot = 0;
		
		for(DefaultWeightedEdge e : path.getPathEdgeList()){
			this.edgeTraversed(e);
			tempoTot += graph.getEdgeWeight(e)*3600 + 30;
		}
		
		while(arrivo!=null ) {
			ris.add(arrivo) ;
			arrivo = map.get(arrivo) ;
		}
		
		Collections.reverse(ris);
		
		int totalSecs = (int)(tempoTot-30);
		
		int hours = totalSecs / 3600;
		int minutes = (totalSecs % 3600) / 60;
		int seconds = totalSecs % 60;
		
		String risTot = ris.toString() + "\n\n" + "Tempo di percorrenza stimato: " + hours + ":" + minutes + ":" + seconds;
		
		return risTot;
	}
	
	public void edgeTraversed(DefaultWeightedEdge e) {

		Fermata f1 = graph.getEdgeSource(e) ;
		Fermata f2 = graph.getEdgeTarget(e) ;
		
		if(map.containsKey(f1) && map.containsKey(f2))
			return ;
		
		if( !map.containsKey(f1) ) {
			// c1 è quello nuovo
			map.put(f1,  f2) ;
		} else {
			// c2 è quello nuovo
			map.put(f2,  f1) ;
		}
	}

}
