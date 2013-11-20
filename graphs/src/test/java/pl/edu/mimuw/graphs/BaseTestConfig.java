package pl.edu.mimuw.graphs;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;

import pl.edu.mimuw.graphs.exporter.magnify.MagnifyPackageOnlyJSONFromDBExporter;
import pl.edu.mimuw.graphs.importer.packages.graph.GraphImporter;
import pl.edu.mimuw.graphs.importer.packages.graph.PackageGraphImporter;

@Configuration
@PropertySource(value = { "classpath:pl/edu/mimuw/graphs/conf/graphs.properties",
		"file:///home/ballo0/GTI/graphs/conf/graphsLocal.properties",
		"file:///home/alumni/g/gtimoszuk/PHD/graphs/conf/graphsDuch.properties" }, ignoreResourceNotFound = true)
public class BaseTestConfig {

	static final Logger LOGGER = LoggerFactory.getLogger(BaseTestConfig.class);

	/**************** Properties and its configuration ***********************/

	@Bean
	public static PropertySourcesPlaceholderConfigurer placeHolderConfigurer() {
		PropertySourcesPlaceholderConfigurer result = new PropertySourcesPlaceholderConfigurer();
		result.setIgnoreResourceNotFound(true);
		result.setIgnoreUnresolvablePlaceholders(true);

		return result;
	}

	@Value("${projectBaseDir}")
	String projectBaseDir;

	@Bean
	public String projectBaseDir() {
		placeHolderConfigurer();
		return projectBaseDir;
	}

	@Value("${junitSimpleDataDir}")
	String junitSimpleDataDir;

	@Bean
	public String junitSimpleDataDir() {
		placeHolderConfigurer();
		try {
			return (new ClassPathResource(junitSimpleDataDir)).getFile().getAbsolutePath() + "/";
		} catch (IOException e) {
			LOGGER.error("Error during junit path finding, excp {}", e);
			return "";
		}
	}

	@Value("${realDataDir}")
	String realDataDir;

	@Bean
	public String realDataDir() {
		placeHolderConfigurer();
		try {
			return (new ClassPathResource(realDataDir)).getFile().getAbsolutePath() + "/";
		} catch (IOException e) {
			LOGGER.error("Error durign junit path findind");
			return "";
		}
	}

	@Value("${inputDataPath}")
	String inputDataPath;

	@Bean
	public String inputDataPath() {
		return inputDataPath;
	}

	/************************************************************************************************/

	@Bean
	public GraphImporter graphImporter() {
		return new PackageGraphImporter();
	}

	@Bean
	public MagnifyPackageOnlyJSONFromDBExporter magnifyJSONExporter() {
		return new MagnifyPackageOnlyJSONFromDBExporter();
	}

}
