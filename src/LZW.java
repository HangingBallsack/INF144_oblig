import java.io.*;

public class LZW { 
	static String dictionary[]; 
	static int point; 
	String eventyr;
	String comp = "";
	
	public LZW(String eventyr) throws FileNotFoundException {
		this.eventyr = eventyr;
		comp = compress(eventyr);
		
		PrintWriter out = new PrintWriter("src/LZW.txt");
		out.println(comp);
		out.close();
	} 
	
//	public static void main(String args[]) throws FileNotFoundException { 
//		String text = ReadFile(); 	
//		String comp = compress(text); 
//		System.out.println("Compressed from " + text.length() + " to " + comp.length() + " chars/ " + (comp.length()*100)/text.length() + " %."); 
//
//		PrintWriter out = new PrintWriter("src/LZW.txt");
//		out.println(comp);
//		out.close();
//
//	} 
	
	public static String compress(String string) { 
		initiateDictionary(string.length()); 
		String output = ""; 
		String w = ""; 
		char k; 
		
		while (string.length() > 0) { 
			k = string.charAt(0); 
			string = string.substring(1); 
			if (Contains(w + k)) { 
				w += k; 
			} else { 
				output+= DictionaryCode(w); 
				DictionaryAdd(w + k); 
				w = k+""; 
			} 
		} 
		return output + DictionaryCode(w); 
	} 

	public static String decompress(String string) { 
		initiateDictionary(string.length()+1); 
		String output = ""; 
		String w = ""; 
		int k = (int)string.charAt(0); 
		w = DictionaryChar(k); 
		string = string.substring(1); 
		output += w; 

		while (string.length() > 0) { 
			k = (int) string.charAt(0); 
			string = string.substring(1); 
			output += DictionaryChar(k); 
			DictionaryAdd(w + DictionaryChar(k).charAt(0)); 
			w = DictionaryChar(k); 
		} 
		return output; 
	} 

	private static void initiateDictionary(int size) { 
		dictionary = new String[size]; 
		point = 0; 
	} 


	private static boolean Contains(String string) { 
		return (DictionaryCode(string).length() > 0); 
	} 

	private static String DictionaryCode(String string) { 
		String code = ""; 
		char c; 
		int i = 0; 
		if (string.length() == 1) {
			return "" + string.charAt(0); 
		} else { 
			while ((code.length() == 0) && (i < dictionary.length)) { 
				if ((dictionary[i] != null) && dictionary[i].equals(string)) { 
					c = (char)(i+256); 
					code = "" + c; 
				} 
				i++; 
			} 
			return code; 
		} 
	} 

	private static String DictionaryChar(int code) { 
		if (code < 256) { return "" + (char)code; } 
		else if ((code-256) < dictionary.length) { return dictionary[code-256]; } 
		else { return ""; } 
	} 

	private static void DictionaryAdd(String string) { 
		dictionary[point] = string; 
		point++; 
	} 


	private static String ReadFile() { 
		InputStreamReader inputSR = new InputStreamReader(System.in); 
		BufferedReader bufferedR = new BufferedReader(inputSR); 
		String text = "";
		try {
			String line = null;
			File file = new File("src/Folktale.html");

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