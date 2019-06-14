package com.example.android.smartdispatcher;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class unvisitedadapter extends ArrayAdapter<locations> {

    int i = 0;

    public unvisitedadapter(Context context, ArrayList<locations> name) {
        super(context, 0, name);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        locations currentpositionwordobj = getItem(position);
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.checkboxshowlist, parent, false);
            i++;
        }
        TextView miwokTextView = listItemView.findViewById(R.id.showtextview);
        miwokTextView.setText(currentpositionwordobj.getPosition() + " : " + currentpositionwordobj.getAddress());
        View textContainer = listItemView.findViewById(R.id.checkboxlinear_id);
        return listItemView;

    }

}
