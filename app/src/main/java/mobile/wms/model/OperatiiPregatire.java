package mobile.wms.model;

import android.content.Context;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import mobile.wms.beans.Articol;
import mobile.wms.beans.CantitateReceptie;
import mobile.wms.beans.Status;
import mobile.wms.beans.TaskSalveazaPregatire;
import mobile.wms.enums.EnumOperatii;
import mobile.wms.listeners.OperatiiPregatireListener;
import mobile.wms.listeners.WebServiceListener;
import mobile.wms.utils.Constants;

public class OperatiiPregatire implements WebServiceListener {

    private EnumOperatii numeOp;
    private HashMap<String, String> params;
    private Context context;
    private OperatiiPregatireListener listener;


    public OperatiiPregatire(Context context) {
        this.context = context;
    }

    public void salveazaTaskPregatire(HashMap<String, String> params) {
        this.params = params;
        this.numeOp = EnumOperatii.SAVE_PREGATIRE;
        performOperation();
    }

    private void performOperation() {
        String url = Constants.restServAddress + this.numeOp.toString();
        WebServiceCall webServiceCall = new WebServiceCall();
        webServiceCall.setServiceListener(this);
        webServiceCall.callPostMethod(context, url, params);
    }

    public String serializeTaskPregatire(TaskSalveazaPregatire taskPregatire) {

        JSONObject obj = new JSONObject();

        try {
            obj.put("id", String.valueOf(taskPregatire.getId()));
            obj.put("codClient", taskPregatire.getCodClient());
            obj.put("codArticol", taskPregatire.getCodArticol());

            JSONObject objCant = new JSONObject();
            objCant.put("cantitate", String.valueOf(taskPregatire.getCantitate().getCantitate()));
            objCant.put("um", taskPregatire.getCantitate().getUm());
            obj.put("cantitate", objCant.toString());
            obj.put("codSursa", taskPregatire.getCodSursa());
            obj.put("codDestinatie", taskPregatire.getCodDestinatie());

        } catch (Exception ex) {
            Toast.makeText(context, ex.toString(), Toast.LENGTH_LONG).show();
        }

        return obj.toString();

    }

    public Status deserializeStatus(String strStatus) {

        Status status = new Status();

        try {
            JSONObject jsonStatus = new JSONObject(strStatus);
            status.setMsg(jsonStatus.getString("msg"));
            status.setSucces(jsonStatus.getBoolean("succes"));

        } catch (JSONException e) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
        }

        return status;

    }


    public void setOperatiiPregatireListener(OperatiiPregatireListener listener) {
        this.listener = listener;
    }


    @Override
    public void onCallComplete(String result) {
        if (listener != null)
            listener.operatiiPregatireComplete(numeOp, result);
    }
}
