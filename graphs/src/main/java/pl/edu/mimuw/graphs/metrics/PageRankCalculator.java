package pl.edu.mimuw.graphs.metrics;

import static pl.edu.mimuw.graphs.api.MetricNames.PAGE_RANK;

import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.oupls.jung.GraphJung;

import edu.uci.ics.jung.algorithms.scoring.PageRank;

public class PageRankCalculator {

	public void calculate(Graph graph) {
		GraphJung<Graph> graphJung = new GraphJung<Graph>(graph);
		PageRank<Vertex, Edge> pageRank = new PageRank<Vertex, Edge>(graphJung, 0.15);
		pageRank.evaluate();
		for (Vertex v : graphJung.getVertices()) {
			double score = pageRank.getVertexScore(v);
			if (graph.getVertex(v.getId()) != null) {
				graph.getVertex(v.getId()).setProperty(PAGE_RANK, score);
			}
		}

	}

}
