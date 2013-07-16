package pl.edu.mimuw.graphs.statistics;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import pl.edu.mimuw.graphs.api.MetricName;

public class GraphStatisticsSummaries {

	private final Map<String, Map<MetricName, Map<String, Double>>> dataStructure = new HashMap<String, Map<MetricName, Map<String, Double>>>();

	public void put(String s, Map<MetricName, Map<String, Double>> map) {
		dataStructure.put(s, map);
	}

	public Set<String> keySet() {
		return dataStructure.keySet();
	}

	public Map<MetricName, Map<String, Double>> get(String key) {
		return dataStructure.get(key);
	}
}
