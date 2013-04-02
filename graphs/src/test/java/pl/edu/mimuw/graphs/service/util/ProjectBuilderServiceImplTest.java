package pl.edu.mimuw.graphs.service.util;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import pl.edu.mimuw.graphs.exceptions.GraphsException;
import pl.edu.mimuw.graphs.exceptions.ProjectPropertiesService;
import pl.edu.mimuw.graphs.services.api.GitSupportService;
import pl.edu.mimuw.graphs.services.api.PathResolvingService;
import pl.edu.mimuw.graphs.services.api.ProjectBuilderService;
import pl.edu.mimuw.graphs.services.util.builders.ProjectBuildTool;
import pl.edu.mimuw.graphs.test.BaseTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:pl/edu/mimuw/graphs/conf/graphs-test-context.xml" })
public class ProjectBuilderServiceImplTest extends BaseTest {

	public final static Logger log = LoggerFactory.getLogger(ProjectBuilderServiceImplTest.class);

	@Autowired
	public GitSupportService gitSupportService;

	@Autowired
	public ProjectPropertiesService projectPropertiesService;

	@Autowired
	public PathResolvingService pathResolvingService;

	@Autowired
	@Qualifier("Graphs.ProjectBuilderService")
	public ProjectBuilderService projectBuilderService;

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
	public void contextTest() {

	}

	@Test(timeout = 60000)
	public void maven2Test() throws GraphsException {
		// prepare
		gitSupportService.cloneRepository(ZHIBERNATE, ZHIBERNATE_REPO);
		projectPropertiesService.setBuildTool(ZHIBERNATE, ProjectBuildTool.MVN);

		// test
		projectBuilderService.buildProject(ZHIBERNATE);

		// check
		String gitDir = pathResolvingService.getGitDir(ZHIBERNATE);
		String classpathPath = gitDir + ".classpath";
		File classpath = new File(classpathPath);
		assertTrue(classpath.exists());

	}

	@Test(timeout = 60000)
	public void maven3Test() throws GraphsException {
		// prepare
		gitSupportService.cloneRepository(JUNIT, JUNIT_REPO);
		projectPropertiesService.setBuildTool(JUNIT, ProjectBuildTool.MVN3);

		// test
		projectBuilderService.buildProject(JUNIT);

		// check
		String gitDir = pathResolvingService.getGitDir(JUNIT);
		String classpathPath = gitDir + ".classpath";
		File classpath = new File(classpathPath);
		assertTrue(classpath.exists());

	}

	@Override
	public Logger getLog() {
		return log;
	}
}
