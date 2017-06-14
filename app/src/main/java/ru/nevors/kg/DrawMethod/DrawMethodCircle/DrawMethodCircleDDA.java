package ru.nevors.kg.DrawMethod.DrawMethodCircle;

import android.graphics.Bitmap;

import ru.nevors.kg.View.MyPaint.MyPaint;

public class DrawMethodCircleDDA extends DrawMethodCircle {
    @Override
    public void draw(Bitmap bmp, int[] points, MyPaint paint) {
        int color = paint.getColor();
        int r = (int) Math.sqrt((points[2] - points[0]) * (points[2] - points[0]) + (points[3] - points[1]) * (points[3] - points[1]));
        double r2 = r / Math.sqrt(2);
        int rr = r * r;
        int y;

        for (int x = 0; x <= r2; x++) {
            y =  (int)Math.sqrt(rr - x * x);
            _setPixel8(bmp, points[0], points[1], x, y, color);
        }
    }
}
