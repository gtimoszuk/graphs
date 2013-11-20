package pl.edu.mimuw.graphs.night;

import static pl.edu.mimuw.graphs.services.projects.ProjectsConstants.DB_SUFFIX;
import static pl.edu.mimuw.graphs.services.projects.ProjectsConstants.RESULTS_SUFFIX;

import java.io.File;

import javax.annotation.Resource;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import pl.edu.mimuw.graphs.exporter.xls.MultipleRunsOnProjectSummaryXlsReportCreator;
import pl.edu.mimuw.graphs.statistics.GraphStatisticsSummaries;
import pl.edu.mimuw.graphs.statistics.PackageGraphStatisticsTools;
import pl.edu.mimuw.graphs.transformations.GraphShrinkerToPackagesOnlyGraph;

import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.impls.neo4j.Neo4jGraph;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { pl.edu.mimuw.graphs.BaseTestConfig.class })
public class NightTest2 {

	private static final Logger LOGGER = LoggerFactory.getLogger(NightTest2.class);

	@Resource(name = "inputDataPath")
	public String dataPath;

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
		File dataDir = new File(dataPath + DB_SUFFIX);
		File[] projectsToAnalyze = dataDir.listFiles();
		for (File f : projectsToAnalyze) {
			String absolutePath = f.getAbsolutePath();

			String[] pathSplitted = absolutePath.split("/");
			String projectName = pathSplitted[pathSplitted.length - 1];
			if (!projectName.startsWith(".")) {

				LOGGER.info("processing of project {} start", projectName);

				String resultsDirPath = dataPath + RESULTS_SUFFIX + projectName + "/";
				Graph graph = new Neo4jGraph(dataPath + DB_SUFFIX + projectName);
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
		File dataDir = new File(dataPath + DB_SUFFIX);
		File[] projectsToAnalyze = dataDir.listFiles();
		for (File f : projectsToAnalyze) {
			String absolutePath = f.getAbsolutePath();

			String[] pathSplitted = absolutePath.split("/");
			String projectName = pathSplitted[pathSplitted.length - 1];
			if (!projectName.startsWith(".")) {

				LOGGER.info("processing of project {} start", projectName);

				String resultsDirPath = dataPath + RESULTS_SUFFIX + projectName + "/";
				Graph graph = new Neo4jGraph(dataPath + DB_SUFFIX + projectName);
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
		File dataDir = new File(dataPath + "data/");
		LOGGER.info("data path: {}", dataPath);
		File[] projectsToAnalyze = dataDir.listFiles();
		for (File f : projectsToAnalyze) {
			String absolutePath = f.getAbsolutePath();

			String[] pathSplitted = absolutePath.split("/");
			String projectName = pathSplitted[pathSplitted.length - 1];
			if (!projectName.startsWith(".")) {
				LOGGER.info("working with projet: {}", projectName);

				PackageGraphStatisticsTools packageGraphStatisticsTools = new PackageGraphStatisticsTools();
				packageGraphStatisticsTools.countOneDirStatsIfProjectIsNew(dataPath, projectName, true);
			}
		}
	}

	public void createSummaryReport() {

		MultipleRunsOnProjectSummaryXlsReportCreator reportCreator = new MultipleRunsOnProjectSummaryXlsReportCreator();
		reportCreator.createReport(dataPath, "base1");
	}
}
