package mobile.wms.model;

import android.content.Context;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


import mobile.wms.beans.Articol;
import mobile.wms.beans.CantitateReceptie;
import mobile.wms.beans.Receptie;
import mobile.wms.enums.EnumOperatii;
import mobile.wms.listeners.OperatiiReceptieListener;
import mobile.wms.listeners.WebServiceListener;
import mobile.wms.utils.Constants;

public class OperatiiReceptie implements WebServiceListener {

    private EnumOperatii numeOp;
    private HashMap<String, String> params;
    private Context context;
    private OperatiiReceptieListener listener;

    public OperatiiReceptie(Context context){
        this.context = context;
    }

    public void getMaterialReceptie(HashMap<String, String> params){
        numeOp = EnumOperatii.MAT_RECEPT;
        this.params = params;
        performOperation();

    }

    public Receptie deserMaterialReceptie(String result){

        Receptie receptie = new Receptie();

        try {

            JSONObject jsonReceptie = new JSONObject(result);

            receptie.setDestinatie(jsonReceptie.getString("destinatie"));

            JSONObject jsonCantitate = new JSONObject(jsonReceptie.getString("cantitate"));
            CantitateReceptie cantitate = new CantitateReceptie();
            cantitate.setCantUmBaza(Integer.parseInt(jsonCantitate.getString("cantUmBaza")));
            cantitate.setUmBaza(jsonCantitate.getString("umBaza"));
            cantitate.setCantPaleti(Integer.parseInt(jsonCantitate.getString("cantPaleti")));
            receptie.setCantitate(cantitate);

            JSONObject jsonArticol = new JSONObject(jsonReceptie.getString("articol"));
            Articol articol = new Articol();
            articol.setCod(jsonArticol.getString("cod"));
            articol.setDenumire(jsonArticol.getString("denumire"));
            receptie.setArticol(articol);

        }
        catch (JSONException e) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
        }

        return receptie;

    }

    private void performOperation(){
        String url = Constants.restServAddress + this.numeOp.toString();
        WebServiceCall webServiceCall = new WebServiceCall();
        webServiceCall.setServiceListener(this);
        webServiceCall.callPostMethod(context, url, params);
    }

    public void setOperatiiReceptieListener(OperatiiReceptieListener listener){
        this.listener = listener;
    }


    @Override
    public void onCallComplete(String result) {
        if (listener != null)
            listener.operatiiReceptieComplete(numeOp, result);
    }
}
