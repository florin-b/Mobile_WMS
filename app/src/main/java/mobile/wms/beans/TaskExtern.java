package mobile.wms.beans;

import java.io.Serializable;

public class TaskExtern implements Serializable {

	private String nume;
	private int cantitate;

	public String getNume() {
		return nume;
	}

	public void setNume(String nume) {
		this.nume = nume;
	}

	public int getCantitate() {
		return cantitate;
	}

	public void setCantitate(int cantitate) {
		this.cantitate = cantitate;
	}
}
