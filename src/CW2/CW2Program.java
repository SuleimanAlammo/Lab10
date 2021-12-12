package CW2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Lab101.src.ws10b.hashmap.Hashmap;

public class CW2Program {
	public static void main(String[] args) throws FileNotFoundException {
//		Map<String, CVE> cve = readCVE("LargeDataset-CVE-Validated.csv");
//		Map<String, Products> proCsv = readPro("LargeDataset-Products.csv");
//		Map<String, Vendor> venCsv = readVen("LargeDataset-Vendors.csv");
		Map<String, CVE> cve = readCVE("SampleDataset-CVE-Validated.csv");
		Map<String, Products> proCsv = readPro("SampleDataset - Products.csv");
		Map<String, Vendor> venCsv = readVen("SampleDataset -Vendors.csv");
		List<CVEData> cveData = readFile("LargeDataset-CVE-Validated.csv");
		long time = System.currentTimeMillis();
		vendorsVuln(cve, venCsv);
		long sortTime = System.currentTimeMillis() - time;
		System.out.println("\n" + sortTime);
		
		Scanner console = new Scanner(System.in);
		int choice = 0;
		do {
			System.out.println("\n\n            -- MAIN MENU --");
			System.out.println("1 -List vendors with vulnerable product(s) (A)");
			System.out.println("2 -Print as simple List (B.i)");
			System.out.println("3 -Print by vulnerability score (B.ii)");
			System.out.println("4 -Print, vender and product name by CVE_id (C.i)");
			System.out.println("5 -Print, CVE, product and cew_code (C.ii)");
			System.out.println("6 -Print, vendor with highest number of CVEs (D.i)");
			System.out.println("7 -Sort the records in the CVE dataset into ascending order (E)");
			System.out.println("8 -Specify a record with incomplete data (F)");
			System.out.println("0 -Quit");
			System.out.print("Pick:");

			// A validation that ensure the user enters a number
			while (true) {
				try {
					choice = console.nextInt();
					break;
				} catch (InputMismatchException e) {
					System.out.print("Invalid input. Please enter a number: ");
					console.nextLine();
				}
			}

			switch (choice) {
			case 1: {
				vendorsVuln(cve, venCsv);
				break;
			}
			case 2: {
				printCVSS(cve);
				break;
			}
			case 3: {
				sortCVSS(cveData);
				break;
			}
			case 4: {
				printByIdPro(proCsv, venCsv);
				break;
			}
			case 5: {
				sepcVend(venCsv, cve, proCsv);
				break;
			}
			case 6: {
				String mostVend = mostFrequent(venCsv);
				printHighCVE(mostVend, venCsv, cve);
				break;
			}
			case 7: {
				sortAsceOrder(cve);
				break;
			}
			case 8: {
				recordSugg(cve);
				break;
			}
			}
		} while (!(choice == 0));
		console.close();
		System.out.println("System Exit!");
	}

	// A method that sort the cvss in the cve file and print them
	public static void sortCVSS(List<CVEData> cve) {
		Comparator<CVEData> sort = null;
		// A simple comparator that uses the bubble sort algorithm that sort the cvss
		// (this algorithm get send to the BST)
		sort = new Comparator<CVEData>() {
			public int compare(CVEData o1, CVEData o2) {
				return Double.valueOf(o1.cvss).compareTo(Double.valueOf(o2.cvss));
			}
		};
		// Call the BST and load the nodes (data) and the type of sort which is cvss
		BST<CVEData> nodes = new BST<CVEData>(cve.get(0), sort);
		for (int i = 1; i < cve.size(); i++) {
			// declaring dataCve to try and optimise the feature, instead of keep calling
			// cve.get(i)
			CVEData dataCve = cve.get(i);
			nodes.insertdups(dataCve);
		}
		// We clear the cve and add the ordered data
		cve.clear();
		cve.addAll(nodes.inOrder());
		// Print the data
		for (int i = 0; i < cve.size(); i++) {
			CVEData currentCVEDate = cve.get(i);
			System.out.println(currentCVEDate.cve_id + " | " + currentCVEDate.mod_date + " | " + currentCVEDate.pub_date
					+ " | " + currentCVEDate.cvss);
		}
	}

