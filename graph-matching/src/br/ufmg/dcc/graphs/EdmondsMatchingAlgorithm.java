package br.ufmg.dcc.graphs;

import br.ufmg.dcc.graphs.SimpleGraph.Edge;
import br.ufmg.dcc.graphs.SimpleGraph.Vertex;
import br.ufmg.dcc.graphs.visualization.Visualization;

public class EdmondsMatchingAlgorithm {

	public void findMaximumMatching(SimpleGraph g) {
		Path p = this.findAugumentingPath(g);
		Visualization.view(g);
		while (!p.isEmpty()) {
			g.augmentMatching(p);
			Visualization.view(g);
			p = this.findAugumentingPath(g);
		}
	}
	
	private Path findAugumentingPath(SimpleGraph g) {
		Forest forest = new Forest(g);
		for (Vertex v : g.vertices()) {
			if (!v.isCovered()) {
				forest.addTree(v);
			}
		}
		
		Vertex v;
		while((v = forest.nextVertex()) != null) {
			
			for (Edge e : g.edgesOf(v)) {
				if (forest.isEdgeMarked(e) || e.isInMatching()) {
					continue;
				}
				
				Vertex w = e.vertex2();
				if (!forest.contains(w)) {
					Edge matchingEdge = g.matchingEdgeOf(w);
					forest.addEdge(e);
					forest.addEdge(matchingEdge);
				} else {
					if (forest.isAtEvenDistanceFromRoot(w)) {
						if (forest.rootOf(v) == forest.rootOf(w)) {
							// blossom found
							contractBlossom(g, forest, v, w);
							Path pathContracted = findAugumentingPath(g);
							Path path = g.lift(pathContracted);
							g.expand();
							return path;
						} else {
							// augmenting path found
							return forest.pathBetweenRootsOf(e);
						}
					}
				}
				forest.markEdge(e);
			}
			//forest.markVertex(v);
		}
		return Path.EMPTY;
	}

	private void contractBlossom(SimpleGraph g, Forest forest, Vertex v, Vertex w) {
		// TODO Auto-generated method stub
	}

}
