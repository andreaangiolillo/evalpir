package ie.dcu.evalpir.measures;

/**
 * @author Andrea Angiolillo
 */
import static org.junit.Assert.*;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import ie.dcu.evalpir.EvalEpir;
import ie.dcu.evalpir.elements.Document;
import ie.dcu.evalpir.elements.DocumentOutputPIR;
import ie.dcu.evalpir.elements.DocumentRelFile;
import ie.dcu.evalpir.elements.PIR;
import ie.dcu.evalpir.elements.Pair;
import ie.dcu.evalpir.elements.Query;
import ie.dcu.evalpir.elements.QueryRelFile;
import ie.dcu.evalpir.extractor.InputReader;


public class TestCaseMeasuresImpl {
	
	private Map<String, Query>  rel;
	private ArrayList<PIR>  result;
	
	public TestCaseMeasuresImpl() {
		String[] args = {"", "src/main/resources/relFile.csv", "", "src/main/resources/model1.csv","src/main/resources/model2.csv","src/main/resources/model3.csv"};
		rel = InputReader.extractRelevanceFile(new File(args[1]));
		result = EvalEpir.extractingModels(args);
	}
	
	@Test
	public void testPrecision() {
		 Map<String, Query> pirQueries;
		 Query pirQuery;
		 Query relQuery;
		 //Model 1
		 pirQueries = result.get(0).getQueries();
		 pirQuery = pirQueries.get("204");
		 relQuery = rel.get("204");
		 assertNotNull(pirQuery);
		 assertNotNull(relQuery);
		 assertEquals(0.002002002,  CalculateMeasure.precision(relQuery, pirQuery), 0.000000001);
		 pirQuery = pirQueries.get("108");
		 relQuery = rel.get("108");
		 assertNotNull(pirQuery);
		 assertNotNull(relQuery);
		 assertEquals(0.016016016,  CalculateMeasure.precision(relQuery, pirQuery), 0.000000001);
		 pirQuery = pirQueries.get("267");
		 relQuery = rel.get("267");
		 assertNotNull(pirQuery);
		 assertNotNull(relQuery);
		 assertEquals(0.006006006,  CalculateMeasure.precision(relQuery, pirQuery), 0.000000001);
		 pirQuery = pirQueries.get("197");
		 relQuery = rel.get("197");
		 assertNotNull(pirQuery);
		 assertNotNull(relQuery);
		 assertEquals(0.0,  CalculateMeasure.precision(relQuery, pirQuery), 0.000000001);
		//Model 2
		 pirQueries = result.get(1).getQueries();
		 pirQuery = pirQueries.get("204");
		 relQuery = rel.get("204");
		 assertNotNull(pirQuery);
		 assertNotNull(relQuery);
		 assertEquals(0.002002002,  CalculateMeasure.precision(relQuery, pirQuery), 0.000000001);
		 pirQuery = pirQueries.get("108");
		 relQuery = rel.get("108");
		 assertNotNull(pirQuery);
		 assertNotNull(relQuery);
		 assertEquals(0.016016016,  CalculateMeasure.precision(relQuery, pirQuery), 0.000000001);
		 pirQuery = pirQueries.get("267");
		 relQuery = rel.get("267");
		 assertNotNull(pirQuery);
		 assertNotNull(relQuery);
		 assertEquals(0.006006006,  CalculateMeasure.precision(relQuery, pirQuery), 0.000000001);
		 pirQuery = pirQueries.get("197");
		 relQuery = rel.get("197");
		 assertNotNull(pirQuery);
		 assertNotNull(relQuery);
		 assertEquals(0.0,  CalculateMeasure.precision(relQuery, pirQuery), 0.000000001);
		//Model 3
		 pirQueries = result.get(2).getQueries();
		 pirQuery = pirQueries.get("204");
		 relQuery = rel.get("204");
		 assertNotNull(pirQuery);
		 assertNotNull(relQuery);
		 assertEquals(0.002002002,  CalculateMeasure.precision(relQuery, pirQuery), 0.000000001);
		 pirQuery = pirQueries.get("108");
		 relQuery = rel.get("108");
		 assertNotNull(pirQuery);
		 assertNotNull(relQuery);
		 assertEquals(0.016016016,  CalculateMeasure.precision(relQuery, pirQuery), 0.000000001);
		 pirQuery = pirQueries.get("267");
		 relQuery = rel.get("267");
		 assertNotNull(pirQuery);
		 assertNotNull(relQuery);
		 assertEquals(0.006006006,  CalculateMeasure.precision(relQuery, pirQuery), 0.000000001);
		 pirQuery = pirQueries.get("197");
		 relQuery = rel.get("197");
		 assertNotNull(pirQuery);
		 assertNotNull(relQuery);
		 assertEquals(0.0,  CalculateMeasure.precision(relQuery, pirQuery), 0.000000001);

	}
	
