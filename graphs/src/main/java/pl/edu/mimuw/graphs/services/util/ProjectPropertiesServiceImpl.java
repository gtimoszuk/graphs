package pl.edu.mimuw.graphs.services.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import pl.edu.mimuw.graphs.exceptions.GraphsException;
import pl.edu.mimuw.graphs.exceptions.InvalidDataExcetion;
import pl.edu.mimuw.graphs.exceptions.ProjectPropertiesService;
import pl.edu.mimuw.graphs.services.api.PathResolvingService;
import pl.edu.mimuw.graphs.services.util.builders.ProjectBuildTool;

import com.google.common.base.Strings;

public class ProjectPropertiesServiceImpl implements ProjectPropertiesService {

	private PathResolvingService pathResolvingService;

	private String projectBuildToolPropertyName;

	@Override
	public ProjectBuildTool getProjectBuildTool(String projectName) throws GraphsException {
		String projectBuildTool = getProperty(projectName, projectBuildToolPropertyName);
		if (Strings.isNullOrEmpty(projectBuildTool)) {
			throw new GraphsException("No build tool defined for project " + projectName);
		} else {
			return ProjectBuildTool.valueOf(projectBuildTool);
		}
	}

	@Override
	public void setBuildTool(String projectName, ProjectBuildTool projectBuildTool) throws GraphsException {
		setProperty(projectName, projectBuildToolPropertyName, projectBuildTool.toString());
	}

	@Override
	public String getProperty(String projectName, String propertyName) throws GraphsException {
		Properties props = new Properties();
		try {
			props.load(new FileInputStream(new File(pathResolvingService.getPropertiesFile(projectName))));
		} catch (FileNotFoundException e) {
			throw new GraphsException(e);
		} catch (IOException e) {
			throw new GraphsException(e);
		} catch (InvalidDataExcetion e) {
			throw new GraphsException(e);
		}
		return props.getProperty(propertyName);
	}

	@Override
	public void setProperty(String projectName, String propertyName, String propertyValue) throws GraphsException {
		Properties props = new Properties();
		try {
			props.load(new FileInputStream(new File(pathResolvingService.getPropertiesFile(projectName))));
			props.put(propertyName, propertyValue);
			props.store(new FileOutputStream(new File(pathResolvingService.getPropertiesFile(projectName))), "");
		} catch (FileNotFoundException e) {
			throw new GraphsException(e);
		} catch (IOException e) {
			throw new GraphsException(e);
		} catch (InvalidDataExcetion e) {
			throw new GraphsException(e);
		}

	}

	public PathResolvingService getPathResolvingService() {
		return pathResolvingService;
	}

	public void setPathResolvingService(PathResolvingService pathResolvingService) {
		this.pathResolvingService = pathResolvingService;
	}

	public String getProjectBuildToolPropertyName() {
		return projectBuildToolPropertyName;
	}

	public void setProjectBuildToolPropertyName(String projectBuildToolPropertyName) {
		this.projectBuildToolPropertyName = projectBuildToolPropertyName;
	}

}
