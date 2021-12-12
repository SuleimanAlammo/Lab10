package ss;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ssd {
	public static void main(String[] args) throws FileNotFoundException {
		File csvFile = new File("hi.txt");
		try (Scanner scanner = new Scanner(csvFile)) {
			scanner.next();

			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] lineParts = line.split(",");
				
				System.out.println(lineParts);
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}
}
