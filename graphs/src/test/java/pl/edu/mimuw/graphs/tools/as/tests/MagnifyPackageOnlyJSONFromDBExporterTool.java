package pl.edu.mimuw.graphs.tools.as.tests;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import pl.edu.mimuw.graphs.exporter.magnify.MagnifyPackageOnlyJSONFromDBExporter;

/**
 * 
 * 
 * @author gtimoszuk
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { pl.edu.mimuw.graphs.BaseTestConfig.class })
public class MagnifyPackageOnlyJSONFromDBExporterTool {

	private static final Logger LOGGER = LoggerFactory.getLogger(MagnifyPackageOnlyJSONFromDBExporterTool.class);

	@Autowired
	public MagnifyPackageOnlyJSONFromDBExporter exporter;

	@Resource(name = "realDataDir")
	public String path;

	@Test
	public void export() throws Exception {
		exporter.generateMagnifyJSONSForPackagageGraphs(path);
	}

}
