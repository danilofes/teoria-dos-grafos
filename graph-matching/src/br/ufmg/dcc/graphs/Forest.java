package br.ufmg.dcc.graphs;

import java.util.LinkedList;
import java.util.List;

import br.ufmg.dcc.graphs.SimpleGraph.Edge;
import br.ufmg.dcc.graphs.SimpleGraph.Vertex;

public class Forest {

	private SimpleGraph g;
	private boolean[] markedEdges;
	private Vertex[] parent;
	private Vertex[] root;
	private int[] distance;
	private LinkedList<Vertex> fifo;
	
	public Forest(SimpleGraph g) {
		this.g = g;
		this.markedEdges = new boolean[g.maxEdgeId() + 1];
		for (int i = 0; i < this.markedEdges.length; i++) {
			this.markedEdges[i] = false;
		}
		this.parent = new Vertex[g.maxVertexId() + 1];
		this.root = new Vertex[g.maxVertexId() + 1];
		this.distance = new int[g.maxVertexId() + 1];
		for (int i = 0; i < this.parent.length; i++) {
			this.parent[i] = null;
			this.root[i] = null;
			this.distance[i] = -1;
		}
		this.fifo = new LinkedList<Vertex>();
	}

	public boolean isEdgeMarked(Edge e) {
		return this.markedEdges[e.index()];
	}

	public void markEdge(Edge e) {
		this.markedEdges[e.index()] = true;
	}
	
	public void addTree(Vertex v) {
		this.root[v.index()] = v;
		this.distance[v.index()] = 0;
		this.fifo.addLast(v);
	}
	
	public boolean contains(Vertex w) {
		return this.root[w.index()] != null;
	}

	public Vertex nextVertex() {
		if (this.fifo.isEmpty()) {
			return null;
		}
		return this.fifo.removeFirst();
	}

	public void addEdge(Edge e) {
		Vertex v1 = e.vertex1();
		Vertex v2 = e.vertex2();
		if (!this.contains(v1)) {
			throw new RuntimeException("forest does not contain " + v1);
		}
		this.parent[v2.index()] = v1;
		this.root[v2.index()] = this.root[v1.index()];
		int dist = this.distance[v1.index()] + 1;
		this.distance[v2.index()] = dist;
		if ((dist % 2) == 0) {
			this.fifo.addLast(v2);
		}
	}

	public boolean isAtEvenDistanceFromRoot(Vertex w) {
		return (this.distance[w.index()] % 2) == 0;
	}

	public Vertex rootOf(Vertex v) {
		return this.root[v.index()];
	}

	public Path pathBetweenRootsOf(Edge e) {
		Path path = new Path();
		Vertex v = e.vertex1();
		Vertex p;
		while ((p = this.parent[v.index()]) != null) {
			Edge edge = g.findEdge(p, v);
			path.prepend(edge);
			v = p;
		}
		
		path.append(e);
		
		v = e.vertex2();
		while ((p = this.parent[v.index()]) != null) {
			Edge edge = g.findEdge(v, p);
			path.append(edge);
			v = p;
		}
		
		return path;
	}

	public List<Vertex> getBlossom(Vertex v, Vertex w) {
		int distV = this.distance[v.index()];
		int distW = this.distance[w.index()];
		int dist = Math.min(distV, distW);
		
		LinkedList<Vertex> vertices = new LinkedList<Vertex>();
		Vertex x = v;
		for (int i = distV; i >= dist; i--) {
			vertices.addFirst(x);
			x = this.parent[x.index()];
		}
		
		x = w;
		for (int i = distW; i > dist; i--) {
			vertices.addLast(x);
			x = this.parent[x.index()];
		}
		return vertices;
	}

}
