package pl.edu.mimuw.graphs.metrics;

import static pl.edu.mimuw.graphs.GraphVertexProperies.NAME;
import static pl.edu.mimuw.graphs.GraphVertexProperies.TYPE;
import static pl.edu.mimuw.graphs.metrics.MetricName.CALLS_TO_OTHER_PACKAGES;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import pl.edu.mimuw.graphs.importer.packages.graph.PackageGraphImporter;

import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { pl.edu.mimuw.graphs.BaseTestConfig.class })
public class CallsToOtherPackagesCalculatorTest {

	private final Logger LOGGER = LoggerFactory.getLogger(CallsToOtherPackagesCalculatorTest.class);

	@Autowired
	public PackageGraphImporter importer;

	@Resource(name = "junitSimpleDataDir")
	public String jutniDir;

	@Test
	public void jUnitImportTest() {

		Graph graph = importer.importGraph(jutniDir);

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
