package pl.edu.mimuw.graphs.controller.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;

import pl.edu.mimuw.graphs.exceptions.GraphsException;
import pl.edu.mimuw.graphs.services.api.ProjectService;

public class AbstractGraphController {

	@Autowired
	private ProjectService projectService;

	public ModelMap createBaseModelMap() throws GraphsException {
		ModelMap modelMap = new ModelMap();
		addProjectList(modelMap);
		return modelMap;
	}

	public void addProjectList(ModelMap modelMap) throws GraphsException {
		modelMap.put("projects", projectService.listProjectNames());
	}

	public ProjectService getProjectService() {
		return projectService;
	}

	public void setProjectService(ProjectService projectService) {
		this.projectService = projectService;
	}
}
