package com.example.android.smartdispatcher;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class Addressadapter extends ArrayAdapter<locations> {

    public Addressadapter(Context context, ArrayList<locations> name) {
        super(context, 0, name);

    }

    public View getView(int position, View convertView, ViewGroup parent) {
        locations currentpositionwordobj = getItem(position);
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.addressshowlist, parent, false);
        }
        TextView miwokTextView = listItemView.findViewById(R.id.addressview);
        miwokTextView.setText(currentpositionwordobj.getAddress());
        View textContainer = listItemView.findViewById(R.id.linear_id);
        return listItemView;

    }
}