	// simple method to print data
	private static void printCVSS(Map<String, CVE> cve) {
		for (String i : cve.keySet()) {
			CVE currentCVE = cve.get(i);
			// declaring currentCVE to try and optimise the feature, instead of keep calling
			// cve.get(i)
			System.out.println(currentCVE.cve_id + " | " + currentCVE.mod_date + " | " + currentCVE.pub_date + " | "
					+ currentCVE.cvss);
		}
	}

	// A method that calculate the most mentioned vendor
	public static String mostFrequent(Map<String, Vendor> venCve) {
		// set some variables to save the final results and to count
		String mostVend = "";
		int count = 0;
		int maxCount = 0;
		/*
		 * A nested loop that loops around venCve and take the first value and then loop
		 * it to the whole venCve file and count how many times it has been mentioned
		 * (this can take some time and it can be implemented in linkedlist to produce a
		 * faster result)
		 */
		for (String i : venCve.keySet()) {
			Vendor currentVenCve = venCve.get(i);
			count = 0;
			for (String j : venCve.keySet()) {
				if (currentVenCve.venName.equals(venCve.get(j).venName)) {
					count++;
				}
				// if the count exceed the maxCount then the mostVend store the name of the
				// vendor
				if (count > maxCount) {
					maxCount = count;
					mostVend = currentVenCve.venName;
				}
			}
		}
		System.out.println("The most mentioned vendor is: " + mostVend + " | Number of mentions: " + maxCount);
		// return a string with the name of the vendor
		return mostVend;
	}

	public static void printHighCVE(String mostVend, Map<String, Vendor> venCve, Map<String, CVE> cve) {
		// A nested that loops around venCve into cve to print every mostVend name in
		// the cve list with their data
		for (String i : venCve.keySet()) {
			Vendor currentVenCsv = venCve.get(i);
			if (mostVend.equals(venCve.get(i).venName)) {
				for (String j : cve.keySet()) {
					CVE currentCVE = cve.get(j);
					if (currentCVE.cve_id.equals(currentVenCsv.venCve_id)) {
						System.out.printf("CVE id %s | PUB date %s\n", currentCVE.cve_id, currentCVE.pub_date);
					}
				}
			}
		}
	}

	public static void recordSugg(Map<String, CVE> cve) {
		Scanner console = new Scanner(System.in);
		boolean flag = true;

		System.out.print("Please specify a CVE ID:");
		String inputCVE;
		// A validation that ensure the user enters the right id type
		while (true) {
			try {
				inputCVE = console.next();
				if (inputCVE.contains("CVE") && inputCVE.contains("-")) {
					break;
				} else {
					System.out.println("Incorrect data type. Try again:");
				}
			} catch (NumberFormatException e) {
			}
		}
		System.out.print("Please specify a CVSS:");
		double inputCVSS;
		// A validation that ensure the user enters a number
		while (true) {
			try {
				inputCVSS = console.nextDouble();
				break;
			} catch (InputMismatchException e) {
				System.out.print("Invalid input. Please enter a number: ");
				console.nextLine();
			}
		}
		System.out.print("Please specify a CWE code:");
		int inputCWE;
		// A validation that ensure the user enters a number
		while (true) {
			try {
				inputCWE = console.nextInt();
				break;
			} catch (InputMismatchException e) {
				System.out.print("Invalid input. Please enter a number: ");
				console.nextLine();
			}
		}
		System.out.print("Please specify a PUP date (eg 2002-12-31T05:00):");
		String inputPUP;
		// A validation that ensure the user enters the right pub date type
		while (true) {
			try {
				inputPUP = console.next();
				if (inputPUP.contains("-") && inputPUP.contains(":")) {
					break;
				} else {
					System.out.println("Incorrect data type. Try again:");
				}
			} catch (NumberFormatException e) {
			}
		}

// if else statement to ask the user few questions and print data as requested 
		for (String i : cve.keySet()) {
			CVE currentCVE = cve.get(i);
			if (inputCVE.equals(currentCVE.cve_id)) {
				print(cve, i);
				flag = false;
			} else if (inputCVSS == currentCVE.cvss) {
				print(cve, i);
				flag = false;
			} else if (inputCWE == currentCVE.cwe_code) {
				print(cve, i);
				flag = false;
			} else if (inputPUP.equals(currentCVE.pub_date.toString())) {
				print(cve, i);
				flag = false;
			}
		}
		if (flag) {
			System.out.println("Sorry we could not find anything to show :(");
		}

	}

