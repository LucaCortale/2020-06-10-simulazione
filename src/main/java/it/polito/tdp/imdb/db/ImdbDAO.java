package it.polito.tdp.imdb.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.imdb.model.Actor;
import it.polito.tdp.imdb.model.Adiacenza;
import it.polito.tdp.imdb.model.Director;
import it.polito.tdp.imdb.model.Genere;
import it.polito.tdp.imdb.model.Movie;

public class ImdbDAO {
	
	public List<Actor> listAllActors(){
		String sql = "SELECT * FROM actors";
		List<Actor> result = new ArrayList<Actor>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Actor actor = new Actor(res.getInt("id"), res.getString("first_name"), res.getString("last_name"),
						res.getString("gender"));
				
				result.add(actor);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Movie> listAllMovies(){
		String sql = "SELECT * FROM movies";
		List<Movie> result = new ArrayList<Movie>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Movie movie = new Movie(res.getInt("id"), res.getString("name"), 
						res.getInt("year"), res.getDouble("rank"));
				
				result.add(movie);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public List<Director> listAllDirectors(){
		String sql = "SELECT * FROM directors";
		List<Director> result = new ArrayList<Director>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Director director = new Director(res.getInt("id"), res.getString("first_name"), res.getString("last_name"));
				
				result.add(director);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List <Genere> getGeneri(Map<Integer,Movie> idMapMovie){
		
		String sql = "SELECT md.movie_id,md.genre "
				+ "FROM movies_genres md";
		List<Genere> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();
		
		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while(res.next()) {
				
				Movie movie = idMapMovie.get(res.getInt("md.movie_id"));
				String s = res.getString("md.genre");
				Genere genere = new Genere(movie,s);
				result.add(genere);
				
			}
			
			conn.close();
			return result;
			
		}catch(SQLException e ) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	public List <String> getGeneriBOX(){
		
		String sql = "SELECT DISTINCT(md.genre) "
				+ "FROM movies_genres md";
		List<String> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();
		
		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while(res.next()) {
				
				
				String s = res.getString("md.genre");
				
				result.add(s);
				
			}
			
			conn.close();
			return result;
			
		}catch(SQLException e ) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	public List<Actor> listAllActorsGenre(String genere,Map <Integer,Actor> idMapAct){
		
		String sql = "SELECT DISTINCT(r.actor_id) "
				+ "FROM movies_genres md,roles r "
				+ "WHERE r.movie_id = md.movie_id AND md.genre = ? ";
		List<Actor> result = new ArrayList<Actor>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, genere);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Actor actor = idMapAct.get(res.getInt("r.actor_id"));
				
				result.add(actor);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Adiacenza> listAdiacenze(String genere,Map <Integer,Actor> idMapAct){
		
		String sql = "SELECT r1.actor_id,r2.actor_id, COUNT(*) AS peso "
				+ "FROM roles r1,roles r2,movies_genres md "
				+ "WHERE r1.actor_id > r2.actor_id AND  md.movie_id = r1.movie_id AND "
				+ "r2.movie_id = md.movie_id AND md.genre = ?"
				+ "GROUP BY r1.actor_id,r2.actor_id ";
		
		List<Adiacenza> result = new ArrayList<Adiacenza>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, genere);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Actor a1 = idMapAct.get(res.getInt("r1.actor_id"));
				Actor a2 = idMapAct.get(res.getInt("r2.actor_id"));	
				int peso = res.getInt("peso");
				result.add(new Adiacenza(a1,a2,peso));
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	
}
