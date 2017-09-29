package ie.dcu.evalpir.input;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.stream.Collectors;
import ie.dcu.evalpir.exceptions.InvalidInputException;

/**
 * @author Andrea Angiolillo
 * @version 1.0
 * 
 * 
 * 
 * */

public class InputCreatorImpl implements InputCreator{
	
	static final int MAX_TOPIC = 13; // max number of topics
	static final int MAX_EVAL_DOC = 20; // number of documents evaluated for each query
	static final int MAX_RETR_DOC = 20; // number of retrieved documents for each query in the result file
	static final int MAX_QUERY = 5; // number of queries for each topics 
	static final String[] TOPIC_ID = {"MOVS-AP","MOVS-YP","SPRT-YP", "SPRT-KS", "TRVL-BT", "TRVL-VT","ART-Y","ART-A","BKS-A", "BKS-Y","MSC-Y","MSC-A"};
	
	
	/**This method allows to create the input files to test the software
	 * @param nUser number of users to be created
	 * @param nTopic number of topics to be created
	 * @see the file is saved in src/main/resources/
	 */
	public void generateFilesInput(int nUser, int nTopic) {
		if (nTopic > MAX_TOPIC) {
			throw new InvalidInputException("Too many topics");
		}
		
		Date time = new Date();
		ArrayList<String> qRels = generateQrels(nUser, nTopic);
		generateCSV(qRels, "src/main/resources/qrels.test." + "nUser." + nUser + ".nTopic." + nTopic + "." + time.toString() + ".csv");
		ArrayList<String> result = generateResult(nUser, nTopic);
		generateCSV(result, "src/main/resources/result.test." + "nUser." + nUser + ".nTopic." + nTopic + "." + time.toString() + ".csv");
		
	}
	
	
	/**
	 * @param nUser
	 * @param nTopic
	 * @return inputFile
	 */
	public ArrayList<String> generateQrels(int nUser, int nTopic) {
		
		ArrayList<String> inputFile = new ArrayList<String>();
		inputFile.add("UserId, TopicId, QueryId, DocId, Rel \n");
		Random rand = new Random();
		int documentId, queryId;
		documentId = queryId = 1;
		int relevance;
		
		for(int i = 0; i < nUser; i ++) {
			for(int j = 0; j < nTopic; j++) {
				for (int y = 0; y < MAX_QUERY; y++) {
					for(int k = 0; k < MAX_EVAL_DOC; k++) {
						if (rand.nextInt(100) > 75) {
							relevance = rand.nextInt(2) + 3;
							//System.out.println(relevance);
						}else {
							relevance = rand.nextInt(2) + 1;
							//System.out.println(relevance);
						}
						inputFile.add( i + ", " + TOPIC_ID[j] + ", " + queryId + ", " + "clueweb12-0100tw-" + documentId + ", " + relevance + "\n");
						documentId ++;
					}
					queryId ++;
				}
			}
		}
		//System.out.println(inputFile.toString());
		return inputFile;
	}
	
	/**
	 * @param nUser
	 * @param nTopic
	 * @return inputFile
	 */
	public ArrayList<String> generateResult(int nUser, int nTopic) {
		ArrayList<String> inputFile = new ArrayList<String>();
		inputFile.add("UserId, TopicId, QueryId, DocId, Rank, Similarity \n");
		int documentId, queryId, rank;
		documentId = queryId = 1;
		double sim;
		for(int i = 0; i < nUser; i ++) {
			for(int j = 0; j < nTopic; j++) {
				for (int y = 0; y < MAX_QUERY; y++) {
					for(int k = 0; k < MAX_RETR_DOC; k++) {	
						rank = k + 1;
						sim = 1 / (1 + (Math.log(rank) / 5)); // the similarity must depend on rank (high rank -> high similarity)
						inputFile.add( i + ", " + TOPIC_ID[j] + ", " + queryId + ", " + "clueweb12-0100tw-" + documentId + ", " + rank + ", " + sim + "\n");
						documentId ++;	
					}
					queryId ++;
					
					
				}
			}
		
		}
		
		//System.out.println(inputFile.toString());	
		return inputFile;
		
		
	}

	

	
	
	/**It creates the CSV file from the ArrayList in input
	 * @param input
	 * @param filepath
	 * @return true/false
	 */
	public boolean generateCSV(ArrayList<String> input, String filepath){
		try {
			
			FileWriter writer = new FileWriter(filepath);
			String collect = input.stream().collect(Collectors.joining(""));
			writer.write(collect);
			writer.close();
			return true;
		} catch (Exception e) {
			return false;
		}
		
	}
	
	

}


