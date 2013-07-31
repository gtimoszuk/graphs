package pl.edu.mimuw.graphs.statistics;

import static pl.edu.mimuw.graphs.GraphVertexProperies.TYPE;
import static pl.edu.mimuw.graphs.GraphVertexTypes.CLASS;
import static pl.edu.mimuw.graphs.GraphVertexTypes.PACKAGE;
import static pl.edu.mimuw.graphs.statistics.GraphStatististicsName.MAX;
import static pl.edu.mimuw.graphs.statistics.GraphStatististicsName.MEAN;
import static pl.edu.mimuw.graphs.statistics.GraphStatististicsName.MEDIAN;
import static pl.edu.mimuw.graphs.statistics.GraphStatististicsName.MIN;
import static pl.edu.mimuw.graphs.statistics.GraphStatististicsName.STD;
import static pl.edu.mimuw.graphs.statistics.GraphStatististicsName.SUM;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.edu.mimuw.graphs.metrics.MetricName;

import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;

/**
 * @author gtimoszuk
 * 
 */
public class GraphStatistics {

	static final Logger LOGGER = LoggerFactory.getLogger(GraphStatistics.class);

	private final GraphStatisticsCounter graphStatisticsCounter = new GraphStatisticsCounter();

	public Map<MetricName, Map<String, Double>> getStatisticsForGraph(Graph graph) {

		Iterable<Vertex> vertices = graph.getVertices();
		return calulateAllStatistics(vertices);
	}

	public Map<MetricName, Map<String, Double>> getStatisticsForClasses(Graph graph) {

		Iterable<Vertex> vertices = getClassVerticesOfType(graph, CLASS);
		return calulateAllStatistics(vertices);
	}

	public Map<MetricName, Map<String, Double>> getStatisticsForPackages(Graph graph) {

		Iterable<Vertex> vertices = getClassVerticesOfType(graph, PACKAGE);
		return calulateAllStatistics(vertices);
	}

	private Iterable<Vertex> getClassVerticesOfType(Graph graph, String type) {
		List<Vertex> result = new ArrayList<Vertex>();
		for (Vertex v : graph.getVertices()) {
			if (v.getPropertyKeys().contains(TYPE) && type.equals(v.getProperty(TYPE))) {
				result.add(v);
			}
		}
		return result;
	}

	private Map<MetricName, Map<String, Double>> calulateAllStatistics(Iterable<Vertex> vertices) {
		Map<MetricName, Map<String, Double>> result = new HashMap<MetricName, Map<String, Double>>();

		for (MetricName metricName : MetricName.values()) {
			LOGGER.trace("Metric name: {}", metricName.name());
			Map<String, Double> oneMetricStats = new HashMap<String, Double>();

			double min = graphStatisticsCounter.min(vertices, metricName.name());
			LOGGER.trace("min: {}", min);
			oneMetricStats.put(MIN.name(), min);

			double max = graphStatisticsCounter.max(vertices, metricName.name());
			LOGGER.trace("max: {}", max);
			oneMetricStats.put(MAX.name(), max);

			double sum = graphStatisticsCounter.sum(vertices, metricName.name());
			LOGGER.trace("sum: {}", sum);
			oneMetricStats.put(SUM.name(), sum);

			double mean = graphStatisticsCounter.mean(vertices, metricName.name());
			LOGGER.trace("mean: {}", mean);
			oneMetricStats.put(MEAN.name(), mean);

			double median = graphStatisticsCounter.median(vertices, metricName.name());
			LOGGER.trace("median: {}", median);
			oneMetricStats.put(MEDIAN.name(), median);

			double std = graphStatisticsCounter.std(vertices, metricName.name());
			LOGGER.trace("std: {}", std);
			oneMetricStats.put(STD.name(), std);

			result.put(metricName, oneMetricStats);

		}

		return result;
	}
}
