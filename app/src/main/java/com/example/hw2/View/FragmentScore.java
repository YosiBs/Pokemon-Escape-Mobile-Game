package com.example.hw2.View;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hw2.Interfaces.CallBack_ConnectLocationToPlayer;
import com.example.hw2.R;
import com.google.android.material.textview.MaterialTextView;

public class FragmentScore extends Fragment {

    private AppCompatButton fragment_BTN_score;
    private CallBack_ConnectLocationToPlayer callBackConnectLocationToPlayer;


    public FragmentScore(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_score, container, false);

        findViews(view);

        fragment_BTN_score.setOnClickListener(v -> itemClicked(32.1129923, 34.8182147));

        return view;
    }

    private void itemClicked(double x, double y) {
        if(callBackConnectLocationToPlayer != null){
            callBackConnectLocationToPlayer.scoreBoxClicked(x, y);
        }

    }

    public void findViews(View view){
        fragment_BTN_score = view.findViewById(R.id.fragment_BTN_score);
    }


    public void setCallBackConnectLocationToPlayer(CallBack_ConnectLocationToPlayer callBackConnectLocationToPlayer){
        this.callBackConnectLocationToPlayer = callBackConnectLocationToPlayer;
    }


}