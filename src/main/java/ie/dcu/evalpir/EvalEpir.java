package ie.dcu.evalpir;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.swing.RowFilter.Entry;

import ie.dcu.evalpir.chart.CreatorChart;
import ie.dcu.evalpir.checking.CheckInput;
import ie.dcu.evalpir.checking.CheckInputImpl;
import ie.dcu.evalpir.elements.Document;
import ie.dcu.evalpir.elements.PIR;
import ie.dcu.evalpir.elements.Query;
import ie.dcu.evalpir.elements.QueryOutputPIR;
import ie.dcu.evalpir.elements.QueryRelFile;
import ie.dcu.evalpir.elements.Topic;
import ie.dcu.evalpir.elements.User;
import ie.dcu.evalpir.exceptions.InvalidInputException;
import ie.dcu.evalpir.extractor.InputReader;
import ie.dcu.evalpir.extractor.InputReaderImpl;
import ie.dcu.evalpir.input.InputCreator;
import ie.dcu.evalpir.input.InputCreatorImpl;
import ie.dcu.evalpir.measures.Measure;
import ie.dcu.evalpir.measures.MeasureImpl;
import me.tongfei.progressbar.ProgressBar;

/**
 * @author Andrea Angiolillo
 * @version 1.0
 * @
 * EvalEpir allows the evaluation of personalized information retrieval
 * 
 * 
 */
public class EvalEpir {
	static final String RELEVANCE_FILE_PATH = "src/main/resources/qrels.test.nUser.2.nTopic.2.Tue Oct 03 12:55:33 IST 2017.csv";
	
//	public static void printOutput(ArrayList<PIR> pirs){
//		int nUser = pirs.get(0).getUsers().size();
//		int nTopic = 0;
//		int nQuery = 0;
//		int nMeasures = 0;
//		for (int user = 0; user < nUser; user++) {
//			nTopic = pirs.get(0).getUsers().get(user).getTopics().size();
//			for (int topic = 0; topic < nTopic; topic ++) {
//				nQuery = pirs.get(0).getUsers().get(user).getTopics().get(topic).getQueries().size();
//				for (int query = 0; query < nQuery; query ++){
//					nMeasures = ((QueryOutputPIR)(pirs.get(0).getUsers().get(user).getTopics().get(topic).getQueries().get(query))).getMeasures().size();
//					
//				}
//			}
//		}
//		
//	}
	
	
    public static void main( String[] args ) {
    	
    	File relevanceFile = new File(RELEVANCE_FILE_PATH);
    	File outputPIR = new File("src/main/resources/result.test.nUser.2.nTopic.2.Tue Oct 03 12:55:33 IST 2017.csv");
    	
    	InputReaderImpl reader = new InputReaderImpl();
    	
    	ArrayList<Query>  qRel = reader.extractRelevanceFile(relevanceFile);
    	ArrayList<PIR> pirs = reader.extractOutputPIR(outputPIR);
    	
    	System.out.println("----------------------Print RELEVANCE File----------------------------------\n\n");    	
    	for (Query s : qRel) {
    		//System.out.println(s.toString());
    	}
    	
    	System.out.println("----------------------Print OUTPUT File----------------------------------\n\n");
    	
    	for (PIR p : pirs) {
    		//System.out.println(p.toString());
    	}
    	
    	System.out.println("\n\n----------------------Print Measures----------------------------------\n\n");
    	
    	MeasureImpl m = new MeasureImpl(qRel);
    	
    	m.calculateMeasures(pirs);
    	
    	for(Query q : qRel) {
    		System.out.print(((QueryRelFile)q).printMeasures());
    	}
    	

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
    
       
}
