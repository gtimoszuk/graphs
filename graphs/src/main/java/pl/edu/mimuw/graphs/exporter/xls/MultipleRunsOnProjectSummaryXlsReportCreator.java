package pl.edu.mimuw.graphs.exporter.xls;

import static pl.edu.mimuw.graphs.api.GraphVertexProperies.TYPE;
import static pl.edu.mimuw.graphs.api.GraphVertexTypes.CLASS;
import static pl.edu.mimuw.graphs.api.GraphVertexTypes.PACKAGE;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.javatuples.Octet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.edu.mimuw.graphs.api.GraphRelationshipType;
import pl.edu.mimuw.graphs.api.GraphStatististicsName;
import pl.edu.mimuw.graphs.api.MetricName;
import pl.edu.mimuw.graphs.statistics.GraphStatistics;
import pl.edu.mimuw.graphs.statistics.GraphStatisticsSummaries;
import pl.edu.mimuw.graphs.statistics.PackageGraphStatisticsTools;

import com.google.common.base.Joiner;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.neo4j.Neo4jGraph;

public class MultipleRunsOnProjectSummaryXlsReportCreator {

	private final static String DATA = "data/";
	private final static String DB = "db/";
	private final static String RESULTS = "results/";

	private static final String CLASSES_ONLY = "Classes only";
	private static final String PACKAGES_ONLY = "Packages only";
	private static final String WHOLE_GRAPH = "Whole graph";

	private final Map<String, List<String>> projectsSplit = new HashMap<String, List<String>>();

	private final Map<String, Octet<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>> graphStatsMap = new HashMap<String, Octet<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>>();

	private final Map<String, GraphStatisticsSummaries> summariesMap = new HashMap<String, GraphStatisticsSummaries>();

	private final GraphStatistics graphStatistics = new GraphStatistics();

	static final Logger LOGGER = LoggerFactory.getLogger(PackageGraphStatisticsTools.class);

	/**
	 * Generates report for multiple runs. Assumptions: results are stored in a
	 * way baseName-runSpecs and there is always base data called baseName
	 * 
	 * @param path
	 * @param reportName
	 */
	public void createReport(String path, String reportName) {
		LOGGER.info("working on report:");
		File dataDir = new File(path + DB);
		File[] projectsToAnalyze = dataDir.listFiles();
		for (File f : projectsToAnalyze) {
			String absolutePath = f.getAbsolutePath();

			String[] pathSplitted = absolutePath.split("/");
			String projectName = pathSplitted[pathSplitted.length - 1];
			if (!projectName.startsWith(".")) {
				String[] nameSplitted = projectName.split("-");
				getStatisticsForSingleProject(projectName, path);
				if (projectsSplit.containsKey(nameSplitted[0])) {
					projectsSplit.get(nameSplitted[0]).add(projectName);
				} else {
					List<String> projects = new ArrayList<String>();
					projects.add(projectName);
					projectsSplit.put(nameSplitted[0], projects);
				}
			}
		}

		for (String familyName : projectsSplit.keySet()) {
			List<String> projects = projectsSplit.get(familyName);
			Collections.sort(projects);
		}

		for (String projectFamily : projectsSplit.keySet()) {
			LOGGER.info("working with projet family: {}", projectFamily);
			generateXlsReport(path, reportName, projectFamily);
		}

	}

