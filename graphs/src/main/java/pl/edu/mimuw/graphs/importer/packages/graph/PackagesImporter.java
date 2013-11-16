package pl.edu.mimuw.graphs.importer.packages.graph;

import static pl.edu.mimuw.graphs.GraphVertexProperies.NAME;
import static pl.edu.mimuw.graphs.GraphVertexProperies.TYPE;
import static pl.edu.mimuw.graphs.GraphVertexTypes.PACKAGE;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.edu.mimuw.graphs.GraphRelationshipType;
import pl.edu.mimuw.graphs.importer.utils.SimpleSequence;

import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;

/**
 * 
 * @author gtimoszuk
 * 
 */
public class PackagesImporter {

	private final Logger LOGGER = LoggerFactory.getLogger(PackagesImporter.class);

	private final Set<String> packageStringSet;
	private final Graph graph;
	private final SimpleSequence sequence;

	public PackagesImporter(Set<String> packagesSet, Graph graph, SimpleSequence sequence) {
		this.packageStringSet = packagesSet;
		this.graph = graph;
		this.sequence = sequence;
	}

	public Map<String, Vertex> processPackages() {
		Map<String, Vertex> packagesMap = new HashMap<String, Vertex>();
		for (String packageString : packageStringSet) {
			processSinglePackage(packageString, packagesMap);
		}
		return packagesMap;
	}

	private void processSinglePackage(String packageString, Map<String, Vertex> packagesMap) {

		String[] packageNameAndTree = packageString.split(" ");
		LOGGER.trace("working with packageString: {}", packageNameAndTree[0]);
		String[] splittedPackageString = packageNameAndTree[0].split("/");
		int arraySize = splittedPackageString.length;
		StringBuffer packageBuffer = new StringBuffer();
		String parentPackageName = null;
		for (int i = 0; i < arraySize; i++) {
			packageBuffer.append(splittedPackageString[i]);
			String currentPackageName = packageBuffer.toString();
			LOGGER.trace("currentPackageName {}", currentPackageName);
			if (!packagesMap.containsKey(currentPackageName)) {
				// keep in mind neo4j uses own id,
				Vertex currentVertex = addPackageVertex(currentPackageName);
				packagesMap.put(currentPackageName, currentVertex);
				if (parentPackageName != null) {
					graph.addEdge(sequence.getId(), packagesMap.get(parentPackageName), currentVertex,
							GraphRelationshipType.CONTAINS.name());
				}

			}
			parentPackageName = currentPackageName;
			packageBuffer.append("/");
		}
	}

	public Vertex addPackageVertex(String currentPackageName) {
		Vertex currentVertex = graph.addVertex(sequence.getId());
		currentVertex.setProperty(NAME, currentPackageName);
		currentVertex.setProperty(TYPE, PACKAGE);
		return currentVertex;
	}
}
