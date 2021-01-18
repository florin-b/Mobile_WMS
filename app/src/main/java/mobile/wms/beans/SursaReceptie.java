package mobile.wms.beans;

import java.io.Serializable;

public class SursaReceptie implements Serializable {

    private String nrAuto;
    private String furnizor;


    public String getNrAuto() {
        return nrAuto;
    }

    public void setNrAuto(String nrAuto) {
        this.nrAuto = nrAuto;
    }

    public String getFurnizor() {
        return furnizor;
    }

    public void setFurnizor(String furnizor) {
        this.furnizor = furnizor;
    }
}
