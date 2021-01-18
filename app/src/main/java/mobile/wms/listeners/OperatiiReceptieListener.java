package mobile.wms.listeners;

import mobile.wms.enums.EnumOperatii;

public interface OperatiiReceptieListener {
    public void operatiiReceptieComplete(EnumOperatii numeOp, String result);
}
