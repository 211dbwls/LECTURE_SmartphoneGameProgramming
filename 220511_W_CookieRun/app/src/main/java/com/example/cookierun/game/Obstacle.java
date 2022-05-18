package com.example.cookierun.game;

import com.example.cookierun.R;
import com.example.cookierun.framework.game.RecycleBin;
import com.example.cookierun.framework.res.BitmapPool;

public class Obstacle extends MapSprite {
    private Modifier modifier;

    public static Obstacle get(int index, float unitLeft, float unitTop) {
        Obstacle obs = (Obstacle) RecycleBin.get(Obstacle.class);
        if (obs == null) {
            obs = new Obstacle();
        }
        obs.init(index, unitLeft, unitTop);
        return obs;
    }

    private static class Modifier {
        private final float height;
        private int mipmapResId;

        public Modifier(float heightUnit) {
            this.height = heightUnit;
        }

        public Modifier(float heightUnit, int mipmapResId) {
            this.height = heightUnit;
            this.mipmapResId = mipmapResId;
        }

        public void update(float frameTime) {
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
        public AnimModifier(float heightUnit, int[] mipmapResIds) {
            super(heightUnit);
            this.mipmapResIds = mipmapResIds;
        }

        @Override
        public void init(Obstacle obstacle, float unitLeft, float unitTop) {
            super.init(obstacle, unitLeft, unitTop);
            obstacle.bitmap = BitmapPool.get(mipmapResIds[0]);
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
            }),
            new AnimModifier(222/87f, new int[] {
                    R.mipmap.epn01_tm01_jp2up_01,
                    R.mipmap.epn01_tm01_jp2up_02,
                    R.mipmap.epn01_tm01_jp2up_03,
                    R.mipmap.epn01_tm01_jp2up_04,
                    R.mipmap.epn01_tm01_jp2up_05,
            }),
            new MoveModifier(482/86f, R.mipmap.epn01_tm01_sda),
    };

    private void init(int index, float unitLeft, float unitTop) {
        modifier = MODIFIERS[index];
        modifier.init(this, unitLeft, unitTop);
    }
}