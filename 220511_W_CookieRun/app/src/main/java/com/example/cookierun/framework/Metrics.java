package com.example.cookierun.framework;

import android.content.res.Resources;
import android.util.TypedValue;

public class Metrics {
    public static int width;
    public static int height;

    public static float size(int dimenResId) {
        Resources res = GameView.view.getResources();
        float size = res.getDimension(dimenResId);
        return size;
    }

    public static float floatValue(int dimenResId) {
        Resources res = GameView.view.getResources();
        // return res.getFloat(dimenResId);  // android q 이전에 나온 버전에서 실행 불가.

        TypedValue outValue = new TypedValue();
        res.getValue(dimenResId, outValue, true);
        float value = outValue.getFloat();

        return value;
    }
}
