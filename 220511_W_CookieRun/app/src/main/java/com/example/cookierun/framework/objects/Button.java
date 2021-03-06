package com.example.cookierun.framework.objects;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.MotionEvent;

import com.example.cookierun.framework.interfaces.Touchable;
import com.example.cookierun.framework.res.BitmapPool;

public class Button extends Sprite implements Touchable {
    private static final String TAG = Button.class.getSimpleName();

    protected final Callback callback;
    private final Bitmap normalBitmap;
    private Bitmap pressedBitmap;
    private boolean pressed;

    public enum Action {
        pressed, released,
    }

    public interface Callback {
        public boolean onTouch(Action action);
    }

    public Button(float x, float y, float w, float h, int bitmapResId, int pressedResId, Callback callback) {
        super(x, y, w, h, bitmapResId);

        normalBitmap = bitmap;
        if (pressedResId != 0) {
            pressedBitmap = BitmapPool.get(pressedResId);
        }

        this.callback = callback;
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        float x = e.getX();
        float y = e.getY();
        if (!pressed && !dstRect.contains(x, y)) {
            return false;
        }

        int action = e.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                pressed = true;
                bitmap = pressedBitmap;
                Log.d(TAG, "Down: " + pressedBitmap);
                callback.onTouch(Action.pressed);
                return true;
            case MotionEvent.ACTION_UP:
                pressed = false;
                bitmap = normalBitmap;
                Log.d(TAG, "Up: " + normalBitmap);
                return callback.onTouch(Action.released);
        }
        return false;
    }
}
