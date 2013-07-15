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
import pl.edu.mimuw.graphs.services.api.GitSupportService;
import pl.edu.mimuw.graphs.test.BaseTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:pl/edu/mimuw/graphs/conf/graphs-test-context.xml" })
public class GitSupportServiceImplTest extends BaseTest {

	public final static Logger log = LoggerFactory.getLogger(GitSupportServiceImplTest.class);

	@Autowired
	public GitSupportService gitSupportService;

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

	@Test(timeout = 60000)
	public void canCheckoutExistingRepo() throws GraphsException {
		gitSupportService.cloneRepository(ZHIBERNATE + randomSuffix(), ZHIBERNATE_REPO);
	}

	@Test(timeout = 60000, expected = GraphsException.class)
	public void cannotUseTheSameProjectName() throws GraphsException {
		gitSupportService.cloneRepository(ZHIBERNATE, ZHIBERNATE_REPO);
		gitSupportService.cloneRepository(ZHIBERNATE, JUNIT_REPO);
	}

	@Test(timeout = 60000, expected = GraphsException.class)
	public void throwsExceptionAtInvalidUrl() throws GraphsException {
		gitSupportService.cloneRepository(ZHIBERNATE, INVALID_URL);

	}

	@Override
	public Logger getLog() {
		return log;
	}
}
