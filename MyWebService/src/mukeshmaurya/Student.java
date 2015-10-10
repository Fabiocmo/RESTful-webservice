package mukeshmaurya;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Student{
	private String name;
	private String roll;
	private String email;
	private String mobile;
	private String pass;
	@XmlElement
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@XmlElement
	public String getRoll() {
		return roll;
	}
	public void setRoll(String roll) {
		this.roll = roll;
	}
	@XmlElement
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@XmlElement
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	@XmlElement
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	

}
