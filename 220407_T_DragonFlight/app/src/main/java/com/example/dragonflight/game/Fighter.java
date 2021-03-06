package com.example.dragonflight.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

import com.example.dragonflight.framework.BaseGame;
import com.example.dragonflight.framework.Metrics;
import com.example.dragonflight.R;
import com.example.dragonflight.framework.Sprite;
import com.example.dragonflight.framework.BitmapPool;

public class Fighter extends Sprite {
    private static final String TAG = Fighter.class.getSimpleName();
    private RectF targetRect = new RectF();

    private float dx, dy;
    private float tx, ty;

    private float elapsedTimeForFire;
    private float fireInterval = 1.0f / 10;  // 초당 10발.

    private static Bitmap targetBitmap;

    public Fighter(float x, float y) {
        super(x, y, R.dimen.fighter_radius, R.mipmap.plane_240);
        setTargetPosition(x, y);

        targetBitmap = BitmapPool.get(R.mipmap.target);
        fireInterval = Metrics.floatValue(R.dimen.fighter_fire_interval);
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, null, dstRect, null);
        if (dx != 0 || dy != 0) {
            canvas.drawBitmap(targetBitmap, null, targetRect, null);
        }
    }

    public void update() {
        float frameTime = BaseGame.getInstance().frameTime;
        elapsedTimeForFire += frameTime;
        if(elapsedTimeForFire >= fireInterval) {  // 주기적으로 총알 발사되도록.
            fire();
            elapsedTimeForFire -= fireInterval;
        }

        if (dx == 0)
            return;

        float dx = this.dx * frameTime;
        boolean arrived = false;

        if ((dx > 0 && x + dx > tx) || (dx < 0 && x + dx < tx)) {
            dx = tx - x;
            x = tx;
            arrived = true;
        }
        else {
            x += dx;
        }

        dstRect.offset(dx, 0);

        if(arrived) {
            this.dx = 0;
        }
    }

    public void setTargetPosition(float tx, float ty) {
        this.tx = tx;
        this.ty = y;

        targetRect.set(tx - radius/2, y - radius/2, tx + radius/2, y + radius/2);

        dx = Metrics.size(R.dimen.fighter_speed);
        if (tx < x) {
            dx = -dx;
        }
    }

    public void fire() {
        int score = MainGame.get().score.get();
        if(score > 100000) {
            score =  100000;
        }
        float power = 10 + score / 1000;
        Bullet bullet = Bullet.get(x, y, power);
        MainGame.get().add(MainGame.Layer.bullet, bullet);
    }
}
