package com.example.kashish.ams;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kashish on 7/17/2016.
 */
public class MyArrayAdapter extends ArrayAdapter {

    Context ctx;
    ArrayList<ListBean> list;

    public MyArrayAdapter(Context context, int resource, ArrayList<ListBean> objects) {
        super(context, resource, objects);

        ctx=context;
        list=objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View rowView = null;

        LayoutInflater inflater=(LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rowView=inflater.inflate(R.layout.timetable_item, parent, false);

        TextView txtsubject=(TextView)rowView.findViewById(R.id.txtsubject);
        TextView txttime=(TextView)rowView.findViewById(R.id.txttime);

        ListBean lb=list.get(position);

        txtsubject.setText(lb.getSubject());
        txttime.setText(lb.getTime());

        return rowView;
    }
}
