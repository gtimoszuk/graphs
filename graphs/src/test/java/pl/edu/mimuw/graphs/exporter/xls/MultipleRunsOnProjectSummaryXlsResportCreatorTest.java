package pl.edu.mimuw.graphs.exporter.xls;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//FIXME:GTI split to test and tool 
public class MultipleRunsOnProjectSummaryXlsResportCreatorTest {

	static final Logger LOGGER = LoggerFactory.getLogger(MultipleRunsOnProjectSummaryXlsResportCreatorTest.class);

	@Ignore
	@Test
	public void baseTest() {

		MultipleRunsOnProjectSummaryXlsReportCreator reportCreator = new MultipleRunsOnProjectSummaryXlsReportCreator();
		reportCreator.createReport("/home/ballo0/GTI/PHD/iter1/", "base1");
	}

	@Ignore
	@Test
	public void fullTest() {
		MultipleRunsOnProjectSummaryXlsReportCreator reportCreator = new MultipleRunsOnProjectSummaryXlsReportCreator();
		reportCreator.createReport("/home/ballo0/GTI/PHD/callCountExp-allData-finished/", "callCountExp");

		reportCreator = new MultipleRunsOnProjectSummaryXlsReportCreator();
		reportCreator.createReport("/home/ballo0/GTI/PHD/callCountOpt-allData-finished/", "callCount");

		reportCreator = new MultipleRunsOnProjectSummaryXlsReportCreator();
		reportCreator.createReport("/home/ballo0/GTI/PHD/couplingExp-allData-finished/", "couplingExp");

		reportCreator = new MultipleRunsOnProjectSummaryXlsReportCreator();
		reportCreator.createReport("/home/ballo0/GTI/PHD/couplingOpt-allData-finished/", "coupling");
	}

}
