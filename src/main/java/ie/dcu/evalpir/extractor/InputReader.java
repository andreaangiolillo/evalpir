package ie.dcu.evalpir.extractor;


import java.util.ArrayList;
import ie.dcu.evalpir.elements.User;

public interface InputReader {
	
	/**
	 * This method extract the data from the input CSV to create a data structure. 
	 * @param csvFile
	 * @param isRealFile -> true: the file to read is the qRels.test. false: the file to read is the result.test
	 * @return map
	 * 
	 */
	public ArrayList<User> extract(String csvFile, boolean isRelevanceFile);

}
