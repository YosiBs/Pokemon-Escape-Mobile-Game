package com.example.hw2.View;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hw2.R;
import com.google.android.material.textview.MaterialTextView;


public class FragmentMap extends Fragment {

    private MaterialTextView fragment_MTV_map;

    public FragmentMap(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        findViews(view);


        return view;

    }

    public void findViews(View view){
        fragment_MTV_map = view.findViewById(R.id.fragment_MTV_map);
    }

    public void changeText(double x, double y){
        fragment_MTV_map.setText(x + ", " + y);
    }

}