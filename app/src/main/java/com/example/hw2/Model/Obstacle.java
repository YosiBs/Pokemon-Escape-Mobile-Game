package com.example.hw2.Model;

import com.google.android.material.imageview.ShapeableImageView;

public class Obstacle extends Character{

    private ShapeableImageView shapableImageView;
    private boolean doingDamage;


    public Obstacle(int posX, ShapeableImageView image, boolean doingDamage) {
        super(posX, 0);
        this.doingDamage = doingDamage;
        this.shapableImageView = image;
    }

    public ShapeableImageView getShapableImageView() {
        return shapableImageView;
    }

    public void setShapableImageView(ShapeableImageView shapableImageView) {

        this.shapableImageView = shapableImageView;
    }

    public boolean isDoingDamage() {
        return doingDamage;
    }


    public void setDoingDamage(boolean doingDamage) {
        this.doingDamage = doingDamage;
    }
}
