package ie.dcu.evalpir.measures;

import java.util.ArrayList;


import ie.dcu.evalpir.elements.Query;


public interface CalculateMeasure {
	
//	public Double calculatePK(ArrayList<Doc> dRel, ArrayList<String[]> result ,int K);
//	public Double calculateNDCG ( List<String> realData, List<String> predictionData );
	
	
	/**
	 * @param relevanceDoc
	 * @param outputPIR
	 * @param k
	 * @Complexity !CRITICAL! O(nUser * nTopic * nQuery * measures) 
	 */
	//public void meanAverageMeasure(ArrayList<Query> relevanceDoc, ArrayList<User> outputPIR, int k);
		
	
	
	
	
	/**
	 * @param queryRel
	 * @param queryResult
	 * @param k
	 * @return (double)(relDoc / k)
	 */
	public Double calculatePK(Query queryRel, Query queryResult , int k);

}
