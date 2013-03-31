package pl.edu.mimuw.graphs.services.api;

import pl.edu.mimuw.graphs.exceptions.GraphsException;
import pl.edu.mimuw.graphs.exceptions.InvalidDataExcetion;

/**
 * Simple facade that hides physical projects layout
 * 
 * @author gtimoszuk
 * 
 */
public interface PathResolvingService {

	/**
	 * Returns directory where Git repo is stored
	 * 
	 * @param projectName
	 * @return
	 * @throws InvalidDataExcetion
	 */
	String getGitDir(String projectName) throws InvalidDataExcetion;

	/**
	 * Returns directory where DB is stored
	 * 
	 * @param projectName
	 * @return
	 * @throws InvalidDataExcetion
	 */
	String getDbDir(String projectName) throws InvalidDataExcetion;

	/**
	 * Return project directory
	 * 
	 * @param projectName
	 * @return
	 * @throws InvalidDataExcetion
	 */
	String getProjectDir(String projectName) throws InvalidDataExcetion;

	/**
	 * Returns properties for given project
	 * 
	 * @param projectName
	 * @return
	 * @throws GraphsException
	 */
	String getPropertiesFile(String projectName) throws GraphsException;

}
