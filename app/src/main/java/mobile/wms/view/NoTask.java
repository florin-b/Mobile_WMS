package mobile.wms.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import mobile.wms.R;
import mobile.wms.adapters.AdapterTaskExtra;
import mobile.wms.beans.TaskExtern;
import mobile.wms.beans.TaskUser;

public class NoTask extends AppCompatActivity {

    private TaskUser taskUser;
    private ListView listExtraTasks;
    private Button exitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.no_task);
        taskUser = (TaskUser) getIntent().getSerializableExtra("taskUser");
        setupLayout();

    }


    private void setupLayout() {

        listExtraTasks = (ListView) findViewById(R.id.listExtraTasks);
        exitButton = (Button) findViewById(R.id.exitButton);
        setListenerExitButton();


        if (taskUser.getAlteTaskuri() != null) {
            AdapterTaskExtra adapter = new AdapterTaskExtra(this, taskUser.getAlteTaskuri());
            listExtraTasks.setAdapter(adapter);
        }


    }

    private void setListenerExitButton() {
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

}
