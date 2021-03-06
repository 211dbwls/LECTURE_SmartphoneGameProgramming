package com.example.cookierun.framework.res;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.cookierun.framework.view.GameView;

import java.util.HashMap;

public class BitmapPool {
    private static final String TAG = BitmapPool.class.getSimpleName();
    private static HashMap<Integer, Bitmap> bitmaps = new HashMap<>();

    public static BitmapFactory.Options opts;
    static {
        opts = new BitmapFactory.Options();
        opts.inScaled = false;
    }

    public static Bitmap get(int mipmapResId) {
        Bitmap bitmap = bitmaps.get(mipmapResId);
        if (bitmap == null) {
            Resources res = GameView.view.getResources();
            bitmap = BitmapFactory.decodeResource(res, mipmapResId, opts);
            // Log.d(TAG, "BID=" + mipmapResId + " w:" + bitmap.getWidth() + " h:" + bitmap.getHeight());
            bitmaps.put(mipmapResId, bitmap);
        }
        return bitmap;
    }
}
