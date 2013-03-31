package pl.edu.mimuw.graphs.service.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

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
import pl.edu.mimuw.graphs.exceptions.ProjectPropertiesService;
import pl.edu.mimuw.graphs.services.api.PathResolvingService;
import pl.edu.mimuw.graphs.services.util.builders.ProjectBuildTool;
import pl.edu.mimuw.graphs.test.BaseTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:pl/edu/mimuw/graphs/conf/graphs-test-context.xml" })
public class ProjectPropertiesServiceImplTest extends BaseTest {

	public final static Logger log = LoggerFactory.getLogger(ProjectPropertiesServiceImplTest.class);

	@Autowired
	public ProjectPropertiesService projectPropertiesService;

	@Autowired
	public PathResolvingService pathResolvingService;

	// private String projectName;

	@Override
	public Logger getLog() {
		return log;
	}

	@Before
	public void before() {
		super.before();

	}

	@After
	public void after() {
		super.after();
	}

	@Test
	public void shouldSetProperty() throws GraphsException {
		// prepare
		String projectName = this.getClass().toString();
		pathResolvingService.getPropertiesFile(projectName);
		String propertyName = "key";
		String propertyValue = "value";

		// test
		projectPropertiesService.setProperty(projectName, propertyName, propertyValue);

		// check
		assertEquals(propertyValue, projectPropertiesService.getProperty(projectName, propertyName));
	}

	@Test
	public void shouldReturnNullIfPropertyIsAbsent() throws GraphsException {
		// prepare
		String projectName = this.getClass().toString();
		pathResolvingService.getPropertiesFile(projectName);
		String propertyName = "key";

		// test && check
		assertNull(projectPropertiesService.getProperty(projectName, propertyName));
	}

	@Test
	public void shouldBeAbleToOverrideProperty() throws GraphsException {
		// prepare
		String projectName = this.getClass().toString();
		pathResolvingService.getPropertiesFile(projectName);
		String propertyName = "key";
		String propertyValue = "value";
		projectPropertiesService.setProperty(projectName, propertyName, propertyValue);
		assertEquals(propertyValue, projectPropertiesService.getProperty(projectName, propertyName));

		// test
		String propertyValue2 = "newValue";
		projectPropertiesService.setProperty(projectName, propertyName, propertyValue2);

		// check
		assertEquals(propertyValue2, projectPropertiesService.getProperty(projectName, propertyName));
	}

	@Test(expected = GraphsException.class)
	public void shouldThrowExcpWhenNoBuildTool() throws GraphsException {
		// prepare
		String projectName = this.getClass().toString();
		pathResolvingService.getPropertiesFile(projectName);

		// test && check
		projectPropertiesService.getProjectBuildTool(projectName);
	}

	@Test
	public void shouldSetAndGetBuildTool() throws GraphsException {
		// prepare
		String projectName = this.getClass().toString();
		pathResolvingService.getPropertiesFile(projectName);

		// test
		projectPropertiesService.setBuildTool(projectName, ProjectBuildTool.MVN3);
		ProjectBuildTool projectBuildTool = projectPropertiesService.getProjectBuildTool(projectName);

		// check
		assertEquals(ProjectBuildTool.MVN3, projectBuildTool);
	}

}
