package ru.nevors.kg.DrawMethod.DrawMethodLine;

import android.graphics.Bitmap;
import android.graphics.Color;

import ru.nevors.kg.DrawMethod.DrawMethod;
import ru.nevors.kg.View.MyPaint.MyPaint;

public class DrawMethodLineDDA extends DrawMethod {
    @Override
    public void draw(Bitmap bmp, int[] points, MyPaint paint) {
        int color = paint.getColor();
        int color2 = paint.getColor2();
        int a = Color.alpha(color);
        float r = Color.red(color), g = Color.green(color), b = Color.blue(color);
        float r2 = Color.red(color2), g2 = Color.green(color2), b2 = Color.blue(color2);
        float x = points[0],
                y = points[1],
                dx = points[2] - points[0],
                dy = points[3] - points[1],
                N = Math.max(Math.abs(dx), Math.abs(dy)),
                t = 1 / N;
        float sx = dx * t, sy = dy * t;
        float dr = (r2 - r) * t;
        float dg = (g2 - g) * t;
        float db = (b2 - b) * t;
        try {
            for (int i = 0; i <= N; i++) {
                //if (checkLimits(bmp, (int)x, (int)y))
                bmp.setPixel((int) x, (int) y, Color.argb(a,(int) r, (int) g, (int) b));
                x += sx;
                y += sy;
                r += dr;
                g += dg;
                b += db;
            }
        } catch (IllegalArgumentException e) {
            return;
        }
    }
}
