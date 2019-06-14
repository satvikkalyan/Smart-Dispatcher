package com.example.android.smartdispatcher;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    ArrayList<String> latlist;
    ArrayList<String> addresslist;
    ArrayList<String> lonlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button placepickerbutton = findViewById(R.id.pickplace);
        placepickerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });

        final Button findpathButton = findViewById(R.id.findpath);
        findpathButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle b = getIntent().getExtras();
                if (b == null) {
                    Toast.makeText(MainActivity.this, "select places first", Toast.LENGTH_LONG).show();
                } else {
                    addresslist = b.getStringArrayList("addresslist");
                    latlist = b.getStringArrayList("latlist");
                    lonlist = b.getStringArrayList("lonlist");
                    if (addresslist.size() <= 2) {
                        Toast.makeText(MainActivity.this, "select more than 2 places", Toast.LENGTH_LONG).show();
                    } else {
                        Intent intent = new Intent(MainActivity.this, currentnextloc.class);
                        intent.putExtra("addresslist", addresslist);
                        intent.putExtra("latlist", latlist);
                        intent.putExtra("lonlist", lonlist);
                        startActivity(intent);
                    }
                }
            }
        });
        final Button showplacesButton = findViewById(R.id.showplaces);
        showplacesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle b = getIntent().getExtras();
                if (b == null) {
                    Toast.makeText(MainActivity.this, "select places first", Toast.LENGTH_LONG).show();
                } else {
                    addresslist = b.getStringArrayList("addresslist");
                    latlist = b.getStringArrayList("latlist");
                    lonlist = b.getStringArrayList("lonlist");
                    ArrayList<locations> addresslocations = new ArrayList<locations>();
                    for (int i = 0; i < addresslist.size(); i++) {
                        addresslocations.add(new locations(addresslist.get(i)));
                    }
                    Addressadapter addressadapter = new Addressadapter(MainActivity.this, addresslocations);
                    ListView listView = findViewById(R.id.listview);
                    listView.setAdapter(addressadapter);
                }
            }
        });

    }
}