	@Test
	public void testRecall() {
		Map<String, Query> pirQueries;
		Query pirQuery;
		Query relQuery;
		//Model 1
		pirQueries = result.get(0).getQueries();
		pirQuery = pirQueries.get("204");
		relQuery = rel.get("204");
		assertNotNull(pirQuery);
		assertNotNull(relQuery);
		assertEquals(1.0,  CalculateMeasure.recall(relQuery, pirQuery), 0.000000001);
		pirQuery = pirQueries.get("108");
		relQuery = rel.get("108");
		assertNotNull(pirQuery);
		assertNotNull(relQuery);
		assertEquals(1.0,  CalculateMeasure.recall(relQuery, pirQuery), 0.000000001);
		pirQuery = pirQueries.get("267");
		relQuery = rel.get("267");
		assertNotNull(pirQuery);
		assertNotNull(relQuery);
		assertEquals(1.0,  CalculateMeasure.recall(relQuery, pirQuery), 0.000000001);
		pirQuery = pirQueries.get("197");
		relQuery = rel.get("197");
		assertNotNull(pirQuery);
		assertNotNull(relQuery);
		assertEquals(0.0,  CalculateMeasure.recall(relQuery, pirQuery), 0.000000001);
		//Model 2
		pirQueries = result.get(1).getQueries();
		pirQuery = pirQueries.get("204");
		relQuery = rel.get("204");
		assertNotNull(pirQuery);
		assertNotNull(relQuery);
		assertEquals(1.0,  CalculateMeasure.recall(relQuery, pirQuery), 0.000000001);
		pirQuery = pirQueries.get("108");
		relQuery = rel.get("108");
		assertNotNull(pirQuery);
		assertNotNull(relQuery);
		assertEquals(1.0,  CalculateMeasure.recall(relQuery, pirQuery), 0.000000001);
		pirQuery = pirQueries.get("267");
		relQuery = rel.get("267");
		assertNotNull(pirQuery);
		assertNotNull(relQuery);
		assertEquals(1.0,  CalculateMeasure.recall(relQuery, pirQuery), 0.000000001);
		pirQuery = pirQueries.get("197");
		relQuery = rel.get("197");
		assertNotNull(pirQuery);
		assertNotNull(relQuery);
		assertEquals(0.0,  CalculateMeasure.recall(relQuery, pirQuery), 0.000000001);
		//Model 3
		pirQueries = result.get(2).getQueries();
		pirQuery = pirQueries.get("204");
		relQuery = rel.get("204");
		assertNotNull(pirQuery);
		assertNotNull(relQuery);
		assertEquals(1.0,  CalculateMeasure.recall(relQuery, pirQuery), 0.000000001);
		pirQuery = pirQueries.get("108");
		relQuery = rel.get("108");
		assertNotNull(pirQuery);
		assertNotNull(relQuery);
		assertEquals(1.0,  CalculateMeasure.recall(relQuery, pirQuery), 0.000000001);
		pirQuery = pirQueries.get("267");
		relQuery = rel.get("267");
		assertNotNull(pirQuery);
		assertNotNull(relQuery);
		assertEquals(1.0,  CalculateMeasure.recall(relQuery, pirQuery), 0.000000001);
		pirQuery = pirQueries.get("197");
		relQuery = rel.get("197");
		assertNotNull(pirQuery);
		assertNotNull(relQuery);
		assertEquals(0.0,  CalculateMeasure.recall(relQuery, pirQuery), 0.000000001);	
	}
	
	@Test
	public void testFMeasure() {
		assertEquals(0.0,  CalculateMeasure.fMeasure(1.0, 0.0), 0.000000001);
		assertEquals(0.0,  CalculateMeasure.fMeasure(0, 1), 0.000000001);
		assertEquals(0.5,  CalculateMeasure.fMeasure(0.5, 0.5), 0.000000001);
		assertEquals(0.01980198,  CalculateMeasure.fMeasure(0.01, 1.0), 0.000000001);
	}
	
