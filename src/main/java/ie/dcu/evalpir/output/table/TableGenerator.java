package ie.dcu.evalpir.output.table;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import de.vandermeer.asciitable.AsciiTable;
import ie.dcu.evalpir.elements.Query;
import ie.dcu.evalpir.elements.QueryRelFile;
import ie.dcu.evalpir.elements.Topic;

public class TableGenerator {
	
	public static void printExample() {
		AsciiTable at = new AsciiTable();
		at.addRule();
		at.addRow("User: userId", "Topic: topicId", "Query: queryId", "S1 - Si", "(Si-1) - Si");
		at.addRule();
		at.addRow("Measure@K", "ModelName1", "Value",  "S1 - Si", "(Si-1) - Si");
		at.addRule();
		at.addRow("", "ModelName2", "Value",  "S1 - Si", "(Si-1) - Si");
		at.addRule();
		at.addRow("", "ModelName3", "Value",  "S1 - Si", "(Si-1) - Si");
		at.addRule();
		at.addRow("Measure@J", "ModelName3", "Value",  "S1 - Si", "(Si-1) - Si");
		at.addRule();
		at.addRow("", "ModelName1", "Value",  "S1 - Si", "(Si-1) - Si");
		at.addRule();
		at.addRow("", "ModelName2", "Value",  "S1 - Si", "(Si-1) - Si");
		at.addRule();
	
		System.out.println(at.render());
	}
	
	public static void printMeasures(Map<String, Query> relevantQueries, Map<String, Topic> sessionMeasures) {
		
		PrintStream out;
		try {
			out = new PrintStream(new FileOutputStream("output.txt"));
			System.setOut(out);
			System.out.println("Example:");
			printExample();
	    	System.out.println("\n----------------------Print Measures----------------------------------\n");
	    	Iterator<Entry<String, Query>> itm = relevantQueries.entrySet().iterator();
	    	Query q ;
	    	while (itm.hasNext()) {
	    		q = itm.next().getValue();
	    			((QueryRelFile)q).printMeasures(q.getUser(), q.getTopic());
	    	}

			System.out.println("\n----------------------Print Session Measures----------------------------------\n");   	
	    	Iterator<Entry<String, Topic>> itTopic = sessionMeasures.entrySet().iterator();
	    	while(itTopic.hasNext()) {
	    		itTopic.next().getValue().printMeasures();	    		
	    	}
	    	
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
	
	


}
