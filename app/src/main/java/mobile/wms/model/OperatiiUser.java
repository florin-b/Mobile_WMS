package mobile.wms.model;

import android.content.Context;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import mobile.wms.beans.Articol;
import mobile.wms.beans.ArticolPregatire;
import mobile.wms.beans.CantitateIncarcare;
import mobile.wms.beans.CantitatePregatire;
import mobile.wms.beans.Client;
import mobile.wms.beans.IncarcarePalet;
import mobile.wms.beans.Pregatire;
import mobile.wms.beans.Receptie;
import mobile.wms.beans.SursaReceptie;
import mobile.wms.beans.TaskExtern;
import mobile.wms.beans.TaskPropriu;
import mobile.wms.beans.TaskUser;
import mobile.wms.beans.User;
import mobile.wms.beans.Valabilitate;
import mobile.wms.enums.EnumOperatii;
import mobile.wms.enums.EnumTipTask;
import mobile.wms.listeners.OperatiiUserListener;
import mobile.wms.listeners.WebServiceListener;
import mobile.wms.utils.Constants;

public class OperatiiUser implements WebServiceListener {

    private EnumOperatii numeOp;
    private HashMap<String, String> params;
    private Context context;
    private OperatiiUserListener listener;

    public OperatiiUser(Context context) {
        this.context = context;
    }

    public void login(HashMap<String, String> params) {
        this.numeOp = EnumOperatii.LOGIN;
        this.params = params;
        performOperation();
    }

    public void getTaskUser(HashMap<String, String> params) {
        this.numeOp = EnumOperatii.TASK_USER;
        this.params = params;
        performOperation();
    }


    public void setOperatiiUserListener(OperatiiUserListener listener) {
        this.listener = listener;
    }

    private void performOperation() {
        String url = Constants.restServAddress + this.numeOp.toString();
        WebServiceCall webServiceCall = new WebServiceCall();
        webServiceCall.setServiceListener(this);
        webServiceCall.callPostMethod(context, url, params);
    }

    public void deserializeUser(String userData) {

        try {
            JSONObject jsonObject = new JSONObject(userData);

            if (jsonObject instanceof JSONObject) {
                User.getInstance().setNume(jsonObject.getString("nume"));
                User.getInstance().setTipAngajat(jsonObject.getString("tipAngajat"));
                User.getInstance().setLoginSucces(Boolean.parseBoolean(jsonObject.getString("loginSucces")));
                User.getInstance().setTaskUser(deserializeTaskUser(jsonObject.getString("taskUser")));
            }
        } catch (JSONException e) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    public TaskUser deserializeTaskUser(String taskData) {

        TaskUser taskUser = new TaskUser();

        try {

            JSONObject jsonObject = new JSONObject(taskData);

            if (jsonObject instanceof JSONObject) {

                if (!jsonObject.getString("taskPropriu").equals("null")) {

                    TaskPropriu taskPropriu = new TaskPropriu();

                    JSONObject jsonTask = new JSONObject(jsonObject.getString("taskPropriu"));

                    EnumTipTask tipTask = EnumTipTask.NEDEFINIT;

                    if (jsonTask.getString("tipTask").equals("RECEPTIE")) {
                        tipTask = EnumTipTask.RECEPTIE;
                        Receptie receptie = getReceptieTask(jsonTask);
                        taskPropriu.setReceptie(receptie);
                    } else if (jsonTask.getString("tipTask").equals("PREGATIRE")) {
                        tipTask = EnumTipTask.PREGATIRE;
                        Pregatire pregatire = getPregatireTask(jsonTask);
                        taskPropriu.setPregatire(pregatire);
                    } else if (jsonTask.getString("tipTask").equals("INCARCARE_PALET")) {
                        tipTask = EnumTipTask.INCARCARE_PALET;
                        IncarcarePalet incarcarePalet = getIncarcarePalet(jsonTask);
                        taskPropriu.setIncarcarePalet(incarcarePalet);
                    }

                    taskPropriu.setTipTask(tipTask);
                    taskUser.setTaskPropriu(taskPropriu);
                }

                if (!jsonObject.getString("alteTaskuri").equals("null")) {
                    JSONArray jsonArray = new JSONArray(jsonObject.getString("alteTaskuri"));

                    List<TaskExtern> taskExternList = new ArrayList<>();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject taskObject = jsonArray.getJSONObject(i);
                        TaskExtern taskExtern = new TaskExtern();
                        taskExtern.setNume(taskObject.getString("nume"));
                        taskExtern.setCantitate(Integer.parseInt(taskObject.getString("cantitate")));
                        taskExternList.add(taskExtern);
                    }

                    taskUser.setAlteTaskuri(taskExternList);
                }
            }


        } catch (JSONException e) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
        }

