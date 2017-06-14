package ru.nevors.kg.OBJ;

import android.graphics.PointF;

public class Point extends PointF {
    public float z;

    public Point() {
    }

    public Point(float x, float y, float z) {
        super(x, y);
        this.z = z;
    }

    public void set(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void set(Point p) {
        this.x = p.x;
        this.y = p.y;
        this.z = p.z;
    }
}
