package pl.edu.mimuw.graphs.statistics;

import static pl.edu.mimuw.graphs.GraphRelationshipType.CALLS;
import static pl.edu.mimuw.graphs.GraphRelationshipType.CONTAINS;
import static pl.edu.mimuw.graphs.metrics.MetricName.PAGE_RANK;
import static pl.edu.mimuw.graphs.services.projects.ProjectsConstants.DATA_SUFFIX;
import static pl.edu.mimuw.graphs.services.projects.ProjectsConstants.DB_SUFFIX;
import static pl.edu.mimuw.graphs.services.projects.ProjectsConstants.RESULTS_SUFFIX;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.edu.mimuw.graphs.exporter.magnify.MagnifyExporter;
import pl.edu.mimuw.graphs.exporter.xls.GraphDataAndStatsToXlsExporter;
import pl.edu.mimuw.graphs.importer.packages.graph.GraphImporter;
import pl.edu.mimuw.graphs.importer.packages.graph.PackageGraphImporter;
import pl.edu.mimuw.graphs.metrics.AfferentCouplingCalculator;
import pl.edu.mimuw.graphs.metrics.CallsFromOtherPackagesCalculator;
import pl.edu.mimuw.graphs.metrics.CallsToOtherPackagesCalculator;
import pl.edu.mimuw.graphs.metrics.ClassesPerPackageCalculator;
import pl.edu.mimuw.graphs.metrics.EfferentCouplingCalculator;
import pl.edu.mimuw.graphs.metrics.MetricName;
import pl.edu.mimuw.graphs.metrics.PageRankCalculator;
import pl.edu.mimuw.graphs.transformations.PackageGraphExpander;

import com.tinkerpop.blueprints.Graph;

public class PackageGraphStatisticsTools {

	private static final String CLASSES_ONLY = "Classes only";
	private static final String PACKAGES_ONLY = "Packages only";
	private static final String WHOLE_GRAPH = "Whole graph";

	static final Logger LOGGER = LoggerFactory.getLogger(PackageGraphStatisticsTools.class);

	private final GraphDataAndStatsToXlsExporter graphDataAndStatsToXlsExporter = new GraphDataAndStatsToXlsExporter();

	// @Autowired
	// @Qualifier("graphImporter")
	public GraphImporter graphImporter = new PackageGraphImporter();

	public void countOneDirStatsIfProjectIsNew(String workingDir, String projectName, boolean ifToSaveDB) {
		String dataDirPath = workingDir + DATA_SUFFIX + projectName + "/";
		String dbDirPath = workingDir + DB_SUFFIX + projectName + "/";
		String resultsDirPath = workingDir + RESULTS_SUFFIX + projectName + "/";
		File resultDir = new File(resultsDirPath);
		if (!resultDir.exists()) {
			resultDir.mkdirs();
		}

		LOGGER.info("Work starting with {}", projectName);

		if (!ifToSaveDB) {
			Graph graph = graphImporter.importGraph(dataDirPath);
			countData(projectName, resultsDirPath, graph);
		} else {
			File dbDir = new File(dbDirPath);
			if (!dbDir.exists()) {
				Graph graph = graphImporter.importGraph(dataDirPath, dbDirPath);
				countData(projectName, resultsDirPath, graph);
			} else {
				LOGGER.info("Project already processed. Exiiting");
			}
		}
	}

	public void countOneDirStatsForProject(String workingDir, String projectName, boolean ifToSaveDB) {
		// preparing paths and dirs
		String dataDirPath = workingDir + DATA_SUFFIX + projectName + "/";
		String dbDirPath = workingDir + DB_SUFFIX + projectName + "/";
		String resultsDirPath = workingDir + RESULTS_SUFFIX + projectName + "/";
		File resultDir = new File(resultsDirPath);
		if (!resultDir.exists()) {
			resultDir.mkdirs();
		}
		LOGGER.info("Work starting with {}", projectName);

		// setting up importer and cleaning dirs
		Graph graph = null;
		if (!ifToSaveDB) {
			graph = graphImporter.importGraph(dataDirPath);
		} else {
			File dbDir = new File(dbDirPath);
			try {
				FileUtils.deleteDirectory(dbDir);
			} catch (IOException e) {
				e.printStackTrace();
			}
			graph = graphImporter.importGraph(dataDirPath, dbDirPath);
		}
		countData(projectName, resultsDirPath, graph);

	}

