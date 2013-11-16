package pl.edu.mimuw.graphs.importer.packages.graph;

import com.tinkerpop.blueprints.Graph;

public interface GraphImporter {

	/**
	 * Imports graph into temporary directory.
	 * 
	 * @param inDir
	 *            points to directory with three files representing graph
	 *            textually
	 * @return
	 */
	Graph importGraph(String inDir);

	/**
	 * Imports graph into given directory outDir
	 * 
	 * @param inDir
	 *            points to directory with three files representing graph
	 *            textually
	 * @param outDir
	 *            result directory
	 * 
	 * @return
	 */
	Graph importGraph(String inDir, String outDir);
}