	private void generateXlsReport(String path, String reportName, String projectFamily) {

		String outPath = path + RESULTS + reportName + "-" + projectFamily + ".xls";
		try {
			WritableWorkbook workbook = Workbook.createWorkbook(new File(outPath));
			WritableSheet genStatsSheet = workbook.createSheet("Stats", 0);
			generateGenerateGeneralStats(genStatsSheet, projectFamily);
			int i = 1;
			for (MetricName metricName : MetricName.values()) {
				WritableSheet sheet = workbook.createSheet(metricName.name(), i++);
				generateSingleMetricSummary(sheet, metricName, projectFamily);
			}
			workbook.write();
			workbook.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RowsExceededException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void generateGenerateGeneralStats(WritableSheet genStatsSheet, String projectFamily)
			throws RowsExceededException, WriteException {
		writeGeneralStatsNames(genStatsSheet);
		int column = 1;
		for (String projectName : projectsSplit.get(projectFamily)) {
			Octet<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> data = graphStatsMap
					.get(projectName);
			Label label = new Label(column, 0, createShortLabel(projectName));
			genStatsSheet.addCell(label);
			jxl.write.Number number1 = new Number(column, 1, data.getValue0());
			genStatsSheet.addCell(number1);
			jxl.write.Number number2 = new Number(column, 2, data.getValue1());
			genStatsSheet.addCell(number2);
			jxl.write.Number number3 = new Number(column, 3, data.getValue2());
			genStatsSheet.addCell(number3);
			jxl.write.Number number4 = new Number(column, 4, data.getValue3());
			genStatsSheet.addCell(number4);
			jxl.write.Number number5 = new Number(column, 5, data.getValue4());
			genStatsSheet.addCell(number5);
			jxl.write.Number number6 = new Number(column, 6, data.getValue5());
			genStatsSheet.addCell(number6);
			jxl.write.Number number7 = new Number(column, 7, data.getValue6());
			genStatsSheet.addCell(number7);
			jxl.write.Number number8 = new Number(column, 8, data.getValue7());
			genStatsSheet.addCell(number8);

			column++;
		}

	}

	private void writeGeneralStatsNames(WritableSheet genStatsSheet) throws WriteException, RowsExceededException {
		Label label1 = new Label(0, 1, "verticesCount");
		genStatsSheet.addCell(label1);
		Label label2 = new Label(0, 2, "classesCount");
		genStatsSheet.addCell(label2);
		Label label3 = new Label(0, 3, "packagesCount");
		genStatsSheet.addCell(label3);
		Label label4 = new Label(0, 4, "otherTypeVerticesCount");
		genStatsSheet.addCell(label4);
		Label label5 = new Label(0, 5, "edgesCount");
		genStatsSheet.addCell(label5);
		Label label6 = new Label(0, 6, "containsEgdesCount");
		genStatsSheet.addCell(label6);
		Label label7 = new Label(0, 7, "callEdgesCount");
		genStatsSheet.addCell(label7);
		Label label8 = new Label(0, 8, "otherTypeEdgesCount");
		genStatsSheet.addCell(label8);
	}

	private void generateSingleMetricSummary(WritableSheet sheet, MetricName metricName, String projectFamily)
			throws RowsExceededException, WriteException {
		int row = 0;
		for (String subsetName : summariesMap.get(projectsSplit.get(projectFamily).get(0)).keySet()) {
			Label label = new Label(0, row, subsetName);
			sheet.addCell(label);
			row++;
			int column = 0;
			generateLeftLegend(row, sheet);
			column++;

			Map<String, String> newFamilyNamesMapping = reorderProjectFamilinyNames(projectsSplit.get(projectFamily));
			List<String> newNamesList = new ArrayList<String>(newFamilyNamesMapping.keySet());
			Collections.sort(newNamesList);

			for (String newProjectName : newNamesList) {
				String projectName = newFamilyNamesMapping.get(newProjectName);

				// tutaj zmienić nazwy projektów
				Label projectNameLabel = new Label(column, row, createShortLabel(projectName));
				sheet.addCell(projectNameLabel);

				Map<String, Double> stats = summariesMap.get(projectName).get(subsetName).get(metricName);
				int rowInc = 1;
				for (GraphStatististicsName graphStatististicsName : GraphStatististicsName.values()) {
					jxl.write.Number number = new Number(column, row + rowInc, stats.get(graphStatististicsName.name()));
					sheet.addCell(number);
					rowInc++;

				}
				column++;
			}

			row += GraphStatististicsName.values().length;
			row += 3;

		}

	}

	private String createShortLabel(String projectName) {
		String[] firstSplit = projectName.split("-");
		if (firstSplit.length < 2) {
			return projectName;
		} else {
			String[] nameSplitted = firstSplit[1].split("_");
			String shortName = shorNameForOps(nameSplitted[0]) + "_" + nameSplitted[2];
			return shortName;
		}

	}

	private String shorNameForOps(String string) {
		if ("move".equals(string)) {
			return "m";
		} else if ("swap".equals(string)) {
			return "s";
		} else {
			return "ms";
		}
	}

	private Map<String, String> reorderProjectFamilinyNames(List<String> list) {
		Map<String, String> result = new HashMap<String, String>();
		for (String projectname : list) {
			String[] splittedName = projectname.split("_");
			if (splittedName.length > 1) {
				Integer i = Integer.parseInt(splittedName[splittedName.length - 1]);
				if (i < 10) {
					splittedName[splittedName.length - 1] = "0" + splittedName[splittedName.length - 1];
				}
				Joiner joiner = Joiner.on("_");
				String newName = joiner.join(splittedName);
				result.put(newName, projectname);
			} else {
				result.put(projectname, projectname);
			}
		}

		return result;
	}

	private void generateLeftLegend(int row, WritableSheet sheet) throws RowsExceededException, WriteException {
		int k = row + 1;
		for (GraphStatististicsName graphStatististicsName : GraphStatististicsName.values()) {
			Label label = new Label(0, k, graphStatististicsName.name());
			k++;
			sheet.addCell(label);
		}

	}

	private void getStatisticsForSingleProject(String projectName, String path) {
		Graph graph = new Neo4jGraph(path + DB + projectName);
		GraphStatisticsSummaries graphStatisticsSummaries = new GraphStatisticsSummaries();
		LOGGER.info("Whole graph statistics");
		graphStatisticsSummaries.put(WHOLE_GRAPH, graphStatistics.getStatisticsForGraph(graph));
		LOGGER.info("Packages only statistics");
		graphStatisticsSummaries.put(PACKAGES_ONLY, graphStatistics.getStatisticsForPackages(graph));
		LOGGER.info("Classes only statistics");
		graphStatisticsSummaries.put(CLASSES_ONLY, graphStatistics.getStatisticsForClasses(graph));
		summariesMap.put(projectName, graphStatisticsSummaries);
		graphStatsMap.put(projectName, graphStats(graph));

		graph.shutdown();
	}

	public Octet<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> graphStats(Graph graph) {
		int verticesCount = 0;
		int classesCount = 0;
		int packagesCount = 0;
		int otherTypeVerticesCount = 0;
		int edgesCount = 0;
		int callEdgesCount = 0;
		int containsEgdesCount = 0;
		int otherTypeEdgesCount = 0;
		for (Vertex v : graph.getVertices()) {
			verticesCount++;
			if (CLASS.equals(v.getProperty(TYPE))) {
				classesCount++;
			} else if (PACKAGE.equals(v.getProperty(TYPE))) {
				packagesCount++;

			} else {
				otherTypeVerticesCount++;
			}
		}
		for (Edge e : graph.getEdges()) {
			edgesCount++;
			if (GraphRelationshipType.CALLS.name().equals(e.getLabel())) {
				callEdgesCount++;
			} else if (GraphRelationshipType.CONTAINS.name().equals(e.getLabel())) {
				containsEgdesCount++;
			} else {
				otherTypeEdgesCount++;
			}
		}

		Octet<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> stats = new Octet<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>(
				verticesCount, classesCount, packagesCount, otherTypeVerticesCount, edgesCount, containsEgdesCount,
				callEdgesCount, otherTypeEdgesCount);
		return stats;
	}
}
