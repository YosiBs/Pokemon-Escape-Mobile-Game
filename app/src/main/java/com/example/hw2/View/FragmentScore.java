package com.example.hw2.View;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hw2.Adapter.PlayerAdapter;
import com.example.hw2.Interfaces.CallBack_ConnectLocationToPlayer;
import com.example.hw2.Interfaces.CallBack_Player;
import com.example.hw2.Model.Player;
import com.example.hw2.Model.PlayerList;
import com.example.hw2.R;
import com.google.android.material.textview.MaterialTextView;

public class FragmentScore extends Fragment {

    private AppCompatButton fragment_BTN_score;
    private CallBack_ConnectLocationToPlayer callBackConnectLocationToPlayer;

    private RecyclerView main_LST_scores;

    public FragmentScore(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_score, container, false);

        findViews(view);
        initViews();
        //fragment_BTN_score.setOnClickListener(v -> itemClicked(32.1129923, 34.8182147));

        return view;
    }

    private void initViews() {
        PlayerAdapter playerAdapter = new PlayerAdapter(this.getContext(), PlayerList.getInstance().getPlayerList());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        main_LST_scores.setLayoutManager(linearLayoutManager);
        main_LST_scores.setAdapter(playerAdapter);

        playerAdapter.setPlayerCallback(new CallBack_Player() {
            @Override
            public void locationButtonClicked(Player player, int position) {
                itemClicked(player.getLat(), player.getLng());
            }
        });
    }

    private void itemClicked(double lat, double lon) {
        if(callBackConnectLocationToPlayer != null){
            callBackConnectLocationToPlayer.scoreBoxClicked(lat, lon);
        }

    }

    public void findViews(View view){
        main_LST_scores = view.findViewById(R.id.main_LST_scores);
    }


    public void setCallBackConnectLocationToPlayer(CallBack_ConnectLocationToPlayer callBackConnectLocationToPlayer){
        this.callBackConnectLocationToPlayer = callBackConnectLocationToPlayer;
    }


}