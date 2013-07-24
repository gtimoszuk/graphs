package pl.edu.mimuw.graphs.transformations;

import static com.tinkerpop.blueprints.Direction.BOTH;
import static com.tinkerpop.blueprints.Direction.IN;
import static com.tinkerpop.blueprints.Direction.OUT;
import static pl.edu.mimuw.graphs.api.GraphRelationshipType.CALLS;
import static pl.edu.mimuw.graphs.api.GraphVertexProperies.TYPE;
import static pl.edu.mimuw.graphs.api.GraphVertexTypes.CLASS;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.javatuples.Pair;

import pl.edu.mimuw.graphs.api.OptionalGraphProperties;
import pl.edu.mimuw.graphs.importer.utils.SimpleSequence;
import pl.edu.mimuw.graphs.importer.utils.ToTinkerGraphCloner;
import pl.edu.mimuw.graphs.metrics.Utils;

import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;

public class GraphShrinkerToPackagesOnlyGraph {

	private final ToTinkerGraphCloner toTinkerGraphCloner = new ToTinkerGraphCloner();

	private final Utils utils = new Utils();

	public Graph shrinkGraphToEgesWithCount(Graph graph) {

		SimpleSequence sequence = new SimpleSequence(utils.getMaxId(graph) + 1L);

		Graph result = toTinkerGraphCloner.cloneGraphToTinker(graph);
		moveEdgesFromClassesPackagesAndRemoveClasses(result, sequence);
		joinMultipleSameEgdesToOneWithCount(result);

		return result;
	}

	public Graph shrinkGraph(Graph graph) {

		SimpleSequence sequence = new SimpleSequence(utils.getMaxId(graph) + 1L);

		Graph result = toTinkerGraphCloner.cloneGraphToTinker(graph);
		moveEdgesFromClassesPackagesAndRemoveClasses(result, sequence);

		return result;
	}

	public void joinMultipleSameEgdesToOneWithCount(Graph result) {
		for (Vertex v : result.getVertices()) {
			Map<Pair<String, String>, List<Edge>> egdesSplit = new HashMap<Pair<String, String>, List<Edge>>();
			for (Edge e : v.getEdges(OUT)) {
				Pair<String, String> egdeKey = new Pair<String, String>((String) e.getVertex(IN).getId(), e.getLabel());
				if (egdesSplit.keySet().contains(egdeKey)) {
					egdesSplit.get(egdeKey).add(e);
				} else {
					List<Edge> edges = new ArrayList<Edge>();
					edges.add(e);
					egdesSplit.put(egdeKey, edges);
				}
			}
			for (Pair<String, String> keyPair : egdesSplit.keySet()) {
				List<Edge> edges = egdesSplit.get(keyPair);
				if (edges.size() > 1) {
					Edge stayingEdge = edges.get(0);
					stayingEdge.setProperty(OptionalGraphProperties.COUNT, edges.size());
					for (int i = 1; i < edges.size(); i++) {
						result.removeEdge(edges.get(i));
					}
				}
			}
		}

	}

	private void moveEdgesFromClassesPackagesAndRemoveClasses(Graph result, SimpleSequence sequence) {
		Stack<Vertex> verticesToBeRemoved = new Stack<Vertex>();
		for (Vertex v : result.getVertices()) {
			if (CLASS.equals(v.getProperty(TYPE))) {
				verticesToBeRemoved.push(v);
				Vertex parentPackage = utils.getParentPackage(v);
				for (Edge e : v.getEdges(IN, new String[] { CALLS.name() })) {
					cloneVertex(parentPackage, e.getVertex(OUT), e, result, sequence);
				}
				for (Edge e : v.getEdges(OUT, new String[] { CALLS.name() })) {
					cloneVertex(e.getVertex(IN), parentPackage, e, result, sequence);
				}
				for (Edge e : v.getEdges(BOTH)) {
					result.removeEdge(e);
				}
			}
		}
		while (!verticesToBeRemoved.empty()) {
			Vertex v = verticesToBeRemoved.pop();
			result.removeVertex(v);
		}

	}

	private void cloneVertex(Vertex inVertex, Vertex outVertex, Edge e, Graph result, SimpleSequence sequence) {
		result.addEdge(sequence.getId(), outVertex, inVertex, e.getLabel());

	}
}
