package pl.edu.mimuw.graphs.exceptions;

/**
 * Exception to start local excp hierarchy for project
 * 
 * @author gtimoszuk
 * 
 */
public class GraphsException extends Throwable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -202575078448377233L;

	public GraphsException() {
		super();
	}

	public GraphsException(String message, Throwable cause) {
		super(message, cause);
	}

	public GraphsException(String message) {
		super(message);
	}

	public GraphsException(Throwable cause) {
		super(cause);
	}

}
