package pl.edu.mimuw.graphs.metrics;

import java.util.HashSet;
import java.util.Set;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;

public class Utils {

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
}
