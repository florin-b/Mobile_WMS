package mobile.wms.view;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import java.util.HashMap;

import mobile.wms.R;

import mobile.wms.beans.User;
import mobile.wms.enums.EnumOperatii;
import mobile.wms.enums.EnumTipTask;
import mobile.wms.listeners.OperatiiUserListener;
import mobile.wms.model.OperatiiUser;


public class Login extends AppCompatActivity implements OperatiiUserListener {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private OperatiiUser opUser;
    private TextView textUser, textUtilaj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.login);
        loginButton = (Button) findViewById(R.id.loginButton);
        setListenerLoginButton();

        opUser = new OperatiiUser(this);
        opUser.setOperatiiUserListener(this);

        textUser = (TextView) findViewById(R.id.textUser);
        textUtilaj = (TextView) findViewById(R.id.textUtilaj);

        textUser.setText("333");
        textUtilaj.setText("789");

    }


    private void setListenerLoginButton() {
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performLogin();
            }
        });
    }


    private void performLogin() {

        String strUser = textUser.getText().toString().trim();
        String strUtilaj = textUtilaj.getText().toString().trim();

        if (strUser.isEmpty() || strUtilaj.isEmpty())
            return;

        User.getInstance().setCodUser(strUser);
        User.getInstance().setCodUtilaj(strUtilaj);

        HashMap<String, String> params = new HashMap<>();
        params.put("codUser", strUser);
        params.put("codUtilaj", strUtilaj);
        opUser.login(params);

    }

    private void handleLoginStatus(String result) {
        opUser.deserializeUser(result);

        if (User.getInstance().isLoginSucces()) {

            if (User.getInstance().getTaskUser().getTaskPropriu() != null) {
                redirectToTaskScreen(User.getInstance().getTaskUser().getTaskPropriu().getTipTask());

            } else {
                Intent nextScreen = new Intent(getApplicationContext(), NoTask.class);
                nextScreen.putExtra("taskUser", User.getInstance().getTaskUser());
                startActivity(nextScreen);
                finish();
            }


        } else {
            ((TextView) findViewById(R.id.textStatus)).setVisibility(View.VISIBLE);
            ((TextView) findViewById(R.id.textStatus)).setText("Eroare la conectare.");
        }

    }

    private void redirectToTaskScreen(EnumTipTask tipTask){


    switch (tipTask) {
        case RECEPTIE:
            Intent nextScreen = new Intent(getApplicationContext(), ReceptieActivity.class);
            nextScreen.putExtra("receptie", User.getInstance().getTaskUser().getTaskPropriu().getReceptie());
            startActivity(nextScreen);
            finish();
            break;
        case PREGATIRE:
            Intent nextScreen1 = new Intent(getApplicationContext(), PregatireActivity.class);
            nextScreen1.putExtra("pregatire", User.getInstance().getTaskUser().getTaskPropriu().getPregatire());
            startActivity(nextScreen1);
            finish();
            break;
        case INCARCARE_PALET:
            Intent nextScreen2 = new Intent(getApplicationContext(), IncarcarePaletiActivity.class);
            nextScreen2.putExtra("incarcarePaleti", User.getInstance().getTaskUser().getTaskPropriu().getIncarcarePalet());
            startActivity(nextScreen2);
            finish();
            break;
        default:
            break;

    }




    }


    @Override
    public void operatiiUserComplete(EnumOperatii numeOp, String result) {
        switch (numeOp) {
            case LOGIN:
                handleLoginStatus(result);
                break;
            default:
                break;
        }
    }
}
