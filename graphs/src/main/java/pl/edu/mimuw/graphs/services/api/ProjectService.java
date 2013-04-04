package pl.edu.mimuw.graphs.services.api;

import java.util.List;
import java.util.Map;

import pl.edu.mimuw.graphs.exceptions.GraphsException;

/**
 * Interface for basic interactions with projects
 * 
 * @author gtimoszuk
 * 
 */
public interface ProjectService {

	/**
	 * Method adds new project
	 * 
	 * @param name
	 *            - project name
	 * @param properties
	 *            - project properties
	 * @return - if project was successfully created
	 */
	boolean addProject(String name, Map<String, String> properties);

	/**
	 * Method returns list of existing projects
	 * 
	 * @return
	 * @throws GraphsException
	 */
	List<String> listProjectNames() throws GraphsException;

	/**
	 * Method tries to remove project
	 * 
	 * @param projectName
	 * @return
	 */
	boolean removeProject(String projectName);
}
