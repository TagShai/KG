package ru.nevors.kg.DrawMethod.DrawMethodFractal;

import android.graphics.Bitmap;

import ru.nevors.kg.DrawMethod.DrawMethod;
import ru.nevors.kg.DrawMethod.DrawMethodLine.DrawMethodLineBRH;
import ru.nevors.kg.View.MyPaint.MyPaint;

public class DrawMethodTreePifagor extends DrawMethod {
    DrawMethod dm = new DrawMethodLineBRH();
    float angleX = 3, angleY = 3;
    float max;
    float start = 50;
    float speed = 0.7F;

    public DrawMethodTreePifagor(int maxIter) {
        max = start;
        for (int i = 0; i < maxIter; i++) {
            max *= speed;
        }
    }

    Bitmap bmp;
    MyPaint paint;
    int[] temp = new int[4];

    @Override
    public void draw(Bitmap bmp, int[] points, MyPaint paint) {
        this.bmp = bmp;
        this.paint = paint;
        float x = points[0] - points[2];
        float y = points[3] - points[1];

        if (x == 0) {
            angleX = (float) (Math.PI / 2);
            angleY = 0;
        } else {
            angleX = (float) Math.tan(y / x);
            angleY = (float) (Math.PI / 2  - angleX);
        }
        draw(points[0], points[1], start, (float) (Math.PI / 2));
    }

    void draw(int x, int y, float iter, float a) {
        if (iter > max) {
            iter *= 0.7F;
            int h = 0;
            temp[h++] = x;
            temp[h++] = y;
            temp[h++] = (int) (x + iter * Math.cos(a));
            temp[h++] = (int) (y - iter * Math.sin(a));
            dm.draw(bmp, temp, paint);
            x = temp[2];
            y = temp[3];

            // рекурсивный вызов
            // draw(x, y, iter, (float) (a + Math.PI / angleX));
            // draw(x, y, iter, (float) (a - Math.PI / angleY));
            draw(x, y, iter, a + angleX);
            draw(x, y, iter, a - angleY);
        }
    }
}
