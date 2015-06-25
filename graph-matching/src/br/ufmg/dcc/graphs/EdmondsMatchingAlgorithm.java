package br.ufmg.dcc.graphs;

public class EdmondsMatchingAlgorithm {

	public Path findAugumentingPath(Matching m) {
		SimpleGraph g = m.graph();
		Forest forest = Forest.create();
		for (Edge e : m.edges()) {
			forest.markEdge(e);
		}
		for (Vertex v : m.exposedVertices()) {
			forest.addTree(v);
		}
		
		Vertex v;
		while((v = forest.findUnmarkedVertex()) != null) {
			
			for (Edge e : g.edgesOf(v)) {
				if (forest.isEdgeMarked(e)) {
					continue;
				}
				
				Vertex w = e.vertex2();
				if (!forest.contains(w)) {
					Edge matchingEdge = m.matchingEdgeOf(w);
					forest.addEdge(e);
					forest.addEdge(matchingEdge);
				} else {
					if (forest.distanceFromRootOf(w) % 2 == 0) {
						if (forest.rootOf(v) == forest.rootOf(w)) {
							// blossom found
							Matching contractedMatching = contractBlossom(m, v, w);
							Path path = findAugumentingPath(contractedMatching);
							return lift(path);
						} else {
							// augmenting path found
							return forest.pathBetweenRootsOf(v, w);
						}
					}
				}
				forest.markEdge(e);
			}
			forest.markVertex(v);
		}
		return Path.EMPTY;
	}

	private Path lift(Path path) {
		// TODO Auto-generated method stub
		return null;
	}

	private Matching contractBlossom(Matching m, Vertex v, Vertex w) {
		// TODO Auto-generated method stub
		return null;
	}

}
