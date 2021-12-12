package CW2;



public class Products {
	public String proCve_id;
	public String proName;

	public Products(String proCve_id, String proName) {
		this.proCve_id = proCve_id;
		this.proName = proName;
	}

	public String toCSVString() {
		return proCve_id + proName;
	}
}
