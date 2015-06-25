package br.ufmg.dcc.graphs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SimpleGraphImpl implements SimpleGraph {

	private ArrayList<Vertex> vertices = new ArrayList<Vertex>();
	private ArrayList<ArrayList<Vertex>> neighbors = new ArrayList<ArrayList<Vertex>>();
	
	public Vertex addVertex(String name) {
		int index = vertices.size();
		VertexImpl v = new VertexImpl(index, name);
		vertices.add(v);
		neighbors.add(new ArrayList<Vertex>());
		return v;
	}

	public void addEdge(Vertex u, Vertex v) {
		neighbors.get(u.index()).add(v);
		neighbors.get(v.index()).add(u);
	}

	public int vertexCount() {
		return this.vertices.size();
	}

	public Collection<Vertex> vertices() {
		return this.vertices;
	}
	
	@Override
	public Collection<Edge> edgesOf(Vertex v) {
		ArrayList<Vertex> neighborsOfV = neighbors.get(v.index());
		List<Edge> edges = new ArrayList<Edge>(neighborsOfV.size());
		for (Vertex n : neighborsOfV) {
			edges.add(new EdgeImpl(v, n));
		}
		return edges;
	}

	public Collection<Vertex> neighborsOf(Vertex v) {
		return neighbors.get(v.index());
	}
	
	public int degreeOf(Vertex v) {
		ArrayList<Vertex> neighborsOfV = neighbors.get(v.index());
		return neighborsOfV.size();
	}
}
class VertexImpl implements Vertex {
	private int index;
	private String name;
	
	public VertexImpl(int index, String name) {
		super();
		this.index = index;
		this.name = name;
	}
	
	public int index() {
		return this.index;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
}
class EdgeImpl implements Edge {
	
	private final Vertex vertex1;
	private final Vertex vertex2;
	
	public EdgeImpl(Vertex vertex1, Vertex vertex2) {
		this.vertex1 = vertex1;
		this.vertex2 = vertex2;
	}

	public Vertex vertex1() {
		return this.vertex1;
	}

	public Vertex vertex2() {
		return this.vertex2;
	}
}
