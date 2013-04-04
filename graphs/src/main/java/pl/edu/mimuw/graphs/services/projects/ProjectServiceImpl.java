package pl.edu.mimuw.graphs.services.projects;

import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.edu.mimuw.graphs.exceptions.GraphsException;
import pl.edu.mimuw.graphs.services.api.ProjectService;

import com.google.common.base.Strings;

public class ProjectServiceImpl implements ProjectService {
	public final static Logger log = LoggerFactory.getLogger(ProjectServiceImpl.class);

	private String projectsDirPath;

	@Override
	public boolean addProject(String name, Map<String, String> properties) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<String> listProjectNames() throws GraphsException {
		if (Strings.isNullOrEmpty(projectsDirPath)) {
			throw new GraphsException("projectsDirPath is null or empty");
		}
		File projectsDir = new File(projectsDirPath);
		String[] projectsArray = projectsDir.list();
		List<String> projectList = ((projectsArray == null) ? new LinkedList<String>() : Arrays.asList(projectsArray));
		log.trace("projects list {}", projectsDirPath.toString());
		return projectList;
	}

	@Override
	public boolean removeProject(String projectName) {
		// TODO Auto-generated method stub
		return false;
	}

	public String getProjectsDirPath() {
		return projectsDirPath;
	}

	public void setProjectsDirPath(String projectsDirPath) {
		this.projectsDirPath = projectsDirPath;
	}

}
