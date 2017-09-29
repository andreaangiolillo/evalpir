package ie.dcu.evalpir.input;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Andrea Angiolillo
 * @version 1.0
 * 
 * The interface allows to create the input files to test the software
 * 
 * */

public interface InputCreator {
	
	/**This method allows to create the input files to test the software
	 * @param nUser number of users to be created
	 * @param nTopic number of topics to be created
	 * @see the file is saved in src/main/resources/
	 */
	public void generateFilesInput(int nUser, int nTopic);
	
	/**It creates the csv file from the ArrayList in input
	 * @param input
	 * @param filepath
	 * @return true/false 
	 */
	public boolean generateCSV(ArrayList<String> input, String filepath);

}
