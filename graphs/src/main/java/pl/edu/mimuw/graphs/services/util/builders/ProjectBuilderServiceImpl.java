package pl.edu.mimuw.graphs.services.util.builders;

import java.util.Map;

import pl.edu.mimuw.graphs.exceptions.GraphsException;
import pl.edu.mimuw.graphs.exceptions.ProjectPropertiesService;
import pl.edu.mimuw.graphs.exceptions.UnsuportedProjectTypeExceprion;
import pl.edu.mimuw.graphs.services.api.ProjectBuilderService;

public class ProjectBuilderServiceImpl implements ProjectBuilderService {

	private Map<ProjectBuildTool, ProjectBuilderService> builders;

	private ProjectPropertiesService projectPropertiesService;

	@Override
	public void buildProject(String projectName) throws GraphsException {
		ProjectBuildTool projectBuildTool = projectPropertiesService.getProjectBuildTool(projectName);
		if (projectBuildTool == null) {
			throw new UnsuportedProjectTypeExceprion("No build tool defined for project " + projectName);
		}
		ProjectBuilderService targetBuilder = builders.get(projectBuildTool);
		if (targetBuilder == null) {
			throw new UnsuportedProjectTypeExceprion("No build tool for project project build tool "
					+ projectBuildTool.toString() + " for project " + projectName);
		}
		targetBuilder.buildProject(projectName);
	}
}
