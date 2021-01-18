package mobile.wms.view;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

import mobile.wms.R;
import mobile.wms.beans.Receptie;
import mobile.wms.beans.TaskUser;
import mobile.wms.beans.User;
import mobile.wms.enums.EnumOperatii;
import mobile.wms.enums.EnumTipScan;
import mobile.wms.enums.EnumTipTask;
import mobile.wms.listeners.OperatiiReceptieListener;
import mobile.wms.listeners.OperatiiUserListener;
import mobile.wms.model.OperatiiReceptie;
import mobile.wms.model.OperatiiUser;

public class ReceptieActivity extends AppCompatActivity implements OperatiiReceptieListener, OperatiiUserListener {

    private Receptie taskReceptie;
    private TextView textSursa1;
    private TextView textSursa2;

    private TextView textDest;
    private Button buttonScan;
    private EditText textScan;
    private TextView textMaterialCod, textMaterialNume;
    private TextView textCantUmb, textPaleti;
    private TextView labelScan;
    private ImageView artScanOk;

    private OperatiiReceptie opReceptie;
    private OperatiiUser opUser;
    private Receptie articolReceptie;
    private EnumTipScan tipScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        taskReceptie = (Receptie) getIntent().getSerializableExtra("receptie");
        setContentView(R.layout.receptie);
        getSupportActionBar().setTitle("RECEPTIE");

        opReceptie = new OperatiiReceptie(this);
        opReceptie.setOperatiiReceptieListener(this);

        opUser = new OperatiiUser(this);
        opUser.setOperatiiUserListener(this);

        initLayout();
        setupLayout();
    }

    private void initLayout() {

        textSursa1 = (TextView) findViewById(R.id.textSursa1);
        textSursa2 = (TextView) findViewById(R.id.textSursa2);
        textDest = (TextView) findViewById(R.id.textDest);
        textScan = (EditText) findViewById(R.id.textScan);

        textMaterialCod = (TextView) findViewById(R.id.textMaterialCod);
        textMaterialNume = (TextView) findViewById(R.id.textMaterialNume);

        textCantUmb = (TextView) findViewById(R.id.textCantUmb);
        textPaleti = (TextView) findViewById(R.id.textPaleti);

        labelScan = (TextView) findViewById(R.id.labelScan);
        artScanOk = (ImageView) findViewById(R.id.artScanOk);

        buttonScan = (Button) findViewById(R.id.buttonScan);
        setListenerButtonScan();

    }

    private void setupLayout() {


        textSursa1.setText(taskReceptie.getSursa().getNrAuto());
        textSursa2.setText(taskReceptie.getSursa().getFurnizor());

        textDest.setText("");
        textScan.setText("");

        textMaterialCod.setText("Scaneaza un material din camion.");
        textMaterialNume.setText("");

        textCantUmb.setText("");
        textPaleti.setText("");

        labelScan.setText("Scan preluare");
        artScanOk.setVisibility(View.INVISIBLE);
        tipScan = EnumTipScan.MATERIAL;


    }


    private void setListenerButtonScan() {
        buttonScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tipScan == EnumTipScan.MATERIAL)
                    performScanPreluare();
                else if (tipScan == EnumTipScan.CELULA)
                    performScanFinalizare();

            }
        });
    }

    private void performScanPreluare() {

        String strScan = textScan.getText().toString().trim();

        if (strScan.isEmpty())
            return;

        //-----TEST
        if (strScan.equals("222"))
            User.getInstance().setCodUser("000");

        //-----TEST


        HashMap<String, String> params = new HashMap<>();
        params.put("idReceptie", String.valueOf(taskReceptie.getId()));
        params.put("codBare", strScan);

        opReceptie.getMaterialReceptie(params);

    }

    private void performScanFinalizare() {

        String strScan = textScan.getText().toString().trim();

        if (strScan.isEmpty())
            return;

        HashMap<String, String> params = new HashMap<>();
        params.put("idReceptie", String.valueOf(taskReceptie.getId()));
        params.put("codArticol", articolReceptie.getArticol().getCod());
        params.put("cantitate", String.valueOf(articolReceptie.getCantitate().getCantPaleti()));
        params.put("codBare", strScan);
        params.put("codUser", User.getInstance().getCodUser());

        getTaskUser();

    }


    private void getTaskUser() {

        HashMap<String, String> params = new HashMap<>();
        params.put("codUser", User.getInstance().getCodUser());
        params.put("codUtilaj", User.getInstance().getCodUtilaj());
        opUser.getTaskUser(params);

    }


    private void showMaterialReceptie(String result) {

        if (result == null || result.isEmpty())
            return;

        articolReceptie = opReceptie.deserMaterialReceptie(result);

        textDest.setText(articolReceptie.getDestinatie());
        textMaterialCod.setText(articolReceptie.getArticol().getCod());
        textMaterialNume.setText(articolReceptie.getArticol().getDenumire());
        textCantUmb.setText(articolReceptie.getCantitate().getCantUmBaza() + " " + articolReceptie.getCantitate().getUmBaza());
        textPaleti.setText(articolReceptie.getCantitate().getCantPaleti() + "");
        textScan.setText("");
        labelScan.setText("Scan finalizare:");
        artScanOk.setVisibility(View.VISIBLE);
        tipScan = EnumTipScan.CELULA;

    }

    private void showAlertDialog(String alertText) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(alertText)
                .setCancelable(false)
                .setPositiveButton("Inchide", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }

    private void handleTaskUser(String result) {

        if (result == null || result.isEmpty())
            return;

        TaskUser taskUser = opUser.deserializeTaskUser(result);

        if (taskUser.getTaskPropriu() != null) {
            if (taskUser.getTaskPropriu().getTipTask() == EnumTipTask.RECEPTIE) {
                taskReceptie = taskUser.getTaskPropriu().getReceptie();
                setupLayout();
            } else if (taskUser.getTaskPropriu().getTipTask() == EnumTipTask.PREGATIRE) {
                Intent nextScreen1 = new Intent(getApplicationContext(), PregatireActivity.class);
                nextScreen1.putExtra("pregatire", taskUser.getTaskPropriu().getPregatire());
                startActivity(nextScreen1);
                finish();
            }

        } else {
            Intent nextScreen = new Intent(getApplicationContext(), NoTask.class);
            nextScreen.putExtra("taskUser", taskUser);
            startActivity(nextScreen);
            finish();
        }


    }


    @Override
    public void operatiiReceptieComplete(EnumOperatii numeOp, String result) {

        switch (numeOp) {
            case MAT_RECEPT:
                showMaterialReceptie(result);
                break;
            default:
                break;
        }

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
}
