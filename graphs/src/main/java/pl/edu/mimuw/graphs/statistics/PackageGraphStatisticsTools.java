package pl.edu.mimuw.graphs.statistics;

import static pl.edu.mimuw.graphs.api.GraphRelationshipType.CALLS;
import static pl.edu.mimuw.graphs.api.GraphRelationshipType.CONTAINS;
import static pl.edu.mimuw.graphs.api.MetricName.EFFERENT_COUPLING;
import static pl.edu.mimuw.graphs.api.MetricName.PAGE_RANK;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.edu.mimuw.graphs.exporter.magnify.MagnifyExporter;
import pl.edu.mimuw.graphs.importer.packages.graph.PackageGraphExpander;
import pl.edu.mimuw.graphs.importer.packages.graph.PackageGraphImporter;
import pl.edu.mimuw.graphs.metrics.AfferentCouplingCalculator;
import pl.edu.mimuw.graphs.metrics.CallsFromOtherPackagesCalculator;
import pl.edu.mimuw.graphs.metrics.CallsToOtherPackagesCalculator;
import pl.edu.mimuw.graphs.metrics.EfferentCouplingCalculator;
import pl.edu.mimuw.graphs.metrics.PageRankCalculator;

import com.tinkerpop.blueprints.Graph;

public class PackageGraphStatisticsTools {

	static final Logger LOGGER = LoggerFactory.getLogger(PackageGraphStatisticsTools.class);

	public void countOneDirStatsForProject(String projectPath, String outPath, String outInfix) {
		String[] pathSplitted = projectPath.split("/");
		String projectName = pathSplitted[pathSplitted.length - 1];

		PackageGraphImporter importer = new PackageGraphImporter(projectPath);
		Graph packageGraphWithCounts = importer.importGraph();
		PackageGraphExpander packageGraphExpander = new PackageGraphExpander(packageGraphWithCounts);
		Graph graph = packageGraphExpander.expandGraph();

		PageRankCalculator pageRankCalculator = new PageRankCalculator();
		pageRankCalculator.calculate(graph);

		AfferentCouplingCalculator afferentCouplingCalculator = new AfferentCouplingCalculator();
		afferentCouplingCalculator.calculate(graph);

		EfferentCouplingCalculator efferentCouplingCalculator = new EfferentCouplingCalculator();
		efferentCouplingCalculator.calculate(graph);

		CallsFromOtherPackagesCalculator callsFromOtherPackagesCalculator = new CallsFromOtherPackagesCalculator();
		callsFromOtherPackagesCalculator.calculate(graph);

		CallsToOtherPackagesCalculator callsToOtherPackagesCalculator = new CallsToOtherPackagesCalculator();
		callsToOtherPackagesCalculator.calculate(graph);

		GraphStatistics graphStatistics = new GraphStatistics();
		LOGGER.info("Whole graph statistics");
		graphStatistics.getStatisticsForGraph(graph);
		LOGGER.info("Packages only statistics");
		graphStatistics.getStatisticsForPackages(graph);
		LOGGER.info("Classes only statistics");
		graphStatistics.getStatisticsForClasses(graph);

		MagnifyExporter magnifyExporter = new MagnifyExporter(graph, EFFERENT_COUPLING.name(), PAGE_RANK.name(),
				CONTAINS.name(), CALLS.name());
		magnifyExporter.export(outPath + projectName + outInfix + ".json");
	}

	public void countOneDirStatsForProject(String projectPath, String outPath) {
		countOneDirStatsForProject(projectPath, outPath, "");
	}

}