	@Test
	public void testCalculatePKRK() {
		Map<String, Query> pirQueries;
		Query pirQuery;
		Query relQuery;
		//Model 1
		pirQueries = result.get(0).getQueries();
		pirQuery = pirQueries.get("204");
		relQuery = rel.get("204");
		assertNotNull(pirQuery);
		assertNotNull(relQuery);
		assertEquals(0.0,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 1, false), 0.000000001);
		assertEquals(0.0,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 1, true), 0.000000001);
		assertEquals(0.0,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 3, false), 0.000000001);
		assertEquals(0.0,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 3, true), 0.000000001);
		assertEquals(0.2,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 5, false), 0.000000001);
		assertEquals(0.5,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 5, true), 0.000000001);
		assertEquals(0.33,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 6, false), 0.01);
		assertEquals(1.0,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 6, true), 0.000000001);
		assertEquals(0.2,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 10, false), 0.000000001);
		assertEquals(1.0,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 10, true), 0.000000001);

		pirQuery = pirQueries.get("108");
		relQuery = rel.get("108");
		assertEquals(1.0,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 1, false), 0.000000001);
		assertEquals(0.0625,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 1, true), 0.000000001);
		assertEquals(1.0,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 3, false), 0.000000001);
		assertEquals(0.1875,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 3, true), 0.000000001);
		assertEquals(1.0,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 5, false), 0.000000001);
		assertEquals(0.3125,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 5, true), 0.000000001);
		assertEquals(1.0,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 6, false), 0.000000001);
		assertEquals(0.375,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 6, true), 0.000000001);
		assertEquals(1.0,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 10, false), 0.000000001);
		assertEquals(0.625,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 10, true), 0.000000001);
		assertEquals(0.733,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 15, false), 0.01);
		assertEquals(0.6875,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 15, true), 0.000000001);
		assertEquals(0.6,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 20, false), 0.000000001);
		assertEquals(0.75,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 20, true), 0.000000001);

		pirQuery = pirQueries.get("267");
		relQuery = rel.get("267");
		assertNotNull(pirQuery);
		assertNotNull(relQuery);
		assertEquals(1.0,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 1, false), 0.000000001);
		assertEquals(0.16,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 1, true), 0.01);
		assertEquals(0.66,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 3, false), 0.01);
		assertEquals(0.33,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 3, true), 0.01);
		assertEquals(0.4,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 5, false), 0.000000001);
		assertEquals(0.33,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 5, true), 0.01);
		assertEquals(0.33,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 6, false), 0.01);
		assertEquals(0.33,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 6, true), 0.01);
		assertEquals(0.2,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 15, false), 0.000000001);
		assertEquals(0.5,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 15, true), 0.000000001);

		pirQuery = pirQueries.get("197");
		relQuery = rel.get("197");
		assertNotNull(pirQuery);
		assertNotNull(relQuery);
		assertEquals(0.0,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 1, false), 0.000000001);
		assertEquals(0.0,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 1, true), 0.000000001);
		assertEquals(0.0,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 3, false), 0.000000001);
		assertEquals(0.0,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 3, true), 0.000000001);
		assertEquals(0.0,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 5, false), 0.000000001);
		assertEquals(0.0,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 5, true), 0.000000001);
		assertEquals(0.0,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 6, false), 0.000000001);
		assertEquals(0.0,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 6, true), 0.000000001);
		assertEquals(0.0,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 15, false), 0.000000001);
		assertEquals(0.0,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 15, true), 0.000000001);

		//Model 2
		pirQueries = result.get(1).getQueries();
		pirQuery = pirQueries.get("204");
		relQuery = rel.get("204");
		assertNotNull(pirQuery);
		assertNotNull(relQuery);
		assertEquals(0.0,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 1, false), 0.000000001);
		assertEquals(0.0,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 1, true), 0.000000001);
		assertEquals(0.0,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 3, false), 0.000000001);
		assertEquals(0.0,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 3, true), 0.000000001);
		assertEquals(0.4,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 5, false), 0.000000001);
		assertEquals(1.0,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 5, true), 0.000000001);
		assertEquals(0.33,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 6, false), 0.01);
		assertEquals(1.0,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 6, true), 0.000000001);
		assertEquals(0.2,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 10, false), 0.000000001);
		assertEquals(1.0,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 10, true), 0.000000001);

		pirQuery = pirQueries.get("108");
		relQuery = rel.get("108");
		assertEquals(1.0,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 1, false), 0.000000001);
		assertEquals(0.0625,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 1, true), 0.000000001);
		assertEquals(1.0,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 3, false), 0.000000001);
		assertEquals(0.1875,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 3, true), 0.000000001);
		assertEquals(1.0,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 5, false), 0.000000001);
		assertEquals(0.3125,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 5, true), 0.000000001);
		assertEquals(1.0,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 6, false), 0.000000001);
		assertEquals(0.375,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 6, true), 0.000000001);
		assertEquals(1.0,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 10, false), 0.000000001);
		assertEquals(0.625,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 10, true), 0.000000001);
		assertEquals(0.733,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 15, false), 0.01);
		assertEquals(0.6875,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 15, true), 0.000000001);
		assertEquals(0.6,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 20, false), 0.000000001);
		assertEquals(0.75,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 20, true), 0.000000001);

		pirQuery = pirQueries.get("267");
		relQuery = rel.get("267");
		assertNotNull(pirQuery);
		assertNotNull(relQuery);
		assertEquals(0.0,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 1, false), 0.000000001);
		assertEquals(0.0,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 1, true), 0.01);
		assertEquals(0.33,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 3, false), 0.01);
		assertEquals(0.16,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 3, true), 0.01);
		assertEquals(0.4,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 5, false), 0.01);
		assertEquals(0.33,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 5, true), 0.01);
		assertEquals(0.33,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 6, false), 0.01);
		assertEquals(0.33,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 6, true), 0.01);
		assertEquals(0.1904,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 21, false), 0.001);
		assertEquals(0.66,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 21, true), 0.01);

		pirQuery = pirQueries.get("197");
		relQuery = rel.get("197");
		assertNotNull(pirQuery);
		assertNotNull(relQuery);
		assertEquals(0.0,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 1, false), 0.000000001);
		assertEquals(0.0,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 1, true), 0.000000001);
		assertEquals(0.0,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 3, false), 0.000000001);
		assertEquals(0.0,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 3, true), 0.000000001);
		assertEquals(0.0,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 5, false), 0.000000001);
		assertEquals(0.0,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 5, true), 0.000000001);
		assertEquals(0.0,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 6, false), 0.000000001);
		assertEquals(0.0,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 6, true), 0.000000001);
		assertEquals(0.0,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 15, false), 0.000000001);
		assertEquals(0.0,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 15, true), 0.000000001);
	
		//Model 3
		pirQueries = result.get(2).getQueries();
		pirQuery = pirQueries.get("204");
		relQuery = rel.get("204");
		assertNotNull(pirQuery);
		assertNotNull(relQuery);
		assertEquals(0.0,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 1, false), 0.000000001);
		assertEquals(0.0,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 1, true), 0.000000001);
		assertEquals(0.0,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 3, false), 0.000000001);
		assertEquals(0.0,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 3, true), 0.000000001);
		assertEquals(0.0,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 5, false), 0.000000001);
		assertEquals(0.0,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 5, true), 0.000000001);
		assertEquals(0.0,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 6, false), 0.01);
		assertEquals(0.0,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 6, true), 0.000000001);
		assertEquals(0.0,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 10, false), 0.000000001);
		assertEquals(0.0,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 10, true), 0.000000001);
		assertEquals(0.024390244,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 41, false), 0.000000001);
		assertEquals(0.5,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 41, true), 0.000000001);
		assertEquals(0.045454545,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 44, false), 0.000000001);
		assertEquals(1.0,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 44, true), 0.000000001);

		pirQuery = pirQueries.get("108");
		relQuery = rel.get("108");
		assertEquals(0.0,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 1, false), 0.000000001);
		assertEquals(0.0,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 1, true), 0.000000001);
		assertEquals(0.0,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 3, false), 0.000000001);
		assertEquals(0.0,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 3, true), 0.000000001);
		assertEquals(0.0,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 5, false), 0.000000001);
		assertEquals(0.0,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 5, true), 0.000000001);
		assertEquals(0.0,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 6, false), 0.000000001);
		assertEquals(0.0,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 6, true), 0.000000001);
		assertEquals(0.1,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 10, false), 0.000000001);
		assertEquals(0.0625,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 10, true), 0.000000001);
		assertEquals(0.2,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 15, false), 0.01);
		assertEquals(0.1875,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 15, true), 0.000000001);
		assertEquals(0.25,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 20, false), 0.000000001);
		assertEquals(0.3125,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 20, true), 0.000000001);

		pirQuery = pirQueries.get("267");
		relQuery = rel.get("267");
		assertNotNull(pirQuery);
		assertNotNull(relQuery);
		assertEquals(0.0,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 1, false), 0.000000001);
		assertEquals(0.0,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 1, true), 0.01);
		assertEquals(0.33,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 3, false), 0.01);
		assertEquals(0.16,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 3, true), 0.01);
		assertEquals(0.6,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 5, false), 0.01);
		assertEquals(0.5,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 5, true), 0.01);
		assertEquals(0.5,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 6, false), 0.01);
		assertEquals(0.5,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 6, true), 0.01);
		assertEquals(0.10,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 49, false), 0.01);
		assertEquals(0.83,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 49, true), 0.01);

		pirQuery = pirQueries.get("197");
		relQuery = rel.get("197");
		assertNotNull(pirQuery);
		assertNotNull(relQuery);
		assertEquals(0.0,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 1, false), 0.000000001);
		assertEquals(0.0,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 1, true), 0.000000001);
		assertEquals(0.0,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 3, false), 0.000000001);
		assertEquals(0.0,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 3, true), 0.000000001);
		assertEquals(0.0,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 5, false), 0.000000001);
		assertEquals(0.0,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 5, true), 0.000000001);
		assertEquals(0.0,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 6, false), 0.000000001);
		assertEquals(0.0,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 6, true), 0.000000001);
		assertEquals(0.0,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 15, false), 0.000000001);
		assertEquals(0.0,  CalculateMeasure.calculatePKRK(relQuery, pirQuery, 15, true), 0.000000001);
	
	}

	@Test
	public void testCalculateNDCG() {
		
		Map<String, Query> pirQueries;
		Query pirQuery;
		Query relQuery;
		//Model 1
		pirQueries = result.get(0).getQueries();
		pirQuery = pirQueries.get("204");
		relQuery = rel.get("204");
		assertNotNull(pirQuery);
		assertNotNull(relQuery);
		assertEquals(6.553889181363094,  CalculateMeasure.DCG(relQuery, pirQuery, 5), 0.000000001);
		assertEquals(9.123212623289701,  CalculateMeasure.IDCG(relQuery, 5), 0.000000001);
		assertEquals(0.718375144,  CalculateMeasure.calculateNDCG(relQuery, pirQuery, 5), 0.001);
		assertEquals(10.011053512063121,  CalculateMeasure.DCG(relQuery, pirQuery, 10), 0.000000001);
		assertEquals(12.508989023,  CalculateMeasure.IDCG(relQuery, 10), 0.000000001);
		assertEquals(0.800308761,  CalculateMeasure.calculateNDCG(relQuery, pirQuery, 10), 0.001);
		assertEquals(12.80053534667898,  CalculateMeasure.DCG(relQuery, pirQuery, 20), 0.001);
		assertEquals(15.356157471,  CalculateMeasure.IDCG(relQuery, 20), 0.001);
		assertEquals(0.833576718,  CalculateMeasure.calculateNDCG(relQuery, pirQuery, 20), 0.001);	
	}

	@Test
	public void testPrecisionRecallCurve() {
		Map<String, Query> pirQueries;
		Query pirQuery;
		Query relQuery;
		ArrayList<Pair<Integer, Double>> curve;
		//Model 1
		pirQueries = result.get(0).getQueries();
		pirQuery = pirQueries.get("204");
		relQuery = rel.get("204");
		assertNotNull(pirQuery);
		assertNotNull(relQuery);
		curve = CalculateMeasure.precisionRecallCurve(relQuery, pirQuery,true);
		assertEquals(11, curve.size());
		assertEquals(0.333333333, curve.get(0).getValue(), 0.0001);
		assertEquals(0.333333333, curve.get(10).getValue(), 0.0001);
		
		pirQuery = pirQueries.get("108");
		relQuery = rel.get("108");
		curve = CalculateMeasure.precisionRecallCurve(relQuery, pirQuery, true);
		assertEquals(11, curve.size());
		assertEquals(1, curve.get(0).getValue(), 0.0001);
		assertEquals(1, curve.get(2).getValue(), 0.0001);
		assertEquals(1, curve.get(5).getValue(), 0.0001);
		assertEquals(0.6666666666666666, curve.get(7).getValue(), 0.0001);
		assertEquals(0.34782608695652173, curve.get(10).getValue(), 0.0001);
		
		pirQuery = pirQueries.get("267");
		relQuery = rel.get("267");
		curve = CalculateMeasure.precisionRecallCurve(relQuery, pirQuery,true);
		assertNotNull(pirQuery);
		assertNotNull(relQuery);
		assertEquals(11, curve.size());
		assertEquals(1, curve.get(0).getValue(), 0.0001);
		assertEquals(1, curve.get(2).getValue(), 0.0001);
		assertEquals(0.2, curve.get(5).getValue(), 0.0001);
		assertEquals(0.11904761904761904, curve.get(7).getValue(), 0.0001);
		assertEquals(0.075, curve.get(10).getValue(), 0.0001);
		
		pirQuery = pirQueries.get("197");
		relQuery = rel.get("197");
		curve = CalculateMeasure.precisionRecallCurve(relQuery, pirQuery, true);
		assertNotNull(pirQuery);
		assertNotNull(relQuery);
		assertNull(curve);
		
	}

	@Test
	public void TestCaseAp() {
		DocumentRelFile d1 = new DocumentRelFile("1", 3);
		DocumentRelFile d2 = new DocumentRelFile("2", 3);
		DocumentRelFile d3 = new DocumentRelFile("3", 3);
		DocumentRelFile d4 = new DocumentRelFile("4", 3);
		DocumentRelFile d5 = new DocumentRelFile("5", 3);
		DocumentRelFile d6 = new DocumentRelFile("6", 1);
		DocumentRelFile d7 = new DocumentRelFile("7", 1);
		DocumentRelFile d8 = new DocumentRelFile("8", 1);
		DocumentRelFile d9 = new DocumentRelFile("9", 1);
		DocumentRelFile d10 = new DocumentRelFile("10", 1);
		Map<String,Document> docs = new HashMap<String, Document>();
		docs.put("1", d1);
		docs.put("2", d2);
		docs.put("3", d3);
		docs.put("4", d4);
		docs.put("5", d5);
		docs.put("6", d6);
		docs.put("7", d7);
		docs.put("8", d8);
		docs.put("9", d9);
		docs.put("10", d10);
		Query qRel = new QueryRelFile("user", "topic", "queryRel", docs);
		
		
		DocumentOutputPIR o1 = new DocumentOutputPIR("1", 1, 0.95);
		DocumentOutputPIR o2 = new DocumentOutputPIR("5", 2, 0.95);
		DocumentOutputPIR o3 = new DocumentOutputPIR("10", 3, 0.95);
		DocumentOutputPIR o4 = new DocumentOutputPIR("4", 4, 0.95);
		DocumentOutputPIR o5 = new DocumentOutputPIR("9", 5, 0.95);
		DocumentOutputPIR o6 = new DocumentOutputPIR("3", 6, 0.95);
		DocumentOutputPIR o7 = new DocumentOutputPIR("8", 7, 0.95);
		DocumentOutputPIR o8 = new DocumentOutputPIR("7", 8, 0.95);
		DocumentOutputPIR o9 = new DocumentOutputPIR("6", 9, 0.95);
		DocumentOutputPIR o10 = new DocumentOutputPIR("2", 10, 0.95);
		Map<String,Document> docs1 = new HashMap<String, Document>();
		docs1.put("1", o1);
		docs1.put("5", o2);
		docs1.put("10", o3);
		docs1.put("4", o4);
		docs1.put("9", o5);
		docs1.put("3", o6);
		docs1.put("8", o7);
		docs1.put("7", o8);
		docs1.put("6", o9);
		docs1.put("2", o10);
		Query qOut = new Query("user", "topic", "queryOut", docs1);

		assertEquals(0.783, CalculateMeasure.calculateAP(qRel, qOut), 0.001);
		
		Map<String, Query> pirQueries;
		Query pirQuery;
		Query relQuery;
		//Model 1
		pirQueries = result.get(0).getQueries();
		pirQuery = pirQueries.get("204");
		relQuery = rel.get("204");
		assertNotNull(pirQuery);
		assertNotNull(relQuery);
		assertEquals(0.266666667, CalculateMeasure.calculateAP(relQuery, pirQuery), 0.001);
		
		pirQueries = result.get(0).getQueries();
		pirQuery = pirQueries.get("108");
		relQuery = rel.get("108");
		assertNotNull(pirQuery);
		assertNotNull(relQuery);
		assertEquals(0.8268, CalculateMeasure.calculateAP(relQuery, pirQuery), 0.001);
	}

}
