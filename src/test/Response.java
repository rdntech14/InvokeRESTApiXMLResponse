
package test;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Response {
	private String data;
	@Override
	public String toString() {
		return "Response [apierror=" + apierror + "]";
	}
	
	private Apierror apierror;
	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	@XmlElement
	public Apierror getApierror() {
		return apierror;
	}

	public void setApierror(Apierror apierror) {
		this.apierror = apierror;
	}
	
}
