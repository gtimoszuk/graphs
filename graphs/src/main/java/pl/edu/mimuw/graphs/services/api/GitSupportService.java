package pl.edu.mimuw.graphs.services.api;

import pl.edu.mimuw.graphs.exceptions.GraphsException;

/**
 * Interface for git related operations
 * 
 * 
 * @author gtimoszuk
 * 
 */
public interface GitSupportService {

	/**
	 * Clones given repoUrl under given name
	 * 
	 * @param name
	 * @param repoUlr
	 * 
	 * @throws GraphsException
	 *             - thrown when something went wrong
	 */
	void cloneRepository(String name, String repoUlr) throws GraphsException;
}
