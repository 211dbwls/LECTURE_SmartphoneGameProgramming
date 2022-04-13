package com.example.dragonflight.game;

import android.graphics.RectF;

import com.example.dragonflight.R;
import com.example.dragonflight.framework.BoxCollidable;
import com.example.dragonflight.framework.Metrics;
import com.example.dragonflight.framework.Sprite;

public class Enemy extends Sprite implements BoxCollidable {
    protected float dy;

    public static float size;
    protected RectF boundingBox = new RectF();

    public Enemy(float x, float speed) {
        super(x, -size, size, size, R.mipmap.f_01_01);

        dy = speed;
    }

    @Override
    public void update() {
        float frameTime = MainGame.getInstance().frameTime;
        y += dy * frameTime;
        setDstRectWithRadius();

        boundingBox.set(dstRect);
        boundingBox.inset(size / 10, size / 10);

        if (dstRect.top > Metrics.height) {
            MainGame.getInstance().remove(this);
        }
    }

    @Override
    public RectF getBoundingRect() {
        return boundingBox;
    }
}
