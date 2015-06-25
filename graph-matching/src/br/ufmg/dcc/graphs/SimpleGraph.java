package br.ufmg.dcc.graphs;

import java.util.Collection;

public interface SimpleGraph {

	Collection<Vertex> vertices();
	
	Collection<Edge> edgesOf(Vertex v);

}
