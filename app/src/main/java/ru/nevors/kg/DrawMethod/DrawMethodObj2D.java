package ru.nevors.kg.DrawMethod;

import android.graphics.Bitmap;

import ru.nevors.kg.DrawMethod.FillMethod.FillMethodTriangle;
import ru.nevors.kg.View.MyPaint.MyPaint;

public class DrawMethodObj2D extends DrawMethod {
    DrawMethod dm = new DrawMethodTriangle();

    @Override
    public void draw(Bitmap bmp, int[] points, MyPaint paint) {
        int temp[] = new int[6];
        int k;
        for (int i = 0; i < points.length; i += 6) {
            k = 0;
            for (int j = 0; j < 6; j++) {
                temp[k] = points[i + k];
                k++;
            }
            dm.draw(bmp, temp, paint);
        }
    }
}
