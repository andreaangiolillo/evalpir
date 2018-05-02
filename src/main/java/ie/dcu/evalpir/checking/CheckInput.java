package ie.dcu.evalpir.checking;

/**
 * @author Andrea Angiolillo
 * @version 1.0
 * 
 **/
public interface CheckInput {

	/**
	 * This method checks the input files constrains
	 * 
	 * @param args
	 * @return true: the input files respect the constrains
	 * @return false: the input files don't respect the constrains
	 */
	public boolean checkInputFiles(String[] args);

}
