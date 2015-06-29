package br.ufmg.dcc.graphs;

import java.util.Random;

import br.ufmg.dcc.graphs.visualization.ExampleGraph;

public class Main {

	public static void main(String[] args) {
		EdmondsMatchingAlgorithm algo = new EdmondsMatchingAlgorithm();
		
		Random random = new Random(123L);
		SimpleGraph g1 = ExampleGraph.generateRandomGraph(8, 0.4, random);
		
		//SimpleGraph g1 = ExampleGraph.ex1();
		
		algo.findMaximumMatching(g1);
		
	}
	
}
