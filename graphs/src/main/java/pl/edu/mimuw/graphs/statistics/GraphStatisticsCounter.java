package pl.edu.mimuw.graphs.statistics;

import org.apache.commons.math.stat.descriptive.DescriptiveStatistics;

import com.tinkerpop.blueprints.Vertex;

/**
 * This class is stateless.
 * 
 * @author gtimoszuk
 * 
 */
public class GraphStatisticsCounter {

	public double mean(Iterable<Vertex> vertices, String property) {
		DescriptiveStatistics stats = prepareStatsData(vertices, property);
		return stats.getMean();
	}

	public double median(Iterable<Vertex> vertices, String property) {
		DescriptiveStatistics stats = prepareStatsData(vertices, property);

		return stats.getPercentile(50.0);

	}

	public double std(Iterable<Vertex> vertices, String property) {
		DescriptiveStatistics stats = prepareStatsData(vertices, property);
		return stats.getStandardDeviation();
	}

	public double sum(Iterable<Vertex> vertices, String property) {
		DescriptiveStatistics stats = prepareStatsData(vertices, property);
		return stats.getSum();
	}

	public double min(Iterable<Vertex> vertices, String property) {
		DescriptiveStatistics stats = prepareStatsData(vertices, property);
		return stats.getMin();
	}

	public double max(Iterable<Vertex> vertices, String property) {
		DescriptiveStatistics stats = prepareStatsData(vertices, property);
		return stats.getMax();
	}

	private DescriptiveStatistics prepareStatsData(Iterable<Vertex> vertices, String property) {
		DescriptiveStatistics stats = new DescriptiveStatistics();
		for (Vertex v : vertices) {
			if (v.getPropertyKeys().contains(property) && v.getProperty(property) != null) {
				Object propertyObject = v.getProperty(property);
				if (propertyObject instanceof Double) {
					stats.addValue((Double) propertyObject);
				} else if (propertyObject instanceof Integer) {
					stats.addValue(((Integer) propertyObject).doubleValue());

				}

			}
		}
		return stats;
	}

}
