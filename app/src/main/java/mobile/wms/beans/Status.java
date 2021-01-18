package mobile.wms.beans;

public class Status {

	private String msg;
	private boolean succes;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public boolean isSucces() {
		return succes;
	}

	public void setSucces(boolean succes) {
		this.succes = succes;
	}

	@Override
	public String toString() {
		return "Status{" +
				"msg='" + msg + '\'' +
				", succes=" + succes +
				'}';
	}
}
