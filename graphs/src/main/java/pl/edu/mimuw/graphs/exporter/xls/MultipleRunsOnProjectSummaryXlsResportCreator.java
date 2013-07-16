package pl.edu.mimuw.graphs.exporter.xls;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.edu.mimuw.graphs.statistics.PackageGraphStatisticsTools;

public class MultipleRunsOnProjectSummaryXlsResportCreator {

	private final static String DATA = "data/";
	private final static String DB = "db/";
	private final static String RESULTS = "results/";

	static final Logger LOGGER = LoggerFactory.getLogger(PackageGraphStatisticsTools.class);

	public void createReport(String path) {
		File dataDir = new File(path + "data/");
		File[] projectsToAnalyze = dataDir.listFiles();
		for (File f : projectsToAnalyze) {
			String absolutePath = f.getAbsolutePath();

			String[] pathSplitted = absolutePath.split("/");
			String projectName = pathSplitted[pathSplitted.length - 1];
			if (!projectName.startsWith(".")) {
				LOGGER.info("working with projet: {}", projectName);

			}

		}

	}

}
