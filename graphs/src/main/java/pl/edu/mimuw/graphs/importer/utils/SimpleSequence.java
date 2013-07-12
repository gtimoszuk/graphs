package pl.edu.mimuw.graphs.importer.utils;

/**
 * Basic sequence, not for concurrent usage
 * 
 * @author gtimoszuk
 * 
 */
public class SimpleSequence {

	private int id = 1000;

	public SimpleSequence() {

	}

	public SimpleSequence(int id) {
		this.id = id;
	}

	public int getId() {
		id++;
		return id;
	}

}
