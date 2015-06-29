package br.ufmg.dcc.graphs;

import br.ufmg.dcc.graphs.visualization.ExampleGraph;
import br.ufmg.dcc.graphs.visualization.Visualization;

public class Main {

	public static void main(String[] args) {
		EdmondsMatchingAlgorithm algo = new EdmondsMatchingAlgorithm();
		
		SimpleGraph g1 = ExampleGraph.ex1();
		algo.findMaximumMatching(g1);
		
		Visualization.view(g1);
	}
	
}