	public void countData(String projectName, String resultsDirPath, Graph graph) {
		LOGGER.info("Import started");

		try {
			coundData(projectName, resultsDirPath, graph);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		graph.shutdown();

	}

	public void coundData(String projectName, String resultsDirPath, Graph graph) throws Exception {
		coundData(projectName, resultsDirPath, graph, "");
	}

	public void coundData(String projectName, String resultsDirPath, Graph graph, String infix) throws Exception {
		GraphStatisticsSummaries graphStatisticsSummaries = gatherStatistics(graph);

		exporData(projectName, resultsDirPath, graph, infix, graphStatisticsSummaries);

	}

	public GraphStatisticsSummaries gatherStatistics(Graph graph) {
		PackageGraphExpander packageGraphExpander = new PackageGraphExpander();
		LOGGER.info("Graph expansion started");
		packageGraphExpander.expandGraphInPlace(graph);

		PageRankCalculator pageRankCalculator = new PageRankCalculator();
		LOGGER.info("Page rank callculation started");
		pageRankCalculator.calculate(graph);

		AfferentCouplingCalculator afferentCouplingCalculator = new AfferentCouplingCalculator();
		LOGGER.info("Afferent coupling callculation started");
		afferentCouplingCalculator.calculate(graph);

		EfferentCouplingCalculator efferentCouplingCalculator = new EfferentCouplingCalculator();
		LOGGER.info("Efferent coupling callculation started");
		efferentCouplingCalculator.calculate(graph);

		CallsFromOtherPackagesCalculator callsFromOtherPackagesCalculator = new CallsFromOtherPackagesCalculator();
		LOGGER.info("Calls from other packages callculation started");
		callsFromOtherPackagesCalculator.calculate(graph);

		CallsToOtherPackagesCalculator callsToOtherPackagesCalculator = new CallsToOtherPackagesCalculator();
		LOGGER.info("Calls to other packages callculation started");
		callsToOtherPackagesCalculator.calculate(graph);

		ClassesPerPackageCalculator classesPerPackageCalculator = new ClassesPerPackageCalculator();
		LOGGER.info("Classes per package callculation started");
		classesPerPackageCalculator.calculate(graph);

		return getGraphStatisticsOndifferentLevel(graph);
	}

	public GraphStatisticsSummaries getGraphStatisticsOndifferentLevel(Graph graph) {
		GraphStatisticsSummaries graphStatisticsSummaries = new GraphStatisticsSummaries();

		GraphStatistics graphStatistics = new GraphStatistics();
		LOGGER.info("Whole graph statistics");
		graphStatisticsSummaries.put(WHOLE_GRAPH, graphStatistics.getStatisticsForGraph(graph));
		LOGGER.info("Packages only statistics");
		graphStatisticsSummaries.put(PACKAGES_ONLY, graphStatistics.getStatisticsForPackages(graph));
		LOGGER.info("Classes only statistics");
		graphStatisticsSummaries.put(CLASSES_ONLY, graphStatistics.getStatisticsForClasses(graph));
		return graphStatisticsSummaries;
	}

	public void exporData(String projectName, String resultsDirPath, Graph graph, String infix,
			GraphStatisticsSummaries graphStatisticsSummaries) throws Exception {
		MagnifyExporter magnifyExporter = new MagnifyExporter();
		LOGGER.info("Magnify export started");

		for (MetricName metricName : MetricName.values()) {
			magnifyExporter.export(resultsDirPath + projectName + "-" + infix + metricName.name() + "-" + ".json",
					graph, metricName.name(), PAGE_RANK.name(), CONTAINS.name(), CALLS.name());
		}

		LOGGER.info("XLS export started");
		graphDataAndStatsToXlsExporter.singleGraphExporter(resultsDirPath + projectName + infix + ".xls", graph,
				graphStatisticsSummaries);
	}

	public void countOneDirStatsForProject(String projectPath, String workingDir) {
		String[] pathSplitted = projectPath.split("/");
		String projectName = pathSplitted[pathSplitted.length - 1];

		String resultsDirPath = workingDir + RESULTS_SUFFIX + projectName + "/";

		LOGGER.info("Work starting with {}", projectName);

		Graph graph = graphImporter.importGraph(projectPath);
		countData(projectName, resultsDirPath, graph);

	}

}
