package ie.dcu.evalpir;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import ie.dcu.evalpir.elements.PIR;
import ie.dcu.evalpir.elements.Query;
import ie.dcu.evalpir.elements.QueryRelFile;
import ie.dcu.evalpir.elements.Session;
import ie.dcu.evalpir.elements.Topic;
import ie.dcu.evalpir.extractor.InputReader;
import ie.dcu.evalpir.measures.CalculateMeasure;
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
	public static final String COMMANDS_FILE_PATH = "src/main/resources/commands.txt";

	public static Set<String> MEASURES_FOR_CHART;
	
	/**Commands**/
	public static boolean CHART = true;
	public static boolean SESSION_METHOD_1 = false;
	public static boolean SESSION_METHOD_2 = false;
	public static boolean SESSION_METHOD_3 = false;
	/**/
	
	public static  Map<String, Session> LOGS = null;
	public static  Map<String, Query> QUERYREL = null;
	public static  ArrayList<PIR> MODELS = null;
	
    public static void main(String[] args ) {
    	//String[] args1 = {COMMANDS_FILE_PATH, RELEVANCE_FILE_PATH, LOGS_FILE_PATH, "src/main/resources/NewData100.csv","src/main/resources/resultChanged.csv"};

    	ConsolePrinter.startEval();
    	  	
    	File commandsFile = new File(args[0]);
    	File relevanceFile = new File(args[1]);
    	File logsFile = new File(args[2]);

    	InputReader.extractCommands(commandsFile);
    	setQUERYREL(InputReader.extractRelevanceFile(relevanceFile));
    	setLOGS(InputReader.extracLogFile(logsFile));
    	setMODELS(extractingModels(args));
  
    	
		CalculateMeasure.calculateMeasures();
    	Map<String, Topic> measures = CalculateSessionMeasure.calculateSessionMeasure(); 
      		
    	if(CHART) {
    		CreatorChart.createChart(QUERYREL);
    		CreatorChart.createChartSession(measures);		
    	
    	}
			
		TableGenerator.printMeasures(measures);		
    }
    
    /**
     * 
     * @param args
     * @return
     */
    public static ArrayList<PIR> extractingModels(String[] args) {
    	File outputPIR; 
    	ArrayList<PIR> allModel = new ArrayList<PIR>();
    	for (int i = 3; i < args.length; i++) {
    		outputPIR = new File(args[i]);
    		allModel.addAll(InputReader.extractOutputPIR(outputPIR));	
    	}
    	
    	return allModel;
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
        	System.out.println(a.getValue().toString());
        	
        
        }
	}
    
	public static void printModelsFile() {
    	System.out.println("----------------------Print OUTPUT File----------------------------------\n\n");	
    	for (PIR p : MODELS) {
    		System.out.println(p.toString());
    	}
	}
	
	public static void printAllMeasures() {
		CalculateMeasure.calculateMeasures();
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
}
































