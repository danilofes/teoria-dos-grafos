package br.ufmg.dcc.graphs;

import java.util.LinkedList;
import java.util.List;

import br.ufmg.dcc.graphs.SimpleGraph.Edge;

public class Path {

	static Path EMPTY = new Path();
	
	private final LinkedList<Edge> edges = new LinkedList<Edge>();
	
	public Path() {
	}
	
	public Path(Edge edge) {
		this.edges.add(edge);
	}
	
	public Path append(Edge e) {
		this.edges.add(e);
		return this;
	}
	
	public Path prepend(Edge e) {
		this.edges.addFirst(e);
		return this;
	}
	
	public List<Edge> edges() {
		return this.edges;
	}

	public boolean isEmpty() {
		return this.edges.isEmpty();
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Edge e : edges) {
			sb.append(e.vertex1());
			sb.append(' ');
		}
		if (edges.size() > 0) {
			sb.append(edges.getLast().vertex2());
		}
		return sb.toString();
	}
}
