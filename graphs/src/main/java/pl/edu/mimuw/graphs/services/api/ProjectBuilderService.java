package pl.edu.mimuw.graphs.services.api;


import pl.edu.mimuw.graphs.exceptions.GraphsException;

/**
 * Interface that hides build details for analyzed projects
 * 
 * @author gtimoszuk
 * 
 */
public interface ProjectBuilderService {

	/**
	 * Build project with given parameters. Important - we need to build project
	 * in order to proceed with analysis
	 * 
	 * @param projectName
	 * @throws GraphsException
	 */
	void buildProject(String projectName) throws GraphsException;

}
