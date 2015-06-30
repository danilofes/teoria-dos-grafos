package br.ufmg.dcc.graphs;

import java.util.Random;

import br.ufmg.dcc.graphs.visualization.ExampleGraph;

public class EvaluatePerformance {

	public static void main(String[] args) {
		EdmondsMatchingAlgorithm algo = new EdmondsMatchingAlgorithm();
		Random random = new Random(123L);
		
		SimpleGraph g1;
		for (int i = 500; i <= 5000; i += 500) {
			g1 = ExampleGraph.generateRandomGraph(i, 0.3, random);
			long t0 = System.currentTimeMillis();
			algo.findMaximumMatching(g1);
			long t1 = System.currentTimeMillis();
			System.out.print(i * 2);
			System.out.print('\t');
			System.out.println(t1 - t0);
		}
	}
	
}
