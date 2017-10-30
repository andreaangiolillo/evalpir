package ie.dcu.evalpir.extractor;


import java.io.File;
import java.util.ArrayList;

import ie.dcu.evalpir.elements.PIR;
import ie.dcu.evalpir.elements.User;

public interface InputReader {
	
	/**
	 * This method extract the data from the input CSV to create a data structure. 
	 * @param csvFile
	 * @return Arraylist<User>
	 * 
	 */
	public ArrayList<User> extractRelevanceFile(File file);
	
	
	/**
	 * This method extract the data from the input CSV to create a data structure. 
	 * @param csvFile
	 * @return ArrayList<PIR>
	 * 
	 */
	public ArrayList<PIR> extractOutputPIR(File file);
	
}
