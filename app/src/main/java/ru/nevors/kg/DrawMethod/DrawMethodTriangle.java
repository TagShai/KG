package ru.nevors.kg.DrawMethod;

import android.graphics.Bitmap;

import ru.nevors.kg.DrawMethod.DrawMethodLine.DrawMethodLineBRH;
import ru.nevors.kg.View.MyPaint.MyPaint;

public class DrawMethodTriangle extends DrawMethod {
    DrawMethod dm = new DrawMethodLineBRH();
    @Override
    public void draw(Bitmap bmp, int[] points, MyPaint paint) {
        int[]temp = new int[4];
        for(int i = 0;i<points.length;i+=2){
            int k = 0;
            temp[k++] = points[i];
            temp[k++] = points[i + 1];

            int j = (i + 2) % points.length;
            temp[k++] = points[j];
            temp[k++] = points[j + 1];
            dm.draw(bmp,temp,paint);
        }
    }
}
