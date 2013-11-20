package pl.edu.mimuw.graphs.importer.packages.graph;

import static pl.edu.mimuw.graphs.GraphVertexProperies.TYPE;
import static pl.edu.mimuw.graphs.GraphVertexTypes.CLASS;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.edu.mimuw.graphs.GraphRelationshipType;
import pl.edu.mimuw.graphs.GraphVertexProperies;
import pl.edu.mimuw.graphs.importer.utils.SimpleSequence;

import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;

public class EntitiesImporter {

	private final Map<String, Vertex> packagesMap;

	private final Map<String, String> classPackageMap;

	private final Graph graph;

	private final SimpleSequence sequence;

	static final Logger LOGGER = LoggerFactory.getLogger(EntitiesImporter.class);

	public EntitiesImporter(Map<String, Vertex> packagesMap, Map<String, String> classPackageMap, Graph graph,
			SimpleSequence sequence) {
		this.packagesMap = packagesMap;
		this.classPackageMap = classPackageMap;
		this.graph = graph;
		this.sequence = sequence;
	}

	public Map<String, Vertex> processEntities() {
		Map<String, Vertex> entitiesMap = new HashMap<String, Vertex>();
		LOGGER.debug("classPackageMap size: {}", classPackageMap.size());
		for (Entry<String, String> entry : classPackageMap.entrySet()) {
			Vertex classVertex = null;
			if (packagesMap.containsKey(entry.getKey())) {
				classVertex = packagesMap.get(entry.getKey());
			} else {
				classVertex = graph.addVertex(sequence.getId());
				classVertex.setProperty(TYPE, CLASS);
				classVertex.setProperty(GraphVertexProperies.NAME, entry.getKey());
			}

			for (Entry<String, Vertex> e : packagesMap.entrySet()) {
				LOGGER.trace(e.getKey());
			}
			LOGGER.trace(entry.getValue());
			LOGGER.trace(packagesMap.get(entry.getValue()).toString());
			LOGGER.trace(packagesMap.get(entry.getValue()).getId().toString());
			LOGGER.trace(graph.getVertex(packagesMap.get(entry.getValue()).getId()).toString());

			graph.addEdge(sequence.getId(), graph.getVertex(packagesMap.get(entry.getValue()).getId()), classVertex,
					GraphRelationshipType.CONTAINS.name());
			entitiesMap.put(entry.getKey(), classVertex);

		}
		return entitiesMap;

	}
}
