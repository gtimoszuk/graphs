package pl.edu.mimuw.graphs.importer.packages.graph;

import static com.tinkerpop.blueprints.Direction.IN;
import static com.tinkerpop.blueprints.Direction.OUT;
import static java.lang.Math.max;
import static pl.edu.mimuw.graphs.api.OptionalGraphProperties.COUNT;
import pl.edu.mimuw.graphs.importer.utils.SimpleSequence;
import pl.edu.mimuw.graphs.importer.utils.ToTinkerGraphCloner;

import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;

/**
 * 
 * @author gtimoszuk
 * 
 */
public class PackageGraphExpander {

	private final ToTinkerGraphCloner toTinkerGraphCloner = new ToTinkerGraphCloner();

	private final Graph graph;

	private final SimpleSequence sequence;

	public PackageGraphExpander(Graph graph) {
		this.graph = graph;
		this.sequence = new SimpleSequence(getMaxId(graph));
	}

	public Graph expandGraph() {

		Graph expandedGraph = toTinkerGraphCloner.cloneGraphToTinker(graph);
		for (Edge edge : expandedGraph.getEdges()) {
			if (edge.getPropertyKeys().contains(COUNT)) {
				int count = edge.getProperty(COUNT);
				for (int i = 0; i < count - 1; i++) {
					expandedGraph.addEdge(sequence.getId(), edge.getVertex(OUT), edge.getVertex(IN), edge.getLabel());
				}
				edge.removeProperty(COUNT);
			}
		}

		return expandedGraph;
	}

	private int getMaxId(Graph graph) {
		int max = 0;
		for (Vertex v : graph.getVertices()) {
			max = max(max, (Integer) v.getId());
		}
		for (Edge e : graph.getEdges()) {
			max = max(max, (Integer) e.getId());
		}
		return max;
	}
}
