package it.polito.tdp.imdb.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.imdb.db.ImdbDAO;

public class Model {
	
	Graph <Actor,DefaultWeightedEdge> grafo;
	ImdbDAO dao;
	Map <Integer,Movie> idMapMovie;
	Map <Integer,Actor> idMapActor;
	
	public Model() {
		
		idMapMovie = new HashMap<>();
		idMapActor = new HashMap<>();
		dao = new ImdbDAO();
	}
	
	public List<String> getGeneriBOX(){
		List<String> lista = dao.getGeneriBOX();
		Collections.sort(lista);
		
		return lista;
	}
	
	
	
	public void creaGrafo(String genere){
		
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		Graphs.addAllVertices(this.grafo, dao.listAllActorsGenre(genere, idMapActor));
		
		for(Adiacenza a : dao.listAdiacenze(genere,idMapActor)) {
			Graphs.addEdgeWithVertices(this.grafo, a.getA1(), a.getA2(),a.getPeso());
		}
		
	}
	
	public List<Actor> attoriSimili(Actor actor) {
		List <Actor> attori = new ArrayList<>();
		
		for(Actor a : Graphs.neighborListOf(this.grafo, actor)) {
			for(Actor aa : Graphs.neighborListOf(this.grafo, a)) {
				if(!attori.contains(aa)) {
					attori.add(aa);
				}
			}
		}
		Collections.sort(attori);
		return attori;
		
	}
	
	public List<Actor> getActorGG(){
		
		List <Actor> list = new ArrayList<>();
		for(Actor a : this.grafo.vertexSet()) {
			list.add(a);
		}
		Collections.sort(list);
		return list;
	}
	
	public String simula(int giorni) {
		
		Simulatore sim = new Simulatore(this.grafo,giorni);
		sim.init();
		sim.processEvent();
		return sim.getRis();
		
	}
	
	
	public String getVertici() {
		return " #VERTICI : "+this.grafo.vertexSet().size();
	}
	
	public String getArchi() {
		return " #ARCHI : "+this.grafo.edgeSet().size();
	}
	
	public void setMap() {
		
		for(Movie m: dao.listAllMovies()) {
			idMapMovie.put(m.getId(), m);
		}
		
		for(Actor a : dao.listAllActors()) {
			idMapActor.put(a.getId(), a);
		}
		
	}

}
