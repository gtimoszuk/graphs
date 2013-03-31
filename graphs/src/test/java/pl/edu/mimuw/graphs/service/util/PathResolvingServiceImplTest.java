package pl.edu.mimuw.graphs.service.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import pl.edu.mimuw.graphs.exceptions.GraphsException;
import pl.edu.mimuw.graphs.exceptions.InvalidDataExcetion;
import pl.edu.mimuw.graphs.services.api.PathResolvingService;
import pl.edu.mimuw.graphs.test.BaseTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:pl/edu/mimuw/graphs/conf/graphs-test-context.xml" })
public class PathResolvingServiceImplTest extends BaseTest {

	public final static Logger log = LoggerFactory.getLogger(PathResolvingServiceImplTest.class);

	@Autowired
	public PathResolvingService pathResolvingService;

	@Override
	public Logger getLog() {
		return log;
	}

	@Override
	@Before
	public void before() {
		super.before();
	}

	@Override
	@After
	public void after() {
		super.after();
	}

	@Test
	public void shouldCreateProjectDir() throws InvalidDataExcetion {
		String path = pathResolvingService.getProjectDir(this.getClass().toString());
		File dir = new File(path);
		assertTrue(dir.exists());
		assertTrue(dir.isDirectory());
	}

	@Test
	public void sholdCreateDbDir() throws InvalidDataExcetion {
		String path = pathResolvingService.getDbDir(this.getClass().toString());
		File dir = new File(path);
		assertTrue(dir.exists());
		assertTrue(dir.isDirectory());
	}

	@Test
	public void sholdCreateGitDir() throws InvalidDataExcetion {
		String path = pathResolvingService.getGitDir(this.getClass().toString());
		File dir = new File(path);
		assertTrue(dir.exists());
		assertTrue(dir.isDirectory());
	}

	@Test
	public void sholdCreatePropsFile() throws GraphsException {
		String path = pathResolvingService.getPropertiesFile(this.getClass().toString());
		File dir = new File(path);
		assertTrue(dir.exists());
		assertFalse(dir.isDirectory());
	}

}
