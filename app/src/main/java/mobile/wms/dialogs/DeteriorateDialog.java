package mobile.wms.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import mobile.wms.R;
import mobile.wms.beans.ArticolPregatire;

public class DeteriorateDialog extends Dialog {

    private Context context;
    private ArticolPregatire articol;
    private Button buttonScan;


    public DeteriorateDialog(Context context, ArticolPregatire articol) {
        super(context);
        this.context = context;
        this.articol = articol;
        setContentView(R.layout.deteriorate_dialog);
        setupLayout();

    }

    private void setupLayout() {
        buttonScan = (Button) findViewById(R.id.buttonScan);
        setListenerScan();
    }


    private void setListenerScan(){
        buttonScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }


}
