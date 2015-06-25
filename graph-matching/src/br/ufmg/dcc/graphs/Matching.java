package br.ufmg.dcc.graphs;

public interface Matching {

	Iterable<Vertex> exposedVertices();

	Edge matchingEdgeOf(Vertex w);

	Iterable<Edge> edges();

	SimpleGraph graph();

}
