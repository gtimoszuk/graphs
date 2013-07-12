package pl.edu.mimuw.graphs.importer.packages.graph;

import static pl.edu.mimuw.graphs.api.GraphVertexProperies.NAME;
import static pl.edu.mimuw.graphs.api.GraphVertexProperies.TYPE;
import static pl.edu.mimuw.graphs.api.GraphVertexTypes.PACKAGE;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.edu.mimuw.graphs.api.GraphRelationshipType;
import pl.edu.mimuw.graphs.importer.utils.SimpleSequence;

import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;

/**
 * 
 * @author gtimoszuk
 * 
 */
public class PackagesImporter {

	static final Logger LOGGER = LoggerFactory.getLogger(PackagesImporter.class);

	private final Set<String> packageStringSet;
	private final Graph graph;
	private final Map<String, Vertex> packagesMap = new HashMap<String, Vertex>();
	private final SimpleSequence sequence;

	public PackagesImporter(Set<String> packagesSet, Graph graph, SimpleSequence sequence) {
		this.packageStringSet = packagesSet;
		this.graph = graph;
		this.sequence = sequence;
	}

	public Map<String, Vertex> processPackages() {
		for (String packageString : packageStringSet) {
			processSinglePackage(packageString);
		}
		return packagesMap;
	}

	private void processSinglePackage(String packageString) {
		String[] splittedPackageString = packageString.split("/");
		int arraySize = splittedPackageString.length;
		StringBuffer packageBuffer = new StringBuffer();
		String parentPackageName = null;
		for (int i = 0; i < arraySize; i++) {
			packageBuffer.append(splittedPackageString[i]);
			String currentPackageName = packageBuffer.toString();
			if (!packagesMap.containsKey(currentPackageName)) {
				// keep in mind neo4j uses own id,
				Vertex currentVertex = graph.addVertex(sequence.getId());
				currentVertex.setProperty(NAME, currentPackageName);
				currentVertex.setProperty(TYPE, PACKAGE);
				packagesMap.put(currentPackageName, currentVertex);
				if (parentPackageName != null) {
					graph.addEdge(sequence.getId(), packagesMap.get(parentPackageName), currentVertex,
							GraphRelationshipType.CONTAINS.name());
				}
				parentPackageName = currentPackageName;

			}
			packageBuffer.append("/");
		}
	}
}
