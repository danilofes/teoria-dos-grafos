package br.ufmg.dcc.graphs;

public class EdmondsMatchingAlgorithm {

	public Path findAugumentingPath(Graph g, Matching m) {
		Forest forest = Forest.create();
		for (Vertex v : m.exposedVertices(g)) {
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
						if (forest.rootOf(v) != forest.rootOf(w)) {
							// augumenting path found
						} else {
							// blossom found
						}
					}
				}
				forest.markEdge(e);
			}
			
			//forest.markEdge();
		}
		
		
//		INPUT:  Graph G, matching M on G
//	    OUTPUT: augmenting path P in G or empty path if none found
//	B01 function find_augmenting_path( G, M ) : P
//	B02    F <- empty forest
//	B03    unmark all vertices and edges in G, mark all edges of M
//	B05    for each exposed vertex v do
//	B06        create a singleton tree { v } and add the tree to F
//	B07    end for
//	B08    while there is an unmarked vertex v in F with distance( v, root( v ) ) even do
//	B09        while there exists an unmarked edge e = { v, w } do
//	B10            if w is not in F then
//	                   // w is matched, so add e and w's matched edge to F
//	B11                x <- vertex matched to w in M
//	B12                add edges { v, w } and { w, x } to the tree of v
//	B13            else
//	B14                if distance( w, root( w ) ) is odd then
//	                       // Do nothing.
//	B15                else
//	B16                    if root( v ) != root( w ) then
//	                           // Report an augmenting path in F \cup { e }.
//	B17                        P <- path ( root( v ) -> ... -> v ) -> ( w -> ... -> root( w ) )
//	B18                        return P
//	B19                    else
//	                           // Contract a blossom in G and look for the path in the contracted graph.
//	B20                        B <- blossom formed by e and edges on the path v -> w in T
//	B21                        G’, M’ <- contract G and M by B
//	B22                        P’ <- find_augmenting_path( G’, M’ )
//	B23                        P <- lift P’ to G
//	B24                        return P
//	B25                    end if
//	B26                end if
//	B27            end if
//	B28            mark edge e
//	B29        end while
//	B30        mark vertex v
//	B31    end while
//	B32    return empty path
//	B33 end function
		
		return null;
	}
	
}
