package ie.dcu.evalpir;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.antlr.v4.codegen.model.ThrowNoViableAlt;
import org.antlr.v4.parse.BlockSetTransformer.setAlt_return;

import java.util.Random;

import ie.dcu.evalpir.elements.AbstractMeasure;
import ie.dcu.evalpir.elements.Document;
import ie.dcu.evalpir.elements.DocumentOutputPIR;
import ie.dcu.evalpir.elements.Measure;
import ie.dcu.evalpir.elements.PIR;
import ie.dcu.evalpir.elements.Query;
import ie.dcu.evalpir.elements.QueryRelFile;
import ie.dcu.evalpir.elements.Session;
import ie.dcu.evalpir.elements.Topic;
import ie.dcu.evalpir.exceptions.InvalidInputException;
import ie.dcu.evalpir.extractor.InputReaderImpl;
import ie.dcu.evalpir.measures.CalculateMeasureImpl;
import ie.dcu.evalpir.measures.CalculateSessionMeasure;
import ie.dcu.evalpir.output.chart.CreatorChart;
import ie.dcu.evalpir.output.table.ConsolePrinter;
import ie.dcu.evalpir.output.table.TableGenerator;


/**
 * @author Andrea Angiolillo
 * @version 1.0
 * @
 * EvalEpir allows the evaluation of personalized information retrieval
 * 
 * 
 */
//clean package assembly:single
public class EvalEpir {
	public static final String RELEVANCE_FILE_PATH = "src/main/resources/relFile.csv";
	public static final String LOGS_FILE_PATH = "src/main/resources/logSFile.csv";
	public static  Map<String, Session> LOGS = null;
	public static  Map<String, Query> QUERYREL = null;
	public static  ArrayList<PIR> MODELS = null;
	
    public static void main( String[] args ) {
    	
//    	File relevanceFile = new File(RELEVANCE_FILE_PATH);
//    	File logsFile = new File(LOGS_FILE_PATH);
//    	File outputPIR = new File("src/main/resources/ar.csv");
//    	
    	ConsolePrinter.startEval();
    	
    	String[] args1 = {RELEVANCE_FILE_PATH, LOGS_FILE_PATH, "src/main/resources/model1.csv","src/main/resources/model2.csv","src/main/resources/model3.csv"};
    	
//    	if(args.length < 3) {
//    		throw new InvalidInputException("The input file must be 3 at least: \n1) Relevance Documents \n2) LogsFile \n3) System to be evaluated ");
//    	}
//    	
    	File relevanceFile = new File(args1[0]);
    	File logsFile = new File(args1[1]);

    	
    	setQUERYREL(InputReaderImpl.extractRelevanceFile(relevanceFile));
    	setLOGS(InputReaderImpl.extracLogFile(logsFile));
    	setMODELS(extractingModels(args1));
    	
    //	setMODELS(InputReaderImpl.extractOutputPIR(outputPIR));
   	  	
    
		CalculateMeasureImpl.calculateMeasures();
    	Map<String, Topic> measures = CalculateSessionMeasure.calculateSessionMeasure(); 
      		
    	
		CreatorChart.createChart(QUERYREL);
		CreatorChart.createChartSession(measures);		
		TableGenerator.printMeasures(measures);
    	
    	
    	
    	
    	
//    	ProgressBar pb = new ProgressBar("Test", 100).start(); 
    	
    	
//    	for (int i = 1; i<=10; i++) {
//    		pb.stepBy(10);
//    		pb.maxHint(100);
//    	}
//    	pb.stop();
//    	
    	
    	
//        double x = 1.0;
//        double y = x * x;
//
//        ArrayList<Integer> l = new ArrayList<Integer>();
//
//        System.out.println("\n\n\n\n\n");
//
//        for (int i = 0; i < 10000; i++) {
//            int sum = 0;
//            for (int j = 0; j < i * 2000; j++)
//                sum += j;
//            l.add(sum);
//
//            pb.step();
//            if (pb.getCurrent() > 1) pb.maxHint(10000);
//
//        }
//        pb.stop();		
    }
    
    /**
     * 
     * @param args
     * @return
     */
    public static ArrayList<PIR> extractingModels(String[] args) {
    	File outputPIR; 
    	ArrayList<PIR> allModel = new ArrayList<PIR>();
    	for (int i = 2; i < args.length; i++) {
    		outputPIR = new File(args[i]);
    		allModel.addAll(InputReaderImpl.extractOutputPIR(outputPIR));	
    	}
    	
    	return allModel;
    }

	public static Map<String, Session> getLOGS() {
		return LOGS;
	}

	public static void setLOGS(Map<String, Session> lOGS) {
		LOGS = lOGS;
	}

	public static Map<String, Query> getQUERYREL() {
		return QUERYREL;
	}

	public static void setQUERYREL(Map<String, Query> qUERYREL) {
		QUERYREL = qUERYREL;
	}

	public static ArrayList<PIR> getMODELS() {
		return MODELS;
	}

	public static void setMODELS(ArrayList<PIR> mODELS) {
		MODELS = mODELS;
	}

	public static void printRelevantFile() {
	
		System.out.println("----------------------Print RELEVANT File----------------------------------\n\n");    	
		Iterator<Entry<String, Query>> itd = QUERYREL.entrySet().iterator();
		Query q1;
		while (itd.hasNext()) {
			q1 = itd.next().getValue();
//			if(q1.getId().equals("122") ) {
				System.out.println(q1.toString());
				System.out.println(((QueryRelFile)q1).getNRelevantDoc());
//			}
			
		}
	}
	
	public static void printLogsFile() {
    	
    	System.out.println("\n\n----------------------Print Logs File----------------------------------\n\n");
        Iterator<Entry<String, Session>> it = LOGS.entrySet().iterator();
    	
        while(it.hasNext()) {
        	Entry<String, Session> a = it.next();
        	if(a.getValue().getSessionMeasure()) {
        		System.out.println(a.getValue().toString());
        	}
        
        }
	}
    
	public static void printModelsFile() {
    	System.out.println("----------------------Print OUTPUT File----------------------------------\n\n");	
    	for (PIR p : MODELS) {
    		System.out.println(p.toString());
    	}
	}
	
	public static void printAllMeasures() {
		CalculateMeasureImpl.calculateMeasures();
    	Map<String, Topic> measures = CalculateSessionMeasure.calculateSessionMeasure(); 
    	System.out.println("\n----------------------Print Measures----------------------------------\n");
    	Iterator<Entry<String, Query>> itm = QUERYREL.entrySet().iterator();
    	Query q ;
    	while (itm.hasNext()) {
    		q = itm.next().getValue();
    			((QueryRelFile)q).printMeasures();
    	}

		System.out.println("\n----------------------Print Session Measures----------------------------------\n");   	
    	Iterator<Entry<String, Topic>> itTopic = measures.entrySet().iterator();
    	while(itTopic.hasNext()) {
    		itTopic.next().getValue().printMeasures();	    		
    	}
	}
    
    
    
}
































