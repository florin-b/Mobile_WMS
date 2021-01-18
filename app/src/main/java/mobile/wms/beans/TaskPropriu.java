package mobile.wms.beans;

import java.io.Serializable;

import mobile.wms.enums.EnumTipTask;

public class TaskPropriu  {

    private EnumTipTask tipTask;
    private Receptie receptie;
    private Pregatire pregatire;
    private IncarcarePalet incarcarePalet;


    public EnumTipTask getTipTask() {
        return tipTask;
    }

    public void setTipTask(EnumTipTask tipTask) {
        this.tipTask = tipTask;
    }

    public Receptie getReceptie() {
        return receptie;
    }

    public void setReceptie(Receptie receptie) {
        this.receptie = receptie;
    }

    public Pregatire getPregatire() {
        return pregatire;
    }

    public void setPregatire(Pregatire pregatire) {
        this.pregatire = pregatire;
    }

    public IncarcarePalet getIncarcarePalet() {
        return incarcarePalet;
    }

    public void setIncarcarePalet(IncarcarePalet incarcarePalet) {
        this.incarcarePalet = incarcarePalet;
    }
}
