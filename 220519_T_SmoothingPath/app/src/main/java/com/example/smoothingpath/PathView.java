package com.example.smoothingpath;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class PathView extends View {
    private Path path;

    public int getPointCount() {
        return points.size();
    }

    public interface Listener {
        public void onAdd();
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    private Listener listener;

    private int mExampleColor = Color.RED;

    private Paint paint;

    public PathView(Context context) {
        super(context);
        init(null, 0);
    }

    public PathView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public PathView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.PathView, defStyle, 0);

        mExampleColor = a.getColor(R.styleable.PathView_exampleColor, mExampleColor);

        a.recycle();

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2.0f);
        paint.setColor(mExampleColor);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int ptCount = points.size();
        if(ptCount == 0) {
            return;
        }
        PointF first = points.get(0);
        if(ptCount == 1) {
            canvas.drawCircle(first.x, first.y, 5.0f, paint);
            return;
        }

        canvas.drawPath(path, paint);
    }

    private void buildPath() {
        int ptCount = points.size();
        if(ptCount < 2) {
            return;
        }
        PointF first = points.get(0);

        path = new Path();
        path.moveTo(first.x, first.y);
        for (int i = 1; i < ptCount; i++) {
            PointF pt = points.get(i);
            path.lineTo(pt.x, pt.y);
        }
    }

    public int getExampleColor() {
        return mExampleColor;
    }

    public void setExampleColor(int exampleColor) {
        mExampleColor = exampleColor;
        //invalidateTextPaintAndMeasurements();
    }

    ArrayList<PointF> points = new ArrayList<>();
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            PointF point = new PointF();
            point.x = event.getX();
            point.y = event.getY();
            points.add(point);
            buildPath();
            if(listener != null) {
                listener.onAdd();
            }
            invalidate();
        }
        return super.onTouchEvent(event);
    }

    public void clear() {
        points.clear();
        invalidate();
    }
}