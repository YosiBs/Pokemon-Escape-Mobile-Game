package com.example.hw2.Logic;

import com.example.hw2.Models.MainCharacter;
import com.example.hw2.Models.Obstacle;

import java.util.ArrayList;
import java.util.Random;

public class GameManager {

    private int numberOfLifes;
    private int bumpCounter = 0;
    private final int LIFES_PER_GAME = 3;
    private final int NUMBER_OF_ROWS = 7;
    private final int NUMBER_OF_COLS = 5;
    private int score = 0;
    private int coinValue = 10;
    private MainCharacter mainCharacter;
    private ArrayList<Obstacle> obstacleList;
    private final int MAX_OBSTACLES = 3;

    public int getCoinValue() {
        return coinValue;
    }

    public void setCoinValue(int coinValue) {
        this.coinValue = coinValue;
    }

    public GameManager(){
        this.numberOfLifes = LIFES_PER_GAME;
        mainCharacter = new MainCharacter();
        obstacleList = new ArrayList<>();
    }

    public int getNumberOfLifes() {
        return numberOfLifes;
    }

    public int getBumpCounter() {
        return bumpCounter;
    }

    public int getNumberOfRows() {
        return NUMBER_OF_ROWS;
    }

    public int getNumberOfCols() {
        return NUMBER_OF_COLS;
    }

    public int getScore() {
        return score;
    }

    public MainCharacter getMainCharacter() {
        return mainCharacter;
    }

    public ArrayList<Obstacle> getObstacleList() {
        return obstacleList;
    }

    public int getMaxObstaclesOnScreen() {
        return MAX_OBSTACLES;
    }

    public void setNumberOfLifes(int numberOfLifes) {
        this.numberOfLifes = numberOfLifes;

    }

    public void setBumpCounter(int bumpCounter) {
        this.bumpCounter = bumpCounter;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setObstacleList(ArrayList<Obstacle> obstacleList) {
        this.obstacleList = obstacleList;
    }

    public int getLifesPerGame(){
        return LIFES_PER_GAME;
    }

    public int isCollisionAccured(){
        for(Obstacle obs : obstacleList){
            if(mainCharacter.getPosX() == obs.getPosX() && mainCharacter.getPosY() == obs.getPosY()){
                if(obs.isDoingDamage()){
                    numberOfLifes--;
                    bumpCounter++;
                }
                return obstacleList.indexOf(obs);
            }
        }
        return -1;
    }

    public int generateRandomNumber(int max) {
        Random random = new Random();
        int randomNumber = random.nextInt(max);
        return randomNumber;
    }
    public void addObstacleToArrayList(Obstacle obs){
        if(this.obstacleList.size() < MAX_OBSTACLES){
            this.obstacleList.add(obs);
        }
    }


}

