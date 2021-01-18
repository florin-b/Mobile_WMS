package mobile.wms.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.HashMap;

import mobile.wms.R;
import mobile.wms.adapters.AdapterPregatire;
import mobile.wms.beans.ArticolPregatire;
import mobile.wms.beans.CantitatePregatire;
import mobile.wms.beans.Pregatire;
import mobile.wms.beans.Status;
import mobile.wms.beans.TaskSalveazaPregatire;
import mobile.wms.beans.TaskUser;
import mobile.wms.beans.User;
import mobile.wms.enums.EnumOperatii;
import mobile.wms.enums.EnumTipScan;
import mobile.wms.enums.EnumTipTask;
import mobile.wms.listeners.OperatiiPregatireListener;
import mobile.wms.listeners.OperatiiUserListener;
import mobile.wms.model.OperatiiPregatire;
import mobile.wms.model.OperatiiUser;


public class PregatireActivity extends AppCompatActivity implements OperatiiUserListener, OperatiiPregatireListener {

    private Pregatire taskPregatire;

    private TextView textClient;
    private TextView labelScan;
    private ListView listViewArticole;
    private EditText textScan;
    private Button buttonScan;
    private AdapterPregatire adapter;
    private EnumTipScan tipScan;
    private OperatiiUser opUser;
    private int nrArticoleScanate;
    private TextView textStatus, textBoxa;
    private ImageView artScanStatus;
    private OperatiiPregatire operatiiPregatire;
    private ArticolPregatire articolCurent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        taskPregatire = (Pregatire) getIntent().getSerializableExtra("pregatire");
        setContentView(R.layout.pregatire);
        getSupportActionBar().setTitle("PREGATIRE");

        opUser = new OperatiiUser(this);
        opUser.setOperatiiUserListener(this);

        operatiiPregatire = new OperatiiPregatire(this);
        operatiiPregatire.setOperatiiPregatireListener(this);