	public static int needNum(String impact) {
		// a switch case that calculate the numeric number of impact that are bing send
		// from soerTAsceOrder method
		int avaCount = 0;
		switch (impact) {
		case "": {
			/*
			 * removed a useless calculation that was used which wasted time and memory to
			 * improve optimisation it was avaCount +=0
			 */
			break;
		}
		case "NONE": {
			avaCount += 2;
			break;
		}
		case "PARTIAL": {
			avaCount += 4;
			break;

		}
		case "COMPLETE": {
			avaCount += 6;
			break;
		}
		}
		return avaCount;
	}

	public static void print(Map<String, CVE> cve, String i) {
		// A simple print method but it receive the String i which is the place of data
		// in the hashmap
		CVE currentCVE = cve.get(i);
		System.out.printf("%s | %s | %s |%04.1f | %03d | %s | %s | %s | %s | %s | %s | %s |%s|%n", currentCVE.cve_id,
				currentCVE.mod_date, currentCVE.pub_date, currentCVE.cvss, currentCVE.cwe_code, currentCVE.cwe_name,
				currentCVE.summary, currentCVE.access_authentication, currentCVE.access_complexity,
				currentCVE.access_vector, currentCVE.impact_availability, currentCVE.impact_confidentiality,
				currentCVE.impact_integrity);
	}

// a method that sort CVEs by ascending order
	public static void sortAsceOrder(Map<String, CVE> cve) {
		List<ArrayCopy> newArray = new ArrayList<>();
		String mpAva = "", mpCon = "", mpInt = "";
		for (String i : cve.keySet()) {
			/*
			 * simple int counters (keep in mind that the counters will rest each time the
			 * loop finishes one loop)
			 */
			int avaCount = 0, conCount = 0, intCount = 0, comCount = 0, result = 0;
			CVE currentCVE = cve.get(i);
			// send each impact type to a method that return a numeric value
			mpAva = currentCVE.impact_availability;
			avaCount += needNum(mpAva);
			mpCon = currentCVE.impact_confidentiality;
			conCount += needNum(mpCon);
			mpInt = currentCVE.impact_integrity;
			intCount += needNum(mpInt);
			// a switch case that calculate the numeric number of access_complexity
			switch (currentCVE.access_complexity) {
			case "": {
				comCount += 1;
				break;
			}
			case "LOW": {
				comCount += 2;
				break;
			}
			case "MEDIUM": {
				comCount += 4;
				break;
			}
			case "HIGH": {
				comCount += 6;
				break;
			}
			}
			// Simple equation to use as reference for sorting
			result = (avaCount + conCount + intCount) / comCount;
			// Made an arraylist to simple sort the data that keep rested each time a loop
			// finishes a run
			ArrayCopy arrayOBJ = new ArrayCopy(currentCVE.cve_id, result);
			newArray.add(arrayOBJ);
		}
		// A simple bubble sort method that sort number in the arraycopy
		/*
		 * moved a sorting method inside here instead of calling it from outside the
		 * method in hope it improve optimisation to sort the array
		 */
		Comparator<ArrayCopy> sort = null;
		sort = new Comparator<ArrayCopy>() {
			public int compare(ArrayCopy o1, ArrayCopy o2) {
				return Double.valueOf(o1.number).compareTo(Double.valueOf(o2.number));
			}
		};
		Collections.sort(newArray, sort);
		// A nested loop that check if both ids from both files are they same and then
		// print the data
		for (int i = 0; i < newArray.size(); i++) {
			for (String j : cve.keySet()) {
				CVE currentCVE = cve.get(j);
				if (newArray.get(i).id.contains(currentCVE.cve_id)) {
					System.out.printf("%s | %s | %s |%04.1f | %03d | %s | %s | %s | %s | %s | %s | %s |%s|%n",
							currentCVE.cve_id, currentCVE.mod_date, currentCVE.pub_date, currentCVE.cvss,
							currentCVE.cwe_code, currentCVE.cwe_name, currentCVE.summary,
							currentCVE.access_authentication, currentCVE.access_complexity, currentCVE.access_vector,
							currentCVE.impact_availability, currentCVE.impact_confidentiality,
							currentCVE.impact_integrity);
				}
			}
		}

	}

