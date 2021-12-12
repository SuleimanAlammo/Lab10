package CW2;


public class Vendor {
	public String venCve_id;
	public String venName;

	public Vendor(String venCve_id, String venName) {
		this.venCve_id = venCve_id;
		this.venName = venName;
	}


	public String toString() {
		return venCve_id + venName;
	}
}
