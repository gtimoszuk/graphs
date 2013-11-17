package pl.edu.mimuw.graphs.exporter.magnify;

import static pl.edu.mimuw.graphs.services.projects.ProjectsConstants.DB_SUFFIX;
import static pl.edu.mimuw.graphs.services.projects.ProjectsConstants.RESULTS_SUFFIX;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.edu.mimuw.graphs.statistics.GraphStatisticsSummaries;
import pl.edu.mimuw.graphs.statistics.PackageGraphStatisticsTools;
import pl.edu.mimuw.graphs.transformations.GraphShrinkerToPackagesOnlyGraph;

import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.impls.neo4j.Neo4jGraph;

/**
 * Simple utility tool that scans a directory finds all projects and then
 * creates proper magnify JSON for every project
 * 
 * @author gtimoszuk
 * 
 */
public class MagnifyPackageOnlyJSONFromDBExporter {

	private static final Logger LOGGER = LoggerFactory.getLogger(MagnifyPackageOnlyJSONFromDBExporter.class);

	private final GraphShrinkerToPackagesOnlyGraph graphShrinker = new GraphShrinkerToPackagesOnlyGraph();

	private final PackageGraphStatisticsTools graphStatisticsTools = new PackageGraphStatisticsTools();

	public void generateMagnifyJSONSForPackagageGraphs(String path) throws Exception {
		File dataDir = new File(path + DB_SUFFIX);
		File[] projectsToAnalyze = dataDir.listFiles();
		for (File f : projectsToAnalyze) {
			String absolutePath = f.getAbsolutePath();

			String[] pathSplitted = absolutePath.split("/");
			String projectName = pathSplitted[pathSplitted.length - 1];
			if (!projectName.startsWith(".")) {

				LOGGER.info("processing of project {} start", projectName);

				String resultsDirPath = path + RESULTS_SUFFIX + projectName + "/";
				Graph graph = new Neo4jGraph(path + DB_SUFFIX + projectName);
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
