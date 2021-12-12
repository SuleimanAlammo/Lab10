package ws10a.bstmap;

public class BSTMapTest {
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

		String paragraph = "This government had an idea "
				+ "And parliament made it law " + "It seems like it's illegal "
				+ "To fight for the union any more " +

				"Which side are you on, boys? " + "Which side are you on? "
				+ "Which side are you on, boys? " + "Which side are you on? " +

				"We set out to join the picket line "
				+ "For together we cannot fail "
				+ "We got stopped by police at the county line "
				+ "They said, Go home boys or you're going to jail " +

				"Which side are you on, boys? " + "Which side are you on? "
				+ "Which side are you on, boys? " + "Which side are you on?";

		for (String word : paragraph.split("( |[,?.])")) {
			word = word.toLowerCase();
			Integer count = wordMap.get(word);
			if (count == null)
				count = 0;
			count++;
			wordMap.put(word, count);
		}
		
		System.out.println("----");
		System.out.println("Words found");
		for (String word : wordMap.keys()) {
			System.out.println(word + ", " + wordMap.get(word));
		}

	}
}