	private static void sepcVend(Map<String, Vendor> venCve, Map<String, CVE> cve, Map<String, Products> proCsv) {
		// Set flags in case of no data to show
		boolean flag = false;
		boolean flag2 = true;
		String CVE_id = "", product = "";
		int cwe_code = 0;
		Scanner console = new Scanner(System.in);
		// Ask for user input
		System.out.println("Please specify a vendor");
		String input = console.next();
		/*
		 * A nested loop that loops around. The first loop checks if the venName is
		 * equal to user input and the second loop check of venCve id is the same as
		 * proCve id
		 */
		for (String i : venCve.keySet()) {
			Vendor currentVenCve = venCve.get(i);
			if (currentVenCve.venName.equals(input)) {
				for (String j : proCsv.keySet()) {
					Products currentProCsv = proCsv.get(j);
					if (currentVenCve.venCve_id.contains(currentProCsv.proCve_id)) {
						product = currentProCsv.proName;
						// Update the flags
						flag = true;
						flag2 = false;
					}
				}
				// The third loop checks if the venCve id is the same as cve id
				for (String k : cve.keySet()) {
					CVE currentCVE = cve.get(k);
					if (currentVenCve.venCve_id.contains(currentCVE.cve_id)) {
						CVE_id = currentCVE.cve_id;
						cwe_code = currentCVE.cwe_code;
						// Update the flags
						flag = true;
						flag2 = false;

						// If the data is found then this message is printed
						if (flag) {
							System.out.println(
									"CVE id: " + CVE_id + " | Product Name: " + product + " | cwe code: " + cwe_code);
						}
					}
				}

			}
		}
		// If the data is NOT found then this message is printed
		if (flag2) {
			System.out.println("*************ERROR*************\nFaild to find the specified vendor");
		}

	}

	// A method that print data by ID
	private static void printByIdPro(Map<String, Products> proCsv, Map<String, Vendor> venCve) {
		// A flag here is made to print if there no data to be shown
		boolean flag = false;
		String venName = "", vunProduct = "";
		Scanner console = new Scanner(System.in);
		// Ask for input
		String input;
		System.out.println("Please specify a CVE");

		// A validation that ensure the user enters the right id type
		while (true) {
			try {
				input = console.next();
				if (input.contains("CVE") && input.contains("-")) {
					break;
				} else {
					System.out.println("Incorrect data type. Try again:");
				}
			} catch (NumberFormatException e) {
			}
		}
		// A loop that loops around venCve and check the input that the user provided
		// with venCve_id
		for (String i : venCve.keySet()) {
			// declaring currentVenCve to try and optimise the feature, instead of keep
			// calling cve.get(i)
			Vendor currentVenCve = venCve.get(i);
			if (currentVenCve.venCve_id.equals(input)) {
				venName = currentVenCve.venName;
				// Set the flag to true
				flag = true;
			}
		}
		// A loop that loops around proCsv and check the input that the user provided
		// with proCve_id
		for (String i : proCsv.keySet()) {
			Products currentProCsv = proCsv.get(i);
			if (currentProCsv.proCve_id.equals(input)) {
				vunProduct = currentProCsv.proName;
				// Set the flag to true
				flag = true;
			}
		}

		if (flag) {
			System.out.println("Name: " + venName + " vulnerable product: " + vunProduct);
			// If the no data has matched the user input this message will appear
		} else {
			System.out.println("*************ERROR*************\nFaild to find the specified CVE");
		}
	}

