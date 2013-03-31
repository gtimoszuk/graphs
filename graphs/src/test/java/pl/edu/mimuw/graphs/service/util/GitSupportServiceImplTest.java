package pl.edu.mimuw.graphs.service.util;

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
import pl.edu.mimuw.graphs.services.util.GitSupportServiceImpl;
import pl.edu.mimuw.graphs.test.BaseTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:pl/edu/mimuw/graphs/conf/graphs-test-context.xml" })
public class GitSupportServiceImplTest extends BaseTest {

	public final static Logger log = LoggerFactory.getLogger(GitSupportServiceImplTest.class);

	public final static String JUNIT_REPO = "https://github.com/junit-team/junit.git";
	public final static String ZHIBERNATE_REPO = "https://github.com/gtimoszuk/zhibernate.git";
	public final static String INVALID_URL = "https://gitSthWornghub.com";

	@Before
	public void before() {
		super.before();
	}

	@After
	public void after() {
		super.after();
	}

	@Autowired
	public GitSupportServiceImpl gitSupportServiceImpl;

	@Test(timeout = 60000)
	public void canCheckoutExistingRepo() throws GraphsException {
		gitSupportServiceImpl.cloneRepository("zhibernate", ZHIBERNATE_REPO);
	}

	@Test(timeout = 60000, expected = GraphsException.class)
	public void cannotUseTheSameProjectName() throws GraphsException {
		gitSupportServiceImpl.cloneRepository("zhibernate", ZHIBERNATE_REPO);
		gitSupportServiceImpl.cloneRepository("zhibernate", JUNIT_REPO);
	}

	@Test(timeout = 60000, expected = GraphsException.class)
	public void throwsExceptionAtInvalidUrl() throws GraphsException {
		gitSupportServiceImpl.cloneRepository("zhibernate", INVALID_URL);

	}

	public Logger getLog() {
		return log;
	}
}
