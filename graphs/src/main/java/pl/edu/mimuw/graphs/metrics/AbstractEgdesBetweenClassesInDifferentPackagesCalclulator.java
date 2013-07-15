package pl.edu.mimuw.graphs.metrics;

import static com.tinkerpop.blueprints.Direction.IN;
import static com.tinkerpop.blueprints.Direction.OUT;
import static pl.edu.mimuw.graphs.api.GraphVertexProperies.NAME;
import static pl.edu.mimuw.graphs.api.GraphVertexTypes.CLASS;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.edu.mimuw.graphs.api.GraphRelationshipType;
import pl.edu.mimuw.graphs.api.GraphVertexProperies;

import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;

public abstract class AbstractEgdesBetweenClassesInDifferentPackagesCalclulator extends AfferentCouplingCalculator {

	static final Logger LOGGER = LoggerFactory
			.getLogger(AbstractEgdesBetweenClassesInDifferentPackagesCalclulator.class);

	public AbstractEgdesBetweenClassesInDifferentPackagesCalclulator() {
		super();
	}

	public AbstractEgdesBetweenClassesInDifferentPackagesCalclulator(Set<String> countedRelationshipsLabels) {
		super(countedRelationshipsLabels);
	}

	@Override
	public void calculate(Graph graph) {
		for (Vertex v : graph.getVertices()) {
			if (CLASS.equals(v.getProperty(GraphVertexProperies.TYPE))) {
				calculateForSingleVertex(graph, v);
			}
		}

	}

	private Vertex getParentPackage(Vertex v) {
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

	@Override
	protected void calculateForSingleVertex(Graph graph, Vertex v) {
		Vertex parent = getParentPackage(v);
		Integer result = 0;
		for (Edge edge : v.getEdges(metricCountDirection, countedRelationshipsLabels.toArray(new String[] {}))) {
			if (!parent.equals(getParentPackage(edge.getVertex(otherThanMetricInterestingDirection)))) {
				result++;
			}
		}
		v.setProperty(metricName, result);
	}

}
