package com.example.hw2.Model;

import android.content.Intent;
import android.util.Log;

import com.example.hw2.Activitys.ScoreBoardActivity;
import com.example.hw2.Utilities.SharedPreferencesManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PlayerList {
    private ArrayList<Player> playerList = new ArrayList<>();
    private static  PlayerList instance = null;
    public static final String PLAYER_LIST ="PLAYER_LIST";
    public PlayerList() {
        this.playerList = loadFromSharedPreferences();
        if(playerList == null) {
            this.playerList = new ArrayList<Player>();
        }
    }
    public ArrayList<Player> getPlayerList() {
        return playerList;
    }
    public static PlayerList getInstance() {
        if (instance == null)
            instance = new PlayerList();
        return instance;
    }
    public PlayerList setPlayerList(ArrayList<Player> playerList) {
        this.playerList = playerList;
        return this;
    }
    public void addPlayer(Player player) {
        this.playerList.add(player);
        Log.d("bbb", "4");
        saveToSharedPreferences();
        Log.d("bbb", "5");
    }
    private void saveToSharedPreferences() {
        Gson gson = new Gson();
        String playerListJson = gson.toJson(this.playerList);
        SharedPreferencesManager.getInstance().putString(PLAYER_LIST, playerListJson);
    }
    public ArrayList<Player> loadFromSharedPreferences(){
        String playersJson = SharedPreferencesManager.getInstance().getString(PLAYER_LIST, "");
        Log.d("rrr", playersJson);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Player>>(){}.getType();
        return gson.fromJson(playersJson, type);
    }
    @Override
    public String toString() {
        return "PlayerList{" +
                "playerArrayList=" + playerList +
                '}';
    }
    public void sortByScore() {
        // Sort the players list based on their scores
        Collections.sort(playerList, new Comparator<Player>() {
            @Override
            public int compare(Player p1, Player p2) {
                // Sort in descending order (highest score first)
                return Integer.compare(p2.getScore(), p1.getScore());
            }
        });
    }

    public static ArrayList<Player> getCopyOfBestPlayers(){
        ArrayList<Player> allPlayers = PlayerList.getInstance().getPlayerList();
        ArrayList<Player> bestPlayerList = new ArrayList<>(allPlayers.subList(0,Math.min(10, allPlayers.size())));
        return bestPlayerList;
    }
}