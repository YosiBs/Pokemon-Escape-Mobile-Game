package com.example.hw2.Activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

import com.example.hw2.Interfaces.CallBack_ConnectLocationToPlayer;
import com.example.hw2.Model.Player;
import com.example.hw2.R;
import com.example.hw2.Utilities.SharedPreferencesManager;
import com.example.hw2.View.FragmentMap;
import com.example.hw2.View.FragmentScore;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;


public class ScoreBoardActivity extends AppCompatActivity {
    private FrameLayout score_board_FRAME_list;
    private FrameLayout score_board_FRAME_map;

    private FragmentScore fragmentScore;
    private FragmentMap fragmentMap;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_board);
        findViews();
        List<Player> playerList = getPlayerListData();
        fragmentScore = new FragmentScore();
        fragmentScore.setCallBackConnectLocationToPlayer(new CallBack_ConnectLocationToPlayer() {
            @Override
            public void scoreBoxClicked(double lat, double lon) {
                fragmentMap.moveToLocation(lat, lon);
            }
        });
        fragmentMap = new FragmentMap();
        getSupportFragmentManager().beginTransaction().add(R.id.score_board_FRAME_list, fragmentScore).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.score_board_FRAME_map, fragmentMap).commit();
    }

    private void findViews() {
        score_board_FRAME_list = findViewById(R.id.score_board_FRAME_list);
        score_board_FRAME_map = findViewById(R.id.score_board_FRAME_map);
    }

    private List<Player> getPlayerListData() {
        String playerListJson = getIntent().getStringExtra("playerListJson");
        Gson gson = new Gson();
        List<Player> bestPlayers = gson.fromJson(playerListJson, new TypeToken<List<Player>>(){}.getType());
        return bestPlayers;
    }


}