package pl.edu.mimuw.graphs.metrics;

import static com.tinkerpop.blueprints.Direction.IN;
import static com.tinkerpop.blueprints.Direction.OUT;
import static pl.edu.mimuw.graphs.metrics.MetricName.CALLS_TO_OTHER_PACKAGES;

import java.util.Set;

public class CallsToOtherPackagesCalculator extends AbstractEgdesBetweenClassesInDifferentPackagesCalclulator {

	{
		this.metricName = CALLS_TO_OTHER_PACKAGES.name();
		this.metricCountDirection = OUT;
		this.otherThanMetricInterestingDirection = IN;
	}

	public CallsToOtherPackagesCalculator() {
		super();
	}

	public CallsToOtherPackagesCalculator(Set<String> countedRelationshipsLabels) {
		super(countedRelationshipsLabels);
	}
}
