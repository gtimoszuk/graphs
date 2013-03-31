package pl.edu.mimuw.graphs.services.util.builders;

import pl.edu.mimuw.graphs.exceptions.GraphsException;
import pl.edu.mimuw.graphs.exceptions.ProjectPropertiesService;
import pl.edu.mimuw.graphs.services.api.PathResolvingService;
import pl.edu.mimuw.graphs.services.api.ProjectBuilderService;

import com.google.common.base.Strings;

public abstract class MavenEclipseProjectBuilder implements ProjectBuilderService {

	protected String mvnCommand;

	private String customPomLocationProperty;

	private ProjectPropertiesService projectPropertiesService;

	private PathResolvingService pathResolvingService;

	private final static String POM_SUFFIX = "pom.xml";

	@Override
	public void buildProject(String projectName) throws GraphsException {
		String pomLocation;
		String customPomLocation = projectPropertiesService.getProperty(projectName, customPomLocationProperty);
		if (Strings.isNullOrEmpty(customPomLocation)) {
			pomLocation = pathResolvingService.getGitDir(projectName) + POM_SUFFIX;
		} else {
			if (customPomLocation.endsWith("/")) {
				pomLocation = pathResolvingService.getGitDir(projectName) + customPomLocation + POM_SUFFIX;
			} else {
				pomLocation = pathResolvingService.getGitDir(projectName) + customPomLocation + "/" + POM_SUFFIX;
			}
		}

		String eclipseBuildCommand = mvnCommand + " -f " + pomLocation;
	}
}
