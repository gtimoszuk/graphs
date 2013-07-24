package pl.edu.mimuw.graphs.metrics;

import static com.tinkerpop.blueprints.Direction.IN;
import static com.tinkerpop.blueprints.Direction.OUT;
import static java.lang.Math.max;
import static pl.edu.mimuw.graphs.api.GraphVertexProperies.NAME;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.edu.mimuw.graphs.api.GraphRelationshipType;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;

public class Utils {

	private static final Logger LOGGER = LoggerFactory.getLogger(Utils.class);

	public Set<Vertex> transitiveClosure(Graph graph, Set<String> relationshipsLabels, Vertex startPoint) {
		Set<Vertex> result = new HashSet<Vertex>();
		deepFirstCollect(graph, relationshipsLabels, result, startPoint);

		return result;
	}

	private void deepFirstCollect(Graph graph, Set<String> relationshipsLabels, Set<Vertex> result, Vertex startPoint) {
		if (!result.contains(startPoint)) {
			result.add(startPoint);
			for (Edge e : startPoint.getEdges(Direction.OUT, relationshipsLabels.toArray(new String[] {}))) {
				deepFirstCollect(graph, relationshipsLabels, result, e.getVertex(Direction.IN));
			}
		}

	}

	public Vertex getParentPackage(Vertex v) {
		int count = 0;
		Vertex result = null;
		for (Edge e : v.getEdges(IN, new String[] { GraphRelationshipType.CONTAINS.name() })) {
			result = e.getVertex(OUT);
			count++;
		}
		if (count == 0) {
			LOGGER.error("no parent found for vertex {} of name {}", v.getId(), v.getProperty(NAME));
		} else if (count > 1) {
			LOGGER.error("multiple parents found for vertex {} of name {}", v.getId(), v.getProperty(NAME));
		}
		return result;
	}

	public Long getIdAsLong(Object id) {
		if (id instanceof Long) {
			return (Long) id;
		} else if (id instanceof String) {
			return Long.parseLong((String) id);
		} else {
			LOGGER.warn("id of strange type, {}", id);
			return null;
		}
	}

	public long getMaxId(Graph graph) {
		long max = 0;
		for (Vertex v : graph.getVertices()) {
			max = max(max, getIdAsLong(v.getId()));
		}
		for (Edge e : graph.getEdges()) {
			max = max(max, getIdAsLong(e.getId()));
		}
		return max;
	}
}
