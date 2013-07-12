package pl.edu.mimuw.graphs.importer.packages.graph;

import java.util.Map;
import java.util.Set;

import org.javatuples.Triplet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.edu.mimuw.graphs.api.GraphRelationshipType;
import pl.edu.mimuw.graphs.api.OptionalGraphProperties;
import pl.edu.mimuw.graphs.importer.utils.SimpleSequence;

import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;

public class EdgesImporter {

	static final Logger LOGGER = LoggerFactory.getLogger(EdgesImporter.class);

	private final Graph graph;
	private final Map<String, Vertex> classMap;
	private final SimpleSequence sequence;
	private final Set<Triplet<String, String, Integer>> edgeSet;

	public EdgesImporter(Graph graph, Map<String, Vertex> classMap, SimpleSequence sequence,
			Set<Triplet<String, String, Integer>> edgeSet) {
		this.graph = graph;
		this.classMap = classMap;
		this.sequence = sequence;
		this.edgeSet = edgeSet;
	}

	public void processEdges() {
		LOGGER.info("egdes processing start");
		LOGGER.debug("classMap size {}", classMap.size());
		for (Triplet<String, String, Integer> triplet : edgeSet) {
			LOGGER.trace("from: {} to: {}", triplet.getValue0(), triplet.getValue1());

			Edge edge = graph.addEdge(sequence.getId(), graph.getVertex(classMap.get(triplet.getValue0()).getId()),
					graph.getVertex(classMap.get(triplet.getValue1()).getId()), GraphRelationshipType.CALLS.name());
			edge.setProperty(OptionalGraphProperties.COUNT, triplet.getValue2());
		}
	}

}
