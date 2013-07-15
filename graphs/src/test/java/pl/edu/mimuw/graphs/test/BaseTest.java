package pl.edu.mimuw.graphs.test;

import java.util.Calendar;

import org.junit.Rule;
import org.junit.rules.TestName;
import org.slf4j.Logger;

/**
 * Class with basic hints used for testing
 * 
 * @author gtimoszuk
 * 
 */
public abstract class BaseTest {

	public static final String JUNIT_REPO = "https://github.com/junit-team/junit.git";
	public static final String ZHIBERNATE_REPO = "https://github.com/gtimoszuk/zhibernate.git";
	public static final String INVALID_URL = "https://gitSthWornghub.com";
	public static final String ZHIBERNATE = "zhibernate";
	public static final String JUNIT = "junit";

	public abstract Logger getLog();

	@Rule
	public TestName name = new TestName();

	public void before() {

		getLog().info("{} test starting", name.getMethodName());

	}

	public void after() {
		getLog().info("{} test finishing", name.getMethodName());

	}

	protected String randomSuffix() {
		return String.valueOf(Calendar.getInstance().getTimeInMillis());
	}

}
