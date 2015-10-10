package mukeshmaurya;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class StudentResult {
	private String subone;
	private String subtwo;
	private String subthree;
	private String subfour;
	private String subfive;
	private String subsix;
	@XmlElement
	public String getSubone() {
		return subone;
	}
	public void setSubone(String subone) {
		this.subone = subone;
	}
	@XmlElement
	public String getSubtwo() {
		return subtwo;
	}
	public void setSubtwo(String subtwo) {
		this.subtwo = subtwo;
	}
	@XmlElement
	public String getSubthree() {
		return subthree;
	}
	public void setSubthree(String subthree) {
		this.subthree = subthree;
	}
	@XmlElement
	public String getSubfour() {
		return subfour;
	}
	public void setSubfour(String subfour) {
		this.subfour = subfour;
	}
	@XmlElement
	public String getSubfive() {
		return subfive;
	}
	public void setSubfive(String subfive) {
		this.subfive = subfive;
	}
	@XmlElement
	public String getSubsix() {
		return subsix;
	}
	public void setSubsix(String subsix) {
		this.subsix = subsix;
	}
	

}
