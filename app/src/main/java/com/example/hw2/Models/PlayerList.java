package com.example.hw2.Models;

import java.util.ArrayList;

public class PlayerList {
    private ArrayList<Player> playerArrayList = new ArrayList<>();

    public PlayerList() {

    }

    public ArrayList<Player> getPlayerArrayList() {
        return playerArrayList;
    }

    public PlayerList setPlayerArrayList(ArrayList<Player> playerArrayList) {
        this.playerArrayList = playerArrayList;
        return this;
    }

    public PlayerList addPlayer(Player player) {
        this.playerArrayList.add(player);
        return this;
    }

    @Override
    public String toString() {
        return "PlayerList{" +
                "playerArrayList=" + playerArrayList +
                '}';
    }
}
