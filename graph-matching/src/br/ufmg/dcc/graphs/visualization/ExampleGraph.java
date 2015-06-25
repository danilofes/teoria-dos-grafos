package br.ufmg.dcc.graphs.visualization;

import br.ufmg.dcc.graphs.SimpleGraph;
import br.ufmg.dcc.graphs.SimpleGraphImpl;
import br.ufmg.dcc.graphs.Vertex;

public class ExampleGraph {

	public static SimpleGraph ex1() {
		SimpleGraphImpl g = new SimpleGraphImpl();

		Vertex vw = g.addVertex("w");
		Vertex v1 = g.addVertex("1");
		Vertex vb = g.addVertex("b");

		g.addEdge(vw, v1);
		g.addEdge(v1, vb);

		Vertex v2 = g.addVertex("2");
		Vertex v3 = g.addVertex("3");
		g.addEdge(vb, v2);
		g.addEdge(vb, v3);

		Vertex v4 = g.addVertex("4");
		Vertex v5 = g.addVertex("5");
		g.addEdge(v2, v4);
		g.addEdge(v3, v5);
		g.addEdge(v4, v5);

		Vertex v6 = g.addVertex("6");
		g.addEdge(v3, v6);

		Vertex v7 = g.addVertex("7");
		g.addEdge(v5, v7);

		Vertex v8 = g.addVertex("8");
		Vertex v9 = g.addVertex("9");
		Vertex v10 = g.addVertex("10");
		g.addEdge(v4, v8);
		g.addEdge(v8, v9);
		g.addEdge(v9, v10);
		return g;
	}
	
}
