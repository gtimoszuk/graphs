package pl.edu.mimuw.graphs.statistics;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PackageGraphStatisticsToolsTest {

	static final Logger LOGGER = LoggerFactory.getLogger(PackageGraphStatisticsToolsTest.class);

	@Test
	public void junitSimpleTest() {
		PackageGraphStatisticsTools packageGraphStatisticsTools = new PackageGraphStatisticsTools();
		packageGraphStatisticsTools.countOneDirStatsForProject(
				"/home/ballo0/GTI/projects/testDataCreatorResults/junit/", "/tmp/");
	}

}
