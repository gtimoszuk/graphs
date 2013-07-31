package pl.edu.mimuw.graphs.metrics;

import static com.tinkerpop.blueprints.Direction.IN;
import static com.tinkerpop.blueprints.Direction.OUT;
import static pl.edu.mimuw.graphs.GraphRelationshipType.CONTAINS;
import static pl.edu.mimuw.graphs.GraphVertexTypes.CLASS;
import static pl.edu.mimuw.graphs.GraphVertexTypes.PACKAGE;
import static pl.edu.mimuw.graphs.metrics.MetricName.CLASSES_PER_PACKAGE;

import java.util.HashSet;
import java.util.Set;

import pl.edu.mimuw.graphs.GraphVertexProperies;

import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;

public class ClassesPerPackageCalculator extends AbstractEgdesBetweenClassesInDifferentPackagesCalclulator {

	{
		this.metricName = CLASSES_PER_PACKAGE.name();
		this.metricCountDirection = OUT;
		this.otherThanMetricInterestingDirection = IN;
	}

	public ClassesPerPackageCalculator() {
		countedRelationshipsLabels = new HashSet<String>();
		countedRelationshipsLabels.add(CONTAINS.name());
	}

	public ClassesPerPackageCalculator(Set<String> countedRelationshipsLabels) {
		super(countedRelationshipsLabels);
	}

	@Override
	public void calculate(Graph graph) {
		for (Vertex v : graph.getVertices()) {
			if (PACKAGE.equals(v.getProperty(GraphVertexProperies.TYPE))) {
				calculateForSingleVertex(graph, v);
			}
		}

	}

	@Override
	protected void calculateForSingleVertex(Graph graph, Vertex v) {
		Integer result = 0;
		for (Edge edge : v.getEdges(metricCountDirection, countedRelationshipsLabels.toArray(new String[] {}))) {
			Vertex childVertex = edge.getVertex(otherThanMetricInterestingDirection);
			if (CLASS.equals(childVertex.getProperty(GraphVertexProperies.TYPE))) {
				result++;
			}
		}
		v.setProperty(metricName, result);
	}

}
