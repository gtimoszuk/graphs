package pl.edu.mimuw.graphs.exporter.xls;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MultipleRunsOnProjectSummaryXlsResportCreatorTest {

	static final Logger LOGGER = LoggerFactory.getLogger(MultipleRunsOnProjectSummaryXlsResportCreatorTest.class);

	@Test
	public void baseTest() {

		MultipleRunsOnProjectSummaryXlsReportCreator reportCreator = new MultipleRunsOnProjectSummaryXlsReportCreator();
		reportCreator.createReport("/home/ballo0/GTI/PHD/iter1/", "base1");
	}

}
