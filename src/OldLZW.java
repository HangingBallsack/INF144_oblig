import java.io.*;
import java.util.*;

public class OldLZW {
	public static List<Integer> compress(String string) {
		int dictSize = 256;
		Map<String,Integer> dictionary = new HashMap<String,Integer>();
		for (int i = 0; i < 256; i++) {
			dictionary.put("" + (char)i, i);
		}
		String w = "";
		List<Integer> result = new ArrayList<Integer>();
		for (char c : string.toCharArray()) {
			String wc = w + c;
			if (dictionary.containsKey(wc)) {
				w = wc;
			} else {
				result.add(dictionary.get(w));
				dictionary.put(wc, dictSize++);
				w = "" + c;
			}
		}

		if (!w.equals("")) {
			result.add(dictionary.get(w));
		}
		return result;
	}

	public static String decompress(List<Integer> compressed) {
		int dictSize = 256;
		Map<Integer,String> dictionary = new HashMap<Integer,String>();
		for (int i = 0; i < 256; i++) {
			dictionary.put(i, "" + (char)i);
		}
		String w = "" + (char)(int)compressed.remove(0);
		StringBuffer result = new StringBuffer(w);
		for (int k : compressed) {
			String entry;
			if (dictionary.containsKey(k)) {
				entry = dictionary.get(k);
			} else if (k == dictSize) {
				entry = w + w.charAt(0);
			} else {
				throw new IllegalArgumentException("Bad compressed k: " + k);
			}
			result.append(entry);
			dictionary.put(dictSize++, w + entry.charAt(0));
			w = entry;
		}
		return result.toString();
	}

	public static void main(String[] args) throws FileNotFoundException {
		String text = ReadFile();
		List<Integer> compressed = compress(text);
		System.out.println(compressed);
		String decompressed = decompress(compressed);
		System.out.println(decompressed);
		PrintWriter out = new PrintWriter("LZW2.txt");
		out.println(compressed);
		out.close();
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
				text += line.toLowerCase();
			}
		} catch(IOException ioe) { 
			System.out.println("Error reading file");
		} 
		return text; 
	} 
}