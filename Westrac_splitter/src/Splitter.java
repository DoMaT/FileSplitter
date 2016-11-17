import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Vector;

public class Splitter {
	
	private String fileName;
	private static final char ENDCHAR = '(';
	private static final String ENDFILENAME = ".sql";
	private Vector<String> fileNames = new Vector<String>();
	
	Vector<File> files = new Vector<File>();
	
	HashMap<String, Writer> fileNameWriterMap = new HashMap<String, Writer>();


	
	public Splitter(String _file) {
		fileName = _file;
	}
	
	public void readAll() throws IOException{
		BufferedReader in = new BufferedReader(new FileReader(fileName));
		
		String line;
		int i =0;
		while((line = in.readLine()) != null && i < 10)
		{
		    System.out.println(line);
			System.out.println(line.substring(0, line.indexOf(ENDCHAR)));
		    i++;
		}
		in.close();

	}
	
	public void split() throws FileNotFoundException, IOException{
		
		try (				
				BufferedReader br = new BufferedReader(new FileReader(fileName))) {
				    String line;
				    while ((line = br.readLine()) != null) {
				       // process the line
				    	
				    	if(fileNames.isEmpty()){
				    		String newFileName = createFile(line);
				    		fileNames.addElement(newFileName);
				    	}
				    	else{
				    		Vector<String> newFileNames = new Vector<String>();
				    		
				    		boolean contains = false;

				    		for (String name : fileNames) {
					    		if(line.contains(name)){
					    			
					    			contains = true;

					    		}
							}
				    		
				    		if(contains){
				    			//add new line to existing file

				    			writeToFile(line);

				    		}
				    		else{
				    			//create file with name from the beginning to first occurance of '('
				    			
				    			String newFileName = createFile(line);
				    			newFileNames.addElement(newFileName);

				    		}
				    		
				    		fileNames.addAll(newFileNames);
				    	}
				    	
				    }
				}
	}

	
private String createFile(String line) throws IOException {
		
	String newFileName = resolveFileName(line);
	System.out.println("Not in vector: " +newFileName);

		
		File file = new File(newFileName);
//		FileOutputStream fileOS = new FileOutputStream(file);
//		OutputStreamWriter fileOSW = new OutputStreamWriter(fileOS, "utf-8");
//		Writer fileWriter = new BufferedWriter(fileOSW);
		
		
		try(FileWriter fw = new FileWriter(newFileName, true);
			    BufferedWriter bw = new BufferedWriter(fw);
			    PrintWriter out = new PrintWriter(bw))
			{
			    out.println(line);

			} catch (IOException e) {
			    //exception handling left as an exercise for the reader
			}

		return newFileName;
	}
	
	
	private void writeToFile(String line){
		
		String newFileName = resolveFileName(line);
		System.out.println("Already in vector: " +newFileName);

		
		try(FileWriter fw = new FileWriter(newFileName, true);
			    BufferedWriter bw = new BufferedWriter(fw);
			    PrintWriter out = new PrintWriter(bw))
			{
			    out.println(line);

			} catch (IOException e) {
			    //exception handling left as an exercise for the reader
			}
	}
	
	private String resolveFileName(String line){
		String newFileName = line.substring(0, line.indexOf(ENDCHAR));
//		newFileName += ENDFILENAME;
		
		return newFileName;
	}
}
