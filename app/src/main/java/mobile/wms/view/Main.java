package mobile.wms.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

import mobile.wms.R;
import mobile.wms.beans.TaskExtern;
import mobile.wms.beans.TaskUser;
import mobile.wms.beans.User;
import mobile.wms.enums.EnumOperatii;
import mobile.wms.listeners.OperatiiUserListener;
import mobile.wms.model.OperatiiUser;

public class Main extends AppCompatActivity implements OperatiiUserListener {

    private OperatiiUser opUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);

        opUser = new OperatiiUser(this);
        opUser.setOperatiiUserListener(this);

        getTaskUser();
    }


    private void getTaskUser(){
        HashMap<String, String> params = new HashMap<>();
        params.put("codUser",User.getInstance().getCodUser());
        params.put("codUtilaj",User.getInstance().getCodUtilaj());
        opUser.getTaskUser(params);
    }

    private void handleTaskUser(String result){
       TaskUser taskUser = opUser.deserializeTaskUser(result);

       if (taskUser.getTaskPropriu() != null) {

       }
       else{

           taskUser.setAlteTaskuri(new ArrayList<TaskExtern>());
           Intent nextScreen = new Intent(getApplicationContext(), NoTask.class);
           nextScreen.putExtra("taskUser", taskUser);
           startActivity(nextScreen);
           finish();
       }

    }


    @Override
    public void operatiiUserComplete(EnumOperatii numeOp, String result) {
        switch(numeOp){
            case TASK_USER:
                handleTaskUser(result);
                break;
            default:break;
        }

    }
}
