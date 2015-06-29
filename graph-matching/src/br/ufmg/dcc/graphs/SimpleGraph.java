package br.ufmg.dcc.graphs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import br.ufmg.dcc.graphs.SimpleGraph.Vertex;

public class SimpleGraph implements Iterable<Vertex> {

	private ArrayList<Vertex> vertices = new ArrayList<Vertex>();
	private ArrayList<ArrayList<Edge>> neighbors = new ArrayList<ArrayList<Edge>>();
	private int edgeId = 0;
//	private int vertexId = 0;
	private LinkedList<Vertex> shrinkedVertices = new LinkedList<Vertex>();

	public Vertex addVertex(String name) {
		int id = this.vertices.size();
		Vertex v = new Vertex(id, name);
		vertices.add(v);
		neighbors.add(new ArrayList<Edge>());
		return v;
	}

	public void addEdge(Vertex u, Vertex v) {
		int id = this.edgeId;
		this.edgeId++;
		neighbors.get(u.index()).add(new Edge(id, u, v));
		neighbors.get(v.index()).add(new Edge(id, v, u));
	}

	public Iterable<Vertex> vertices() {
		return this;
	}

	public Collection<Edge> edgesOf(Vertex v) {
		return this.neighbors.get(v.index());
	}

	public Vertex shrink(List<Vertex> vertices) {
		Vertex base = this.addVertex("b_" + vertices.get(0));
		for (Vertex v : vertices) {
			v.shrinkedTo = base;
			shrinkedVertices.push(v);
		}
		for (Vertex v : vertices) {
			for (Edge e : this.edgesOf(v)) {
				Vertex v2 = e.vertex2();
				if (v2.shrinkedTo != base) {
					//this.addEdge(base, v2);
					neighbors.get(base.index()).add(new Edge(e.id, base, v));
				}
			}
		}
		return base;
	}
	
	public List<Vertex> expand() {
		LinkedList<Vertex> expanded = new LinkedList<Vertex>();
		if (this.vertices.isEmpty()) {
			return expanded;
		}
		Vertex base = this.vertices.get(this.vertices.size() - 1);
		while (true) {
			Vertex v = this.shrinkedVertices.peek();
			if (v == null || !base.equals(v.shrinkedTo)) {
				break;
			}
			this.shrinkedVertices.pop();
			v.shrinkedTo = null;
			expanded.add(v);
		}
		this.vertices.remove(base.id);
		this.neighbors.remove(base.id);
		return expanded;
	}
	
	public static class Vertex {
		private int id;
		private String name;
		private Vertex shrinkedTo = null;

		private Vertex(int id, String name) {
			super();
			this.id = id;
			this.name = name;
		}

		public int index() {
			return this.id;
		}

		@Override
		public String toString() {
			return this.name;
		}

		private Vertex dereference() {
			if (this.shrinkedTo == null) {
				return this;
			}
			return this.shrinkedTo.dereference(); 
		}
		
		@Override
		public boolean equals(Object obj) {
			if (obj instanceof Vertex) {
				return ((Vertex) obj).id == this.id;
			}
			return false;
		}

		@Override
		public int hashCode() {
			return Integer.hashCode(this.id);
		}
	}

	public static class Edge {

		private final int id;
		private final Vertex vertex1;
		private final Vertex vertex2;

		private Edge(int id, Vertex vertex1, Vertex vertex2) {
			this.id = id;
			this.vertex1 = vertex1;
			this.vertex2 = vertex2;
		}

		public Vertex vertex1() {
			return this.vertex1;
		}

		public Vertex vertex2() {
			return this.vertex2.dereference();
		}

		@Override
		public String toString() {
			return "(" + this.vertex1.toString() + "," + this.vertex2.toString() + ")";
		}

		@Override
		public boolean equals(Object obj) {
			if (obj instanceof Edge) {
				return ((Edge) obj).id == this.id;
			}
			return false;
		}

		@Override
		public int hashCode() {
			return Integer.hashCode(this.id);
		}
	}

	@Override
	public Iterator<Vertex> iterator() {
		return new VertexIterator();
	}
	
	private class VertexIterator implements Iterator<Vertex> {
		private int i = -1;

		public VertexIterator() {
			findNext();
		}
		
		private void findNext() {
			while(true) {
				i++;
				if (i < vertices.size()) {
					Vertex v = vertices.get(i);
					if (v.shrinkedTo == null) {
						break;
					} else {
						continue;
					}
				} else {
					break;
				}
			}
		}

		@Override
		public boolean hasNext() {
			return i < vertices.size();
		}

		@Override
		public Vertex next() {
			Vertex v = vertices.get(i);
			findNext();
			return v;
		}
		
	}
}
