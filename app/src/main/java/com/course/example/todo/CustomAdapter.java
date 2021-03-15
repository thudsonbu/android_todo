package com.course.example.todo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

class CustomAdapter extends ArrayAdapter {
    ArrayList<Todo> arrayList;
    Context context;
    public CustomAdapter(Context context, ArrayList<Todo> arrayList) {
        super(context, 0, arrayList);
        this.arrayList = arrayList;
        this.context = context;
    }

    //return view displaying data at position
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Todo todo = arrayList.get(position);
        if( convertView == null ){
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.list_row, null);

            TextView text = convertView.findViewById(R.id.text);
            text.setText(todo.getText());
        }
        return convertView;
    }
}
