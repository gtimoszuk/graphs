package pl.edu.mimuw.graphs.metrics;

import static pl.edu.mimuw.graphs.GraphVertexProperies.NAME;
import static pl.edu.mimuw.graphs.GraphVertexProperies.TYPE;
import static pl.edu.mimuw.graphs.metrics.MetricName.EFFERENT_COUPLING;

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
public class EfferentCouplingCalculatorTest {

	private final Logger LOGGER = LoggerFactory.getLogger(EfferentCouplingCalculatorTest.class);

	@Autowired
	public PackageGraphImporter importer;

	@Resource(name = "junitSimpleDataDir")
	public String jutniDir;

	@Test
	public void jUnitImportTest() {

		Graph graph = importer.importGraph(jutniDir);

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
