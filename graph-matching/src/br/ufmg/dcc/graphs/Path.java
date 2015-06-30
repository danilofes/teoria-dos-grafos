package br.ufmg.dcc.graphs;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import br.ufmg.dcc.graphs.SimpleGraph.Edge;
import br.ufmg.dcc.graphs.SimpleGraph.Vertex;

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
	
	public Path append(List<Edge> list) {
		for (Edge e : list) {
			this.edges.add(e);
		}
		return this;
	}
	
	public Path prepend(Edge e) {
		this.edges.addFirst(e);
		return this;
	}
	
	public List<Edge> edges() {
		return this.edges;
	}

	public List<Vertex> vertices() {
		List<Vertex> vertices = new ArrayList<Vertex>();
		for (Edge e : edges) {
			vertices.add(e.vertex1());
		}
		vertices.add(edges.getLast().vertex2());
		return vertices;
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
