import java.io.IOException;

public class SplitFile {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Splitter splitter = null;

		int argsCount = 0;
		for (String counter : args) {
			argsCount++;
		}

		if (argsCount > 0) {
			System.out.println(args[0]);

			splitter = new Splitter(args[0]);
			try {
				System.out.println("Splitting");
				splitter.split();
				// splitter.readAll();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
//			String s = "C:\\Users\\matdom\\git\\FileSplitter\\Westrac_splitter\\src\\toSplit.sql";
			String s = "C:\\Users\\matdom\\OneDrive - Syncron\\L010\\L010_jailer_GIM.sql";
			s = s.replace("\\", "/");

			splitter = new Splitter(s);

			try {
				splitter.split();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	//TODO jak w linijce nie wystêpuje znak to pomiñ
}
