package it.polito.tdp.imdb.model;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.imdb.model.Event.EventType;

public class Simulatore {
	
	//input
	private int N;
	
	//output
	int nPause;
	List<Actor> intervistati;
	
	//coda
	private PriorityQueue<Event> queue;
	
	//mondo
	
	private Graph<Actor, DefaultWeightedEdge> grafo;
	private List<Actor> listActor;
	int stessoGenere=0;
	boolean giornoDpoPausa=false;
	
	public Simulatore(Graph <Actor,DefaultWeightedEdge>grafo,int N,List<Actor> listActor){
		this.grafo = grafo;
		this.N=N;
		this.listActor=listActor;
		intervistati = new ArrayList<>();
	}

	public void init() {
		
		this.queue= new PriorityQueue<>();
		
		Event e = new Event(0,EventType.NUOVA_INTERVISTA);
		this.queue.add(e);
	}
	
	public void run() {
		while(!this.queue.isEmpty()) {
			Event e = this.queue.poll();
			
			this.processEvent(e);
		}
	}
	
	private void processEvent(Event e) {
		Actor intervistato;
		if(e.getTime()<this.N) {
		
			switch(e.getType()) {
			
			case NUOVA_INTERVISTA:
				//per primo giorno
				if(e.getTime()==0) {
					intervistato = this.actorCasuale();
					intervistati.add(intervistato);
					listActor.remove(intervistato);
					Event event = new Event(e.getTime()+1,EventType.NUOVA_INTERVISTA);
					this.queue.add(event);
					this.stessoGenere++;
					break;
				}else {
					
				//per gli altri
					if(Math.random()<0.60) {
						
						if(this.stessoGenere>=2) {
							if(Math.random()<0.90) {
								Event event = new Event(e.getTime()+1,EventType.PAUSA);
								this.queue.add(event);
								stessoGenere=0;
								giornoDpoPausa=true;
								break;
							}else {
								intervistato = this.actorCasuale();
								this.incrementoGenere(intervistato);
								intervistati.add(intervistato);
								listActor.remove(intervistato);
								Event event = new Event(e.getTime()+1,EventType.NUOVA_INTERVISTA);
								this.queue.add(event);
								break;
							}
						}
						
						intervistato = this.actorCasuale();
						this.incrementoGenere(intervistato);
						intervistati.add(intervistato);
						listActor.remove(intervistato);
						Event event = new Event(e.getTime()+1,EventType.NUOVA_INTERVISTA);
						this.queue.add(event);
						break;
						
					}else {
						
					
						if(this.stessoGenere>=2) {
							if(Math.random()<0.90) {
								Event event = new Event(e.getTime()+1,EventType.PAUSA);
								this.queue.add(event);
								stessoGenere=0;
								giornoDpoPausa=true;
								break;
							}else {
								intervistato = this.actorGradoMAssimo(this.intervistati.get(this.intervistati.size()-1));
								this.incrementoGenere(intervistato);
								intervistati.add(intervistato);
								listActor.remove(intervistato);
								Event event = new Event(e.getTime()+1,EventType.NUOVA_INTERVISTA);
								this.queue.add(event);
								break;
								}
							}
								
						if(giornoDpoPausa == true) {
							intervistato = this.actorCasuale();
						}else {
						intervistato = this.actorGradoMAssimo(this.intervistati.get(this.intervistati.size()-1));
						}
						this.incrementoGenere(intervistato);
						intervistati.add(intervistato);
						listActor.remove(intervistato);
						Event event = new Event(e.getTime()+1,EventType.NUOVA_INTERVISTA);
						this.queue.add(event);
						break;
							}
					
				}
					
					
				
				
				
			case PAUSA:
				Event event = new Event(e.getTime()+1,EventType.NUOVA_INTERVISTA);
				this.queue.add(event);
				this.giornoDpoPausa=true;
				this.nPause++;
				break;
				
			}
			
		}
		
	}
	
	public Actor actorGradoMAssimo(Actor ActorGGprecedente){
		Actor gradoMax=null;
		int grado =0;
		for(Actor a : Graphs.neighborListOf(this.grafo, ActorGGprecedente)) {
			if(this.grafo.degreeOf(a)>grado) {
				gradoMax = a;
				grado = this.grafo.degreeOf(a);
			}
		}
		
		return gradoMax;
	}
	
	public void incrementoGenere(Actor a) {
		if(a.getGender().equals(intervistati.get(intervistati.size()-1).getGender()) ) {
			this.stessoGenere++;
		}
	}
	
	public Actor actorCasuale(){
		
	
		int n = (int)(Math.random()*this.listActor.size());
		return listActor.get(n);
	}
}
