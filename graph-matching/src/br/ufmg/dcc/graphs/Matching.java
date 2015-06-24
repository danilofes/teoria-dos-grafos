package br.ufmg.dcc.graphs;

public interface Matching {

	Iterable<Vertex> exposedVertices(Graph g);

	Edge matchingEdgeOf(Vertex w);

}
