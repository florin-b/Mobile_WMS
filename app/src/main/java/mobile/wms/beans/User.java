package mobile.wms.beans;

public class User {
	private String nume;
	private String codUser;
	private String codUtilaj;
	private String tipAngajat;
	private boolean loginSucces;
	private TaskUser taskUser;

	private static User instance = new User();

	private User(){

	}

	public static User getInstance(){
		return instance;
	}

	public String getNume() {
		return nume;
	}

	public String getCodUser() {
		return codUser;
	}

	public String getCodUtilaj() {
		return codUtilaj;
	}

	public String getTipAngajat() {
		return tipAngajat;
	}

	public boolean isLoginSucces() {
		return loginSucces;
	}

	public void setNume(String nume) {
		this.nume = nume;
	}

	public void setCodUser(String codUser) {
		this.codUser = codUser;
	}

	public void setCodUtilaj(String codUtilaj) {
		this.codUtilaj = codUtilaj;
	}

	public void setTipAngajat(String tipAngajat) {
		this.tipAngajat = tipAngajat;
	}

	public void setLoginSucces(boolean loginSucces) {
		this.loginSucces = loginSucces;
	}

	public TaskUser getTaskUser() {
		return taskUser;
	}

	public void setTaskUser(TaskUser taskUser) {
		this.taskUser = taskUser;
	}
}
