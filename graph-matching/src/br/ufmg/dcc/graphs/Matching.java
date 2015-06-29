package br.ufmg.dcc.graphs;

import br.ufmg.dcc.graphs.SimpleGraph.Edge;
import br.ufmg.dcc.graphs.SimpleGraph.Vertex;

public interface Matching {

	Iterable<Vertex> exposedVertices();

	Edge matchingEdgeOf(Vertex w);

	Iterable<Edge> edges();

	SimpleGraph graph();

}
