package mobile.wms.beans;

import java.io.Serializable;

public class Valabilitate implements Serializable {

	private String dataProd;
	private String dataExp;

	public String getDataProd() {
		return dataProd;
	}

	public void setDataProd(String dataProd) {
		this.dataProd = dataProd;
	}

	public String getDataExp() {
		return dataExp;
	}

	public void setDataExp(String dataExp) {
		this.dataExp = dataExp;
	}

}
