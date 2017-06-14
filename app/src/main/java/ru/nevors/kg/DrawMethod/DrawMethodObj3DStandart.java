package ru.nevors.kg.DrawMethod;

import android.graphics.Bitmap;

import ru.nevors.kg.View.MyPaint.MyPaint;

public class DrawMethodObj3DStandart extends DrawMethod {
    DrawMethod dm = new DrawMethodTriangle();

    @Override
    public void draw(Bitmap bmp, int[] points, MyPaint paint) {
        int temp[] = new int[6];
        int k, h;
        for (int i = 0; i < points.length; i += 9) {
            k = 0;
            h = 0;
            temp[k++] = points[i + h++];
            temp[k++] = points[i + h++];
            h++;
            temp[k++] = points[i + h++];
            temp[k++] = points[i + h++];
            h++;
            temp[k++] = points[i + h++];
            temp[k++] = points[i + h++];
            dm.draw(bmp, temp, paint);
        }
    }
}
