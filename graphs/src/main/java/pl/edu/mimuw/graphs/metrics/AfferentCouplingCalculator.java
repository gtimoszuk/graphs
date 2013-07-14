package pl.edu.mimuw.graphs.metrics;

import static com.tinkerpop.blueprints.Direction.IN;
import static com.tinkerpop.blueprints.Direction.OUT;
import static pl.edu.mimuw.graphs.api.MetricName.AFFERENT_COUPLING;

import java.util.Set;

public class AfferentCouplingCalculator extends AbstractCouplingCalculator {

	{
		this.metricName = AFFERENT_COUPLING.name();
		this.metricCountDirection = OUT;
		this.otherThanMetricInterestingDirection = IN;
	}

	public AfferentCouplingCalculator() {
		super();
	}

	public AfferentCouplingCalculator(Set<String> countedRelationshipsLabels) {
		super(countedRelationshipsLabels);
	}
}
