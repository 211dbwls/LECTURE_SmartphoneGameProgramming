package com.example.cookierun.game;

import android.graphics.Canvas;

import com.example.cookierun.framework.BaseGame;
import com.example.cookierun.framework.Metrics;
import com.example.cookierun.framework.Sprite;

public class HorzScrollBackground extends Sprite {
    private final float speed;
    private final int width;

    public HorzScrollBackground(int bitmapResId, float speed) {
        super(Metrics.width / 2, Metrics.height / 2,  Metrics.width, Metrics.height, bitmapResId);

        this.width = bitmap.getWidth() * Metrics.height / bitmap.getHeight();

        setDstRect(width, Metrics.height);setDstRect(width, Metrics.height);

        this.speed = speed;
    }

    @Override
    public void update() {
        this.x += speed * BaseGame.getInstance().frameTime;
    }

    @Override
    public void draw(Canvas canvas) {
        int curr = (int)x % width;
        if(curr > 0) {
            curr -= width;
        }

        while(curr < Metrics.width) {
            dstRect.set(curr, 0, curr + width, Metrics.height);
            canvas.drawBitmap(bitmap, null, dstRect, null);
            curr += width;
        }
    }
}
