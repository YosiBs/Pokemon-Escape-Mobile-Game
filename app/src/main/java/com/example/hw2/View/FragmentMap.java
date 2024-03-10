package com.example.hw2.View;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.hw2.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class FragmentMap extends Fragment {

    // private MaterialTextView fragment_MTV_map;


    private GoogleMap gMap;
    public FragmentMap(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        SupportMapFragment supportMapFragment = ((SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.maps));
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                gMap = googleMap;
                LatLng location = new LatLng(40.7128, 74.0060);
                googleMap.addMarker(new MarkerOptions().position(location).title("New York"));
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location,15));
            }
        });
        return view;
    }


    public void moveToLocation(double lat, double lon) { //zoom
        reDirectCamera(lat, lon);
    }

    private void reDirectCamera(double lat, double lon) {
        if (gMap != null) {
            LatLng location = new LatLng(lat, lon);
            gMap.clear(); // Clear existing markers
            gMap.addMarker(new MarkerOptions().position(location).title("New Location"));
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(lat, lon))
                    .zoom(15)
                    .build();
            gMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        }
    }


}