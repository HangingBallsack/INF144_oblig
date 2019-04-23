import java.io.*;
import java.util.*;

public class MarkovChain {
	public static HashMap<String, ArrayList<Character>> map = new HashMap<>();

	public static void main(String[] args) throws IOException {
		String folktale = "";
		Scanner sc = new Scanner(System.in);
		HashMap<String, ArrayList<Character>> map = new HashMap<>();

		System.out.println("> Enter order (0-3): ");
		int order = sc.nextInt();
		System.out.println("> Enter length: ");
		int characters = sc.nextInt();
		System.out.println("> Include html tags? Y/N");
		String decisions = sc.next();

		// read input start
		try {
			String line = null;
			File file = new File("src/Folktale.html");
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
			if (decisions.equalsIgnoreCase("n")) {
				while((line = br.readLine()) != null){
					folktale = folktale + line.replaceAll("\\<.*?>", "").replaceAll(" +", " ").toLowerCase();
				}
			} else {
				while((line = br.readLine()) != null){
					folktale = folktale + line.toLowerCase();
				}
			}
			sc.close();
			br.close();

		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		} catch (IOException e) {
			System.out.println("Error reading file");
		}
		// read input end

		// put input in hashmap to get statistics
		for(int i=0; i< folktale.length() - order; i++) {
			String string = folktale.substring(i, i + order);

			if (!map.containsKey(string)) {
				map.put(string, new ArrayList<>());
			}
			map.get(string).add(folktale.charAt(i+order));
		}


		double random = (folktale.length() - order) * Math.random();
		String currentString = folktale.substring((int) random, (int) random + order);
		String newString = currentString;     

		for (int j = 0; j <characters; j++) {
			ArrayList<Character> choices = map.get(currentString);

			double outRandom = Math.random() * (choices.size());
			newString += choices.get((int) outRandom);

			int length = newString.length();
			currentString = newString.substring(length - order, length); 

		}
		System.out.println("\nNew string: " + newString + "\n");
		System.out.println("Entropy of Folktale: " + entropy(folktale));
		System.out.println("Entropy of new string: " + entropy(newString));
		//		pairs(map);
		//		printMap(map);
	}

	public static double entropy(String str) {
		int length = str.length();
		char[] count = new char[256];
		for (char i : str.toCharArray()) {
			count[i]++;
		}

		double entropy = 0;
		for (int i = 0; i < 256; i++) {
			int j = count[i];
			if (j == 0) {
				continue;
			}

			double d = (double)j / length;
			entropy += d * log2(d);
		}
		return (entropy * (-1));
	}

	@SuppressWarnings("rawtypes")
	public static void printMap(HashMap map) {
		Iterator it = map.entrySet().iterator();
		while (it.hasNext()) {
			HashMap.Entry pair = (HashMap.Entry)it.next();
			System.out.println(pair.getKey() + " = " + pair.getValue());
			it.remove();
		}
	}

	@SuppressWarnings("rawtypes")
	public static void pairs(HashMap map) {
		Iterator it = map.entrySet().iterator();
		int twoChar = 0;
		while(it.hasNext()) {
			twoChar++;
			it.next();
		}
		System.out.println("pairs: " + twoChar);
	}

	public static double log2(double x) {
		return (Math.log(x)/Math.log(2.0));
	}
}

