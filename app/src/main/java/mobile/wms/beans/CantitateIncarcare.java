package mobile.wms.beans;

import java.io.Serializable;

public class CantitateIncarcare implements Serializable {

	private int cantUmBaza;
	private String umBaza;
	private int cantPaleti;

	public int getCantUmBaza() {
		return cantUmBaza;
	}

	public void setCantUmBaza(int cantUmBaza) {
		this.cantUmBaza = cantUmBaza;
	}

	public String getUmBaza() {
		return umBaza;
	}

	public void setUmBaza(String umBaza) {
		this.umBaza = umBaza;
	}

	public int getCantPaleti() {
		return cantPaleti;
	}

	public void setCantPaleti(int cantPaleti) {
		this.cantPaleti = cantPaleti;
	}

}
