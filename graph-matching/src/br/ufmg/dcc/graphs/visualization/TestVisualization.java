package br.ufmg.dcc.graphs.visualization;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;

import javax.swing.JFrame;

import org.apache.commons.collections15.Transformer;
import org.junit.Test;

import br.ufmg.dcc.graphs.SimpleGraph;
import br.ufmg.dcc.graphs.SimpleGraph.Edge;
import br.ufmg.dcc.graphs.SimpleGraph.Vertex;
import edu.uci.ics.jung.algorithms.layout.ISOMLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseGraph;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;

public class TestVisualization {

	@Test
	public static void main(String[] args) {

		Graph<Vertex, String> g = simpleGraphToJung(ExampleGraph.ex1());

		// The Layout<V, E> is parameterized by the vertex and edge types
		Layout<Vertex, String> layout = new ISOMLayout<Vertex, String>(g);
		layout.setSize(new Dimension(600, 600)); // sets the initial size of the
													// space
		// The BasicVisualizationServer<V,E> is parameterized by the edge types
		BasicVisualizationServer<Vertex, String> vv = new BasicVisualizationServer<Vertex, String>(
				layout);
		vv.setPreferredSize(new Dimension(600, 600)); // Sets the viewing area
														// size
		vv.getRenderContext().setVertexLabelTransformer(
				new ToStringLabeller<Vertex>());
		vv.getRenderer().getVertexLabelRenderer().setPosition(Position.CNTR);

		// Setup up a new vertex to paint transformer...
		Transformer<Vertex, Paint> vertexPaint = new Transformer<Vertex, Paint>() {
			public Paint transform(Vertex i) {
				return Color.GREEN;
			}
		};
		vv.getRenderContext().setVertexFillPaintTransformer(vertexPaint);

		JFrame frame = new JFrame("Simple Graph View");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(vv);
		frame.pack();
		frame.setVisible(true);
	}

	private static Graph<Vertex, String> simpleGraphToJung(SimpleGraph sg) {
		Graph<Vertex, String> g = new SparseGraph<Vertex, String>();
		for (Vertex v : sg.vertices()) {
			g.addVertex(v);
		}
		for (Vertex v : sg.vertices()) {
			for (Edge e : sg.edgesOf(v)) {
				Vertex v2 = e.vertex2();
				if (v.index() < v2.index()) {
					g.addEdge("" + v + "-" + v2, v, v2);
					if (v.toString().equals("b_8")) {
						System.out.println("x");
					}
					if (v2.toString().equals("b_8")) {
						System.out.println("x");
					}
				}
			}
		}
		return g;
	}

}
