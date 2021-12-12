package Lab101.src.ws10b.hashmap;

public class HashMapTest {
	public static void main(String[] args) {
		Hashmap<String, String> testMap = new Hashmap<String, String>();

		System.out.println("x2636".hashCode());
		System.out.println("The quick brown fox jumped over the lazy dog"
				.hashCode());
		System.out
				.println("The quick brown fox jumped over the lazy dog.The quick brown fox jumped over the lazy dog.The quick brown fox jumped over the lazy dog.The quick brown fox jumped over the lazy dog.The quick brown fox jumped over the lazy dog."
						.hashCode());

		System.out.println("Inserting elements");
		testMap.put("0151 231 2636", "David Lamb");
		testMap.put("0151 231 2106", "Chris Carter");
		testMap.put("0151 231 2268", "Stephen Tang");
		testMap.put("0151 231 2635", "Andy Symons");
		testMap.put("0151 231 2476", "Hulya Francis");
		testMap.put("0151 231 2276", "Mitch Mackay");
		testMap.put("0151 231 2641", "Martin Randles");
		testMap.put("0151 231 2080", "Tom Berry");

		System.out.println("Finished Inserting elements");
		System.out.println("----");
		System.out.println("The map size is now " + testMap.size());

		System.out.println(testMap.get("0151 231 2636"));
		System.out.println(testMap.get("0151 231 2641"));
		System.out.println(testMap.get("0151 231 2640"));

		// Lab 10.2 - Intermediate/Advanced - measure performance/rehashing; 
		// better for testing than above as many more items
		System.out.println("---");
		Hashmap<String, Integer> wordMap = new Hashmap<String, Integer>();

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
		
		System.out.println("---- "+wordMap.size()+", load: "+wordMap.loadFactor());
		System.out.println("Words found");
		for (String word : wordMap.keys()) {
			System.out.println(word + ", " + wordMap.get(word));
		}

	}
}
