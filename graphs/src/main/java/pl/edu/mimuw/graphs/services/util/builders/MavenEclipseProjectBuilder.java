package pl.edu.mimuw.graphs.services.util.builders;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.edu.mimuw.graphs.exceptions.GraphsException;
import pl.edu.mimuw.graphs.exceptions.ProjectPropertiesService;
import pl.edu.mimuw.graphs.services.api.PathResolvingService;
import pl.edu.mimuw.graphs.services.api.ProjectBuilderService;

import com.google.common.base.Strings;

public class MavenEclipseProjectBuilder implements ProjectBuilderService {

	public final static Logger log = LoggerFactory.getLogger(MavenEclipseProjectBuilder.class);

	protected String mvnCommand;

	private String customPomLocationProperty;

	private ProjectPropertiesService projectPropertiesService;

	private PathResolvingService pathResolvingService;

	private final static String MAVEN_GOAL = "eclipse:eclipse";

	@Override
	public void buildProject(String projectName) throws GraphsException {
		String pomLocation;
		String customPomLocation = projectPropertiesService.getProperty(projectName, customPomLocationProperty);
		if (Strings.isNullOrEmpty(customPomLocation)) {
			pomLocation = pathResolvingService.getGitDir(projectName);
		} else {
			if (customPomLocation.endsWith("/")) {
				pomLocation = pathResolvingService.getGitDir(projectName) + customPomLocation;
			} else {
				pomLocation = pathResolvingService.getGitDir(projectName) + customPomLocation;
			}
		}
		File pomFileDir = new File(pomLocation);
		log.info("pomLocation: {}", pomLocation);

		String eclipseBuildCommand = mvnCommand + " -e " + MAVEN_GOAL;
		log.info("mvnCommand: {}", eclipseBuildCommand);

		execMaven(eclipseBuildCommand, pomFileDir);
	}

	private void execMaven(String eclipseBuildCommand, File workingDir) throws GraphsException {
		String inputStreamString;
		String errorStreamString;
		Process mavenProcess;
		try {
			mavenProcess = Runtime.getRuntime().exec(eclipseBuildCommand, null, workingDir);

			BufferedReader inputBufferedReader = new BufferedReader(
					new InputStreamReader(mavenProcess.getInputStream()));
			while ((inputStreamString = inputBufferedReader.readLine()) != null)
				log.trace("input line: {}", inputStreamString);
			BufferedReader errorBufferedReader = new BufferedReader(
					new InputStreamReader(mavenProcess.getErrorStream()));
			while ((errorStreamString = errorBufferedReader.readLine()) != null)
				log.trace("error line: {}", errorStreamString);

			mavenProcess.waitFor();
			int mavenExitValue = mavenProcess.exitValue();
			log.info("MavenEclipseProjectBuilder exit: " + mavenExitValue);
			if (mavenExitValue != 0) {
				log.error("maven finished with errors aborting");
				throw new GraphsException("maven command" + eclipseBuildCommand + " in dir "
						+ workingDir.getAbsolutePath() + "finished with errors aborting");
			}
			mavenProcess.destroy();
		} catch (Exception e) {
			throw new GraphsException(e);
		}
	}

	public String getMvnCommand() {
		return mvnCommand;
	}

	public void setMvnCommand(String mvnCommand) {
		this.mvnCommand = mvnCommand;
	}

	public String getCustomPomLocationProperty() {
		return customPomLocationProperty;
	}

	public void setCustomPomLocationProperty(String customPomLocationProperty) {
		this.customPomLocationProperty = customPomLocationProperty;
	}

	public ProjectPropertiesService getProjectPropertiesService() {
		return projectPropertiesService;
	}

	public void setProjectPropertiesService(ProjectPropertiesService projectPropertiesService) {
		this.projectPropertiesService = projectPropertiesService;
	}

	public PathResolvingService getPathResolvingService() {
		return pathResolvingService;
	}

	public void setPathResolvingService(PathResolvingService pathResolvingService) {
		this.pathResolvingService = pathResolvingService;
	}
}
