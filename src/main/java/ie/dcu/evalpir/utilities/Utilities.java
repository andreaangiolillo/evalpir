package ie.dcu.evalpir.utilities;

import java.util.HashMap;
import java.util.Map;

import ie.dcu.evalpir.elements.Document;
import ie.dcu.evalpir.elements.DocumentRelFile;

public class Utilities {
	
	/**
	 * Changing of base
	 * 
	 * @input value
	 * @input base
	 * */
	public static double log(int value, int base) {
		return Math.log(value) / Math.log(base);
	}
	
	/**
	 * 
	 * @param map
	 * @return
	 */
	public static  Map<String,Document> copyMap(Map<String, Document> mapToCopy) {
		Map<String,Document> newMap = new HashMap<String,Document>();
		for(Map.Entry<String, Document> entry: mapToCopy.entrySet()) {
			newMap.put(entry.getKey(), new DocumentRelFile((DocumentRelFile)entry.getValue()));
		}
		return newMap;
	}
}
