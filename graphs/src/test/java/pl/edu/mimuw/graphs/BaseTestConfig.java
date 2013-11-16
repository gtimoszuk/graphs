package pl.edu.mimuw.graphs;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import pl.edu.mimuw.graphs.importer.packages.graph.PackageGraphImporter;

@Configuration
public class BaseTestConfig {

	private static final String DATA_JUNIT_SIMPLE = "pl/edu/mimuw/graphs/data/junit/simple";

	static final Logger LOGGER = LoggerFactory.getLogger(BaseTestConfig.class);

	@Bean
	public String projectBaseDir() {
		return "/home/ballo0/GTI/projects/";
	}

	@Bean
	public PackageGraphImporter packageImporter() {
		return new PackageGraphImporter();
	}

	@Bean
	public String junitSimpleDataDir() {
		try {
			return (new ClassPathResource(DATA_JUNIT_SIMPLE)).getFile().getAbsolutePath() + "/";
		} catch (IOException e) {
			LOGGER.error("Error durign junit path findind");
			return "";
		}
	}
}
