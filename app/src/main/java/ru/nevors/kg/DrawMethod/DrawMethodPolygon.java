package ru.nevors.kg.DrawMethod;

import android.graphics.Bitmap;

import ru.nevors.kg.DrawMethod.DrawMethod;
import ru.nevors.kg.DrawMethod.DrawMethodLine.DrawMethodLineBRH;
import ru.nevors.kg.View.MyPaint.MyPaint;

public class DrawMethodPolygon extends DrawMethod {
    @Override
    public void draw(Bitmap bmp, int[] points, MyPaint paint) {
        DrawMethodLineBRH brh = new DrawMethodLineBRH();
        int temp[] = new int[4];
        for (int i = 0; i < points.length; i+=2) {
            int pre = (i + points.length - 2) % points.length;
            temp[0] = points[pre];
            temp[1] = points[pre + 1];
            temp[2] = points[i];
            temp[3] = points[i + 1];
            brh.draw(bmp, temp, paint);
        }
    }
}
