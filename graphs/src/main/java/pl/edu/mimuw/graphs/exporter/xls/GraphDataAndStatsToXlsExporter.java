package pl.edu.mimuw.graphs.exporter.xls;

import static pl.edu.mimuw.graphs.api.GraphVertexProperies.NAME;
import static pl.edu.mimuw.graphs.api.GraphVertexProperies.TYPE;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import jxl.JXLException;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import pl.edu.mimuw.graphs.api.GraphStatististicsName;
import pl.edu.mimuw.graphs.api.MetricName;

import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;

/**
 * this is a utility stateless class
 * 
 * @author gtimoszuk
 * 
 */
public class GraphDataAndStatsToXlsExporter {

	public void singleGraphExporter(String outPath, Graph graph,
			Map<String, Map<MetricName, Map<String, Double>>> graphStatisticsSummaries) {
		try {
			WritableWorkbook workbook = Workbook.createWorkbook(new File(outPath));

			writeSummary(graph, workbook, graphStatisticsSummaries);
			writeData(graph, workbook);

			workbook.write();
			workbook.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JXLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void writeData(Graph graph, WritableWorkbook workbook) throws RowsExceededException, WriteException {
		WritableSheet sheet = workbook.createSheet("Vertices", 1);
		writeVerticesHeader(sheet);
		writeSingleVertexData(graph, sheet);

	}

	private void writeSummary(Graph graph, WritableWorkbook workbook,
			Map<String, Map<MetricName, Map<String, Double>>> graphStatisticsSummaries) throws RowsExceededException,
			WriteException {
		WritableSheet sheet = workbook.createSheet("Summary", 0);
		int i = 0;
		for (String s : graphStatisticsSummaries.keySet()) {
			Label label = new Label(0, i, s);
			sheet.addCell(label);
			i++;
			i = writeSummaryForView(sheet, graphStatisticsSummaries.get(s), i);
			i++;
			i++;
		}
	}

	private void writeSingleVertexData(Graph graph, WritableSheet sheet) throws WriteException, RowsExceededException {
		int r = 1;
		for (Vertex v : graph.getVertices()) {
			if (v.getProperty(NAME) != null) {
				Label name = new Label(0, r, (String) v.getProperty(NAME));
				sheet.addCell(name);
				Label type = new Label(1, r, (String) v.getProperty(TYPE));
				sheet.addCell(type);
				int c = 2;

				for (MetricName metricName : MetricName.values()) {
					if (v.getProperty(metricName.name()) != null) {
						Object propertyObject = v.getProperty(metricName.name());
						Double result = null;
						if (propertyObject instanceof Double) {
							result = (Double) propertyObject;
						} else if (propertyObject instanceof Integer) {
							result = ((Integer) propertyObject).doubleValue();

						}
						Number number = new Number(c, r, result);
						sheet.addCell(number);
					} else {
						Number number = new Number(c, r, Double.NaN);
						sheet.addCell(number);
					}
					c++;
				}
				r++;
			}
		}
	}

	private void writeVerticesHeader(WritableSheet sheet) throws WriteException, RowsExceededException {
		Label nameLabel = new Label(0, 0, NAME);
		sheet.addCell(nameLabel);
		Label typeLabel = new Label(1, 0, TYPE);
		sheet.addCell(typeLabel);
		int i = 2;
		for (MetricName metricName : MetricName.values()) {
			Label label = new Label(i, 0, metricName.name());
			sheet.addCell(label);
			i++;
		}
	}

	private int writeSummaryForView(WritableSheet sheet, Map<MetricName, Map<String, Double>> map, int i)
			throws RowsExceededException, WriteException {
		i = writeMetricsNames(sheet, i);

		writeStatsNames(sheet, i);

		i = writeSummaryData(sheet, map, i);

		return i;

	}

	private int writeSummaryData(WritableSheet sheet, Map<MetricName, Map<String, Double>> map, int i)
			throws WriteException, RowsExceededException {
		int c = 1;
		for (MetricName metricName : MetricName.values()) {
			Map<String, Double> currentStats = map.get(metricName);
			int r = i;
			for (GraphStatististicsName graphStatististicsName : GraphStatististicsName.values()) {
				jxl.write.Number number = new Number(c, r, currentStats.get(graphStatististicsName.name()));
				sheet.addCell(number);
				r++;
			}
			c++;
		}

		i += MetricName.values().length;
		return i;
	}

	private void writeStatsNames(WritableSheet sheet, int i) throws WriteException, RowsExceededException {
		int k = i;
		for (GraphStatististicsName graphStatististicsName : GraphStatististicsName.values()) {
			Label label = new Label(0, k, graphStatististicsName.name());
			k++;
			sheet.addCell(label);
		}
	}

	private int writeMetricsNames(WritableSheet sheet, int i) throws WriteException, RowsExceededException {
		int j = 1;
		for (MetricName metricName : MetricName.values()) {
			Label label = new Label(j, i, metricName.name());
			j++;
			sheet.addCell(label);
		}
		// move to next row as we created header
		i++;
		return i;
	}
}
