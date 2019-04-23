import java.io.*;
import java.util.*;

class Huffman{

	static PrintWriter out;
	static StringBuilder sb;
	
	public static void encode(Node root, String string, Map<Character, String> code){
		if (root == null)
			return;

		if (root.left == null && root.right == null) {
			code.put(root.c, string);
		}
		encode(root.left, string + "0", code);
		encode(root.right, string + "1", code);
	}

	public static void huffmanTree(String text) {
		Map<Character, Integer> freq = new HashMap<>();
		PriorityQueue<Node> pq = new PriorityQueue<>((l, r) -> l.occurance - r.occurance);
		for (int i = 0 ; i < text.length(); i++) {
			if (!freq.containsKey(text.charAt(i))) {
				freq.put(text.charAt(i), 0);
			}
			freq.put(text.charAt(i), freq.get(text.charAt(i)) + 1);
		}


		for (Map.Entry<Character, Integer> entry : freq.entrySet()) {
			pq.add(new Node(entry.getKey(), entry.getValue()));
		}
		
		while (pq.size() != 1) {
			Node left = pq.poll();
			Node right = pq.poll();

			int sum = left.occurance + right.occurance;
			pq.add(new Node('\0', sum, left, right));
		}

		Node root = pq.peek();

		Map<Character, String> huffmanCode = new HashMap<>();
		encode(root, "", huffmanCode);

		sb = new StringBuilder();
		for (int i = 0 ; i < text.length(); i++) {
			sb.append(huffmanCode.get(text.charAt(i)));
		}

		System.out.println("Encoded:\n" + sb);
		out.print(sb.toString());
	}

	public static void main(String[] args) throws FileNotFoundException{
		out = new PrintWriter("src/Huffman.txt");
		String text = ReadFile();
		huffmanTree(text);
		out.close();
	}

	private static String ReadFile() { 
		InputStreamReader inputSR = new InputStreamReader(System.in); 
		BufferedReader bufferedR = new BufferedReader(inputSR); 
		String text = "";
		try {
			String line = null;
			File file = new File("src/LZW.txt");

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

class Node {
	char c;
	int occurance;
	Node left, right = null;

	Node(char c, int occurance){
		this.c = c;
		this.occurance = occurance;
	}

	public Node(char c, int occurance, Node left, Node right) {
		this.c = c;
		this.occurance = occurance;
		this.left = left;
		this.right = right;
	}
};
