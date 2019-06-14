package com.example.android.smartdispatcher;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class constraintlayout extends AppCompatActivity {

    ArrayList<String> latlist;
    ArrayList<String> addresslist;
    ArrayList<String> lonlist;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.constraintlayout);
        Bundle b = getIntent().getExtras();
        if (b == null) {
            Toast.makeText(constraintlayout.this, "select places first", Toast.LENGTH_LONG).show();
        } else {
            addresslist = b.getStringArrayList("addresslist");
            latlist = b.getStringArrayList("latlist");
            lonlist = b.getStringArrayList("lonlist");
            ArrayList<locations> addresslocations = new ArrayList<locations>();
            for (int i = 0; i < addresslist.size(); i++) {
                locations dummy = new locations();
                dummy.setAddress(addresslist.get(i));
                dummy.setPosition((i + 1));
                addresslocations.add(dummy);
            }
            constraintadapter addressadapter = new constraintadapter(constraintlayout.this, addresslocations);
            ListView listView = findViewById(R.id.checkboxlistview);
            listView.setAdapter(addressadapter);
        }

        Button timebutton = (Button) findViewById(R.id.timeconstraint);
        timebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(constraintlayout.this, timelayout.class);
                intent.putExtra("addresslist", addresslist);
                intent.putExtra("latlist", latlist);
                intent.putExtra("lonlist", lonlist);
                startActivity(intent);
            }
        });
        Button removeposition = (Button) findViewById(R.id.removeposition);
        removeposition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int flag = 0;
                ArrayList<Integer> removeindex = new ArrayList<Integer>();
                EditText removeedittext = (EditText) findViewById(R.id.itemstoremove);
                String removinglist = removeedittext.getText().toString();
                if (removinglist.equals("")) {
                    Toast.makeText(constraintlayout.this, "enter valid index", Toast.LENGTH_LONG).show();
                } else {
                    String numbers[] = removinglist.split(",");
                    for (int i = 0; i < numbers.length; i++) {
                        if (Integer.parseInt(numbers[i]) <= (addresslist.size())) {
                            removeindex.add(Integer.parseInt(numbers[i]));
                        } else {
                            removeindex = new ArrayList<Integer>();
                            flag = 1;
                            Toast.makeText(constraintlayout.this, "enter valid index", Toast.LENGTH_LONG).show();
                        }
                    }
                    if (flag == 0) {
                        ArrayList<String> newlatlist = new ArrayList<String>();
                        ArrayList<String> newaddresslist = new ArrayList<String>();
                        ArrayList<String> newlonlist = new ArrayList<String>();
                        for (int i = 0; i < addresslist.size(); i++) {
                            if (!removeindex.contains((i + 1))) {
                                newaddresslist.add(addresslist.get(i));
                                newlatlist.add(latlist.get(i));
                                newlonlist.add(lonlist.get(i));
                            }
                        }
                        Intent intent = new Intent(constraintlayout.this, currentnextloc.class);
                        intent.putExtra("addresslist", newaddresslist);
                        intent.putExtra("latlist", newlatlist);
                        intent.putExtra("lonlist", newlonlist);
                        intent.putExtra("fromactivity", "constraintlayout");
                        startActivity(intent);
                    }
                }
            }
        });
    }
}
