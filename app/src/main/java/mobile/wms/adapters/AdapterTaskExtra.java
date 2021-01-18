package mobile.wms.adapters;

import java.util.List;


import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import mobile.wms.R;
import mobile.wms.beans.TaskExtern;

public class AdapterTaskExtra extends BaseAdapter {

    private List<TaskExtern> listTask;
    private Context context;
    private int[] colors = new int[] { 0x3098BED9, 0x30E8E8E8 };

    public AdapterTaskExtra(Context context, List<TaskExtern> listTask) {
        this.context = context;
        this.listTask = listTask;
    }

    static class ViewHolder {
        TextView textNume, textCantitate;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.task_extern, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.textNume = (TextView) convertView.findViewById(R.id.textNume);
            viewHolder.textCantitate = (TextView) convertView.findViewById(R.id.textCantitate);

            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        TaskExtern task = getItem(position);
        viewHolder.textNume.setText(task.getNume());
        viewHolder.textCantitate.setText(String.valueOf(task.getCantitate()));

        convertView.setBackgroundColor(colors[position % colors.length]);

        return convertView;
    }

    public int getCount() {
        return listTask.size();
    }

    public TaskExtern getItem(int position) {
        return listTask.get(position);
    }

    public long getItemId(int arg0) {
        return 0;
    }

}
