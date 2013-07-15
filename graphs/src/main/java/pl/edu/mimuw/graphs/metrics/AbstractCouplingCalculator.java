package pl.edu.mimuw.graphs.metrics;

import static pl.edu.mimuw.graphs.api.GraphRelationshipType.CALLS;
import static pl.edu.mimuw.graphs.api.GraphRelationshipType.CONTAINS;

import java.util.HashSet;
import java.util.Set;

import com.google.common.collect.Sets;
import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;

public abstract class AbstractCouplingCalculator {

	protected Set<String> countedRelationshipsLabels;
	private final Utils utils = new Utils();
	protected String metricName;
	protected Direction metricCountDirection;
	protected Direction otherThanMetricInterestingDirection;

	public AbstractCouplingCalculator() {
		countedRelationshipsLabels = new HashSet<String>();
		countedRelationshipsLabels.add(CALLS.name());
	}

	public AbstractCouplingCalculator(Set<String> countedRelationshipsLabels) {
		this.countedRelationshipsLabels = countedRelationshipsLabels;
	}

	public void calculate(Graph graph) {
		for (Vertex v : graph.getVertices()) {
			calculateForSingleVertex(graph, v);
		}
	}

	protected void calculateForSingleVertex(Graph graph, Vertex v) {
		Set<String> containsSet = new HashSet<String>();
		containsSet.add(CONTAINS.name());
		Set<Vertex> transitiveClosure = utils.transitiveClosure(graph, containsSet, v);
		Set<Vertex> otherPackages = findOtherPackages(transitiveClosure);
		Set<Vertex> result = Sets.difference(otherPackages, transitiveClosure);
		v.setProperty(metricName, result.size());
	}

	private Set<Vertex> findOtherPackages(Set<Vertex> transitiveClosure) {
		Set<Vertex> result = new HashSet<Vertex>();
		for (Vertex v : transitiveClosure) {
			for (Edge e : v.getEdges(metricCountDirection, countedRelationshipsLabels.toArray(new String[] {}))) {
				result.add(e.getVertex(otherThanMetricInterestingDirection));
			}
		}
		return result;
	}

}