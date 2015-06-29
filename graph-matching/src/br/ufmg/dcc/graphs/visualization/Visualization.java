package br.ufmg.dcc.graphs.visualization;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;

import org.apache.commons.collections15.Transformer;

import br.ufmg.dcc.graphs.SimpleGraph;
import br.ufmg.dcc.graphs.SimpleGraph.Edge;
import br.ufmg.dcc.graphs.SimpleGraph.Vertex;
import edu.uci.ics.jung.algorithms.layout.ISOMLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.util.RandomLocationTransformer;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseGraph;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;

public class Visualization {

	public static void view(SimpleGraph graph) {
		Graph<VVertex, VEdge> g = simpleGraphToJung(graph);

		// The Layout<V, E> is parameterized by the vertex and edge types
		Layout<VVertex, VEdge> layout = new ISOMLayout<VVertex, VEdge>(g);
		Dimension dimension = new Dimension(600, 600);
		layout.setSize(dimension); // sets the initial size of the
		layout.setInitializer(new RandomLocationTransformer<VVertex>(dimension, 2345L));
													// space
		// The BasicVisualizationServer<V,E> is parameterized by the edge types
		BasicVisualizationServer<VVertex, VEdge> vv = new BasicVisualizationServer<VVertex, VEdge>(
				layout);
		vv.setPreferredSize(dimension); // Sets the viewing area
														// size
		vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller<VVertex>());
		vv.getRenderer().getVertexLabelRenderer().setPosition(Position.CNTR);

		// Setup up a new vertex to paint transformer...
		Transformer<VVertex, Paint> vertexPaint = new Transformer<VVertex, Paint>() {
			public Paint transform(VVertex v) {
				if (v.covered) {
					return Color.CYAN;
				}
				return Color.LIGHT_GRAY;
			}
		};
		
		// Setup up a new vertex to paint transformer...
		Transformer<VEdge, Paint> edgePaint = new Transformer<VEdge, Paint>() {
			public Paint transform(VEdge e) {
				if (e.covered) {
					return Color.BLUE;
				}
				return Color.BLACK;
			}
		};
		vv.getRenderContext().setVertexFillPaintTransformer(vertexPaint);
		vv.getRenderContext().setEdgeDrawPaintTransformer(edgePaint);

		JFrame frame = new JFrame("Simple Graph View");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().add(vv);
		frame.pack();
		frame.setVisible(true);
	}

	private static Graph<VVertex, VEdge> simpleGraphToJung(SimpleGraph sg) {
		Map<String, VVertex> vertices = new HashMap<String, VVertex>();
		Graph<VVertex, VEdge> g = new SparseGraph<VVertex, VEdge>();
		for (Vertex v : sg.vertices()) {
			VVertex vertex = new VVertex(v.toString(), v.isCovered());
			vertices.put(v.toString(), vertex);
			g.addVertex(vertex);
		}
		for (Vertex v : sg.vertices()) {
			for (Edge e : sg.edgesOf(v)) {
				Vertex v2 = e.vertex2();
				if (v.index() < v2.index()) {
					g.addEdge(new VEdge(e.isInMatching()), vertices.get(v.toString()), vertices.get(v2.toString()));
				}
			}
		}
		return g;
	}

	private static class VVertex {
		private final String name;
		private final boolean covered;
		public VVertex(String name, boolean covered) {
			this.name = name;
			this.covered = covered;
		}
		@Override
		public String toString() {
			return this.name;
		}
	}
	
	private static class VEdge {
		private final boolean covered;
		public VEdge(boolean covered) {
			this.covered = covered;
		}
	}
}
