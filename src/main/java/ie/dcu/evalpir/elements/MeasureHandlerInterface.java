//package ie.dcu.evalpir.elements;
//
//import java.util.Map;
//import java.util.TreeMap;
//
//public interface MeasureHandlerInterface {
//
//	public Map<String, AbstractMeasure> getMeasures();
//	
//
//	default TreeMap<String, AbstractMeasure>  sortMeasureForKey() {
//		TreeMap<String, AbstractMeasure> measureSorted = new TreeMap<String, AbstractMeasure>(getMeasures());
//		return measureSorted;	
//	}
//
//	
//	default void addMeasure(AbstractMeasure m) {
//		getMeasures().put(m.getName().trim().toLowerCase(), m);
//	}
//	
//	default void removeMeasure(String name) {
//		getMeasures().remove(name.trim().toLowerCase());
//	}
//	
//	default AbstractMeasure searchMeasure(String name) {
//		return getMeasures().get(name.trim().toLowerCase());
//	}
//	
//	default AbstractMeasure searchAddMeasure(String name, boolean compound) {
//		if(searchMeasure(name) == null) {
//			if(compound) {
//				addMeasure(new MeasureCompound(name.trim()));
//			}else {
//				addMeasure(new Measure(name.trim()));
//			}
//		}
//		
//		
//		return searchMeasure(name);
//	}
//}
