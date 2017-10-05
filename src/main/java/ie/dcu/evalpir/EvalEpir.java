package ie.dcu.evalpir;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.swing.RowFilter.Entry;

import ie.dcu.evalpir.checking.CheckInput;
import ie.dcu.evalpir.checking.CheckInputImpl;
import ie.dcu.evalpir.elements.Document;
import ie.dcu.evalpir.elements.Query;
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
 * @see https://github.com/ctongfei/progressbar
 */
public class EvalEpir {
    public static void main( String[] args ) {
    	
    	ProgressBar pb = new ProgressBar("Test", 100).start(); 
    	
    	pb.stepBy(50);
    	for (int i = 0; i<10000000 ; i ++) {
    		
    	}
    	pb.stepBy(50);
    	pb.stop();
    	
//    	InputCreator input = new InputCreatorImpl();
//    	input.generateFilesInput(2, 2);
    	
      
//    	InputReaderImpl reader = new InputReaderImpl();
//    
//		ArrayList<User>  qRel = reader.extract("src/main/resources/qrels.test.nUser.5.nTopic.6.Fri Sep 29 10:31:28 IST 2017.csv", true);
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
