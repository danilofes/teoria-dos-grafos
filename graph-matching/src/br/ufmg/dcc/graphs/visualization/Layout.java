package br.ufmg.dcc.graphs.visualization;

import edu.uci.ics.jung.algorithms.layout.ISOMLayout;
import edu.uci.ics.jung.graph.Graph;

public class Layout<V, E> extends ISOMLayout<V, E>{

	public Layout(Graph<V, E> g) {
		super(g);
	}

	
}
