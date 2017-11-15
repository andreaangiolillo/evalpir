package ie.dcu.evalpir;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import ie.dcu.evalpir.chart.CreatorChart;
import ie.dcu.evalpir.elements.Document;
import ie.dcu.evalpir.elements.DocumentOutputPIR;
import ie.dcu.evalpir.elements.PIR;
import ie.dcu.evalpir.elements.Path;
import ie.dcu.evalpir.elements.Query;
import ie.dcu.evalpir.elements.QueryRelFile;
import ie.dcu.evalpir.elements.Session;
import ie.dcu.evalpir.extractor.InputReaderImpl;
import ie.dcu.evalpir.measures.CalculateMeasureImpl;

/**
 * @author Andrea Angiolillo
 * @version 1.0
 * @
 * EvalEpir allows the evaluation of personalized information retrieval
 * 
 * 
 */
public class EvalEpir {
	static final String RELEVANCE_FILE_PATH = "src/main/resources/relFile.csv";
	static final String LOGS_FILE_PATH = "src/main/resources/logSFile.csv";
	
    public static void main( String[] args ) {
    	
    	File relevanceFile = new File(RELEVANCE_FILE_PATH);
    	File logsFile = new File(LOGS_FILE_PATH);
    	File outputPIR = new File("src/main/resources/a.csv");
    	
    	
    	Map<String, Query> qRel = InputReaderImpl.extractRelevanceFile(relevanceFile);
    	ArrayList<PIR> pirs = InputReaderImpl.extractOutputPIR(outputPIR);
    	Map<String, Session> logs = InputReaderImpl.extracLogFile(logsFile);
    	
    	System.out.println("----------------------Print RELEVANCE File----------------------------------\n\n");    	
    	Iterator<Entry<String, Query>> itd = qRel.entrySet().iterator();
    	
//    	while (itd.hasNext()) {
//    		System.out.println(itd.next().getValue().toString());
//    	}
//    	
//    	System.out.println("\n\n----------------------Print Logs File----------------------------------\n\n");
//        Iterator<Entry<String, Session>> it = logs.entrySet().iterator();
//    	
//        while(it.hasNext()) {
//        	Entry<String, Session> a = it.next();
//        	if(a.getValue().getSessionMeasure()) {
//        		System.out.println(a.getValue().toString());
//        	}
//        
//        }
//    	
//    	
    	System.out.println("----------------------Print OUTPUT File----------------------------------\n\n");
    	
    	for (PIR p : pirs) {
    		//System.out.println(p.toString());
    	}
    	
    	
    	
    	
    	System.out.println("\n\n----------------------Print Measures----------------------------------\n\n");
    	
    	CalculateMeasureImpl m = new CalculateMeasureImpl(qRel, logs);
    	
    	m.calculateMeasures(pirs);
    	Iterator<Entry<String, Query>> itm = qRel.entrySet().iterator();
    	
//    	while (itm.hasNext()) {
//    		System.out.println(((QueryRelFile)itm.next().getValue()).printMeasures());
//    	}
//    	
//    	for(Query q : qRel) {
//    		//System.out.print(((QueryRelFile)q).printMeasures());
//    	}
//    	

//    	Path p = new Path();
//    	ArrayList<Path> memo = new ArrayList<Path>();
//    	
//    	ArrayList<Integer> input = new ArrayList<Integer>();
//    
//    	
//    	ArrayList<ArrayList<Integer>> a = new ArrayList<ArrayList<Integer>>();
//    	//CalculateMeasureImpl.partition(60,15, 5, input, a);
    	
//    	Path p = new Path(4);
//    	Map <String, ArrayList<Path>> memo = new HashMap<String, ArrayList<Path>>();
//    	CalculateMeasureImpl.findPaths(44, 11, 4, p, memo);
    	
    	
		CreatorChart.createChart(qRel);
		
    	
    	
    	
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
//    	
//    	InputCreator input = new InputCreatorImpl();
//    	input.generateFilesInput(2, 2);
    	
      
//    	InputReaderImpl reader = new InputReaderImpl();
//    
//		ArrayList<User>  qRel = reader.extract(RELEVANCE_FILE_PATH, true);
//    	ArrayList<User>  result = reader.extract("src/main/resources/result.test.nUser.5.nTopic.6.Fri Sep 29 10:31:28 IST 2017.csv", false);
//    	
//    	System.out.println(result.get(0).getTopics().get(1).getQueries().get(0).getDocs().size());
//    	System.out.println(result.get(4).toString());
//    	System.out.println("Number of users:" + result.size() );
//    	
//    	Query qRelevance = qRel.get(0).getTopics().get(0).findQuery("1");
//    	Query qResult = result.get(0).getTopics().get(0).findQuery("1");;
//
//    	MeasureImpl m = new MeasureImpl();
//    	m.evaluationProcess(qRel, result, 5);
//    	System.out.println(m.calculateNDCG(qRelevance, qResult, 5));
    	

    	
//    	System.out.print(qRelevance.entriesSortedByValues(qRelevance.getDocs()));
    	
    	//System.out.println(m.calculatePK(qRelevance, qResult, 20));
    	//m.meanAverageMeasure(qRel, result, 10);
    	
////    	
    	
//    	System.out.println(qRel.get("0MOVS-AP1").toString());
//    	
//    	for (String[] string : input2.get("0MOVS-YP6")) {
//    		System.out.println(string[0] + " " + string[1] + " " + string[2]);
//			
//		}
//    	System.out.println(input1.get("0MOVS-AP")[1]);
//    	System.out.println(input1.get("0MOVS-AP")[2]);
//    	CheckInput checker = new CheckInputImpl();
//    	if (!checker.checkInputFiles( args )){
//    		throw new InvalidInputException("The input files don't meet the constrains");
//    	}
    	
    }
    
    
    public static void randomPIR(PIR p) {
    	
    	PIR p1 = new PIR(p);
    	Map<String, Query> qs = p1.getQueries();
    	Map<String, Document> dcs;
    	Iterator<Entry<String, Document>> it;
    	Random rand = new Random();
    	int  n = rand.nextInt(1000) + 1;
    	HashSet<Integer> listRandom = new HashSet<Integer>();
    	PrintWriter pw = null;
    	DocumentOutputPIR d;
    	try {
    	    pw = new PrintWriter(new File("src/main/resources/NewData.csv"));
    	} catch (FileNotFoundException e) {
    	    e.printStackTrace();
    	}
    	StringBuilder builder = new StringBuilder();
    	
    	//50 is the maximum and the 1 i
    	Iterator <Entry<String, Query>> its = qs.entrySet().iterator();
    	while (its.hasNext()){
    		Query q = its.next().getValue();
    		dcs = q.getDocs();
    		it = dcs.entrySet().iterator();
    		while(it.hasNext()) {
    			while(listRandom.contains(n)) {
    				n = rand.nextInt(1000) + 1;
    			}
    			
    			listRandom.add(n);
    			d = ((DocumentOutputPIR)it.next().getValue());
    			d.setRank(n);
    			builder.append(q.getUser() + "," + q.getTopic()+ "," + q.getId()+ "," + d.getId()+ "," + d.getRank()+ "," + d.getSimilarity() + "," + "random");
    			builder.append("\n");
    		}
    		
    		listRandom = new HashSet<Integer>();
    	}
    	pw.write(builder.toString());
    	pw.close();
    	
    	
    	
    	
    }
       
}
































