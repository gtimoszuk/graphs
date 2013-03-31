package pl.edu.mimuw.graphs.exceptions;

/**
 * Simple exceptrion for gir related problems
 * 
 * @author gtimoszuk
 * 
 */
public class GitException extends GraphsException {

	private static final long serialVersionUID = -5098247955491012649L;

	public GitException(Throwable paramThrowable) {
		super(paramThrowable);
	}

	public GitException() {
		super();
	}

	public GitException(String paramString, Throwable paramThrowable) {
		super(paramString, paramThrowable);
	}

	public GitException(String paramString) {
		super(paramString);
	}

}
