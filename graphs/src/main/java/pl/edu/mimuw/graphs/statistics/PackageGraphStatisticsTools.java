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
import pl.edu.mimuw.graphs.metrics.EfferentCouplingCalculator;
import pl.edu.mimuw.graphs.metrics.PageRankCalculator;

import com.tinkerpop.blueprints.Graph;

public class PackageGraphStatisticsTools {

	static final Logger LOGGER = LoggerFactory.getLogger(PackageGraphStatisticsTools.class);

	public void countOneDirStatsForProject(String projectPath, String outPath) {
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

		GraphStatistics graphStatistics = new GraphStatistics();
		graphStatistics.getStatisticsForGraph(graph);
		graphStatistics.getStatisticsForPackages(graph);
		graphStatistics.getStatisticsForClasses(graph);

		MagnifyExporter magnifyExporter = new MagnifyExporter(graph, EFFERENT_COUPLING.name(), PAGE_RANK.name(),
				CONTAINS.name(), CALLS.name());
		magnifyExporter.export(outPath + projectName + ".json");
	}

}
