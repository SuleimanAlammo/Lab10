package CW2;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CVEData implements Comparable<CVEData> {
	public DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
	public String cve_id;
	public LocalDateTime mod_date;
	public LocalDateTime pub_date;
	public double cvss;
	public int cwe_code;
	public String cwe_name;
	public String summary;
	public String access_authentication;
	public String access_complexity;
	public String access_vector;
	public String impact_availability;
	public String impact_confidentiality;
	public String impact_integrity;

	public CVEData(String cve_id, String modDate, String pubDate, double cvss, int cwe_code, String cwe_name,
			String summary, String access_authentication, String access_complexity, String access_vector,
			String impact_availability, String impact_confidentiality, String impact_integrity) {
		this.cve_id = cve_id;
		String mod_date_temp = modDate;
		mod_date = LocalDateTime.parse(mod_date_temp, formatter);
		String pub_date_temp = pubDate;
		pub_date = LocalDateTime.parse(pub_date_temp, formatter);
		this.cvss = cvss;
		this.cwe_code = cwe_code;
		this.cwe_name = cwe_name;
		this.summary = summary;
		this.access_authentication = access_authentication;
		this.access_complexity = access_complexity;
		this.access_vector = access_vector;
		this.impact_availability = impact_availability;
		this.impact_confidentiality = impact_confidentiality;
		this.impact_integrity = impact_integrity;

	}
	public String toString() {
		return cve_id +","+ mod_date.format(formatter) +","+ pub_date.format(formatter) +","+ cvss +","+ cwe_code +","+ cwe_name
				+","+ summary +","+ access_authentication +","+ access_complexity +","+ access_vector +","+ impact_availability
				+","+ impact_confidentiality +","+ impact_integrity;
	}
	public int compareTo(CVEData o) {
		return 0;
	}


}