        return taskUser;

    }


    private Receptie getReceptieTask(JSONObject jsonTask) {

        Receptie receptie = new Receptie();

        try {
            SursaReceptie sursa = new SursaReceptie();
            JSONObject jsonReceptie = new JSONObject(jsonTask.getString("receptie"));
            JSONObject jsonSursa = new JSONObject(jsonReceptie.getString("sursa"));
            sursa.setNrAuto(jsonSursa.getString("nrAuto"));
            sursa.setFurnizor(jsonSursa.getString("furnizor"));
            receptie.setSursa(sursa);
            receptie.setId(Integer.parseInt(jsonReceptie.getString("id")));
        } catch (JSONException e) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
        }

        return receptie;
    }


    private Pregatire getPregatireTask(JSONObject jsonTask) {
        Pregatire pregatire = new Pregatire();
        List<ArticolPregatire> listArticole = new ArrayList<>();

        try {

            JSONObject jsonPregatire = new JSONObject(jsonTask.getString("pregatire"));
            pregatire.setId(Integer.valueOf(jsonPregatire.getString("id")));

            JSONObject jsonClient = new JSONObject(jsonPregatire.getString("client"));
            Client client = new Client();
            client.setCod(jsonClient.getString("cod"));
            client.setNume(jsonClient.getString("nume"));
            pregatire.setClient(client);
            pregatire.setBoxaPregatire(jsonPregatire.getString("boxaPregatire"));

            JSONArray jsonArray = new JSONArray(jsonPregatire.getString("listArticole"));

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject taskObject = jsonArray.getJSONObject(i);

                ArticolPregatire artPregatire = new ArticolPregatire();
                Articol art = new Articol();

                JSONObject jsonArticol = new JSONObject(taskObject.getString("articol"));
                art.setCod(jsonArticol.getString("cod"));
                art.setDenumire(jsonArticol.getString("denumire"));
                artPregatire.setArticol(art);

                JSONObject jsonCantitate = new JSONObject(taskObject.getString("cantitate"));
                CantitatePregatire cantPreg = new CantitatePregatire();
                cantPreg.setCantitate(Integer.parseInt(jsonCantitate.getString("cantitate")));
                cantPreg.setUm(jsonCantitate.getString("um"));
                artPregatire.setCantitate(cantPreg);

                artPregatire.setSursa(taskObject.getString("sursa"));
                artPregatire.setDataProd(taskObject.getString("dataProd").substring(0, 2) + "." + taskObject.getString("dataProd").substring(2, 6));
                artPregatire.setDataExp(taskObject.getString("dataExp").substring(0, 2) + "." + taskObject.getString("dataExp").substring(2, 6));
                artPregatire.setCodBare(taskObject.getString("codBare"));

                listArticole.add(artPregatire);
            }

            pregatire.setListArticole(listArticole);

        } catch (JSONException e) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
        }

        return pregatire;
    }

    private IncarcarePalet getIncarcarePalet(JSONObject jsonTask) {
        IncarcarePalet incarcarePalet = new IncarcarePalet();

        //incarcarePalet":{"id":123,"client":{"cod":"4110016380","nume":"TANCRAD SRL GALATI"},"sursa":"74.501.6",
        // "destinatie":"GL-12-ARB (TRAP)","articol":{"cod":"10700879","denumire":"CM9 ADEZIV CERESIT INT 25KG"},
        // "cantitate":{"cantUmBaza":48,"umBaza":"SAC","cantPaleti":1},"valabilitate":{"dataProd":"06.2020","dataExp":"03.2021"}

        try{

            JSONObject jsonIncarcare = new JSONObject(jsonTask.getString("incarcarePalet"));

            incarcarePalet.setId(Integer.valueOf(jsonIncarcare.getString("id")));

            JSONObject jsonClient = new JSONObject(jsonIncarcare.getString("client"));

            Client client = new Client();
            client.setCod(jsonClient.getString("cod"));
            client.setNume(jsonClient.getString("nume"));
            incarcarePalet.setClient(client);

            incarcarePalet.setSursa(jsonIncarcare.getString("sursa"));
            incarcarePalet.setDestinatie(jsonIncarcare.getString("destinatie"));

            JSONObject jsonArticol = new JSONObject(jsonIncarcare.getString("articol"));
            Articol articol = new Articol();
            articol.setCod(jsonArticol.getString("cod"));
            articol.setDenumire(jsonArticol.getString("denumire"));
            incarcarePalet.setArticol(articol);

            JSONObject jsonCant = new JSONObject(jsonIncarcare.getString("cantitate"));
            CantitateIncarcare cant = new CantitateIncarcare();
            cant.setCantUmBaza(Integer.valueOf(jsonCant.getString("cantUmBaza")));
            cant.setUmBaza(jsonCant.getString("umBaza"));
            cant.setCantPaleti(Integer.valueOf(jsonCant.getString("cantPaleti")));

            incarcarePalet.setCantitate(cant);

            JSONObject jsonValabil = new JSONObject(jsonIncarcare.getString("valabilitate"));
            Valabilitate valabil = new Valabilitate();
            valabil.setDataProd(jsonValabil.getString("dataProd"));
            valabil.setDataExp(jsonValabil.getString("dataExp"));
            incarcarePalet.setValabilitate(valabil);

        }catch (JSONException e) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
        }

        return incarcarePalet;
    }

    @Override
    public void onCallComplete(String result) {
        if (listener != null)
            listener.operatiiUserComplete(numeOp, result);
    }
}