        initLayout();
        setupLayout();

    }


    private void initLayout() {
        textClient = (TextView) findViewById(R.id.textClient);
        listViewArticole = (ListView) findViewById(R.id.listArticole);
        textScan = (EditText) findViewById(R.id.textScan);
        buttonScan = (Button) findViewById((R.id.buttonScan));
        labelScan = (TextView) findViewById(R.id.labelScan);
        textStatus = (TextView) findViewById(R.id.textStatus);
        artScanStatus = (ImageView) findViewById(R.id.artScanStatus);
        textBoxa = (TextView) findViewById(R.id.textBoxa);
    }

    private void setupLayout() {

        textClient.setText(taskPregatire.getClient().getNume());
        textBoxa.setText(taskPregatire.getBoxaPregatire());
        adapter = new AdapterPregatire(this, taskPregatire.getListArticole());
        listViewArticole.setAdapter(adapter);
        setListenerButtonScan();

        tipScan = EnumTipScan.MATERIAL;
        nrArticoleScanate = 0;
        textStatus.setText(nrArticoleScanate + " / " + taskPregatire.getListArticole().size());

    }

    private void setListenerButtonScan() {
        buttonScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tipScan == EnumTipScan.MATERIAL)
                    scanPreluare();
                else
                    scanFinalizare();
            }
        });
    }


    private void scanPreluare() {

        String strScan = textScan.getText().toString().trim();

        if (strScan.isEmpty())
            return;

        setMaterialScan(strScan);

    }

    private void saveMatPregatire(){

        if (articolCurent == null)
            return;

        TaskSalveazaPregatire taskSalveazaPregatire = new TaskSalveazaPregatire();
        taskSalveazaPregatire.setId(taskPregatire.getId());
        taskSalveazaPregatire.setCodClient(taskPregatire.getClient().getCod());
        taskSalveazaPregatire.setCodArticol(articolCurent.getArticol().getCod());
        taskSalveazaPregatire.setCodSursa(articolCurent.getSursa());
        taskSalveazaPregatire.setCodDestinatie(textScan.getText().toString().trim());

        CantitatePregatire cantPreg = new CantitatePregatire();
        cantPreg.setCantitate(articolCurent.getCantitate().getCantitate());
        cantPreg.setUm(articolCurent.getCantitate().getUm());
        taskSalveazaPregatire.setCantitate(cantPreg);

        HashMap<String, String> params = new HashMap<>();
        params.put("params", operatiiPregatire.serializeTaskPregatire(taskSalveazaPregatire));

        operatiiPregatire.salveazaTaskPregatire(params);


    }


    private void scanFinalizare() {
        String strScan = textScan.getText().toString().trim();

        if (strScan.isEmpty())
            return;

        if (isPregatireComplete()) {

            //--------TEST
            User.getInstance().setCodUser("000");
            //--------

            getTaskUser();
            return;
        }

        saveMatPregatire();

        tipScan = EnumTipScan.MATERIAL;
        labelScan.setText("Scan preluare");
        textScan.setText("");

    }

    private void getTaskUser() {

        HashMap<String, String> params = new HashMap<>();
        params.put("codUser", User.getInstance().getCodUser());
        params.put("codUtilaj", User.getInstance().getCodUtilaj());
        opUser.getTaskUser(params);

    }

    private void setMaterialScan(String scanCode) {

        int pos = 0;

        boolean artFound = false;
        for (ArticolPregatire articol : taskPregatire.getListArticole()) {
            if (!articol.isPreluat() && articol.getCodBare().equals(scanCode)) {
                articol.setPreluat(true);
                artFound = true;
                articolCurent = articol;
                adapter.notifyDataSetChanged();
                listViewArticole.smoothScrollToPosition(pos);
                break;
            }

            pos++;
        }

        textScan.setText("");

        if (artFound) {
            labelScan.setText("Scan finalizare");
            tipScan = EnumTipScan.CELULA;
            nrArticoleScanate++;

            if (nrArticoleScanate > 0)
                artScanStatus.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.circle_yellow));

            textStatus.setText(nrArticoleScanate + " / " + taskPregatire.getListArticole().size());
        }


    }

    private boolean isPregatireComplete() {

        boolean isComplete = true;

        for (ArticolPregatire articol : taskPregatire.getListArticole()) {
            if (!articol.isPreluat()) {
                isComplete = false;
                break;
            }

        }

        return isComplete;
    }


    private void handleTaskUser(String result) {

        if (result == null || result.isEmpty())
            return;

        TaskUser taskUser = opUser.deserializeTaskUser(result);

        if (taskUser.getTaskPropriu() != null) {
            if (taskUser.getTaskPropriu().getTipTask() == EnumTipTask.PREGATIRE) {
                taskPregatire = taskUser.getTaskPropriu().getPregatire();
                setupLayout();
            } else if (taskUser.getTaskPropriu().getTipTask() == EnumTipTask.RECEPTIE) {
                Intent nextScreen = new Intent(getApplicationContext(), ReceptieActivity.class);
                nextScreen.putExtra("receptie", taskUser.getTaskPropriu().getReceptie());
                startActivity(nextScreen);
                finish();
            }

        } else {
            Intent nextScreen = new Intent(getApplicationContext(), NoTask.class);
            nextScreen.putExtra("taskUser", taskUser);
            startActivity(nextScreen);
            finish();
        }


    }

    private void handleSaveMaterial(String result){
        Status status = operatiiPregatire.deserializeStatus(result);

    }

    @Override
    public void operatiiUserComplete(EnumOperatii numeOp, String result) {
        switch (numeOp) {
            case TASK_USER:
                handleTaskUser(result);
                break;
            default:
                break;
        }
    }

    @Override
    public void operatiiPregatireComplete(EnumOperatii numeOp, String result) {
        switch(numeOp){
            case SAVE_PREGATIRE:
                handleSaveMaterial(result);
                break;
            default:
                break;

        }
    }
}
