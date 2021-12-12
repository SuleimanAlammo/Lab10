package Lab101.src.ws10a.bstmap;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BSTMapTest<T> {
	public static void main(String[] args) {
		BSTMap<String, String> testMap = new BSTMap<String, String>();

		// a simple back-to-front phonebook (i.e. resolves phone numbers to names)
		
		System.out.println("Inserting elements");
		testMap.put("0151 231 2636", "David Lamb");
		testMap.put("0151 231 2106", "Chris Carter");
		testMap.put("0151 231 2268", "Stephen Tang");
		testMap.put("0151 231 2635", "Andy Symons");
		testMap.put("0151 231 2476", "Hulya Francis");
		testMap.put("0151 231 2276", "Mitch Mackay");
		testMap.put("0151 231 2080", "Tom Berry");
		testMap.put("0151 231 2641", "Martin Randles");
		System.out.println("Finished Inserting elements");
		System.out.println("----");
		System.out.println("The map size is now " + testMap.size());

		// test get function
		System.out.println(testMap.get("0151 231 2636"));
		System.out.println(testMap.get("0151 231 2641"));
		System.out.println(testMap.get("0151 231 2640"));

		// Lab 10.1 - Advanced - Word count
		System.out.println("---");
		BSTMap<String, Integer> wordMap = new BSTMap<String, Integer>();


		public static void readFile() throws FileNotFoundException {
			
			BSTMap<String, String> cve = new BSTMap<String, String>();
			File csvFile = new File("hi.txt");
			Scanner csvScan = new Scanner(csvFile);
			csvScan.nextLine();
			while (csvScan.hasNextLine()) {
				String line = csvScan.nextLine();
				cve.put(line,line);
			}
			csvScan.close();		
	}
//		for (String word : .split(",")) {
//
//		}
		
//		for (String word : wordMap.keys()) {
//			System.out.println(word + ", " + wordMap.get(word));
//		}

	}
}
