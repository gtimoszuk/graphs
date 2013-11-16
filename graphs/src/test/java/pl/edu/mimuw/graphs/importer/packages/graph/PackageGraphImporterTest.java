package pl.edu.mimuw.graphs.importer.packages.graph;

import static pl.edu.mimuw.graphs.GraphVertexProperies.NAME;
import static pl.edu.mimuw.graphs.GraphVertexProperies.TYPE;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { pl.edu.mimuw.graphs.BaseTestConfig.class })
public class PackageGraphImporterTest {

	static final Logger LOGGER = LoggerFactory.getLogger(PackageGraphImporterTest.class);

	@Autowired
	public PackageGraphImporter importer;

	@Resource(name = "junitSimpleDataDir")
	public String jutniDir;

	@Test
	public void jUnitImportTest() {
		PackageGraphImporter importer = new PackageGraphImporter();
		Graph graph = importer.importGraph(jutniDir);
		for (Vertex v : graph.getVertices()) {
			if (v.getPropertyKeys().contains(NAME)) {
				LOGGER.info("vertex name: {} type: {}", v.getProperty(NAME), v.getProperty(TYPE));
			}

		}
	}
}