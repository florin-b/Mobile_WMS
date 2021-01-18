package mobile.wms.beans;

import java.io.Serializable;

public class Receptie implements Serializable {

    private int id;
    private SursaReceptie sursa;
    private String destinatie;
    private CantitateReceptie cantitate;
    private Articol articol;
    private String codBarePreluare;
    private String codBareFinalizare;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public SursaReceptie getSursa() {
        return sursa;
    }

    public void setSursa(SursaReceptie sursa) {
        this.sursa = sursa;
    }

    public String getDestinatie() {
        return destinatie;
    }

    public void setDestinatie(String destinatie) {
        this.destinatie = destinatie;
    }

    public CantitateReceptie getCantitate() {
        return cantitate;
    }

    public void setCantitate(CantitateReceptie cantitate) {
        this.cantitate = cantitate;
    }

    public Articol getArticol() {
        return articol;
    }

    public void setArticol(Articol articol) {
        this.articol = articol;
    }

    public String getCodBarePreluare() {
        return codBarePreluare;
    }

    public void setCodBarePreluare(String codBarePreluare) {
        this.codBarePreluare = codBarePreluare;
    }

    public String getCodBareFinalizare() {
        return codBareFinalizare;
    }

    public void setCodBareFinalizare(String codBareFinalizare) {
        this.codBareFinalizare = codBareFinalizare;
    }
}
