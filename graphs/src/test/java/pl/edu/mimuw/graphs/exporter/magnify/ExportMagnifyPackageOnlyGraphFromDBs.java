package pl.edu.mimuw.graphs.exporter.magnify;

import java.io.File;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.edu.mimuw.graphs.statistics.GraphStatisticsSummaries;
import pl.edu.mimuw.graphs.statistics.PackageGraphStatisticsTools;
import pl.edu.mimuw.graphs.transformations.GraphShrinkerToPackagesOnlyGraph;

import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.impls.neo4j.Neo4jGraph;

public class ExportMagnifyPackageOnlyGraphFromDBs {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExportMagnifyPackageOnlyGraphFromDBs.class);

	private final static String DB = "db/";
	private final static String RESULTS = "results/";

	private final GraphShrinkerToPackagesOnlyGraph graphShrinker = new GraphShrinkerToPackagesOnlyGraph();

	private final PackageGraphStatisticsTools graphStatisticsTools = new PackageGraphStatisticsTools();

	@Test
	public void generateMagnifyJSONSForPackagageGraphs() {
		String path = "/home/ballo0/GTI/PHD/iter1/";
		File dataDir = new File(path + DB);
		File[] projectsToAnalyze = dataDir.listFiles();
		for (File f : projectsToAnalyze) {
			String absolutePath = f.getAbsolutePath();

			String[] pathSplitted = absolutePath.split("/");
			String projectName = pathSplitted[pathSplitted.length - 1];
			if (!projectName.startsWith(".")) {

				LOGGER.info("processing of project {} start", projectName);

				String resultsDirPath = path + RESULTS + projectName + "/";
				Graph graph = new Neo4jGraph(path + DB + projectName);
				Graph newGraph = graphShrinker.shrinkGraph(graph);
				graph.shutdown();

				GraphStatisticsSummaries graphStatisticsSummaries = graphStatisticsTools.gatherStatistics(newGraph);
				graphShrinker.joinMultipleSameEgdesToOneWithCount(newGraph);
				graphStatisticsTools.exporData(projectName, resultsDirPath, newGraph, "packages",
						graphStatisticsSummaries);
			}
		}
	}

}
