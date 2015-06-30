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
	private LinkedList<ArrayList<Vertex>> shrinkedVertices = new LinkedList<ArrayList<Vertex>>();

	public Vertex addVertex(String name) {
		int id = this.vertices.size();
		Vertex v = new Vertex(id, name);
		vertices.add(v);
		neighbors.add(new ArrayList<Edge>());
		return v;
	}

	public Edge addEdge(Vertex u, Vertex v) {
		int id = this.edgeId;
		this.edgeId++;
		Edge edge = new Edge(id, u, v);
		neighbors.get(u.index()).add(edge);
		neighbors.get(v.index()).add(new Edge(id, v, u));
		return edge;
	}

	public Edge findEdge(Vertex u, Vertex v) {
		ArrayList<Edge> edges = neighbors.get(u.index());
		for (Edge e : edges) {
			if (e.vertex2().equals(v)) {
				return e;
			}
		}
		return null;
	}
	
	public int maxVertexId() {
		return vertices.size() - 1;
	}

	public int maxEdgeId() {
		return edgeId - 1;
	}
	
	public Iterable<Vertex> vertices() {
		return this;
	}

	public Collection<Edge> edgesOf(Vertex v) {
		return this.neighbors.get(v.index());
	}

	public Vertex shrink(List<Vertex> vertices) {
		Vertex base = this.addVertex("b_" + vertices.get(0));
		ArrayList<Vertex> group = new ArrayList<Vertex>();
		for (Vertex v : vertices) {
			v.shrinkedTo = base;
			group.add(v);
		}
		shrinkedVertices.push(group);
		for (Vertex v : vertices) {
			for (Edge e : this.edgesOf(v)) {
				Vertex v2 = e.vertex2();
				if (v2.shrinkedTo != base) {
					Edge edge = new Edge(e.id, base, v2, e);
					if (v.isCovered()) {
						base.matchingEdge = edge;
					}
					neighbors.get(base.index()).add(edge);
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
		ArrayList<Vertex> group = this.shrinkedVertices.pop();
		for (Vertex v : group) {
			v.shrinkedTo = null;
		}
		this.vertices.remove(base.id);
		this.neighbors.remove(base.id);
		return group;
	}
	
	public Edge matchingEdgeOf(Vertex w) {
		return w.matchingEdge;
	}
	
	public static class Vertex {
		private final int id;
		private final String name;
		private Vertex shrinkedTo = null;
		private Edge matchingEdge = null;

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

		public boolean isCovered() {
			return this.matchingEdge != null;
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
			return this.id;
		}
	}

	public class Edge {

		private final int id;
		private final Vertex vertex1;
		private final Vertex vertex2;
		private Edge proxyFor = null;

		private Edge(int id, Vertex vertex1, Vertex vertex2) {
			this(id, vertex1, vertex2, null);
		}
		
		private Edge(int id, Vertex vertex1, Vertex vertex2, Edge proxyFor) {
			this.id = id;
			this.vertex1 = vertex1;
			this.vertex2 = vertex2;
			this.proxyFor = proxyFor;
		}

		public int index() {
			return this.id;
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

		public void addToMatching() {
			this.vertex1.matchingEdge = this;
			Edge dual = this.getDual();
			dual.vertex1.matchingEdge = dual;
		}
		
		public void removeFromMatching() {
			this.vertex1.matchingEdge = null;
			this.vertex2().matchingEdge = null;
		}
		
		public boolean isInMatching() {
			return this.vertex1.matchingEdge == this;
		}
		
		private Edge getDual() {
			for (Edge e : edgesOf(this.vertex2())) {
				if (e.id == this.id) {
					return e;
				}
			}
			throw new RuntimeException("edge " + this.toString() + " has no dual");
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
			return this.id;
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

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}

	public Path lift(Path path, Vertex supervertex, List<Vertex> blossom) {
		Edge in = null;
		Edge out = null;
		Edge[] edges = path.edges().toArray(new Edge[0]);
		for (Edge e : edges) {
			if (e.vertex2.shrinkedTo == supervertex) {
				in = e;
			}
			if (e.vertex1 == supervertex) {
				out = e.proxyFor;
			}
		}
		if (in == null && out == null) {
			return path;
		}
		Vertex v1;
		Vertex v2;
		if (in == null) {
			v1 = blossom.get(0);
			v2 = out.vertex1;
		} else if (out == null) {
			v1 = in.vertex2;
			v2 = blossom.get(0);
		} else {
			v1 = in.vertex2;
			v2 = out.vertex1;
		}
		Path lifted = new Path();
		int i = 0;
		for (Edge e : edges) {
			if (e == in || e == out) {
				break;
			}
			lifted.append(e);
			i++;
		}
		if (in != null) {
			lifted.append(in);
			i++;
		}
		lifted.append(evenPathBetween(v1, v2, blossom));
		if (out != null) {
			lifted.append(out);
			i++;
		}
		for (; i < edges.length; i++) {
			lifted.append(edges[i]);
		}
		return lifted;
	}

	private List<Edge> evenPathBetween(Vertex v1, Vertex v2, List<Vertex> blossom) {
		LinkedList<Edge> path = new LinkedList<Edge>();
		if (v1 == v2) {
			return path;
		}
		int i = blossom.indexOf(v1);
		int j = blossom.indexOf(v2);
		if (i < 0 || j < 0) {
			throw new RuntimeException("invalid blossom");
		}
		boolean evenDist = (j - i) % 2 == 0;
		int dir;
		if ((i < j && evenDist) || (i > j && !evenDist)) {
			dir = 1;
		} else {
			dir = -1;
		}
		for (int pos = i; pos != j; pos = (pos + dir) % blossom.size()) {
			Vertex u = blossom.get(pos);
			Vertex v = blossom.get((pos + dir) % blossom.size());
			path.add(this.findEdge(u, v));
		}
		return path;
	}
	
	public void augmentMatching(Path augumentingPath) {
		LinkedList<Edge> edgesToAdd = new LinkedList<Edge>();
		for (Edge e : augumentingPath.edges()) {
			if (e.isInMatching()) {
				e.removeFromMatching();
			} else {
				edgesToAdd.add(e);
			}
		}
		for (Edge e : edgesToAdd) {
			e.addToMatching();
		}
	}
}
