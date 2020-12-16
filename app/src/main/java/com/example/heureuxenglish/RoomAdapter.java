package com.example.heureuxenglish;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class RoomAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<Room> rooms;

    public RoomAdapter(Context context, int layout, ArrayList<Room> rooms) {
        this.context = context;
        this.layout = layout;
        this.rooms = rooms;
    }

    @Override
    public int getCount() {
        return rooms.size();
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
        TextView roomNameText;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(layout,null);

            holder = new ViewHolder();

            holder.roomNameText = convertView.findViewById(R.id.roomName_element);

            convertView.setTag(holder);
        }   else {
            holder = (ViewHolder) convertView.getTag();
        }

        Room room = rooms.get(position);

        holder.roomNameText.setText(room.getName());

        return convertView;
    }
}
