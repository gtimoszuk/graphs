package pl.edu.mimuw.graphs.metrics;

import static pl.edu.mimuw.graphs.GraphVertexProperies.NAME;
import static pl.edu.mimuw.graphs.GraphVertexProperies.TYPE;
import static pl.edu.mimuw.graphs.metrics.MetricName.EFFERENT_COUPLING;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.edu.mimuw.graphs.importer.packages.graph.PackageGraphImporter;

import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;

public class EfferentCouplingCalculatorTest {

	private final Logger LOGGER = LoggerFactory.getLogger(EfferentCouplingCalculatorTest.class);

	@Test
	public void jUnitImportTest() {
		PackageGraphImporter importer = new PackageGraphImporter(
				"/home/ballo0/GTI/projects/testDataCreatorResults/junit/");
		Graph graph = importer.importGraph();

		AbstractCouplingCalculator efferentCouplingCalculator = new EfferentCouplingCalculator();
		efferentCouplingCalculator.calculate(graph);
		for (Vertex v : graph.getVertices()) {
			if (v.getPropertyKeys().contains(NAME)) {
				if (v.getPropertyKeys().contains(EFFERENT_COUPLING.name())) {
					LOGGER.debug("Node: " + v.getProperty(NAME) + " of type: " + v.getProperty(TYPE)
							+ " has efferent coupling: " + v.getProperty(EFFERENT_COUPLING.name()));
				} else {
					LOGGER.debug("Node: " + v.getProperty(NAME) + " of type: " + v.getProperty(TYPE)
							+ " has no efferent coupling property.");
				}
			}

		}
	}
}
