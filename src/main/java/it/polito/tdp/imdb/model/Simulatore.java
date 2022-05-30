package it.polito.tdp.imdb.model;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;

public class Simulatore {
	
	//coda degli eventi
	//private PriorityQueue<Event> queue;
	
	//Parametri SIMULAZIONE
	private int giorni;
	List<Actor>list;
	boolean flag=false;
	//output
	private int pause=0;
	private List <Actor> listaAttori = new ArrayList<>();
	
	//stato iniziale
	private Graph <Actor,DefaultWeightedEdge> grafo;
	
	public Simulatore(Graph <Actor,DefaultWeightedEdge> grafo,int giorni) {
	this.grafo= grafo;	
	this.giorni = giorni;
	}
	
	public void init() {
		
		
		list = new ArrayList<>();
		
		for(Actor a : this.grafo.vertexSet()) {
			list.add(a);
		}
		
	}
	
	
	public void processEvent( ) {
		
		
		for(int i=1;i<=this.giorni;i++){
			
		if(i>2 && listaAttori.get(listaAttori.size()-1).gender.compareTo(listaAttori.get(listaAttori.size()-1).gender)==0 && flag ==false) {
			if(Math.random() <= 0.90) {
				i++;
				this.pause++;
				flag = true;
				continue;
			}
		}
			
		if(i ==1 || flag == true) {
			//scelgo a caso il primo
			int n = (int) (Math.random()*(this.list.size()-1)+1);
			Actor a = this.list.get(n);
			listaAttori.add(a);
			System.out.println(a);
			flag = false;
			continue;
		}
		
		if(Math.random() <= 0.60) {
			
			int n = (int) (Math.random()*(this.list.size()-1)+1);
			Actor a = this.list.get(n);
			if(!listaAttori.contains(a)) { listaAttori.add(a);System.out.println(a);}
			
		}else {
			int max =0;
			Actor act = new Actor() ;
			for(Actor aa : Graphs.neighborListOf(this.grafo, listaAttori.get(listaAttori.size()-1))) {
				
				if(aa != null) {
					
					if(this.grafo.degreeOf(aa) >  max) {
						max = this.grafo.degreeOf(aa);
						act = aa;
						}
					}else continue;
					
				if(!listaAttori.contains(act)) { listaAttori.add(act); System.out.println(act);	}
				}
			}
		
		}

	}
	
	public String getRis() {
		String s ="";
		for(Actor a : listaAttori) {
			s += a +"\n";
		}
		s += "Prendendo : "+this.pause+" pause";
		return s;
	}
	
}
