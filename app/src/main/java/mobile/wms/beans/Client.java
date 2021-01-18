package mobile.wms.beans;

import java.io.Serializable;

public class Client implements Serializable {

	private String cod;
	private String nume;

	public String getCod() {
		return cod;
	}

	public void setCod(String cod) {
		this.cod = cod;
	}

	public String getNume() {
		return nume;
	}

	public void setNume(String nume) {
		this.nume = nume;
	}
}
