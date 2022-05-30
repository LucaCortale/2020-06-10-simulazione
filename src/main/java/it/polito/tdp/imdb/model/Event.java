package it.polito.tdp.imdb.model;

public class Event {
	
	private int time;
	private Actor attore;
	
	
	public Event(int time, Actor attore) {
	
		this.time = time;
		this.attore = attore;
	}
	
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	public Actor getAttore() {
		return attore;
	}
	public void setAttore(Actor attore) {
		this.attore = attore;
	}
	
	

}
