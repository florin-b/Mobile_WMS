package mobile.wms.listeners;

import mobile.wms.enums.EnumOperatii;

public interface OperatiiUserListener {
    public void operatiiUserComplete(EnumOperatii numeOp, String result);
}
