package pl.edu.mimuw.graphs.importer.packages.graph;

import static pl.edu.mimuw.graphs.api.GraphVertexProperies.NAME;
import static pl.edu.mimuw.graphs.api.GraphVertexProperies.TYPE;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.edu.mimuw.graphs.importer.packages.graph.PackageGraphImporter;

import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;

public class PackageGraphImporterTest {

	static final Logger LOGGER = LoggerFactory.getLogger(PackageGraphImporterTest.class);

	@Test
	public void jUnitImportTest() {
		PackageGraphImporter importer = new PackageGraphImporter(
				"/home/ballo0/GTI/projects/testDataCreatorResults/junit/");
		Graph graph = importer.importGraph();
		for (Vertex v : graph.getVertices()) {
			if (v.getPropertyKeys().contains(NAME)) {
				LOGGER.info("vertex name: {} type: {}", v.getProperty(NAME), v.getProperty(TYPE));
			}

		}
	}

	/**
	 * Perform pre-test initialization.
	 * 
	 * @throws Exception
	 *             if the initialization fails for some reason
	 * 
	 * @generatedBy CodePro at 12.07.13 12:37
	 */
	@Before
	public void setUp() throws Exception {
		// add additional set up code here
	}

	/**
	 * Perform post-test clean-up.
	 * 
	 * @throws Exception
	 *             if the clean-up fails for some reason
	 * 
	 * @generatedBy CodePro at 12.07.13 12:37
	 */
	@After
	public void tearDown() throws Exception {
		// Add additional tear down code here
	}

}