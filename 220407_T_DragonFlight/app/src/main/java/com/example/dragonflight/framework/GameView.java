package com.example.dragonflight.framework;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Choreographer;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.dragonflight.game.MainGame;

public class GameView extends View implements Choreographer.FrameCallback {
    public static GameView view;

    private static final String TAG = GameView.class.getSimpleName();

    private Paint fpsPaint = new Paint();

    private long lastTimeNanos;
    private int framesPerSecond;
    private boolean initialized;

    private boolean running;

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        view = this;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Metrics.width = w;
        Metrics.height = h;

        if (!initialized) {
            initView();
            initialized = true;
            running = true;
            Choreographer.getInstance().postFrameCallback(this);
        }
    }

    @Override
    public void doFrame(long currentTimeNanos) {
        if(!running){  // 게임 실행하지 않도록.
            Log.d(TAG, "Running is false on doFrame()");
            return;
        }

        long now = currentTimeNanos;
        int elapsed = (int) (now - lastTimeNanos);
        if (elapsed != 0) {
            framesPerSecond = 1_000_000_000 / elapsed;
            lastTimeNanos = now;
            MainGame game = MainGame.getInstance();
            game.update(elapsed);
            invalidate();
        }

        Choreographer.getInstance().postFrameCallback(this);
    }

    private void initView() {
        MainGame.getInstance().init();
        fpsPaint.setColor(Color.BLUE);
        fpsPaint.setTextSize(100);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return MainGame.getInstance().onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        MainGame.getInstance().draw(canvas);

        canvas.drawText("FPS:" + framesPerSecond, framesPerSecond * 10, 100, fpsPaint);
        canvas.drawText("" + MainGame.getInstance().objectCount(), 10, 100, fpsPaint);
    }

    public void pauseGame() {  // 게임 멈추는 함수.
        running = false;
    }

    public void resumeGame() {  // 게임 다시 시작하는 함수.
        if(initialized && !running) {
            running = true;
            Choreographer.getInstance().postFrameCallback(this);

            Log.d(TAG, "Resuming game");
        }
    }
}