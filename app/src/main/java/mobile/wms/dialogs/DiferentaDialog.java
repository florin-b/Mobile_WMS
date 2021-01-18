package mobile.wms.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import mobile.wms.R;
import mobile.wms.beans.ArticolPregatire;

public class DiferentaDialog extends Dialog {

    private Context context;
    private ArticolPregatire articol;
    private TextView textUm;
    private Button buttonLipsa, buttonDet;

    public DiferentaDialog(Context context, ArticolPregatire articol){
        super(context);
        this.context = context;
        this.articol = articol;
        setContentView(R.layout.diferenta_dialog);
        setupLayout();

    }

    private void setupLayout(){
        textUm = findViewById(R.id.textUm);
        textUm.setText(articol.getCantitate().getUm());
        buttonLipsa = (Button) findViewById(R.id.buttonLipsa);
        setListenerLipsa();
        buttonDet = (Button) findViewById(R.id.buttonDet);
        setListenerDet();
    }


    private void setListenerLipsa(){

    }

    private void setListenerDet(){

        buttonDet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int width = (int) (context.getResources().getDisplayMetrics().widthPixels * 0.95);
                int height = (int) (context.getResources().getDisplayMetrics().heightPixels * 0.32);

                DeteriorateDialog detDialog = new DeteriorateDialog(context, articol);
                detDialog.getWindow().setLayout(width, height);
                detDialog.show();
                dismiss();
            }
        });



    }



}
