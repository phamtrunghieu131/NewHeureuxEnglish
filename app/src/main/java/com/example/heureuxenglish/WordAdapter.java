package com.example.heureuxenglish;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class WordAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<Word> list;

    public WordAdapter(Context context, int layout, ArrayList<Word> list) {
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

    public class ViewHolder {
        TextView englishText,vietnameseText;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(layout,null);

            holder = new ViewHolder();

            holder.englishText = convertView.findViewById(R.id.element_english);
            holder.vietnameseText = convertView.findViewById(R.id.element_vietnamese);
            convertView.setTag(holder);
        }   else {
            holder = (ViewHolder) convertView.getTag();
        }

        Word word = list.get(position);

        holder.englishText.setText(word.getWordInEnglish());
        holder.vietnameseText.setText(word.getWordInVietnamese());

        return convertView;
    }
}
