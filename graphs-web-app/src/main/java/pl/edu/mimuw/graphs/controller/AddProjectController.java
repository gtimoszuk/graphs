package pl.edu.mimuw.graphs.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import pl.edu.mimuw.graphs.controller.util.AbstractGraphController;
import pl.edu.mimuw.graphs.exceptions.GraphsException;
import pl.edu.mimuw.graphs.forms.CreateProjectForm;
import pl.edu.mimuw.graphs.services.api.ProjectService;
import pl.edu.mimuw.graphs.services.util.builders.ProjectBuildTool;
import pl.edu.mimuw.graphs.services.util.builders.RevisionControlSystem;

@Controller
public class AddProjectController extends AbstractGraphController {

	@Autowired
	private ProjectService projectService;

	@RequestMapping(value = "/addProject", method = RequestMethod.GET)
	public ModelAndView defaultAddress(HttpServletRequest request) throws GraphsException {
		ModelMap modelMap = createBaseModelMap();

		Map<String, String> buildTools = new HashMap<String, String>();
		for (ProjectBuildTool buildTool : ProjectBuildTool.values()) {
			buildTools.put(buildTool.name(), buildTool.getToolName());
		}
		modelMap.put("buildTools", buildTools);

		Map<String, String> revisionControlSystems = new HashMap<String, String>();
		for (RevisionControlSystem revisionControlSystem : RevisionControlSystem.values()) {
			revisionControlSystems.put(revisionControlSystem.name(), revisionControlSystem.getRevisionControlName());
		}
		modelMap.put("revisionControlSystems", revisionControlSystems);

		return new ModelAndView("pages/addProject", modelMap);
	}

	@RequestMapping(value = "/createProject", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView createProject(@ModelAttribute("createProjectForm") CreateProjectForm form) {

		return new ModelAndView("redirect:/index");
	}

}
