package pl.edu.mimuw.graphs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import pl.edu.mimuw.graphs.exporter.magnify.MagnifyPackageOnlyJSONFromDBExporter;
import pl.edu.mimuw.graphs.importer.packages.graph.PackageGraphImporter;

@Configuration
public class BaseTestConfig {

	private static final String JUNIT_SIMPLE_DATA_DIR = "pl/edu/mimuw/graphs/data/junit/simple";
	private static final String REAL_DATA_DIR = "pl/edu/mimuw/graphs/real";

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
			return (new ClassPathResource(JUNIT_SIMPLE_DATA_DIR)).getFile().getAbsolutePath() + "/";
		} catch (IOException e) {
			LOGGER.error("Error durign junit path findind");
			return "";
		}
	}

	@Bean
	public String realDataDir() {
		try {
			return (new ClassPathResource(REAL_DATA_DIR)).getFile().getAbsolutePath() + "/";
		} catch (IOException e) {
			LOGGER.error("Error durign junit path findind");
			return "";
		}
	}

	@Bean
	public MagnifyPackageOnlyJSONFromDBExporter magnifyJSONExporter() {
		return new MagnifyPackageOnlyJSONFromDBExporter();
	}

	private List<String> propertiesLocations() {
		ArrayList<String> result = new ArrayList<>();
		result.add("classpath:pl/edu/mimuw/graphs/conf/graphs.properties");
		// GTI: ad hoc changes
		result.add("file:///home/ballo0/GTI/graphs/conf/graphs.properties");
		// GTI: my duch props location
		result.add("file:///home/ballo0/GTI/graphs/conf/graphs.properties");
		return result;

	}
}
