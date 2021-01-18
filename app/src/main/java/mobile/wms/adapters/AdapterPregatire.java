package mobile.wms.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import mobile.wms.R;
import mobile.wms.beans.ArticolPregatire;
import mobile.wms.dialogs.DiferentaDialog;


public class AdapterPregatire extends BaseAdapter {

    private List<ArticolPregatire> listArticole;
    private Context context;
    private int[] colors = new int[]{0x3098BED9, 0x30E8E8E8};

    public AdapterPregatire(Context context, List<ArticolPregatire> listArticole) {
        this.context = context;
        this.listArticole = listArticole;
    }

    static class ViewHolder {
        TextView textSursa, textMaterialCod, textMaterialNume, textCantUmb, textDataProd;
        ImageView artPreluat;
        Button btnLipsa;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.articol_pregatire, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.textSursa = (TextView) convertView.findViewById(R.id.textSursa);
            viewHolder.textMaterialCod = (TextView) convertView.findViewById(R.id.textMaterialCod);
            viewHolder.textMaterialNume = (TextView) convertView.findViewById(R.id.textMaterialNume);
            viewHolder.textCantUmb = (TextView) convertView.findViewById(R.id.textCantUmb);
            viewHolder.textDataProd = (TextView) convertView.findViewById(R.id.textDataProd);
            viewHolder.artPreluat = (ImageView) convertView.findViewById(R.id.artPreluat);
            viewHolder.btnLipsa = (Button) convertView.findViewById(R.id.btnLipsa);

            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        ArticolPregatire articol = getItem(position);
        viewHolder.textSursa.setText(articol.getSursa());
        viewHolder.textMaterialCod.setText(articol.getArticol().getCod());
        viewHolder.textMaterialNume.setText(articol.getArticol().getDenumire());
        viewHolder.textCantUmb.setText(articol.getCantitate().getCantitate() + " " + articol.getCantitate().getUm());
        viewHolder.textDataProd.setText(articol.getDataProd() + " - " + articol.getDataExp());

        setListenerBtnLipsa(viewHolder.btnLipsa, articol);

        if (articol.isPreluat())
            viewHolder.artPreluat.setVisibility(View.VISIBLE);
        else
            viewHolder.artPreluat.setVisibility(View.INVISIBLE);

        convertView.setBackgroundColor(colors[position % colors.length]);

        return convertView;
    }

    private void setListenerBtnLipsa(Button btnLipsa, ArticolPregatire articol) {
        btnLipsa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDiferentaDialog(articol);
            }
        });
    }


    private void showDiferentaDialog(ArticolPregatire articol) {


        int width = (int) (context.getResources().getDisplayMetrics().widthPixels * 0.95);
        int height = (int) (context.getResources().getDisplayMetrics().heightPixels * 0.32);

        DiferentaDialog difDialog = new DiferentaDialog(context, articol);
        difDialog.getWindow().setLayout(width, height);
        difDialog.show();

    }


    public int getCount() {
        return listArticole.size();
    }

    public ArticolPregatire getItem(int position) {
        return listArticole.get(position);
    }

    public long getItemId(int arg0) {
        return 0;
    }

}
