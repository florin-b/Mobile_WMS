package mobile.wms.beans;

import java.io.Serializable;

public class TaskSalveazaPregatire implements Serializable {

	private int id;
	private String codClient;
	private String codArticol;
	private CantitatePregatire cantitate;
	private String codSursa;
	private String codDestinatie;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCodClient() {
		return codClient;
	}

	public void setCodClient(String codClient) {
		this.codClient = codClient;
	}

	public String getCodArticol() {
		return codArticol;
	}

	public void setCodArticol(String codArticol) {
		this.codArticol = codArticol;
	}

	public CantitatePregatire getCantitate() {
		return cantitate;
	}

	public void setCantitate(CantitatePregatire cantitate) {
		this.cantitate = cantitate;
	}

	public String getCodSursa() {
		return codSursa;
	}

	public void setCodSursa(String codSursa) {
		this.codSursa = codSursa;
	}

	public String getCodDestinatie() {
		return codDestinatie;
	}

	public void setCodDestinatie(String codDestinatie) {
		this.codDestinatie = codDestinatie;
	}
}
