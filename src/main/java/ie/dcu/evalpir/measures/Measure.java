package ie.dcu.evalpir.measures;

import java.util.ArrayList;
import java.util.List;

import ie.dcu.evalpir.elements.Query;
import ie.dcu.evalpir.elements.User;

public interface Measure {
	
//	public Double calculatePK(ArrayList<Doc> dRel, ArrayList<String[]> result ,int K);
//	public Double calculateNDCG ( List<String> realData, List<String> predictionData );
	
	
	/**
	 * @param relevanceDoc
	 * @param outputPIR
	 * @param k
	 * @Complexity !CRITICAL! O(nUser * nTopic * nQuery * measures) 
	 */
	public void meanAverageMeasure(ArrayList<User> relevanceDoc, ArrayList<User> outputPIR, int k);
		
	
	
	
	
	/**
	 * @param queryRel
	 * @param queryResult
	 * @param k
	 * @return (double)(relDoc / k)
	 */
	public Double calculatePK(Query queryRel, Query queryResult , int k);

}
