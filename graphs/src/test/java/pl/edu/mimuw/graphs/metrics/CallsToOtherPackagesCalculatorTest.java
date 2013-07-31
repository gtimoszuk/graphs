package pl.edu.mimuw.graphs.metrics;

import static pl.edu.mimuw.graphs.GraphVertexProperies.NAME;
import static pl.edu.mimuw.graphs.GraphVertexProperies.TYPE;
import static pl.edu.mimuw.graphs.metrics.MetricName.CALLS_TO_OTHER_PACKAGES;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.edu.mimuw.graphs.importer.packages.graph.PackageGraphImporter;

import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;

public class CallsToOtherPackagesCalculatorTest {

	private final Logger LOGGER = LoggerFactory.getLogger(CallsToOtherPackagesCalculatorTest.class);

	@Test
	public void jUnitImportTest() {
		PackageGraphImporter importer = new PackageGraphImporter(
				"/home/ballo0/GTI/projects/testDataCreatorResults/junit/");
		Graph graph = importer.importGraph();

		AbstractCouplingCalculator metricCalculator = new CallsToOtherPackagesCalculator();
		metricCalculator.calculate(graph);
		for (Vertex v : graph.getVertices()) {
			if (v.getPropertyKeys().contains(NAME)) {
				if (v.getPropertyKeys().contains(CALLS_TO_OTHER_PACKAGES.name())) {
					LOGGER.info("Node: " + v.getProperty(NAME) + " of type: " + v.getProperty(TYPE)
							+ " has calls to other packages metric count: "
							+ v.getProperty(CALLS_TO_OTHER_PACKAGES.name()));
				} else {
					LOGGER.info("Node: " + v.getProperty(NAME) + " of type: " + v.getProperty(TYPE)
							+ " has no calls to other packages metric count.");
				}
			}

		}
	}

}
