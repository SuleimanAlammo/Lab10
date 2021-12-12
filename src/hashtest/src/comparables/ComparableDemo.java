package comparables;

public class ComparableDemo {

	public static void main(String[] args) {
		String test1 = "Sorren";
		String test2 = "Sorren";
		
		int cmp1_2 = test1.compareTo(test2);
	
		if (cmp1_2 > 0)
			System.out.println(test1+" > "+test2);
		else if (cmp1_2 < 0)
			System.out.println(test1+" < "+test2);
		else
			System.out.println(test1+" == "+test2);
		
	}
	
	
}
