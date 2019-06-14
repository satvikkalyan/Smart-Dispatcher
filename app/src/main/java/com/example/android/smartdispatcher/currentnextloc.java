package com.example.android.smartdispatcher;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.location.Location.distanceBetween;

public class currentnextloc extends AppCompatActivity {

    ArrayList<String> latlist = new ArrayList<String>();
    ArrayList<String> addresslist = new ArrayList<String>();
    ArrayList<String> lonlist = new ArrayList<String>();

    String routepath = "";
    String activityfinder;
    String a, c, d;
    int flag = 0;

    ArrayList<String> removelatlist = new ArrayList<String>();
    ArrayList<String> removeaddresslist = new ArrayList<String>();
    ArrayList<String> removelonlist = new ArrayList<String>();

    public String findlocation(ArrayList<String> removeaddresslist) {
        if (removeaddresslist.size() == 0) {
            return "no next location";
        }
        return removeaddresslist.get(0);
    }

    public String findcurrentlocation(ArrayList<String> removeaddresslist) {
        if (removeaddresslist.size() == 0) {
            return "no new location";
        }
        removelatlist.remove(0);
        removelonlist.remove(0);
        return removeaddresslist.remove(0);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.currentnextloc);
        Bundle b = getIntent().getExtras();
        addresslist = b.getStringArrayList("addresslist");
        latlist = b.getStringArrayList("latlist");
        lonlist = b.getStringArrayList("lonlist");
        activityfinder = b.getString("fromactivity");
        if (activityfinder != null) {
            TextView textView = (TextView) findViewById(R.id.currentposition);
            String currentpositiontext = textView.getText().toString();
            if (addresslist.size() != 0 && currentpositiontext.equals(addresslist.get(addresslist.size() - 1))) {
                flag = 1;
                a = addresslist.remove(addresslist.size() - 1);
                d = latlist.remove(latlist.size() - 1);
                c = lonlist.remove(lonlist.size() - 1);
            }
        }
        double[][] distanceMatrix = new double[addresslist.size()][addresslist.size()];
        for (int i = 0; i < addresslist.size(); i++) {
            for (int j = 0; j < addresslist.size(); j++) {
                float[] distance = new float[1];
                distanceBetween(Double.parseDouble(latlist.get(i)),
                        Double.parseDouble(lonlist.get(i)),
                        Double.parseDouble(latlist.get(j)),
                        Double.parseDouble(lonlist.get(j)),
                        distance);
                distanceMatrix[i][j] = distance[0] / 1000;
            }
        }
        List<Integer> route = new ArrayList<>();
        if (activityfinder != null && addresslist.size() != 0) {
            removetsp removesolver = new removetsp(distanceMatrix);
            route = removesolver.getTour();
        } else if (addresslist.size() != 0) {
            tsp solver = new tsp(distanceMatrix);
            route = solver.getTour();
        }
        for (int i = 0; i < route.size(); i++) {
            routepath = routepath + route.get(i);
        }
        for (int i = 0; i < routepath.length(); i++) {
            removeaddresslist.add(addresslist.get(routepath.charAt(i) - 48));
            removelonlist.add(lonlist.get(routepath.charAt(i) - 48));
            removelatlist.add(latlist.get(routepath.charAt(i) - 48));
        }
        latlist = new ArrayList<String>();
        addresslist = new ArrayList<String>();
        lonlist = new ArrayList<String>();
        for (int i = 0; i < removelatlist.size(); i++) {
            latlist.add(removelatlist.get(i));
            addresslist.add(removeaddresslist.get(i));
            lonlist.add(removelonlist.get(i));
        }
        if (activityfinder != null) {
            if (flag == 1) {
                removeaddresslist.add(a);
                addresslist.add(a);
                removelatlist.add(d);
                latlist.add(d);
                removelonlist.add(c);
                lonlist.add(c);
            }
        }
        TextView currentposition = findViewById(R.id.currentposition);
        currentposition.setText(findcurrentlocation(removeaddresslist));
        TextView nextposition = findViewById(R.id.nextposition);
        nextposition.setText(findlocation(removeaddresslist));

        Button constraintbutton = findViewById(R.id.constraintbutton);
        constraintbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (removeaddresslist.size() != 0) {

                    Intent intent = new Intent(currentnextloc.this, constraintlayout.class);
                    intent.putExtra("addresslist", removeaddresslist);
                    intent.putExtra("latlist", removelatlist);
                    intent.putExtra("lonlist", removelonlist);
                    startActivity(intent);
                } else {
                    Toast.makeText(currentnextloc.this, "no locations to apply constraint", Toast.LENGTH_LONG).show();
                }

            }
        });

        Button nextbutton = findViewById(R.id.next);
        nextbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //show next location
                if (removeaddresslist.size() != 0) {
                    TextView currentposition = findViewById(R.id.currentposition);
                    TextView nextposition = findViewById(R.id.nextposition);
                    currentposition.setText(findcurrentlocation(removeaddresslist));
                    nextposition.setText(findlocation(removeaddresslist));
                }
            }
        });

        Button navigate = findViewById(R.id.navigate);
        navigate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int var = latlist.size() - removelatlist.size() - 1;
                Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + latlist.get(var) + "," + lonlist.get(var) + "(" + addresslist.get(var) + ")");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                } else {
                    Toast.makeText(currentnextloc.this, "Install Google Maps", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
