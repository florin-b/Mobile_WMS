package mobile.wms.view;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import mobile.wms.R;
import mobile.wms.beans.IncarcarePalet;
import mobile.wms.beans.Pregatire;

public class IncarcarePaletiActivity extends AppCompatActivity {

    private TextView textClient;
    private TextView textSursa;
    private TextView textDestinatie;
    private TextView textMaterialCod;
    private TextView textMaterialNume;
    private TextView textCantUmb;
    private TextView textPaleti;
    private TextView textValabil;
    private EditText textScan;
    private IncarcarePalet taskIncarcare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.incarcare_palet);
        taskIncarcare = (IncarcarePalet) getIntent().getSerializableExtra("incarcarePaleti");
        getSupportActionBar().setTitle("INCARCARE");

        initLayout();
        setupLayout();
    }

    private void initLayout(){
        textClient = (TextView) findViewById(R.id.textClient);
        textSursa = (TextView) findViewById(R.id.textSursa);
        textDestinatie = (TextView) findViewById(R.id.textDestinatie);
        textMaterialCod = (TextView) findViewById(R.id.textMaterialCod);
        textMaterialNume = (TextView) findViewById(R.id.textMaterialNume);
        textCantUmb= (TextView) findViewById(R.id.textCantUmb);
        textPaleti= (TextView) findViewById(R.id.textPaleti);
        textValabil= (TextView) findViewById(R.id.textValabil);
        textScan= (EditText) findViewById(R.id.textScan);
    }

    private void setupLayout(){
        textClient.setText(taskIncarcare.getClient().getNume());
        textSursa.setText(taskIncarcare.getSursa());
        textDestinatie.setText(taskIncarcare.getDestinatie());
        textMaterialCod.setText(taskIncarcare.getArticol().getCod());
        textMaterialNume.setText(taskIncarcare.getArticol().getDenumire());
        textCantUmb.setText(taskIncarcare.getCantitate().getCantUmBaza() + " " + taskIncarcare.getCantitate().getUmBaza());
        textPaleti.setText(taskIncarcare.getCantitate().getCantPaleti()+"");
        textValabil.setText(taskIncarcare.getValabilitate().getDataProd() + " - " + taskIncarcare.getValabilitate().getDataExp());
    }
}
