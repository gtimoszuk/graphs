package pl.edu.mimuw.graphs.metrics;

import static pl.edu.mimuw.graphs.api.GraphVertexProperies.NAME;
import static pl.edu.mimuw.graphs.api.GraphVertexProperies.TYPE;
import static pl.edu.mimuw.graphs.api.MetricName.AFFERENT_COUPLING;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.edu.mimuw.graphs.importer.packages.graph.PackageGraphImporter;

import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;

public class AfferentCouplingCalculatorTest {

	static final Logger LOGGER = LoggerFactory.getLogger(AfferentCouplingCalculatorTest.class);

	@Test
	public void jUnitImportTest() {
		PackageGraphImporter importer = new PackageGraphImporter(
				"/home/ballo0/GTI/projects/testDataCreatorResults/junit/");
		Graph graph = importer.importGraph();

		AbstractCouplingCalculator afferentCouplingCalculator = new AfferentCouplingCalculator();
		afferentCouplingCalculator.calculate(graph);
		for (Vertex v : graph.getVertices()) {
			if (v.getPropertyKeys().contains(NAME)) {
				if (v.getPropertyKeys().contains(AFFERENT_COUPLING)) {
					LOGGER.info("Node: " + v.getProperty(NAME) + " of type: " + v.getProperty(TYPE)
							+ " has afferent coupling: " + v.getProperty(AFFERENT_COUPLING.name()));
				} else {
					LOGGER.warn("Node: " + v.getProperty(NAME) + " of type: " + v.getProperty(TYPE)
							+ " has no afferent coupling property.");
				}
			}

		}
	}
}