package mobile.wms.beans;

import java.io.Serializable;

public class CantitatePregatire implements Serializable {

	private int cantitate;
	private String um;

	public int getCantitate() {
		return cantitate;
	}

	public void setCantitate(int cantitate) {
		this.cantitate = cantitate;
	}

	public String getUm() {
		return um;
	}

	public void setUm(String um) {
		this.um = um;
	}
}
