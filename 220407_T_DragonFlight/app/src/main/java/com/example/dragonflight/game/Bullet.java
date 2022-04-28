package com.example.dragonflight.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

import com.example.dragonflight.framework.BoxCollidable;
import com.example.dragonflight.framework.GameObject;
import com.example.dragonflight.framework.Metrics;
import com.example.dragonflight.R;
import com.example.dragonflight.framework.Recyclable;
import com.example.dragonflight.framework.RecycleBin;

import java.util.ArrayList;

public class Bullet implements GameObject, BoxCollidable, Recyclable {
    private static final String TAG = Bullet.class.getSimpleName();

    protected float x, y;
    protected final float length;

    protected final float speed;

    protected static Paint paint;

    protected static float laserWidth;

    protected RectF boundgingBox = new RectF();

    protected float power;

    public static Bullet get(float x, float y, float power) {
        Bullet bullet = (Bullet) RecycleBin.get(Bullet.class);

        if (bullet != null) {
            // Log.d(TAG, "Recycle: " + recycleBin.size() + " bullets");
            bullet.set(x, y, power);
            return bullet;
        }
        return new Bullet(x, y, power);
    }

    private void set(float x, float y, float power) {
        this.x = x;
        this.y = y;
        this.power = power;
    }

    private Bullet(float x, float y, float power) {
        this.x = x;
        this.y = y;
        this.power = power;

        this.length = Metrics.size(R.dimen.laser_length);
        this.speed = Metrics.size(R.dimen.laser_speed);

        if (paint == null) {
            paint = new Paint();
            paint.setColor(Color.RED);
            laserWidth = Metrics.size(R.dimen.laser_width);
            paint.setStrokeWidth(laserWidth);
        }

        // Log.d(TAG, "Created: " + this);
    }

    @Override
    public void update() {
        MainGame game = MainGame.getInstance();

        float frameTime = game.frameTime;
        y -= speed * frameTime;

        float hw = laserWidth / 2;
        boundgingBox.set(x - hw, y, x + hw, y + length);

        if (y < 0) {
            game.remove(this);
            // Log.d(TAG, "Remove: " + recycleBin.size() + " bullets");
        }
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawLine(x, y, x, y + length, paint);
    }

    @Override
    public RectF getBoundingRect() {
        return boundgingBox;
    }

    @Override
    public void finish() {
    }

    public float getPower() {
        return power;
    }
}
