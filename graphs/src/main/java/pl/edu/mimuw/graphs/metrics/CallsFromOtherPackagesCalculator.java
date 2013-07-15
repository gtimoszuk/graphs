package pl.edu.mimuw.graphs.metrics;

import static com.tinkerpop.blueprints.Direction.IN;
import static com.tinkerpop.blueprints.Direction.OUT;
import static pl.edu.mimuw.graphs.api.MetricName.CALLS_FROM_OTHER_PACKAGES;

import java.util.Set;

public class CallsFromOtherPackagesCalculator extends AbstractEgdesBetweenClassesInDifferentPackagesCalclulator {

	{
		this.metricName = CALLS_FROM_OTHER_PACKAGES.name();
		this.metricCountDirection = IN;
		this.otherThanMetricInterestingDirection = OUT;
	}

	public CallsFromOtherPackagesCalculator() {
		super();
	}

	public CallsFromOtherPackagesCalculator(Set<String> countedRelationshipsLabels) {
		super(countedRelationshipsLabels);
	}
}
