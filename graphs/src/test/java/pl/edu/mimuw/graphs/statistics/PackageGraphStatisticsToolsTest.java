package pl.edu.mimuw.graphs.statistics;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PackageGraphStatisticsToolsTest {

	static final Logger LOGGER = LoggerFactory.getLogger(PackageGraphStatisticsToolsTest.class);

	@Ignore
	@Test
	public void junitOriginalest() {
		PackageGraphStatisticsTools packageGraphStatisticsTools = new PackageGraphStatisticsTools();
		packageGraphStatisticsTools.countOneDirStatsForProject(
				"/home/ballo0/GTI/projects/testDataCreatorResults/junit/", "/tmp/");
	}

	@Test
	public void junitSimpleTest() {
		PackageGraphStatisticsTools packageGraphStatisticsTools = new PackageGraphStatisticsTools();
		packageGraphStatisticsTools
				.countOneDirStatsForProject("/home/ballo0/GTI/package_graph/results/junit/", "/tmp/");
	}

}
