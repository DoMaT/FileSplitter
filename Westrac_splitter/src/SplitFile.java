import java.io.IOException;

public class SplitFile {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Splitter splitter = null;
		
//		String s = "L010_jailer_GIM.sql";
//		
//		splitter = new Splitter(s);
		
		for (String s: args) {
			if(s.toString().contains("L010")){
				System.out.println(s);

				splitter = new Splitter(s);
				try {
					System.out.println("Splitting");
					splitter.split();
//					splitter.readAll();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
