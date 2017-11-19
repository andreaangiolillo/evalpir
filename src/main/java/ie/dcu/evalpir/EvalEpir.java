package ie.dcu.evalpir;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import ie.dcu.evalpir.chart.CreatorChart;
import ie.dcu.evalpir.elements.AbstractMeasure;
import ie.dcu.evalpir.elements.Document;
import ie.dcu.evalpir.elements.DocumentOutputPIR;
import ie.dcu.evalpir.elements.Measure;
import ie.dcu.evalpir.elements.PIR;
import ie.dcu.evalpir.elements.Path;
import ie.dcu.evalpir.elements.Query;
import ie.dcu.evalpir.elements.QueryRelFile;
import ie.dcu.evalpir.elements.Session;
import ie.dcu.evalpir.elements.Topic;
import ie.dcu.evalpir.extractor.InputReaderImpl;
import ie.dcu.evalpir.measures.CalculateMeasureImpl;
import me.tongfei.progressbar.ProgressBar;

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
	static final String RELEVANCE_FILE_PATH = "src/main/resources/relFile.csv";
	static final String LOGS_FILE_PATH = "src/main/resources/logSFile.csv";
	
    public static void main( String[] args ) {
    	
//    	File relevanceFile = new File(RELEVANCE_FILE_PATH);
//    	File logsFile = new File(LOGS_FILE_PATH);
//    	File outputPIR = new File("src/main/resources/ar.csv");
//    	
    	File relevanceFile = new File(args[0]);
    	File outputPIR = new File(args[1]); 
    	File logsFile = new File(args[2]);
    	   	
    	
    	Map<String, Query> qRel = InputReaderImpl.extractRelevanceFile(relevanceFile);
    	ArrayList<PIR> pirs = InputReaderImpl.extractOutputPIR(outputPIR);
    	Map<String, Session> logs = InputReaderImpl.extracLogFile(logsFile);
//    	
//    	System.out.println("----------------------Print RELEVANCE File----------------------------------\n\n");    	
//    	Iterator<Entry<String, Query>> itd = qRel.entrySet().iterator();
//    	Query q1;
//    	while (itd.hasNext()) {
//    		q1 = itd.next().getValue();
//    		
//    		if(q1.getId().equals("122") ) {
//    			System.out.println(q1.toString());
//    			System.out.println(((QueryRelFile)q1).getNRelevantDoc());
//    		}
    		
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
 //   	System.out.println("----------------------Print OUTPUT File----------------------------------\n\n");
    	
//    	for (PIR p : pirs) {
//    		//System.out.println(p.toString());
//    	}
    	
    	
    	
    	
		ProgressBar progressBar = new ProgressBar("Measures Calculation ", 100).start(); // progressbar
    	CalculateMeasureImpl m = new CalculateMeasureImpl(qRel, logs);
   	
    	m.calculateMeasures(pirs);
    	progressBar.stepTo(100);
    	progressBar.stop();
    	System.out.print("\n");
//    	
// 	System.out.println("\n\n----------------------Print Measures----------------------------------\n\n");
//    	Iterator<Entry<String, Query>> itm = qRel.entrySet().iterator();
//    	Query q ;
//    	while (itm.hasNext()) {
//    		q = itm.next().getValue();
//    		//if(q.getUser().equals("121")) {
//    			System.out.println(((QueryRelFile)q).printMeasures());
//    		//}
//    		
//    		
//    	}
////    	
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
//    	System.out.println("\n\n----------------------Print Session Measures----------------------------------\n\n");
    	
    	
//    	Map<String, Topic> measures = m.calculateSessionMeasure(pirs);    	
//    	
//    	Iterator<Entry<String, Topic>> itTopic = measures.entrySet().iterator();
//    	Iterator<Entry<String, AbstractMeasure>> itM;
//    	while(itTopic.hasNext()) {
//    		
//    		System.out.println(itTopic.next().getValue().printMeasures());
//    		
//    	}
    	
    
    	
    	/*----------------------Print Session Measures on .txt----------------------------------*/
    	
    	PrintStream out;
		try {
			out = new PrintStream(new FileOutputStream("output.txt"));
			System.setOut(out);
	    	System.out.println("\n\n----------------------Print Measures----------------------------------\n\n");
	    	Iterator<Entry<String, Query>> itm = qRel.entrySet().iterator();
	    	Query q ;
	    	while (itm.hasNext()) {
	    		q = itm.next().getValue();
	    			System.out.println(((QueryRelFile)q).printMeasures());
	    	}

			System.out.println("\n\n----------------------Print Session Measures----------------------------------\n\n");
	    	Map<String, Topic> measures = m.calculateSessionMeasure(pirs);    	
	    	
	    	Iterator<Entry<String, Topic>> itTopic = measures.entrySet().iterator();
	    	Iterator<Entry<String, AbstractMeasure>> itM;
	    	while(itTopic.hasNext()) {
	    		
	    		System.out.println(itTopic.next().getValue().printMeasures());
	    		
	    	}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    
    public static void randomPIR(PIR p) {
    	
    	PIR p1 = new PIR(p);
    	Map<String, Query> qs = p1.getQueries();
    	Map<String, Document> dcs;
    	Iterator<Entry<String, Document>> it;
    	Random rand = new Random();
    	int  n = rand.nextInt(10) + 1;
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
    			d = ((DocumentOutputPIR)it.next().getValue());
    			if (d.getRank()<11) {
    				while(listRandom.contains(n)) {
        				n = rand.nextInt(10) + 1;
        			}
    				listRandom.add(n);
    				d.setRank(n);
    			}
    			
    			builder.append(q.getUser() + "," + q.getTopic()+ "," + q.getId()+ "," + d.getId()+ "," + d.getRank()+ "," + d.getSimilarity() + "," + "random");
    			builder.append("\n");
    			
    		}
    		
    		listRandom = new HashSet<Integer>();
    	}
    	pw.write(builder.toString());
    	pw.close();

    }   
}
































