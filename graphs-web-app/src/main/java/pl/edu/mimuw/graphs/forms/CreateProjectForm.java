package pl.edu.mimuw.graphs.forms;

import java.util.HashMap;
import java.util.Map;

public class CreateProjectForm {

	public CreateProjectForm() {

	}

	private String projectName;

	private String repoUrl;

	private String repoType;

	private String buildSystem;

	public Map<String, String> toMap() {
		Map<String, String> result = new HashMap<String, String>();

		// result.put();

		return result;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getRepoUrl() {
		return repoUrl;
	}

	public void setRepoUrl(String repoUrl) {
		this.repoUrl = repoUrl;
	}

	public String getRepoType() {
		return repoType;
	}

	public void setRepoType(String repoType) {
		this.repoType = repoType;
	}

	public String getBuildSystem() {
		return buildSystem;
	}

	public void setBuildSystem(String buildSystem) {
		this.buildSystem = buildSystem;
	}

}
