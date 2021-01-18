package mobile.wms.listeners;

import mobile.wms.enums.EnumOperatii;

public interface OperatiiPregatireListener {
    public void operatiiPregatireComplete(EnumOperatii numeOp, String result);
}
