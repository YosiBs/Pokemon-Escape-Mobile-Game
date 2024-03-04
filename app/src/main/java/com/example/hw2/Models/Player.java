package com.example.hw2.Models;

public class Player {

    private String name = "";
    private int score;
    private double lat;
    private double lng;

    public Player(){

    }

    public String getName() {
        return name;
    }

    public Player setName(String name) {
        this.name = name;
        return this;
    }

    public int getScore() {
        return score;
    }

    public Player setScore(int score) {
        this.score = score;
        return this;
    }

    public double getLat() {
        return lat;
    }

    public Player setLat(double lat) {
        this.lat = lat;
        return this;
    }

    public double getLng() {
        return lng;
    }

    public Player setLng(double lng) {
        this.lng = lng;
        return this;
    }
}
