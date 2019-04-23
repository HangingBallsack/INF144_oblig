import java.io.*;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		String eventyr = ReadFile();
		
		LZW lzw = new LZW(eventyr);
		System.out.println("Compressed from " + eventyr.length() + " to " + lzw.comp.length() + " chars/ " + (lzw.comp.length()*100)/eventyr.length() + " %."); 
		System.out.println(lzw.comp);
	}

	private static String ReadFile() { 
		InputStreamReader inputSR = new InputStreamReader(System.in); 
		BufferedReader bufferedR = new BufferedReader(inputSR); 
		String text = "";
		try {
			String line = null;
			File file = new File("src/Folktale.txt");

			bufferedR = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));

			while((line = bufferedR.readLine()) != null){
				text += line.replaceAll("\\<.*?>", "").replaceAll(" +", " ").toLowerCase();
			}
		} catch(IOException ioe) { 
			System.out.println("Error reading from file");
		} 
		return text; 
	}
}
