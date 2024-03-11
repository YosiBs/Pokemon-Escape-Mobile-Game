package com.example.hw2.Utilities;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Looper;

import com.example.hw2.R;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class BackgroundSound {
    private Context context;
    private Executor executor;
    private Handler handler;

    private MediaPlayer mp;

    public BackgroundSound(Context context){
        this.context = context;
        this.executor = Executors.newSingleThreadExecutor();
        this.handler = new Handler(Looper.getMainLooper());
    }

    public void playSound(int stage){
        executor.execute(() -> {
            switch(stage){
                case 0:
                    mp = MediaPlayer.create(context, R.raw.menu_background_sound);
                    break;
                case 1:
                    mp = MediaPlayer.create(context, R.raw.pokemon_battle_start_background_music);
                    break;
            }
            mp.setLooping(true);
            mp.setVolume(1.0f, 1.0f);
            mp.start();
        });
    }
    public void stopSound(){
        if(mp != null){
            executor.execute(() -> {
                mp.stop();
                mp.release();
                mp = null;
            });
        }
    }
}