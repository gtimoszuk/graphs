package pl.edu.mimuw.graphs.test;

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

	public abstract Logger getLog();

	@Rule
	public TestName name = new TestName();

	public void before() {

		getLog().info("{} test starting", name.getMethodName());

	}

	public void after() {
		getLog().info("{} test finishing", name.getMethodName());

	}

}
