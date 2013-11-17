package pl.edu.mimuw.graphs.night;

import static pl.edu.mimuw.graphs.services.projects.ProjectsConstants.DB_SUFFIX;
import static pl.edu.mimuw.graphs.services.projects.ProjectsConstants.RESULTS_SUFFIX;

import java.io.File;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.edu.mimuw.graphs.exporter.xls.MultipleRunsOnProjectSummaryXlsReportCreator;
import pl.edu.mimuw.graphs.statistics.GraphStatisticsSummaries;
import pl.edu.mimuw.graphs.statistics.PackageGraphStatisticsTools;
import pl.edu.mimuw.graphs.transformations.GraphShrinkerToPackagesOnlyGraph;

import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.impls.neo4j.Neo4jGraph;

@Ignore
public class NightTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(NightTest.class);

	private final static String DATA_PATH = "/home/ballo0/GTI/PHD/iter1/";

	private final GraphShrinkerToPackagesOnlyGraph graphShrinker = new GraphShrinkerToPackagesOnlyGraph();

	private final PackageGraphStatisticsTools graphStatisticsTools = new PackageGraphStatisticsTools();

	@Test
	public void nightTest() throws Exception {
		allDataRunButAddOnlyNewProjects();
		createSummaryReport();
		generateMagnifyJSONSForPackagageGraphs();
	}

	@Ignore
	@Test
	public void exportDataAndGenerateMagnifyJSONSForPackagageGraphs() throws Exception {
		File dataDir = new File(DATA_PATH + DB_SUFFIX);
		File[] projectsToAnalyze = dataDir.listFiles();
		for (File f : projectsToAnalyze) {
			String absolutePath = f.getAbsolutePath();

			String[] pathSplitted = absolutePath.split("/");
			String projectName = pathSplitted[pathSplitted.length - 1];
			if (!projectName.startsWith(".")) {

				LOGGER.info("processing of project {} start", projectName);

				String resultsDirPath = DATA_PATH + RESULTS_SUFFIX + projectName + "/";
				Graph graph = new Neo4jGraph(DATA_PATH + DB_SUFFIX + projectName);
				graphStatisticsTools.exporData(projectName, resultsDirPath, graph, "",
						graphStatisticsTools.getGraphStatisticsOndifferentLevel(graph));
				Graph newGraph = graphShrinker.shrinkGraph(graph);
				graph.shutdown();

				GraphStatisticsSummaries graphStatisticsSummaries = graphStatisticsTools.gatherStatistics(newGraph);
				graphShrinker.joinMultipleSameEgdesToOneWithCount(newGraph);
				graphStatisticsTools.exporData(projectName, resultsDirPath, newGraph, "packages",
						graphStatisticsSummaries);
			}
		}
	}

	public void generateMagnifyJSONSForPackagageGraphs() throws Exception {
		File dataDir = new File(DATA_PATH + DB_SUFFIX);
		File[] projectsToAnalyze = dataDir.listFiles();
		for (File f : projectsToAnalyze) {
			String absolutePath = f.getAbsolutePath();

			String[] pathSplitted = absolutePath.split("/");
			String projectName = pathSplitted[pathSplitted.length - 1];
			if (!projectName.startsWith(".")) {

				LOGGER.info("processing of project {} start", projectName);

				String resultsDirPath = DATA_PATH + RESULTS_SUFFIX + projectName + "/";
				Graph graph = new Neo4jGraph(DATA_PATH + DB_SUFFIX + projectName);
				Graph newGraph = graphShrinker.shrinkGraph(graph);
				graph.shutdown();

				GraphStatisticsSummaries graphStatisticsSummaries = graphStatisticsTools.gatherStatistics(newGraph);
				graphShrinker.joinMultipleSameEgdesToOneWithCount(newGraph);
				graphStatisticsTools.exporData(projectName, resultsDirPath, newGraph, "packages",
						graphStatisticsSummaries);
			}
		}
	}

	public void allDataRunButAddOnlyNewProjects() {
		File dataDir = new File(DATA_PATH + "data/");
		File[] projectsToAnalyze = dataDir.listFiles();
		for (File f : projectsToAnalyze) {
			String absolutePath = f.getAbsolutePath();

			String[] pathSplitted = absolutePath.split("/");
			String projectName = pathSplitted[pathSplitted.length - 1];
			if (!projectName.startsWith(".")) {
				LOGGER.info("working with projet: {}", projectName);

				PackageGraphStatisticsTools packageGraphStatisticsTools = new PackageGraphStatisticsTools();
				packageGraphStatisticsTools.countOneDirStatsIfProjectIsNew(DATA_PATH, projectName, true);
			}
		}
	}

	public void createSummaryReport() {

		MultipleRunsOnProjectSummaryXlsReportCreator reportCreator = new MultipleRunsOnProjectSummaryXlsReportCreator();
		reportCreator.createReport(DATA_PATH, "base1");
	}
}
