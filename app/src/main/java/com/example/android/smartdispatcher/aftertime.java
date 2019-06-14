package com.example.android.smartdispatcher;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.location.Location.distanceBetween;

public class aftertime extends AppCompatActivity {
    ArrayList<String> latlist = new ArrayList<String>();
    ArrayList<String> addresslist = new ArrayList<String>();
    ArrayList<String> lonlist = new ArrayList<String>();
    ArrayList<String> dlatlist = new ArrayList<String>();
    ArrayList<String> daddresslist = new ArrayList<String>();
    ArrayList<String> dlonlist = new ArrayList<String>();
    ArrayList<Integer> removeindexlist = new ArrayList<Integer>();
    ArrayList<Integer> removetimelist = new ArrayList<Integer>();
    String routepath = "";
    private float time = 0;
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
        removeindexlist = b.getIntegerArrayList("indexes");
        removetimelist = b.getIntegerArrayList("timevalues");
        for (int i = 0; i < addresslist.size(); i++) {
            if (!removeindexlist.contains(i)) {
                daddresslist.add(addresslist.get(i));
                dlatlist.add(latlist.get(i));
                dlonlist.add(lonlist.get(i));
            }
        }
        double[][] distanceMatrix = new double[daddresslist.size()][daddresslist.size()];
        for (int i = 0; i < daddresslist.size(); i++) {
            for (int j = 0; j < daddresslist.size(); j++) {
                float[] distance = new float[1];
                distanceBetween(Double.parseDouble(dlatlist.get(i)),
                        Double.parseDouble(dlonlist.get(i)),
                        Double.parseDouble(dlatlist.get(j)),
                        Double.parseDouble(dlonlist.get(j)),
                        distance);
                distanceMatrix[i][j] = distance[0] / 1000;
            }
        }
        List<Integer> route = new ArrayList<>();
        if (daddresslist.size() != 0) {
            removetsp removesolver = new removetsp(distanceMatrix);
            route = removesolver.getTour();
        }
        for (int i = 0; i < route.size(); i++) {
            routepath = routepath + route.get(i);
        }
        for (int i = 0; i < routepath.length(); i++) {
            removeaddresslist.add(daddresslist.get(routepath.charAt(i) - 48));
            removelonlist.add(dlonlist.get(routepath.charAt(i) - 48));
            removelatlist.add(dlatlist.get(routepath.charAt(i) - 48));
        }
        dlatlist = new ArrayList<String>();
        daddresslist = new ArrayList<String>();
        dlonlist = new ArrayList<String>();
        for (int i = 0; i < removelatlist.size(); i++) {
            dlatlist.add(removelatlist.get(i));
            daddresslist.add(removeaddresslist.get(i));
            dlonlist.add(removelonlist.get(i));
        }

        float[] distance = new float[1];
        distanceBetween(Double.parseDouble(dlatlist.get(0)),
                Double.parseDouble(dlonlist.get(0)),
                Double.parseDouble(dlatlist.get(1)),
                Double.parseDouble(dlonlist.get(1)),
                distance);
        time = time + (distance[0] / 1000);
        TextView currentposition = findViewById(R.id.currentposition);
        currentposition.setText(findcurrentlocation(removeaddresslist));
        TextView nextposition = findViewById(R.id.nextposition);
        nextposition.setText(findlocation(removeaddresslist));

        Button constraintbutton = findViewById(R.id.constraintbutton);
        constraintbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (removeaddresslist.size() != 0) {

                    Intent intent = new Intent(aftertime.this, constraintlayout.class);
                    intent.putExtra("addresslist", removeaddresslist);
                    intent.putExtra("latlist", removelatlist);
                    intent.putExtra("lonlist", removelonlist);
                    startActivity(intent);
                } else {
                    Toast.makeText(aftertime.this, "No Locations to Apply Constraint", Toast.LENGTH_LONG).show();
                }

            }
        });

        Button nextbutton = findViewById(R.id.next);
        nextbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //show next location
                if (removeaddresslist.size() != 0) {
                    if (removeaddresslist.size() > 1) {
                        float[] distance = new float[1];
                        distanceBetween(Double.parseDouble(removelatlist.get(0)),
                                Double.parseDouble(removelonlist.get(0)),
                                Double.parseDouble(removelatlist.get(1)),
                                Double.parseDouble(removelonlist.get(1)),
                                distance);
                        time = time + (distance[0] / 1000);
                    }
                    for (int i = 0; i < removetimelist.size(); i++) {
                        if (time > removetimelist.get(i)) {
                            daddresslist.add(addresslist.get(removeindexlist.get(i)));
                            dlatlist.add(latlist.get(removeindexlist.get(i)));
                            dlonlist.add(lonlist.get(removeindexlist.get(i)));
                            removeaddresslist.add(addresslist.get(removeindexlist.get(i)));
                            removelatlist.add(latlist.get(removeindexlist.get(i)));
                            removelonlist.add(lonlist.get(removeindexlist.get(i)));
                            removetimelist.remove(i);
                            removeindexlist.remove(i);
                        }
                    }
                    TextView currentposition = findViewById(R.id.currentposition);
                    TextView nextposition = findViewById(R.id.nextposition);
                    currentposition.setText(findcurrentlocation(removeaddresslist));
                    String value = findlocation(removeaddresslist);
                    nextposition.setText(value);
                    Log.d("value", value);

                    if (value.equals("no next location")) {
                        Log.d("loopcheck", "in if loop");
                        ArrayList<locations> addresslocations = new ArrayList<locations>();
                        for (int i = 0; i < removetimelist.size(); i++) {
                            locations dummy = new locations();
                            dummy.setAddress(addresslist.get(removeindexlist.get(i)));
                            dummy.setPosition((i + 1));
                            addresslocations.add(dummy);
                        }
                        unvisitedadapter addressadapter = new unvisitedadapter(aftertime.this, addresslocations);
                        ListView listView = findViewById(R.id.unvisitedlist);
                        listView.setAdapter(addressadapter);
                    }
                }
            }
        });

        Button navigate = findViewById(R.id.navigate);
        navigate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int var = dlatlist.size() - removelatlist.size() - 1;
                Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + dlatlist.get(var) + "," + dlonlist.get(var) + "(" + daddresslist.get(var) + ")");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                } else {
                    Toast.makeText(aftertime.this, "Install Google Maps", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}

