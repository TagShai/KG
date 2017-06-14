package ru.nevors.kg.DrawMethod.DrawMethodFractal;

import android.graphics.Bitmap;

import ru.nevors.kg.DrawMethod.DrawMethod;
import ru.nevors.kg.View.MyPaint.MyPaint;

public class DrawMethodFerm extends DrawMethod {
    int w, h, iter;

    public DrawMethodFerm(int w, int h, int iter) {
        this.w = w;
        this.h = h;
        this.iter = iter;
    }

    @Override
    public void draw(Bitmap bmp, int[] points, MyPaint paint) {
        int color = paint.getColor();
        double x = 0;
        double y = 0;

        int w2 = points[0] - w / 2;
        int h2 = points[1] - h / 2;

        for (int i = 0; i < iter; i++) {
            double tmpx, tmpy;
            double r = Math.random();
            if (r <= 0.01) {
                tmpx = 0;
                tmpy = 0.16 * y;
            } else if (r <= 0.08) {
                tmpx = 0.2 * x - 0.26 * y;
                tmpy = 0.23 * x + 0.22 * y + 1.6;
            } else if (r <= 0.15) {
                tmpx = -0.15 * x + 0.28 * y;
                tmpy = 0.26 * x + 0.24 * y + 0.44;
            } else {
                tmpx = 0.85 * x + 0.04 * y;
                tmpy = -0.04 * x + 0.85 * y + 1.6;
            }
            x = tmpx;
            y = tmpy;

            setPixel(bmp, (int) Math.round(w / 2 + x * w / 11) + w2, (int) Math.round(h - y * h / 11) + h2, color);
        }
    }
}
