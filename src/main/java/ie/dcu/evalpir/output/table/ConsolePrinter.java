package ie.dcu.evalpir.output.table;

public class ConsolePrinter {

	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";
	public static final String BOLD = "\033[0;1m";

	private static int n = 1;

	public static void startEval() {
		System.out.println();
		System.out.println("Step: " + n + " " + "Starting Evaluation ... ");
	}

	public static void printMessage(String message) {
		System.out.println(message);

	}

	public static void startTask(String task) {
		n++;
		System.out.print("Step: " + n + " " + task + " ...");

	}

	public static void endTask(String task) {

		System.out.print(" Done \n");
	}

}
