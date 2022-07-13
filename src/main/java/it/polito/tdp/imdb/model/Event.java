package it.polito.tdp.imdb.model;

public class Event implements Comparable<Event>{

	public enum EventType{
		
		NUOVA_INTERVISTA,
		PAUSA,
		
	}
	
	private int time;
	private EventType type;
	
	
	
	
	public Event(int time, EventType type) {
		super();
		this.time = time;
		this.type = type;
	}




	public int getTime() {
		return time;
	}




	public void setTime(int time) {
		this.time = time;
	}




	public EventType getType() {
		return type;
	}




	public void setType(EventType type) {
		this.type = type;
	}




	@Override
	public int compareTo(Event o) {
		// TODO Auto-generated method stub
		return this.time-o.time;
	}
	
	

}
