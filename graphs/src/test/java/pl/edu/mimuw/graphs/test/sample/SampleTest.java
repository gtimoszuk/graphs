package pl.edu.mimuw.graphs.test.sample;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:pl/edu/mimuw/graphs/conf/graphs-test-context.xml" })
public class SampleTest {

	@Rule
	public TestName name = new TestName();

	private static final Logger log = LoggerFactory.getLogger(SampleTest.class);

	@Test
	public void testGti() {
		log.info(name.getMethodName());
		log.error("just a message");
	}
}
