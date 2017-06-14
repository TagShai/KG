package ru.nevors.kg.DrawMethod.DrawMethodCurve;

import android.graphics.Bitmap;

import ru.nevors.kg.DrawMethod.DrawMethod;
import ru.nevors.kg.View.MyPaint.MyPaint;

public class DrawMethodBezierErmit extends DrawMethod {

    @Override
    public void draw(Bitmap bmp, int[] points, MyPaint paint) {
        DrawMethod b = new DrawMethodBezier();
        DrawMethod e = new DrawMethodErmit();

        b.draw(bmp,new int[]{100,100,100,0,150,200,150,100},paint);
        e.draw(bmp,new int[]{100,100,100,0,150,100,150,0},paint);
    }
}
