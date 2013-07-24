package pl.edu.mimuw.graphs.metrics;

import static pl.edu.mimuw.graphs.api.GraphVertexTypes.CLASS;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.edu.mimuw.graphs.api.GraphVertexProperies;

import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;

public abstract class AbstractEgdesBetweenClassesInDifferentPackagesCalclulator extends AfferentCouplingCalculator {

	static final Logger LOGGER = LoggerFactory
			.getLogger(AbstractEgdesBetweenClassesInDifferentPackagesCalclulator.class);

	private final Utils utils = new Utils();

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

	@Override
	protected void calculateForSingleVertex(Graph graph, Vertex v) {
		Vertex parent = utils.getParentPackage(v);
		Integer result = 0;
		for (Edge edge : v.getEdges(metricCountDirection, countedRelationshipsLabels.toArray(new String[] {}))) {
			if (!parent.equals(utils.getParentPackage(edge.getVertex(otherThanMetricInterestingDirection)))) {
				result++;
			}
		}
		v.setProperty(metricName, result);
	}

}
