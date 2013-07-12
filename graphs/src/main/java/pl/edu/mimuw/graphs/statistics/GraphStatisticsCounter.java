package pl.edu.mimuw.graphs.statistics;

import java.util.Set;

import org.apache.commons.math.stat.descriptive.DescriptiveStatistics;

import com.tinkerpop.blueprints.Vertex;

public class GraphStatisticsCounter {

	public double mean(Set<Vertex> vertices, String property) {
		DescriptiveStatistics stats = prepareStatsData(vertices, property);
		return stats.getMean();
	}

	private DescriptiveStatistics prepareStatsData(Set<Vertex> vertices, String property) {
		DescriptiveStatistics stats = new DescriptiveStatistics();
		for (Vertex v : vertices) {
			if (v.getPropertyKeys().contains(property) && v.getProperty(property) != null) {
				stats.addValue((Double) v.getProperty(property));
			}
		}
		return stats;
	}

	public double median(Set<Vertex> vertices, String property) {
		DescriptiveStatistics stats = prepareStatsData(vertices, property);

		return stats.getPercentile(50.0);
	}

	public double std(Set<Vertex> vertices, String property) {
		DescriptiveStatistics stats = prepareStatsData(vertices, property);

		return stats.getStandardDeviation();
	}

}
