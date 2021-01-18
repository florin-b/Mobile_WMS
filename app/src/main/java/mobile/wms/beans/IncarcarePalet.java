package mobile.wms.beans;

import java.io.Serializable;

public class IncarcarePalet implements Serializable {

	private int id;
	private Client client;
	private String sursa;
	private String destinatie;
	private Articol articol;
	private CantitateIncarcare cantitate;
	private Valabilitate valabilitate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public String getSursa() {
		return sursa;
	}

	public void setSursa(String sursa) {
		this.sursa = sursa;
	}

	public String getDestinatie() {
		return destinatie;
	}

	public void setDestinatie(String destinatie) {
		this.destinatie = destinatie;
	}

	public Articol getArticol() {
		return articol;
	}

	public void setArticol(Articol articol) {
		this.articol = articol;
	}

	public CantitateIncarcare getCantitate() {
		return cantitate;
	}

	public void setCantitate(CantitateIncarcare cantitate) {
		this.cantitate = cantitate;
	}

	public Valabilitate getValabilitate() {
		return valabilitate;
	}

	public void setValabilitate(Valabilitate valabilitate) {
		this.valabilitate = valabilitate;
	}

}
