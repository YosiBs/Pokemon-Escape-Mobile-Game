package com.example.hw2.Utilities;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.example.hw2.Interfaces.CallBack_CharacterMovement;
import com.example.hw2.Interfaces.CallBack_GameSpeed;

public class SensorMovement {

    private SensorManager movementSensorManager;
    private SensorManager speedSensorManager;
    private Sensor movementSensor;
    private Sensor speedSensor;
    private SensorEventListener movementSensorEventListener;
    private SensorEventListener speedSensorEventListener;
    private long timeStamp = 0l;
    private final float X_LIMIT = 4.0f;
    private final float Y_LIMIT = 9.0f;
    private CallBack_CharacterMovement callBack_CharacterMovement;
    private CallBack_GameSpeed callBack_gameSpeed;

    public SensorMovement(Context context, CallBack_CharacterMovement callBack_CharacterMovement){
        movementSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        movementSensor = movementSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        this.callBack_CharacterMovement = callBack_CharacterMovement;
        initEventListenerMovement();
    }
    public SensorMovement(Context context, CallBack_GameSpeed callBack_gameSpeed){
        speedSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        speedSensor = speedSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        this.callBack_gameSpeed = callBack_gameSpeed;
        initEventListenerSpeed();
    }

    private void initEventListenerSpeed() {
        speedSensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float y = event.values[2];
                calculateSpeed(y);
            }
            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                // pass
            }
        };

    }

    private void initEventListenerMovement() {
        movementSensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float x = event.values[0];
                calculateStep(x);
            }
            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                // pass
            }
        };
    }

    private void calculateSpeed(float y) {
        if (y > Y_LIMIT) {
            if (callBack_gameSpeed != null) {
                callBack_gameSpeed.GameMoveFaster();
            }
        } else {
            if (callBack_gameSpeed != null) {
                callBack_gameSpeed.GameMoveSlower();
            }
        }
    }


    private void calculateStep(float x) {
        if(System.currentTimeMillis() - timeStamp > 500){
            timeStamp = System.currentTimeMillis();
            if(x > X_LIMIT){
                if(callBack_CharacterMovement != null){
                    callBack_CharacterMovement.CharacterMoveLeft();
                }
            }
            if(x < -X_LIMIT){
                if(callBack_CharacterMovement != null){
                    callBack_CharacterMovement.CharacterMoveRight();
                }
            }
        }
    }

    public void startStepSensor(){
        movementSensorManager.registerListener(
                movementSensorEventListener,
                movementSensor,
                SensorManager.SENSOR_DELAY_NORMAL
            );
    }
    public void stopStepSensor(){
        movementSensorManager.unregisterListener(
                movementSensorEventListener,
                movementSensor
            );
    }
    public void startSpeedSensor(){
        speedSensorManager.registerListener(
                speedSensorEventListener,
                speedSensor,
                SensorManager.SENSOR_DELAY_NORMAL
        );
    }
    public void stopSpeedSensor(){
        speedSensorManager.unregisterListener(
                speedSensorEventListener,
                speedSensor
        );
    }

}
