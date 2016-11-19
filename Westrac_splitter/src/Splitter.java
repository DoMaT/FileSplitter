import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Vector;

public class Splitter {

	private static String FILENAME;
	private static char ENDCHAR = '(';
	private static String ENDFILENAME = ".sql";
	private static boolean VERBOSE = true;
	private Vector<String> fileNames = new Vector<String>();

	Vector<File> files = new Vector<File>();

	HashMap<String, PrintWriter> fileNameWriterMap = new HashMap<String, PrintWriter>();
	// TODO przechowywanie writera w hashmapie
	// TODO wybór œcie¿ki zapisu

	public Splitter(String _FILE) {
		FILENAME = _FILE;
	}

	public Splitter(String _FILE, char _ENDCHAR, String _ENDFILENAME, boolean _VERBOSE) {
		FILENAME = _FILE;
		ENDCHAR = _ENDCHAR;
		ENDFILENAME = _ENDFILENAME;
		VERBOSE = _VERBOSE;
	}

	public void readAll() throws IOException {
		BufferedReader in = new BufferedReader(new FileReader(FILENAME));

		String line;
		int i = 0;
		while ((line = in.readLine()) != null && i < 10) {
			System.out.println(line);
			System.out.println(line.substring(0, line.indexOf(ENDCHAR)));
			i++;
		}
		in.close();

	}

	public void split() throws FileNotFoundException, IOException {

		try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
			String line;

			int counter = 0;
			while ((line = br.readLine()) != null) {
				// process the line
				if (line.contains(Character.toString(ENDCHAR))) {
					counter++;
					if (fileNames.isEmpty()) {
						String newFileName = createFile(line);
						fileNames.addElement(newFileName);
					} else {
						Vector<String> newFileNames = new Vector<String>();

						boolean contains = false;

						for (String name : fileNames) {

							String shortName[] = name.split(ENDFILENAME);

							if (line.contains(shortName[0])) {
								contains = true;

							}
						}

						if (contains) {
							// add new line to existing file

							writeToFile(line);

						} else {
							// create file with name from the beginning to first
							// occurance of '('

							String newFileName = createFile(line);
							newFileNames.addElement(newFileName);

						}

						fileNames.addAll(newFileNames);
					}

				} else {
					// line does not contain special character - go ahead
				}

			}

			System.out.println("Finito");
			System.out.println("Splitted " + counter + " lines into following files: ");
			for (String name : fileNames) {
				System.out.println(name);
			}
		} catch (FileNotFoundException e) {
			System.out.println("File not found...");
			System.exit(0);
		}
	}

	private String createFile(String line) throws IOException {

		String newFileName = resolveFileName(line);

		try (BufferedReader br = new BufferedReader(new FileReader(newFileName))) {
			// file found - delete

			File here = new File(".");
			String absPath = here.getAbsolutePath();
			absPath = absPath.substring(0, absPath.length() - 1);

			if (VERBOSE)
				System.out.println(absPath);

			File file = new File(absPath + newFileName);
			if (file.exists()) {
				if (VERBOSE) {
					System.out.println("File already exists, overwriting: " + absPath + newFileName);
				}
			}
		} catch (FileNotFoundException e) {
			// file not found
			if (VERBOSE)
				System.out.println("Created file: " + newFileName);
		}

//		try (FileWriter fw = new FileWriter(newFileName, false);
//				BufferedWriter bw = new BufferedWriter(fw);
//				PrintWriter out = new PrintWriter(bw)) {
//			out.println(line);
//			//put(key - file name,value - printer)
//			fileNameWriterMap.put(newFileName, out);
//
//		} catch (IOException e) {
//			// exception handling
//		}
		PrintWriter out = getWriter(newFileName, false);
		out.println(line);
		out.close();
		
		PrintWriter outAppend = getWriter(newFileName, true);
		fileNameWriterMap.put(newFileName, outAppend);

		return newFileName;
	}

	private void writeToFile(String line) {

		String newFileName = resolveFileName(line);

		if (VERBOSE)
			System.out.println("Saved in file: " + newFileName);

		try (FileWriter fw = new FileWriter(newFileName, true);
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter out = new PrintWriter(bw)) {
			out.println(line);

		} catch (IOException e) {
			// exception handling
		}
	}
	
	private PrintWriter getWriter(String outputFile, boolean append){

		FileWriter fw = null;
		try {
			fw = new FileWriter(outputFile, append);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedWriter bw = new BufferedWriter(fw);
		PrintWriter out = new PrintWriter(bw);
		
		return out;
	}
	
	private void writeToFile2(String line) {

		String newFileName = resolveFileName(line);

		PrintWriter out = fileNameWriterMap.get(newFileName);
		out.println(line);
		
		if (VERBOSE)
			System.out.println("Saved in file: " + newFileName);

//		try (FileWriter fw = new FileWriter(newFileName, true);
//				BufferedWriter bw = new BufferedWriter(fw);
//				PrintWriter out = new PrintWriter(bw)) {
//			out.println(line);
//
//		} catch (IOException e) {
//			// exception handling
//		}
	}

	private String resolveFileName(String line) {
		String newFileName = line.substring(0, line.indexOf(ENDCHAR));
		newFileName += ENDFILENAME;

		return newFileName;
	}
}
