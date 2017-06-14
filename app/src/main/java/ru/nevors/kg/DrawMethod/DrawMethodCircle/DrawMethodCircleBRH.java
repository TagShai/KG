package ru.nevors.kg.DrawMethod.DrawMethodCircle;

import android.graphics.Bitmap;

import ru.nevors.kg.View.MyPaint.MyPaint;

public class DrawMethodCircleBRH extends DrawMethodCircle {
    @Override
    public void draw(Bitmap bmp, int[] points, MyPaint paint) {
        int color = paint.getColor();
        int r = (int) Math.sqrt((points[2] - points[0]) * (points[2] - points[0]) + (points[3] - points[1]) * (points[3] - points[1]));
        int x = 0, y = r, f = 1 - r;
        _setPixel8(bmp, points[0], points[1], x, y, color);
        while (x <= y) {
            if (f > 0) {
                y--;
                f+=2*(x-y)+5;
            } else {
                f+=2*x+3;
            }
            x++;
            _setPixel8(bmp, points[0], points[1], x, y, color);
        }
    }
}
