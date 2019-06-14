package com.example.android.smartdispatcher;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class timelayout extends AppCompatActivity {

    ArrayList<String> latlist;
    ArrayList<String> addresslist;
    ArrayList<String> lonlist;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timelayout);
        Bundle b = getIntent().getExtras();
        if (b == null) {
            Toast.makeText(timelayout.this, "select places first", Toast.LENGTH_LONG).show();
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
            constraintadapter addressadapter = new constraintadapter(timelayout.this, addresslocations);
            ListView listView = findViewById(R.id.timelistview);
            listView.setAdapter(addressadapter);

            Button applytime = (Button) findViewById(R.id.timeconstraintbutton);
            applytime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditText index = (EditText) findViewById(R.id.indexno);
                    EditText ltime = (EditText) findViewById(R.id.time);
                    String removeindex = index.getText().toString();
                    String removetime = index.getText().toString();
                    if (removeindex.equals("") || removetime.equals("")) {
                        Toast.makeText(timelayout.this, "enter valid index", Toast.LENGTH_LONG).show();
                    } else {
                        String timearray[] = removetime.split(",");
                        String indexarray[] = removeindex.split(",");
                        int flag = 0;
                        ArrayList<Integer> removeindexlist = new ArrayList<Integer>();
                        ArrayList<Integer> removetimelist = new ArrayList<Integer>();
                        for (int i = 0; i < timearray.length; i++) {
                            if (Integer.parseInt(indexarray[i]) <= (addresslist.size() )) {
                                removeindexlist.add(Integer.parseInt(indexarray[i])-1);
                                removetimelist.add(Integer.parseInt(indexarray[i]));
                            } else {
                                removeindexlist = new ArrayList<Integer>();
                                removetimelist = new ArrayList<Integer>();
                                flag = 1;
                                Toast.makeText(timelayout.this, "Enter index less than number of locations", Toast.LENGTH_LONG).show();
                                break;

                            }
                        }
                        if (flag == 0) {
                            Intent intent = new Intent(timelayout.this, aftertime.class);
                            intent.putExtra("addresslist", addresslist);
                            intent.putExtra("latlist", latlist);
                            intent.putExtra("lonlist", lonlist);
                            intent.putExtra("indexes",removeindexlist);
                            intent.putExtra("timevalues",removetimelist);
                            startActivity(intent);
                        } else {
                            Toast.makeText(timelayout.this,"enter correct index", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            });
        }
    }
}
