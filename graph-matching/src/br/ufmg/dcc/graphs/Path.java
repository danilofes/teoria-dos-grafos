package br.ufmg.dcc.graphs;

import java.util.ArrayList;
import java.util.List;

import br.ufmg.dcc.graphs.SimpleGraph.Vertex;

public class Path {

	static Path EMPTY = new Path();
	
	private final List<Vertex> vertices = new ArrayList<Vertex>();
	
	public List<Vertex> vertices() {
		return this.vertices;
	}

}
