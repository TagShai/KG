package ru.nevors.kg.HandlerTouch.DrawFigure.DrawRect.Transform2D;

import android.graphics.Point;

import ru.nevors.kg.View.PD2;

public class Transform2DScale extends Transform2DCenterPoint {
    float k = 0.1F;
    float kUp = 1 + k;
    float kDown = 1 - k;

    public Transform2DScale(PD2 pd) {
        super(pd);
    }

    @Override
    protected int[] actionPoint(int[] points, Point start, Point cur, Point center) {
        int diffX = cur.x-start.x;
        int diffY = cur.y-start.y;
        float kX, kY;
        if (diffX < 0) {
            kX = kDown;
        } else {
            if (diffX > 0) {
                kX = kUp;
            } else {
                kX = 1;
            }
        }

        if (diffY < 0) {
            kY = kUp;
        } else {
            if (diffY > 0) {
                kY = kDown;
            } else {
                kY = 1;
            }
        }

        for (int i = 0; i < points.length; i += 2) {
            points[i] -= center.x;
            points[i + 1] -= center.y;
            points[i] *= kX;
            points[i + 1] *= kY;
            points[i] += center.x;
            points[i + 1] += center.y;
        }
        return points;
    }
}
