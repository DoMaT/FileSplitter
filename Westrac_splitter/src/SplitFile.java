import java.io.IOException;

public class SplitFile {

	private static char _ENDCHAR;
	private static String _ENDFILENAME;
	private static boolean _VERBOSE;
	private static String _FILE;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Splitter splitter = null;

		System.out.println("Splitting the following file:");

		int argsCount = 0;
		for (String counter : args) {
			if (counter != null)
				argsCount++;
		}

		if (argsCount == 1) {

			// TODO if args count > 1
			_FILE = args[0];

			System.out.println(_FILE);

			splitter = new Splitter(_FILE);

		} else if (argsCount == 4) {
			_FILE = args[0];

			System.out.println(_FILE);

			boolean error_in_params = false;

			if (args[1].length() != 1) {
				error_in_params = true;
			}

			_ENDCHAR = args[1].charAt(0);

			_ENDFILENAME = args[2];

			if (args[3].length() != 1) {
				error_in_params = true;
				_VERBOSE = false;
			}
			else
				_VERBOSE = true;
			
			if (error_in_params)
				System.out.println("Error in parameters, some functions are default");

			splitter = new Splitter(_FILE, _ENDCHAR, _ENDFILENAME, _VERBOSE);
		} else {
			// String s =
			// "C:\\Users\\matdom\\git\\FileSplitter\\Westrac_splitter\\src\\toSplit.sql";
			String s = "C:\\Users\\matdom\\Desktop\\L010_jailer_GIM.sql";
			s = s.replace("\\", "/");

			System.out.println(s);
			System.out.println("Running with default settings");
			splitter = new Splitter(s);
		}

		try {
			splitter.split();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
