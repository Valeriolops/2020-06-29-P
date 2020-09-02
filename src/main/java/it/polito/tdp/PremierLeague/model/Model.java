package it.polito.tdp.PremierLeague.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;

public class Model {
	
	
private PremierLeagueDAO dao;
private Map<Integer,Match>idMapMatch;
private Graph<Match,DefaultWeightedEdge>grafo;
private Double pesoMassimo;





public Model() {
	dao = new PremierLeagueDAO();
	idMapMatch= new HashMap<Integer, Match>();
	dao.MaplistAllMatches(idMapMatch);
}
	
	
	
public List<Integer>getMese(){
	return dao.getMese();
}
	
	



	
	public void creaGrafo(Integer minuti,Integer mese) {
		grafo = new SimpleWeightedGraph<Match, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		Graphs.addAllVertices(grafo, dao.getAllMAtchVertici(mese, idMapMatch));
		
		for(Adiacenza ad : dao.getAdiacenza(mese, minuti, idMapMatch)) {
			if(grafo.containsVertex(ad.getM1())&&grafo.containsVertex(ad.getM2())) {
				
				Graphs.addEdgeWithVertices(grafo, ad.getM1(), ad.getM2(), ad.getPeso());
			}
		}
		
		
		
		
		
	}
	
	
	
	
	
	
	public int getNVertici() {
		return this.grafo.vertexSet().size();
	}
	
	
	
	
	
	
	public int getNArchi() {
		return this.grafo.edgeSet().size();
	}
	
	
	
	
	
	
	
	public void getPesoMassimo() {
		
		Double pesoSuperMassimo=-1.0;
		for(DefaultWeightedEdge e : this.grafo.edgeSet()) {
			Double peso=grafo.getEdgeWeight(e);
			if(peso>pesoSuperMassimo) {
				pesoSuperMassimo=peso;
				this.pesoMassimo=pesoSuperMassimo;
			}
			
			
			
			
			
		}
		
	}
	
	
	public List<Adiacenza>getAdiacenza(){
		List<Adiacenza>result= new ArrayList<Adiacenza>();
		this.getPesoMassimo();
		for(DefaultWeightedEdge e :this. grafo.edgeSet()) {
			if(grafo.getEdgeWeight(e)==this.pesoMassimo) {
				Match m1= this.grafo.getEdgeSource(e);
				Match m2= this.grafo.getEdgeTarget(e);
				Double peso=this.pesoMassimo;
				Adiacenza ad = new Adiacenza(m1, m2, peso);
				result.add(ad);
				
				
			}
		}
		
		return result;
		
	}
	
	
	
	
	
	
	
	
	
	
	
}
