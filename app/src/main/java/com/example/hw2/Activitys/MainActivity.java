package com.example.hw2.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.hw2.Interfaces.CallBack_CharacterMovement;
import com.example.hw2.Interfaces.CallBack_GameSpeed;
import com.example.hw2.Logic.GameManager;
import com.example.hw2.Models.Obstacle;
import com.example.hw2.R;
import com.example.hw2.Utilities.BackgroundSound;
import com.example.hw2.Utilities.SensorMovement;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private final int SLOW_VALUE = 750;
    private final int FAST_VALUE = 350;
    private final int FASTER_VALUE = 250;
    private final int VIBRATIONT_TIME = 300;

    private boolean speed = false;
    private boolean useStepSensor = false;
    private MaterialTextView main_MTV_chosen_speed;
    private GameManager gm;
    private FloatingActionButton main_btn_left;
    private FloatingActionButton main_btn_right;
    private GridLayout main_grid;
    private RelativeLayout[][] gameMat;
    private ShapeableImageView main_IMG_pikachu;
    private Handler handler = new Handler(Looper.getMainLooper());
    private int loopInterval = 750;
    private ShapeableImageView[] main_IMG_hearts;
    private MaterialTextView main_text_score;
    private CardView main_cardview_gameover;
    private MaterialTextView main_card_lbl_score;
    private AppCompatEditText main_ET_player_name;
    private boolean isGameRunning = true;
    private boolean isGameOver = false;
    // Sensors:
    private SensorMovement stepSensorsManager;
    private SensorMovement speedSensorsManager;
    private final int ODOMETER_VALUE = 1;

    // Sounds:
    private BackgroundSound backgroundSound;
    private MediaPlayer[] mpCoinSound;
    private MediaPlayer mpGameOverSound;
    private MediaPlayer mpStepRightSound;
    private MediaPlayer mpStepLeftSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        showEndGameCard(false);
        adjustGameMode();
        this.gm = new GameManager();
        gameMat = getAllRelativeLayouts();
        main_btn_left.setOnClickListener(v -> moveLeft());
        main_btn_right.setOnClickListener(v -> moveRight());
        initSounds();
        startGameLoop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        isGameRunning = false;

        Log.d("ddd", "Before stop Sound");
        backgroundSound.stopSound();
        Log.d("ddd", "After stop Sound");

        speedSensorsManager.stopSpeedSensor();
        if(useStepSensor){
            stepSensorsManager.stopStepSensor();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        backgroundSound = new BackgroundSound(this);
        backgroundSound.playSound(1);

        if(!isGameRunning){
            isGameRunning = true;
            speedSensorsManager.startSpeedSensor();
            if(useStepSensor){
                stepSensorsManager.startStepSensor();
            }
            if(isGameOver){
                return;
            }
            startGameLoop();
        }
    }

    private void initSounds(){
        mpCoinSound = new MediaPlayer[] {
            MediaPlayer.create(this, R.raw.pikachu_get_coin_1),
                MediaPlayer.create(this, R.raw.pikachu_get_coin_2)
        };
        mpGameOverSound = MediaPlayer.create(this, R.raw.pikachu_sad_game_over);
        mpStepRightSound = MediaPlayer.create(this, R.raw.movementright);
        mpStepLeftSound = MediaPlayer.create(this, R.raw.movementleft);
    }
    private void moveRight() {
        int currLane = gm.getMainCharacter().getPosX();
        int newLane = currLane + 1;
        if(newLane < gm.getNumberOfCols()){
            //Switch lanes
            switchLaneHandler(currLane, newLane);
            mpStepRightSound.start();
            int collidedObsIndex = gm.isCollisionAccured();
            if(collidedObsIndex != -1){
                collisionUI(collidedObsIndex);
            }
        }
    }
    private void moveLeft() {
        int currLane = gm.getMainCharacter().getPosX();
        int newLane = currLane - 1;
        if (newLane >= 0) {
            //Switch lanes
            switchLaneHandler(currLane, newLane);
            mpStepLeftSound.start();
            int collidedObsIndex = gm.isCollisionAccured();
            if(collidedObsIndex != -1){
                collisionUI(collidedObsIndex);
            }
        }
    }

    private void switchLaneHandler(int oldLane, int newLane) {
        RelativeLayout currRl = gameMat[5][oldLane];
        currRl.removeView(main_IMG_pikachu);
        RelativeLayout newRl = gameMat[5][newLane];
        newRl.addView(main_IMG_pikachu);
        gm.getMainCharacter().setPosX(newLane);
    }

    private void findViews() {
        main_MTV_chosen_speed = findViewById(R.id.main_MTV_chosen_speed);
        main_btn_left = findViewById(R.id.main_btn_left);
        main_btn_right = findViewById(R.id.main_btn_right);
        main_grid = findViewById(R.id.main_grid);
        main_IMG_pikachu = findViewById(R.id.main_IMG_pikachu);
        main_IMG_hearts = new ShapeableImageView[] {
                findViewById(R.id.main_IMG_heart1),
                findViewById(R.id.main_IMG_heart2),
                findViewById(R.id.main_IMG_heart3) };
        main_IMG_hearts[1] = findViewById(R.id.main_IMG_heart2);
        main_IMG_hearts[2] = findViewById(R.id.main_IMG_heart3);
        main_text_score = findViewById(R.id.main_text_score);
        main_cardview_gameover = findViewById(R.id.main_cardview_gameover);
        main_card_lbl_score = findViewById(R.id.main_card_lbl_score);
        main_ET_player_name = findViewById(R.id.main_ET_player_name);

    }

    private void adjustGameMode() {
        // Get the Intent that started this activity
        Intent intent = getIntent();
        // Get the JSON string from the Intent's extra data
        String jsonString = intent.getStringExtra("jsonString");
        try {
            // Convert the JSON string back to a JSON object
            if(jsonString != null){
                JSONObject gameSetupValues = new JSONObject(jsonString);
                // Get data from the JSON object
                this.speed = gameSetupValues.getBoolean("speed");
                this.useStepSensor = gameSetupValues.getBoolean("playStyle");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("ccc", speed + " " + useStepSensor);
        loopInterval = speed ? FAST_VALUE : SLOW_VALUE;

        //Sensor to control the speed of the obstacle coming down:
        activateSpeedSensors();


        if(useStepSensor){ //Activate Sensors if the user chose this option:
            main_btn_right.setVisibility(View.INVISIBLE);
            main_btn_left.setVisibility(View.INVISIBLE);
            activateMovementSensors();

        }

    }
    private void activateSpeedSensors() {
        speedSensorsManager = new SensorMovement(this, new CallBack_GameSpeed() {
            @Override
            public void GameMoveFaster() {
                loopInterval = FASTER_VALUE;
            }
            @Override
            public void GameMoveSlower() {
                loopInterval = speed ? FAST_VALUE : SLOW_VALUE;
            }
        });
        speedSensorsManager.startSpeedSensor();
    }
    private void activateMovementSensors() {
        stepSensorsManager = new SensorMovement(this, new CallBack_CharacterMovement() {
            @Override
            public void CharacterMoveRight() {
                moveRight();
            }
            @Override
            public void CharacterMoveLeft() {
                moveLeft();
            }
        });
        stepSensorsManager.startStepSensor();
    }

    private RelativeLayout[][] getAllRelativeLayouts() {
        int rowCount = main_grid.getRowCount();
        int columnCount = main_grid.getColumnCount();

        RelativeLayout[][] relativeLayouts = new RelativeLayout[rowCount][columnCount];

        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                int linearIndex = i * columnCount + j;
                View childView = main_grid.getChildAt(linearIndex);

                if (childView instanceof RelativeLayout) {
                    relativeLayouts[i][j] = (RelativeLayout) childView;
                    Log.d("mainActivity", "i = " + i + "j = " + j);
                }
            }
        }

        return relativeLayouts;
    }

    private void startGameLoop() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(!isGameRunning){
                    return;
                }
                odometer();
                ObstacleViewMovement(); // Move the obstacle one line below each time
                handler.postDelayed(this, loopInterval); // Schedule the next iteration
                int randomDisplay = gm.generateRandomNumber(5);
                if (randomDisplay == 1 && gm.getObstacleList().size() < gm.getMaxObstaclesOnScreen()) {
                    createNewObstacle();
                }
            }
        }, loopInterval); // Initial delay
    }
    private void odometer(){
        gm.setScore(gm.getScore() + ODOMETER_VALUE);
        main_text_score.setText(gm.getScore() + "");

    }

    private void createNewObstacle() {
        ShapeableImageView obstacleImageView = new ShapeableImageView(this);
        obstacleImageView.setImageResource(R.drawable.pokeball); // Set your obstacle image resource here

        Obstacle obs = new Obstacle(gm.generateRandomNumber(5), obstacleImageView, true);
        // Set layout parameters as needed
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        obstacleImageView.setLayoutParams(layoutParams);

        int posX = obs.getPosX();
        int posY = obs.getPosY();

        gm.addObstacleToArrayList(obs);
        RelativeLayout newRelativeLayout = gameMat[posY][posX];
        newRelativeLayout.addView(obstacleImageView);
    }
    private void nextRowHandler(int oldRow, int newRow, int lane, Obstacle obs, boolean isReset) {
        RelativeLayout currRl = gameMat[oldRow][lane];
        currRl.removeView(obs.getShapableImageView());
        if(isReset){
            lane = gm.generateRandomNumber(gm.getNumberOfCols());
            obs.setPosX(lane);
            newRow = 0;
            obs.getShapableImageView().setVisibility(View.VISIBLE);
            randomObstacleType(obs);
        }
        RelativeLayout newRl = gameMat[newRow][lane];
        newRl.addView(obs.getShapableImageView());
        obs.setPosY(newRow);
    }

    private void randomObstacleType(Obstacle obs) {
        // random number to choose the new type
        // according to the type change the picture
        int randomNumber = gm.generateRandomNumber(3);
        if(randomNumber == 2){
            obs.setDoingDamage(false);
            obs.getShapableImageView().setImageResource(R.drawable.onigiri_rice_cake);
        }else{
            obs.setDoingDamage(true);
            obs.getShapableImageView().setImageResource(R.drawable.pokeball);
        }
    }



    private void ObstacleViewMovement() {
        for(Obstacle obs : gm.getObstacleList()){
            int nextRow = obs.getPosY() + 1;
            Log.d("aaa", "from row "+ obs.getPosY() +" to row " + nextRow +" on lane " + obs.getPosX());
            nextRowHandler(obs.getPosY(), nextRow, obs.getPosX(), obs, (nextRow < gm.getNumberOfRows()) ? false : true);
        }
        int collidedObsIndex = gm.isCollisionAccured();
        if(collidedObsIndex != -1){
            collisionUI(collidedObsIndex);
        }
    }

    private void collisionUI(int i){

        Obstacle currObstacle = gm.getObstacleList().get(i);
        if(currObstacle.isDoingDamage()){ // Crash
            Log.d("bbb", "from changeUI : lifes : " + gm.getNumberOfLifes() + " type: " + currObstacle.isDoingDamage());
            loseALife();
            vibrationEffect();
            createToast("Oops");
            if(gm.getNumberOfLifes() == 0){
                gameOver();
            }
        }else{ // COIN COLLECT
            Log.d("bbb", "from changeUI : lifes : " + gm.getNumberOfLifes() + " type: " + currObstacle.isDoingDamage());
            receivePoints();
            removeCoin(currObstacle);
            makeCoinCollectingSound();
        }
    }

    private void makeCoinCollectingSound() {
        int random = gm.generateRandomNumber(2);
        mpCoinSound[random].start();
    }


    public void createToast(String message) {
        CharSequence text = message;
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(this, text, duration);
        toast.show();
    }


    private void removeCoin( Obstacle currObstacle) {
        currObstacle.getShapableImageView().setVisibility(View.INVISIBLE);
    }

    private void loseALife() {
        if(gm.getNumberOfLifes() >= 0) {
            main_IMG_hearts[main_IMG_hearts.length - gm.getBumpCounter()].setVisibility(View.INVISIBLE);
        }
    }

    private void receivePoints() {
        gm.setScore(gm.getScore() + gm.getCoinValue());
        main_text_score.setText(gm.getScore() + "");
    }

    private void gameOver() {
        Log.d("bbb", "GAME OVER");
        isGameRunning = false;
        isGameOver = true;
        showEndGameCard(true);
        mpGameOverSound.start();


        main_btn_right.setVisibility(View.INVISIBLE);
        main_btn_left.setVisibility(View.INVISIBLE);

        main_card_lbl_score.setText("Final Score: " + gm.getScore());

    }

    private void showEndGameCard(boolean isVisible) {
        if(isVisible){
            main_cardview_gameover.setVisibility(View.VISIBLE);
        }else{
            main_cardview_gameover.setVisibility(View.INVISIBLE);
        }
    }

    public void vibrationEffect() {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(VIBRATIONT_TIME, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            v.vibrate(VIBRATIONT_TIME);
        }
    }

    public void saveScore(){
        // TODO:
        // 1. save the score to the game manager
        // in game manager create a sorted array and show only the top 10 scores and players

        if(main_ET_player_name.length() == 0){
            createToast("Please Add Your Name");
        }else{

        }

    }
    public void goToScoreBoardActivity(){
        // TODO:
    }
}