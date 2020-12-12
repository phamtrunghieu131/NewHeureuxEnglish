package com.example.heureuxenglish;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class UserAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<User> list;

    public UserAdapter(Context context, int layout, ArrayList<User> list) {
        this.context = context;
        this.layout = layout;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public class ViewHolder{
        TextView nameText,levelText,pointText;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(layout,null);

            holder = new ViewHolder();

            holder.nameText = convertView.findViewById(R.id.userElement_name);
            holder.levelText = convertView.findViewById(R.id.userElement_level);
            holder.pointText = convertView.findViewById(R.id.userElement_point);
            convertView.setTag(holder);
        }   else {
            holder = (ViewHolder) convertView.getTag();
        }

        User user = list.get(position);

        holder.nameText.setText(user.getName());
        holder.levelText.setText(user.getHardDay()+"");
        holder.pointText.setText( user.getPoint()+"");
        return convertView;
    }
}
