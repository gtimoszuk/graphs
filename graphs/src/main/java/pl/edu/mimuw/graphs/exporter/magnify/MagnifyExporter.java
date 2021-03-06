package pl.edu.mimuw.graphs.exporter.magnify;

import static com.tinkerpop.blueprints.Direction.IN;
import static com.tinkerpop.blueprints.Direction.OUT;
import static pl.edu.mimuw.graphs.GraphVertexProperies.NAME;
import static pl.edu.mimuw.graphs.OptionalGraphProperties.COUNT;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.edu.mimuw.graphs.importer.utils.SimpleSequence;
import pl.edu.mimuw.graphs.metrics.Utils;

import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;

public class MagnifyExporter {

	static final Logger LOGGER = LoggerFactory.getLogger(MagnifyExporter.class);

	private final static String KIND_STRING = "\"kind\": ";
	private final static String COUNT_STRING = "\"count\": ";
	private final static String METRIC_LINES_OF_CODE_STRING = "\"metric--lines-of-code\": ";
	private final static String NAME_STRING = "\"name\": ";
	private final static String PAGE_RANK = "\"page-rank\": ";
	private final static String NODES = "\"nodes\": ";
	private final static String EDGES = "\"edges\": ";
	private final static String PACKAGE_IMPORTS_STRING = "\"package-imports\"";
	private final static String IN_PACKAGE_STRING = "\"in-package\"";
	private final static String SOURCE_STRING = "\"source\": ";
	private final static String TARGET_STRING = "\"target\": ";

	private final Utils utils = new Utils();

	public void export(String outPath, Graph graph, String metricToExport, String sizeProperty, String inPackageLabel,
			String packageImportsLabel) {

		Map<Long, Long> graphIdToJsonId = new HashMap<Long, Long>();

		Writer jsonWriter = null;

		try {
			jsonWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outPath)));
			jsonWriter.append("{\n");
			jsonWriter.append(NODES + " ");
			exportNodes(jsonWriter, graph, graphIdToJsonId, metricToExport, sizeProperty);
			jsonWriter.append(",");
			jsonWriter.append(EDGES + " ");
			exportEdges(jsonWriter, graph, packageImportsLabel, inPackageLabel, graphIdToJsonId);
			jsonWriter.append("}");
			jsonWriter.close();
		} catch (IOException e) {
			LOGGER.warn(e.toString());
		}

	}

	private void exportEdges(Writer jsonWriter, Graph graph, String packageImportsLabel, String inPackageLabel,
			Map<Long, Long> graphIdToJsonId) throws IOException {
		jsonWriter.append("[\n");
		boolean ifFirst = true;
		for (Edge e : graph.getEdges()) {
			if (packageImportsLabel.equals(e.getLabel()) || inPackageLabel.equals(e.getLabel())) {
				if (!ifFirst) {
					jsonWriter.append(",\n");
				} else {
					ifFirst = false;
				}
				exportSingleEdge(e, jsonWriter, packageImportsLabel, graphIdToJsonId);
			}
		}

		jsonWriter.append("]\n");
	}

	private void exportSingleEdge(Edge e, Writer jsonWriter, String packageImportsLabel, Map<Long, Long> graphIdToJsonId)
			throws IOException {
		jsonWriter.append("{\n");
		jsonWriter.append(SOURCE_STRING);
		jsonWriter.append(graphIdToJsonId.get(utils.getIdAsLong(e.getVertex(OUT).getId())).toString());
		jsonWriter.append(",\n");
		jsonWriter.append(TARGET_STRING);
		jsonWriter.append(graphIdToJsonId.get(utils.getIdAsLong(e.getVertex(IN).getId())).toString());
		jsonWriter.append(",\n");
		jsonWriter.append(COUNT_STRING);
		if (e.getPropertyKeys().contains(COUNT) && e.getProperty(COUNT) != null) {
			jsonWriter.append("\"" + e.getProperty(COUNT) + "\",\n");
		} else {
			jsonWriter.append("\"1\",\n");
		}
		jsonWriter.append(KIND_STRING);
		if (packageImportsLabel.equals(e.getLabel())) {
			jsonWriter.append(PACKAGE_IMPORTS_STRING);
		} else {
			jsonWriter.append(IN_PACKAGE_STRING);
		}
		jsonWriter.append("\n");
		jsonWriter.append("}");
	}

	private void exportNodes(Writer jsonWriter, Graph graph, Map<Long, Long> graphIdToJsonId, String metricToExport,
			String sizeProperty) throws IOException {
		SimpleSequence sequence = new SimpleSequence(-1L);

		jsonWriter.append("[\n");
		boolean ifFirst = true;
		for (Vertex v : graph.getVertices()) {
			if (v.getPropertyKeys().contains(NAME) && v.getProperty(NAME) != null) {
				if (!ifFirst) {
					jsonWriter.append(",\n");
				} else {
					ifFirst = false;
				}
				exportSingleVertex(v, jsonWriter, graphIdToJsonId, sequence, metricToExport, sizeProperty);
			}
		}

		jsonWriter.append("]\n");

	}

	private void exportSingleVertex(Vertex v, Writer jsonWriter, Map<Long, Long> graphIdToJsonId,
			SimpleSequence sequence, String metricToExport, String sizeProperty) throws IOException {

		graphIdToJsonId.put(utils.getIdAsLong(v.getId()), sequence.getId());

		jsonWriter.append("{\n");
		jsonWriter.append(NAME_STRING + "\"" + v.getProperty(NAME) + "\",\n");
		if (v.getPropertyKeys().contains(metricToExport) && v.getProperty(metricToExport) != null) {
			jsonWriter.append(METRIC_LINES_OF_CODE_STRING + "\"" + v.getProperty(metricToExport) + "\",\n");

		}
		if (v.getPropertyKeys().contains(sizeProperty) && v.getProperty(sizeProperty) != null) {
			jsonWriter.append(PAGE_RANK + "\"" + v.getProperty(sizeProperty) + "\",\n");

		}
		jsonWriter.append("\"kind\": \"package\"\n");
		jsonWriter.append("}");
	}
}
