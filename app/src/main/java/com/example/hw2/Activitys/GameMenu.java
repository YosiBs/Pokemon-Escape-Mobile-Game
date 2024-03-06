package com.example.hw2.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.example.hw2.R;
import com.example.hw2.Utilities.BackgroundSound;

import org.json.JSONException;
import org.json.JSONObject;

public class GameMenu extends AppCompatActivity {

    private AppCompatButton game_menu_btn_Slow;
    private AppCompatButton game_menu_btn_Fast;
    private AppCompatButton game_menu_btn_play_game;
    private AppCompatButton game_menu_btn_Sensor;
    private AppCompatButton game_menu_btn_Arrows;
    private AppCompatButton game_menu_btn_scoreboard;

    private boolean speed;
    private boolean playStyle;
    private BackgroundSound backgroundSound;

   // private FusedLocationProviderClient fusedLocationClient;
   // private static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;
    private double lat;
    private double lon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_menu);

        findViews();
        initViews();
    }
    public void initViews(){
        game_menu_btn_Slow.setOnClickListener(v -> {speedBtnClicked(false);});
        game_menu_btn_Fast.setOnClickListener(v -> {speedBtnClicked(true);});
        game_menu_btn_Sensor.setOnClickListener(v -> {playStyleBtnClicked(true);});
        game_menu_btn_Arrows.setOnClickListener(v -> {playStyleBtnClicked(false);});
        game_menu_btn_play_game.setOnClickListener(v -> {startGameActivity();});
        game_menu_btn_scoreboard.setOnClickListener(v -> {startScoreBoardActivity();});
    }

    @Override
    protected void onPause() {
        super.onPause();
        backgroundSound.stopSound();
    }

    @Override
    protected void onResume() {
        super.onResume();
        backgroundSound = new BackgroundSound(this);
        backgroundSound.playSound(0);
    }

    private void playStyleBtnClicked(boolean b) {
        playStyle = b;
        if(b){
            game_menu_btn_Sensor.setBackgroundResource(R.drawable.rounded_clicked_button_background);
            game_menu_btn_Arrows.setBackgroundResource(R.drawable.rounded_button_background);
        }else{
            game_menu_btn_Arrows.setBackgroundResource(R.drawable.rounded_clicked_button_background);
            game_menu_btn_Sensor.setBackgroundResource(R.drawable.rounded_button_background);
        }
    }

    private void speedBtnClicked(boolean b){
        speed = b;
        if(b){
            game_menu_btn_Fast.setBackgroundResource(R.drawable.rounded_clicked_button_background);
            game_menu_btn_Slow.setBackgroundResource(R.drawable.rounded_button_background);
        }else{
            game_menu_btn_Slow.setBackgroundResource(R.drawable.rounded_clicked_button_background);
            game_menu_btn_Fast.setBackgroundResource(R.drawable.rounded_button_background);
        }
    }

    private void startScoreBoardActivity() {
        Intent intent = new Intent(this, ScoreBoardActivity.class);
        startActivity(intent);
        finish();
    }

    private void startGameActivity() {
        //Create a JSON Object
        JSONObject gameSetupValues = new JSONObject();
        try{
            //Put Data into the JSON object
            gameSetupValues.put("speed", speed);
            gameSetupValues.put("playStyle", playStyle);
            //gameSetupValues.put("lat", lat);
            //gameSetupValues.put("lon", lon);


        }catch (JSONException e) {
            e.printStackTrace();
        }

        //Create an Intent to start Activity2
        String jsonString = gameSetupValues.toString();
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("jsonString", jsonString);
        startActivity(intent);


    }

    private void findViews(){
        game_menu_btn_Slow = findViewById(R.id.game_menu_btn_Slow);
        game_menu_btn_Fast = findViewById(R.id.game_menu_btn_Fast);
        game_menu_btn_play_game = findViewById(R.id.game_menu_btn_play_game);
        game_menu_btn_Sensor = findViewById(R.id.game_menu_btn_Sensor);
        game_menu_btn_Arrows = findViewById(R.id.game_menu_btn_Arrows);
        game_menu_btn_scoreboard = findViewById(R.id.game_menu_btn_scoreboard);
    }



}