package pl.edu.mimuw.graphs.transformations;

import static com.tinkerpop.blueprints.Direction.IN;
import static com.tinkerpop.blueprints.Direction.OUT;
import static pl.edu.mimuw.graphs.OptionalGraphProperties.COUNT;
import pl.edu.mimuw.graphs.importer.utils.SimpleSequence;
import pl.edu.mimuw.graphs.importer.utils.ToTinkerGraphCloner;
import pl.edu.mimuw.graphs.metrics.Utils;

import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Graph;

/**
 * 
 * @author gtimoszuk
 * 
 */
public class PackageGraphExpander {

	private final ToTinkerGraphCloner toTinkerGraphCloner = new ToTinkerGraphCloner();

	private final Utils utils = new Utils();

	public Graph expandGraphAndReturnNew(Graph graph) {

		SimpleSequence sequence;
		sequence = new SimpleSequence(utils.getMaxId(graph));

		Graph expandedGraph = toTinkerGraphCloner.cloneGraphToTinker(graph);
		for (Edge edge : expandedGraph.getEdges()) {
			if (edge.getPropertyKeys().contains(COUNT)) {
				Integer count = edge.getProperty(COUNT);
				for (int i = 0; i < count - 1; i++) {
					expandedGraph.addEdge(sequence.getId(), edge.getVertex(OUT), edge.getVertex(IN), edge.getLabel());
				}
				edge.removeProperty(COUNT);
			}
		}

		return expandedGraph;
	}

	public void expandGraphInPlace(Graph graph) {

		SimpleSequence sequence;
		sequence = new SimpleSequence(utils.getMaxId(graph));

		for (Edge edge : graph.getEdges()) {
			if (edge.getPropertyKeys().contains(COUNT)) {
				Integer count = edge.getProperty(COUNT);
				for (int i = 0; i < count - 1; i++) {
					graph.addEdge(sequence.getId(), edge.getVertex(OUT), edge.getVertex(IN), edge.getLabel());
				}
				edge.removeProperty(COUNT);
			}
		}
	}

}
