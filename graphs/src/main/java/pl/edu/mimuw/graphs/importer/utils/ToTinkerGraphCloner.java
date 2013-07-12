package pl.edu.mimuw.graphs.importer.utils;

import static com.tinkerpop.blueprints.Direction.IN;
import static com.tinkerpop.blueprints.Direction.OUT;

import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.tg.TinkerGraph;

/**
 * 
 * @author gtimoszuk
 * 
 */
public class ToTinkerGraphCloner {

	/**
	 * This method assumes that all properties are immutable, otherwise this
	 * will create lost of error causing references
	 * 
	 * @param graph
	 * @return
	 */
	public TinkerGraph cloneGraphToTinker(Graph graph) {
		TinkerGraph tinkerGraph = new TinkerGraph();
		for (Vertex vertex : graph.getVertices()) {
			Vertex tinkerVertex = tinkerGraph.addVertex(vertex.getId());
			for (String key : vertex.getPropertyKeys()) {
				tinkerVertex.setProperty(key, vertex.getProperty(key));
			}
		}
		for (Edge edge : graph.getEdges()) {
			Edge tinkerEdge = tinkerGraph.addEdge(edge.getId(), tinkerGraph.getVertex(edge.getVertex(OUT).getId()),
					tinkerGraph.getVertex(edge.getVertex(IN).getId()), edge.getLabel());
			for (String key : edge.getPropertyKeys()) {
				tinkerEdge.setProperty(key, edge.getProperty(key));
			}
		}
		return tinkerGraph;
	}
}
