package mobile.wms.beans;

import java.io.Serializable;

public class Articol implements Serializable {

    private String cod;
    private String denumire;

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }
}
