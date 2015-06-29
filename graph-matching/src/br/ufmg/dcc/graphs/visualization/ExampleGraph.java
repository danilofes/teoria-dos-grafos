package br.ufmg.dcc.graphs.visualization;

import java.util.LinkedList;

import br.ufmg.dcc.graphs.SimpleGraph;
import br.ufmg.dcc.graphs.SimpleGraph.Edge;
import br.ufmg.dcc.graphs.SimpleGraph.Vertex;

public class ExampleGraph {

	public static SimpleGraph ex1() {
		SimpleGraph g = new SimpleGraph();

		Vertex vw = g.addVertex("w");
		Vertex v1 = g.addVertex("1");
		g.addEdge(vw, v1);

		Vertex vb = g.addVertex("b");
		g.addEdge(v1, vb);

		Vertex v2 = g.addVertex("2");
		Vertex v3 = g.addVertex("3");
		g.addEdge(vb, v2);
		g.addEdge(vb, v3);

		Vertex v4 = g.addVertex("4");
		Vertex v5 = g.addVertex("5");
		Edge e2to4 = g.addEdge(v2, v4);
		Edge e3to5 = g.addEdge(v3, v5);
		g.addEdge(v4, v5);

		Vertex v6 = g.addVertex("6");
		g.addEdge(v3, v6);

		Vertex v7 = g.addVertex("7");
		Edge e5to7 = g.addEdge(v5, v7);

		Vertex v8 = g.addVertex("8");
		Vertex v9 = g.addVertex("9");
		Vertex v10 = g.addVertex("10");
		g.addEdge(v4, v8);
		g.addEdge(v8, v9);
		Edge e9to10 = g.addEdge(v9, v10);
		
		
		e9to10.addToMatching();
		e9to10.removeFromMatching();
		e2to4.addToMatching();
		e5to7.addToMatching();
		
		LinkedList<Vertex> blossom = new LinkedList<Vertex>();
		blossom.add(vb);
		blossom.add(v2);
		blossom.add(v3);
		blossom.add(v4);
		blossom.add(v5);
		Vertex b1 = g.shrink(blossom);
		
		LinkedList<Vertex> blossom2 = new LinkedList<Vertex>();
		blossom2.add(v8);
		blossom2.add(v9);
		g.shrink(blossom2);
		
		g.expand();
		g.expand();
		
		
		
		return g;
	}
	
}
