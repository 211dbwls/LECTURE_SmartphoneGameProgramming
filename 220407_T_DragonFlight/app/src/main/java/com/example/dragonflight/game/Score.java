package com.example.dragonflight.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import com.example.dragonflight.R;
import com.example.dragonflight.framework.BitmapPool;
import com.example.dragonflight.framework.GameObject;
import com.example.dragonflight.framework.Metrics;
import com.example.dragonflight.framework.Recyclable;

public class Score implements GameObject {
    private final Bitmap bitmap;

    private final int srcCharWidth, srcCharHeight;
    private final float right, top;

    private final float dstCharWidth, dstCharHeight;
    private Rect srcRect = new Rect();
    private RectF dstRect = new RectF();

    private int score;
    private int displayScore;

    public Score(int mipmapResId, float marginTop, float marginRight, float charWidth) {
        this.bitmap = BitmapPool.get(mipmapResId);

        this.right = Metrics.width - marginRight;
        this.top = marginTop;

        this.dstCharWidth = charWidth;
        this.srcCharWidth = bitmap.getWidth() / 10;
        this.srcCharHeight = bitmap.getHeight();
        this.dstCharHeight = dstCharWidth * srcCharHeight / srcCharWidth;
    }

    public void set(int score) {
        this.score = score;
    }

    public int get() {
        return score;
    }

    public void add(int score) {
        this.score += score;
    }

    @Override
    public void update() {
        int diff = score - displayScore;
        if(diff == 0) {
            return;
        }

        if(-10 < diff && diff < 0) {
            displayScore--;
        }
        else if(0 < diff && diff <  10) {
            displayScore++;
        }
        else {
            displayScore += diff / 10;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        int value = this.displayScore;

        float x = right;

        while (value > 0) {
            int digit = value % 10;
            srcRect.set(digit * srcCharWidth, 0, (digit + 1) * srcCharWidth, srcCharHeight);

            x -= dstCharWidth;
            dstRect.set(x, top, x + dstCharWidth, top + dstCharHeight);
            canvas.drawBitmap(bitmap, srcRect, dstRect, null);
            value /= 10;
        }
    }
}

