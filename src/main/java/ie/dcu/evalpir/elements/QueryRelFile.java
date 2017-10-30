package ie.dcu.evalpir.elements;

import java.util.Iterator;
import java.util.Map;

public class QueryRelFile extends ie.dcu.evalpir.elements.Query{
	
	
	
	
	/**
	 * 
	 */
	public QueryRelFile() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param id
	 * @param docs
	 */
	public QueryRelFile(String id, Map<String, Document> docs) {
		super(id, docs);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param id
	 */
	public QueryRelFile(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	/**
	 * This method return the number of relevant documents of this query. If the query contains DocumentOutputPIR object return 0.
	 * @return nRelDoc
	 * */
	public int getNRelevantDoc() {
		int nRelDoc = 0;
		Iterator<?> it = this.getDocs().entrySet().iterator();
		boolean instance = true;
		while(it.hasNext() && instance) {
			Map.Entry<?,?> pair = (Map.Entry<?,?>)it.next();
			if (!(pair.getValue() instanceof DocumentRelFile)) {
				instance = false;
			}
			nRelDoc += (instance && ((DocumentRelFile)pair.getValue()).getIsRelevance()) ? 1 : 0;
			
		}
		
		return nRelDoc;
	}
	

	
	

}
