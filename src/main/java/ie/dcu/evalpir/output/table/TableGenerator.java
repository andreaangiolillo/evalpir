package ie.dcu.evalpir.output.table;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import de.vandermeer.asciitable.AsciiTable;
import ie.dcu.evalpir.EvalEpir;
import ie.dcu.evalpir.elements.Query;
import ie.dcu.evalpir.elements.QueryRelFile;
import ie.dcu.evalpir.elements.Topic;

public class TableGenerator {
	
	public static void printExample() {
		AsciiTable at = new AsciiTable();
		at.addRule();
		at.addRow("User: userId", "Topic: topicId", "Query: queryId", "S1 - Si", "(Si-1) - Si");
		at.addRule();
		at.addRow("Measure@K", "SystemName1", "Value",  "S1 - Si", "(Si-1) - Si");
		at.addRule();
		at.addRow("", "SystemName2", "Value",  "S1 - Si", "(Si-1) - Si");
		at.addRule();
		at.addRow("", "SystemName3", "Value",  "S1 - Si", "(Si-1) - Si");
		at.addRule();
		at.addRow("Measure@J", "SystemName3", "Value",  "S1 - Si", "(Si-1) - Si");
		at.addRule();
		at.addRow("", "SystemName1", "Value",  "S1 - Si", "(Si-1) - Si");
		at.addRule();
		at.addRow("", "SystemName2", "Value",  "S1 - Si", "(Si-1) - Si");
		at.addRule();
	
		System.out.println(at.render());
	}
	
	/**
	 * @param sessionMeasures
	 */
	public static void printMeasures(Map<String, Topic> sessionMeasures) {
		ConsolePrinter.startTask("Creating output.csv");
		PrintStream out;
		try {
			out = new PrintStream(new FileOutputStream("output.txt"));
			System.setOut(out);
			System.out.println("Example:");
			printExample();
	    	System.out.println("\n----------------------Print Measures----------------------------------\n");
	    	for(Map.Entry<String, Query> entry : EvalEpir.QUERYREL.entrySet()) {
	    		System.out.println(((QueryRelFile)entry.getValue()).printMeasures());
	    	}

			System.out.println("\n----------------------Print Session Measures----------------------------------\n");   
			for(Map.Entry<String, Topic> entry : sessionMeasures.entrySet()) {
				System.out.println(entry.getValue().printMeasures());
			}
	    	
	    	System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
	    	ConsolePrinter.endTask("Creating output.csv");
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
	
	
	
	
	
//	ProgressBar pb = new ProgressBar("Test", 100).start(); 
	
	
//	for (int i = 1; i<=10; i++) {
//		pb.stepBy(10);
//		pb.maxHint(100);
//	}
//	pb.stop();
//	
	
	
//    double x = 1.0;
//    double y = x * x;
//
//    ArrayList<Integer> l = new ArrayList<Integer>();
//
//    System.out.println("\n\n\n\n\n");
//
//    for (int i = 0; i < 10000; i++) {
//        int sum = 0;
//        for (int j = 0; j < i * 2000; j++)
//            sum += j;
//        l.add(sum);
//
//        pb.step();
//        if (pb.getCurrent() > 1) pb.maxHint(10000);
//
//    }
//    pb.stop();
	


}
