package pl.edu.mimuw.graphs.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GraphWebAppTestRun {

	private static final Logger LOGGER = LoggerFactory.getLogger(GraphWebAppTestRun.class);

	private static final String WEB_DESCRIPTOR = "src/main/webapp/WEB-INF/web.xml";
	private static final String RESOURCE_BASE = "src/main/webapp";
	private static final int DEFAULT_PORT = 8080;
	private static final String CONTEXT = "graphs";

	public static void main(String[] args) throws Exception {
		GraphWebAppTestRun server = new GraphWebAppTestRun();
		server.start();
	}

	public void start() throws Exception {
		LOGGER.info("Starting Jetty server");

		Server server = new Server(DEFAULT_PORT);

		ContextHandlerCollection handlers = new ContextHandlerCollection();

		WebAppContext waContext = new WebAppContext();
		waContext.setDescriptor(WEB_DESCRIPTOR);
		waContext.setResourceBase(RESOURCE_BASE);
		waContext.setContextPath("/" + getContextName());
		waContext.setParentLoaderPriority(true);
		handlers.addHandler(waContext);

		WebAppContext resourceContext = new WebAppContext();
		resourceContext.setDescriptor("../webapp-resources/" + WEB_DESCRIPTOR);
		resourceContext.setResourceBase("../webapp-resources/" + RESOURCE_BASE);
		resourceContext.setContextPath("/resources");
		resourceContext.setParentLoaderPriority(true);
		handlers.addHandler(resourceContext);

		server.setHandler(handlers);

		server.start();
		server.join();
	}

	protected String getContextName() {
		return CONTEXT;
	}
}
