package pl.edu.mimuw.graphs.services.projects;

public enum ProjectBuildTool {

	MVN("Maven 2"), MVN3("Maven 3");

	private String toolName;

	ProjectBuildTool() {

	}

	ProjectBuildTool(String toolName) {
		this.toolName = toolName;
	}

	public String getToolName() {
		return toolName;
	}

}
