package com.example.samplegame;

import android.content.res.Resources;

public class Metrics {
    public static int width;
    public static int height;

    public static float size(int dimenRedId) {
        Resources res = GameView.view.getResources();
        float size = res.getDimension(dimenRedId);
        return size;
    }

}
