package it.polito.tdp.imdb.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.imdb.db.ImdbDAO;

public class Model {
	
	ImdbDAO dao;
	Map<Integer,Actor> mappaActor;
	Graph<Actor, DefaultWeightedEdge> grafo;
	
	//risultati simulazione
	public int nPause;
	public List<Actor> intervistati;
	
	public Model() {
		
		dao = new ImdbDAO();
		
	}
	
	
	public void creaGrafo(String genere) {
		
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		for(Adiacenza a : this.dao.listAdiacenze(genere, mappaActor)) {
			Graphs.addEdgeWithVertices(this.grafo, a.getA1(), a.getA2(),a.getPeso());
		}
		
	}
	
	public void simula(int n) {
		
		Simulatore sim = new Simulatore(this.grafo, n,this.getAttori());
		intervistati = new ArrayList<>();
		sim.init();
		sim.run();
		this.nPause = sim.nPause;
		
		for(Actor a : sim.intervistati) {
			this.intervistati.add(a);
		}
	
	}
	
	
	public List<Actor> getAttori(){
		List<Actor> lista = new ArrayList<>();
		for(Actor a :this.grafo.vertexSet())
			lista.add(a);
		Collections.sort(lista);
		return lista;
	}
	
	public List<Actor> getSimili(Actor a){
		
		List<Actor> simili = new ArrayList<>();
		ConnectivityInspector<Actor, DefaultWeightedEdge> ci = new ConnectivityInspector<>(this.grafo);
		simili.addAll(ci.connectedSetOf(a));
		Collections.sort(simili);
		return simili;
	}
	
	public String getVertici() {
		return "#VERTICI : "+this.grafo.vertexSet().size()+"\n";
	}
		
	public String getEdge() {
		return "#ARCHI : "+this.grafo.edgeSet().size()+"\n";
	}
	
	
	public List<String> listAllGenere(){
		return this.dao.listAllGenere();
	}
	
	public void setMappe() {
		mappaActor= new HashMap<>();
		for(Actor a : this.dao.listAllActors()) {
			mappaActor.put(a.getId(), a);
		}
		
	}
	
}
