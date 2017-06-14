package ru.nevors.kg.HandlerTouch.DrawFigure.DrawRect.Transform2D;

import android.graphics.Point;

import ru.nevors.kg.View.PD2;

public class Transform2DMove extends Transform2D {
    public Transform2DMove(PD2 pd) {
        super(pd);
    }

    @Override
    protected int[] actionPoint(int[] points, Point start, Point cur) {
        for (int i = 0; i < points.length; i += 2) {
            points[i] += cur.x - start.x;
            points[i + 1] += cur.y - start.y;
        }
        return points;
    }
}
