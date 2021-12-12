package hashtest.src.ws10b.hashmap;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import Lab101.src.ws10b.hashmap.Hashmap;

public class HashMapTest {
	public static void main(String[] args) throws FileNotFoundException {
		Hashmap<String, String> tets = readCVE("hi.txt");			
		print(tets);
	}
	public static void print(Hashmap<String, String> tets) {
		String f = "CVE-2005-2352";
		System.out.println(tets.get(f));
	}
	public static Hashmap<String, String> readCVE(String filename) throws FileNotFoundException {
		Hashmap<String, String> testMap = new Hashmap<String, String>();
		File csvFile = new File(filename);
		try (Scanner csvScan = new Scanner(csvFile)) {
			csvScan.nextLine(); 
			while (csvScan.hasNextLine()) {
				String line = csvScan.nextLine();
				String[] lineParts = line.split(",", -1);
				testMap.put(lineParts[0],
						lineParts[0]);
			}
			csvScan.close();
		}
		return testMap;
	}
}
