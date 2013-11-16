package pl.edu.mimuw.graphs.statistics;

import java.io.File;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { pl.edu.mimuw.graphs.BaseTestConfig.class })
public class PackageGraphStatisticsToolsTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(PackageGraphStatisticsToolsTest.class);

	@Ignore("passes always")
	@Test
	public void junitOriginalest() {
		PackageGraphStatisticsTools packageGraphStatisticsTools = new PackageGraphStatisticsTools();
		packageGraphStatisticsTools.countOneDirStatsForProject(
				"/home/ballo0/GTI/projects/testDataCreatorResults/junit/", "/tmp/");
	}

	@Ignore
	@Test
	public void allDataRun() {
		File dataDir = new File("/home/ballo0/GTI/PHD/iter1/data/");
		File[] projectsToAnalyze = dataDir.listFiles();
		for (File f : projectsToAnalyze) {
			String absolutePath = f.getAbsolutePath();

			String[] pathSplitted = absolutePath.split("/");
			String projectNameSuffix = pathSplitted[pathSplitted.length - 1];
			LOGGER.info("working with projet: {}", projectNameSuffix);
			String resultDirPath = "/home/ballo0/GTI/PHD/iter1/";
			File resultDir = new File(resultDirPath);
			resultDir.mkdirs();

			if (!absolutePath.endsWith("/")) {
				absolutePath = absolutePath + "/";
			}

			PackageGraphStatisticsTools packageGraphStatisticsTools = new PackageGraphStatisticsTools();
			packageGraphStatisticsTools.countOneDirStatsForProject(absolutePath, resultDirPath);

		}
	}

	@Ignore
	@Test
	public void allDataRunImprovedVersion() {
		String dataPath = "/home/ballo0/GTI/PHD/iter1/";
		File dataDir = new File(dataPath + "data/");
		File[] projectsToAnalyze = dataDir.listFiles();
		for (File f : projectsToAnalyze) {
			String absolutePath = f.getAbsolutePath();

			String[] pathSplitted = absolutePath.split("/");
			String projectName = pathSplitted[pathSplitted.length - 1];
			if (!projectName.startsWith(".")) {
				LOGGER.info("working with projet: {}", projectName);

				PackageGraphStatisticsTools packageGraphStatisticsTools = new PackageGraphStatisticsTools();
				packageGraphStatisticsTools.countOneDirStatsForProject(dataPath, projectName, true);
			}
		}
	}

	@Test
	public void allDataRunButAddOnlyNewProjects() {
		String dataPath = "/home/ballo0/GTI/PHD/iter1/";
		File dataDir = new File(dataPath + "data/");
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

}
