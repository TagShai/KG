package ru.nevors.kg.DrawMethod.DrawMethodFractal;

import android.graphics.Bitmap;

import ru.nevors.kg.DrawMethod.DrawMethod;
import ru.nevors.kg.DrawMethod.DrawMethodLine.DrawMethodLineBRH;
import ru.nevors.kg.DrawMethod.DrawMethodObj3D.DrawMethodGraph;
import ru.nevors.kg.View.MyPaint.MyPaint;

public class DrawMethodDragon extends DrawMethod {
    DrawMethod dm = new DrawMethodLineBRH();
    int n;
    int[] temp = new int[4];

    public DrawMethodDragon(int n){
        this.n = n;
    }

    @Override
    public void draw(Bitmap bmp, int[] points, MyPaint paint) {
        int h = 0;
        drawDragon(points[h++], points[h++], points[h++], points[h++], n, bmp, paint);
    }

    void drawDragon(float x1, float y1, float x2, float y2, int n, Bitmap bmp, MyPaint paint) {
        float xn, yn;
        if (n > 0) {
            xn = (x1 + x2) / 2 + (y2 - y1) / 2;
            yn = (y1 + y2) / 2 - (x2 - x1) / 2;
            drawDragon(x2, y2, xn, yn, n - 1, bmp, paint);
            drawDragon(x1, y1, xn, yn, n - 1, bmp, paint);
        }
        if (n == 0) {
            int h = 0;
            temp[h++] = Math.round(x1);
            temp[h++] = Math.round(y1);
            temp[h++] = Math.round(x2);
            temp[h++] = Math.round(y2);
            dm.draw(bmp, temp, paint);
        }
    }
}