	// a method that display vulnerability in the cve file
	public static void vendorsVuln(Map<String, CVE> cve, Map<String, Vendor> venCve) {
		Hashmap<String, String> testMap = new Hashmap<String, String>();
		for (String i : cve.keySet()) {
			testMap.put(cve.get(i).cve_id, cve.get(i).cve_id);
		}
		System.out.println("------------------------------------------");
		int vendCount = 0;
		// A loop that loops around the cve file to check if summary var contains
		// vulnerability
		for (String i : cve.keySet()) {
			CVE currentCVE = cve.get(i);
			if (currentCVE.summary.contains("vulnerability")) {
				// A loop that loops the vendor file and check if both ids from cve and vender
				// are equal
				for (String k : venCve.keySet()) {
					Vendor currentCVE1 = venCve.get(k);
					if (testMap.get(venCve.get(k).venCve_id) != null) {
						System.out.println("Vendor Name: " + currentCVE1.venName);
						vendCount++;
					}
				}
			}
		}
		System.out.println("\nTotal vendors with vulnerabilities are:" + vendCount);
	}

	public static List<CVEData> readFile(String filename) throws FileNotFoundException {
		// A list is made
		List<CVEData> cves = new ArrayList<>();
		// Create a file obj that read the passed path from the main method
		File csvFile = new File(filename);
		Scanner csvScan = new Scanner(csvFile);
		// scann the file
		csvScan.nextLine();
		while (csvScan.hasNextLine()) {
			// Take one line and store it in line
			String line = csvScan.nextLine();
			// split data by "," and the -1 make sure the program does not crash if the last
			// value in the line is empty
			String[] lineParts = line.split(",", -1);
			// Put the ID of the has and the rest of the CVE
			CVEData cve = new CVEData(lineParts[0], lineParts[1], lineParts[2], Double.parseDouble(lineParts[3]),
					Integer.parseInt(lineParts[4]), lineParts[5], lineParts[6], lineParts[7], lineParts[8],
					lineParts[9], lineParts[10], lineParts[11], lineParts[12]);
			cves.add(cve);
		}
		csvScan.close();
		return cves;
	}

	public static Map<String, CVE> readCVE(String filename) throws FileNotFoundException {
		// A hashmap is made
		Map<String, CVE> map = new HashMap<>();
		// Create a file obj that read the passed path from the main method
		File csvFile = new File(filename);
		// scann the file
		try (Scanner csvScan = new Scanner(csvFile)) {
			csvScan.nextLine(); // skip fist line
			while (csvScan.hasNextLine()) {
				// Take one line and store it in line
				String line = csvScan.nextLine();
				// split data by "," and the -1 make sure the program does not crash if the last
				// value in the line is empty
				String[] lineParts = line.split(",", -1);
				// Put the ID of the has and the rest of the CVE
				map.put(lineParts[0],
						new CVE(lineParts[0], lineParts[1], lineParts[2], Double.parseDouble(lineParts[3]),
								Integer.parseInt(lineParts[4]), lineParts[5], lineParts[6], lineParts[7], lineParts[8],
								lineParts[9], lineParts[10], lineParts[11], lineParts[12]));
			}
			csvScan.close();
		}
		return map;
	}

	public static Map<String, Products> readPro(String filename) throws FileNotFoundException {
		Map<String, Products> proCsv = new HashMap<>();
		File csvFile = new File(filename);
		try (Scanner csvScan = new Scanner(csvFile)) {
			csvScan.nextLine();
			while (csvScan.hasNextLine()) {
				String line = csvScan.nextLine();
				String[] lineParts = line.split(",", -1);
				proCsv.put(lineParts[0], new Products(lineParts[0], lineParts[1]));
			}
			csvScan.close();
		}
		return proCsv;
	}

	public static Map<String, Vendor> readVen(String filename) throws FileNotFoundException {
		Map<String, Vendor> venCsv = new HashMap<>();
		File csvFile = new File(filename);
		try (Scanner csvScan = new Scanner(csvFile)) {
			csvScan.nextLine();
			while (csvScan.hasNextLine()) {
				String line = csvScan.nextLine();
				String[] lineParts = line.split(",", -1);
				venCsv.put(lineParts[0], new Vendor(lineParts[0], lineParts[1]));
			}
			csvScan.close();
		}
		return venCsv;
	}
}