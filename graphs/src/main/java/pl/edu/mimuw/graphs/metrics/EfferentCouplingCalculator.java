package pl.edu.mimuw.graphs.metrics;

import static com.tinkerpop.blueprints.Direction.IN;
import static com.tinkerpop.blueprints.Direction.OUT;
import static pl.edu.mimuw.graphs.api.MetricName.EFFERENT_COUPLING;

import java.util.Set;

public class EfferentCouplingCalculator extends AbstractCouplingCalculator {

	{
		this.metricName = EFFERENT_COUPLING.name();
		this.metricCountDirection = IN;
		this.otherThanMetricInterestingDirection = OUT;
	}

	public EfferentCouplingCalculator() {
		super();
	}

	public EfferentCouplingCalculator(Set<String> countedRelationshipsLabels) {
		super(countedRelationshipsLabels);
	}
}
