package com.example.cookierun.game;

import com.example.cookierun.R;
import com.example.cookierun.framework.BitmapPool;
import com.example.cookierun.framework.RecycleBin;
import com.example.cookierun.framework.Sprite;

public class Platform extends ScrollObject {
    public enum Type {
        T_10x2, T_2x2, T_3x1;

        float width() {
            int w = 1;
            switch (this) {
                case T_10x2: w = 10; break;
                case T_2x2: w = 2; break;
                case T_3x1: w = 3; break;
            }
            return MainGame.get().size(w);
        }

        float height() {
            int h = 1;
            switch (this) {
                case T_10x2: case T_2x2: h = 2; break;
                case T_3x1: h = 1; break;
            }
            return MainGame.get().size(h);
        }

        int bitmapId() {
            return BITMAP_IDS[this.ordinal()];
        }
    }

    protected static int[] BITMAP_IDS = {
            R.mipmap.cookierun_platform_480x48,
            R.mipmap.cookierun_platform_124x120,
            R.mipmap.cookierun_platform_120x40,
    };

    public static Platform get(Type type, float unitLeft, float unitTop) {
        Platform platform = (Platform) RecycleBin.get(Platform.class);
        if (platform == null) {
            platform = new Platform();
        }
        platform.init(type, unitLeft, unitTop);
        return platform;
    }

    private Platform() {
    }

    private void init(Type type, float unitLeft, float unitTop) {
        bitmap = BitmapPool.get(type.bitmapId());
        MainGame game = MainGame.get();
        float left = game.size(unitLeft);
        float top = game.size(unitTop);
        dstRect.set(left, top, left + type.width(), top + type.height());
    }
}
