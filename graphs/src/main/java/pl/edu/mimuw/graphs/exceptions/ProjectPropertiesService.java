package pl.edu.mimuw.graphs.exceptions;

import pl.edu.mimuw.graphs.services.util.builders.ProjectBuildTool;

/**
 * Simple interface for resolving project properties
 * 
 * @author gtimoszuk
 * 
 */
public interface ProjectPropertiesService {

	String getProperty(String projectName, String propertyName) throws GraphsException;

	void setProperty(String projectName, String propertyName, String propertyValue) throws GraphsException;

	ProjectBuildTool getProjectBuildTool(String projectName) throws GraphsException;

	void setBuildTool(String projectName, ProjectBuildTool projectBuildTool) throws GraphsException;
}
