package it.polito.tdp.imdb.model;

public class Genere {
	
	private Movie movie;
	private String genere;
	
	public Genere(Movie movie, String genere) {
		
		this.movie = movie;
		this.genere = genere;
	}
	
	
	public Movie getMovie() {
		return movie;
	}
	public void setMovie(Movie movie) {
		this.movie = movie;
	}
	public String getGenere() {
		return genere;
	}
	public void setGenere(String genere) {
		this.genere = genere;
	}
	
	

}
