package mobile.wms.beans;

import java.io.Serializable;
import java.util.List;

public class Pregatire implements Serializable {

	private int id;
	private Client client;
	private List<ArticolPregatire> listArticole;
	private String boxaPregatire;

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

	public List<ArticolPregatire> getListArticole() {
		return listArticole;
	}

	public void setListArticole(List<ArticolPregatire> listArticole) {
		this.listArticole = listArticole;
	}

	public String getBoxaPregatire() {
		return boxaPregatire;
	}

	public void setBoxaPregatire(String boxaPregatire) {
		this.boxaPregatire = boxaPregatire;
	}
}
