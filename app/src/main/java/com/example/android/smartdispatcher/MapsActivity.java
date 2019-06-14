package com.example.android.smartdispatcher;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    ArrayList<String> addresslist = new ArrayList<String>();
    ArrayList<String> latlist=new ArrayList<String>();
    ArrayList<String> lonlist=new ArrayList<String>();
    private GoogleMap mMap;
    private Button back;
    private Button done;
    private LatLng latlong;
    private String s = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        back = findViewById(R.id.backButton);
        done = findViewById(R.id.doneButton);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapsActivity.this, MainActivity.class);
                intent.putExtra("addresslist",addresslist);
                intent.putExtra("latlist",latlist);
                intent.putExtra("lonlist",lonlist);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng bhel = new LatLng(17.5116485, 78.2932053);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(bhel, 14));
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                latlong = latLng;
                mMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title(getAddress(latLng.latitude, latLng.longitude)));
                addresslist.add(getAddress(latLng.latitude, latLng.longitude));
                latlist.add(String.valueOf(latLng.latitude));
                lonlist.add(String.valueOf(latLng.longitude));
            }
        });
    }

    public String getAddress(double lat, double lng) {
        Geocoder geocoder = new Geocoder(MapsActivity.this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            Address obj = addresses.get(0);
            return obj.getAddressLine(0);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
