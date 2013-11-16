package pl.edu.mimuw.graphs.importer.packages.graph;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.javatuples.Triplet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.edu.mimuw.graphs.importer.utils.SimpleSequence;

import com.google.common.io.Files;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.neo4j.Neo4jGraph;

/**
 * Stateless data importer
 * 
 * @author gtimoszuk
 * 
 */
public class PackageGraphImporter implements GraphImporter {

	static final Logger LOGGER = LoggerFactory.getLogger(PackageGraphImporter.class);

	private static final String EDGES_SUFFIX = "/edges.txt";
	private static final String ENTITIES_SUFFIX = "/entities.txt";
	private static final String PACKAGES_SUFFIX = "/packages.txt";
	private final SimpleSequence sequence = new SimpleSequence();

	public PackageGraphImporter() {
	}

	@Override
	public Graph importGraph(String inDir) {
		return importGraph(inDir, Files.createTempDir());
	}

	@Override
	public Graph importGraph(String inDir, String outDir) {
		File dbDir = new File(outDir);

		return importGraph(inDir, dbDir);
	}

	public Graph importGraph(String inDir, File dbDir) {

		LOGGER.info("importing of dir {} is beginning", inDir);

		Graph resultGraph = new Neo4jGraph(dbDir.getAbsolutePath());

		File packagesFile = new File(inDir + PACKAGES_SUFFIX);
		Map<String, Vertex> packagesMap = processPackages(resultGraph, packagesFile);

		File entitiesFile = new File(inDir + ENTITIES_SUFFIX);
		Map<String, Vertex> classMap = processEntities(packagesMap, resultGraph, entitiesFile);

		File edgesFile = new File(inDir + EDGES_SUFFIX);
		processEdges(classMap, resultGraph, edgesFile);

		LOGGER.info("importing of dir {} has finished", inDir);
		return resultGraph;
	}

	private Map<String, Vertex> processEntities(Map<String, Vertex> packagesMap, Graph resultGraph, File entitiesFile) {
		LOGGER.info("entities processing start");
		Map<String, Vertex> result = new HashMap<String, Vertex>();

		try {
			BufferedReader entitiesReader = new BufferedReader(new InputStreamReader(new FileInputStream(entitiesFile)));
			String line;
			Map<String, String> childParentMap = new HashMap<String, String>();
			while ((line = entitiesReader.readLine()) != null) {
				LOGGER.trace("line: {}", line);
				String newLine = line.replace("\n", "");
				String[] splittedLine = newLine.split(" ");
				childParentMap.put(splittedLine[0], splittedLine[1]);
			}
			entitiesReader.close();
			LOGGER.debug("packageClassMap size: {}", childParentMap.size());

			EntitiesImporter entitiesImporter = new EntitiesImporter(packagesMap, childParentMap, resultGraph, sequence);
			result = entitiesImporter.processEntities();
		} catch (IOException e) {
			LOGGER.warn("EXCP: {}", e);
		}
		return result;
	}

	private void processEdges(Map<String, Vertex> classMap, Graph resultGraph, File edgesFile) {
		LOGGER.info("egdes processing start");
		Set<Triplet<String, String, Integer>> edgeSet = new HashSet<Triplet<String, String, Integer>>();
		try {
			BufferedReader egdesReader = new BufferedReader(new InputStreamReader(new FileInputStream(edgesFile)));
			String line;
			while ((line = egdesReader.readLine()) != null) {
				String newLine = line.replace("\n", "");
				String[] splittedLine = newLine.split(" ");
				Triplet<String, String, Integer> egde = new Triplet<String, String, Integer>(splittedLine[0],
						splittedLine[1], Integer.valueOf(splittedLine[2]));
				edgeSet.add(egde);
			}
			egdesReader.close();
			EdgesImporter edgesImporter = new EdgesImporter(resultGraph, classMap, sequence, edgeSet);
			edgesImporter.processEdges();

		} catch (IOException e) {
			LOGGER.warn("EXCP: {}", e);

		}
	}

	private Map<String, Vertex> processPackages(Graph resultGraph, File packagesFile) {
		LOGGER.info("packages processing start");
		Map<String, Vertex> result = new HashMap<String, Vertex>();
		Set<String> packagesSet = new HashSet<String>();
		try {
			BufferedReader paclagesReader = new BufferedReader(new InputStreamReader(new FileInputStream(packagesFile)));
			String line;
			while ((line = paclagesReader.readLine()) != null) {
				String newLine = line.replace("\n", "");
				packagesSet.add(newLine);
				LOGGER.info(newLine);
			}
			paclagesReader.close();
			PackagesImporter packagesImporter = new PackagesImporter(packagesSet, resultGraph, sequence);
			result = packagesImporter.processPackages();

		} catch (IOException e) {
			LOGGER.warn("EXCP: {}", e);
		}
		return result;
	}
}
