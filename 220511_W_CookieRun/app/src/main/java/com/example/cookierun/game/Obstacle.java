package com.example.cookierun.game;

import android.graphics.RectF;

import com.example.cookierun.R;
import com.example.cookierun.framework.game.RecycleBin;
import com.example.cookierun.framework.res.BitmapPool;

public class Obstacle extends MapSprite {
    protected Modifier modifier;
    protected long createdOn;
    protected RectF collisionBox = new RectF();

    public static Obstacle get(int index, float unitLeft, float unitTop) {
        Obstacle obs = (Obstacle) RecycleBin.get(Obstacle.class);
        if (obs == null) {
            obs = new Obstacle();
        }
        obs.init(index, unitLeft, unitTop);
        return obs;
    }

    private static class Modifier {
        protected final float height;
        protected int mipmapResId;

        public Modifier(float heightUnit) {
            this.height = heightUnit;
        }

        public Modifier(float heightUnit, int mipmapResId) {
            this.height = heightUnit;
            this.mipmapResId = mipmapResId;
        }

        public void update(Obstacle obstacle, float frameTime) {
            obstacle.collisionBox.set(obstacle.dstRect);
        }

        public void init(Obstacle obstacle, float unitLeft, float unitTop) {
            unitTop -= this.height - 1;
            obstacle.setUnitDstRect(unitLeft, unitTop, 1, height);
            if (mipmapResId != 0) {
                obstacle.bitmap = BitmapPool.get(mipmapResId);
            }
        }
    }

    private static class AnimModifier extends Modifier {
        private final int[] mipmapResIds;
        private final float topTransparent;

        public AnimModifier(float heightUnit, int[] mipmapResIds, float topTransparent) {
            super(heightUnit);
            this.mipmapResIds = mipmapResIds;
            this.topTransparent = topTransparent;
        }

        @Override
        public void init(Obstacle obstacle, float unitLeft, float unitTop) {
            super.init(obstacle, unitLeft, unitTop);
            obstacle.bitmap = BitmapPool.get(mipmapResIds[0]);
            obstacle.createdOn = System.currentTimeMillis();
        }

        @Override
        public void update(Obstacle obstacle, float frameTime) {
            RectF rect = obstacle.dstRect;
            float height = rect.height();
            float trans = height * topTransparent;
            obstacle.collisionBox.set(
                    rect.left,
                    rect.top + trans,
                    rect.right,
                    rect.bottom
            );
        }
    }

    private static class MoveModifier extends Modifier {
        public MoveModifier(float heightUnit, int mipmapResId) {
            super(heightUnit, mipmapResId);
        }
    }

    private static Modifier[] MODIFIERS = {
            new Modifier(99/63f, R.mipmap.epn01_tm01_jp1a),
            new AnimModifier(131/81f, new int[] {
                    R.mipmap.epn01_tm01_jp1up_01,
                    R.mipmap.epn01_tm01_jp1up_02,
                    R.mipmap.epn01_tm01_jp1up_03,
                    R.mipmap.epn01_tm01_jp1up_04,
            }, 64/131f),
            new AnimModifier(222/87f, new int[] {
                    R.mipmap.epn01_tm01_jp2up_01,
                    R.mipmap.epn01_tm01_jp2up_02,
                    R.mipmap.epn01_tm01_jp2up_03,
                    R.mipmap.epn01_tm01_jp2up_04,
                    R.mipmap.epn01_tm01_jp2up_05,
            }, 68/222f),
            new MoveModifier(482/86f, R.mipmap.epn01_tm01_sda),
    };

    private void init(int index, float unitLeft, float unitTop) {
        modifier = MODIFIERS[index];
        modifier.init(this, unitLeft, unitTop);
    }

    @Override
    public void update(float frameTime) {
        super.update(frameTime);
        modifier.update(this, frameTime);
    }

    @Override
    public RectF getBoundingRect() {
        return collisionBox;
    }
}