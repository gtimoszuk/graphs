package pl.edu.mimuw.graphs.importer.utils;

/**
 * Basic sequence, not for concurrent usage
 * 
 * @author gtimoszuk
 * 
 */
public class SimpleSequence {

	private long id = 1000;

	public SimpleSequence() {

	}

	public SimpleSequence(long id) {
		this.id = id;
	}

	public long getId() {
		id++;
		return id;
	}

}
