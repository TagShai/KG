package ru.nevors.kg.DrawMethod.DrawMethodObj3D;

import android.graphics.Bitmap;

import ru.nevors.kg.DrawMethod.DrawMethod;
import ru.nevors.kg.View.MyPaint.MyPaint;

class DrawMethodLineDda3D extends DrawMethod {
    IManagerPixel mp;

    public DrawMethodLineDda3D(IManagerPixel mp){
        this.mp = mp;
    }
    public void draw(Bitmap bmp, int[] points, MyPaint paint) {
        int color = paint.getColor();
        float x = points[0],
                y = points[1],
                z = points[2],
                dx = points[3] - points[0],
                dy = points[4] - points[1],
                dz = points[5] - points[2],
                N = Math.max(Math.abs(dx), Math.max(Math.abs(dz), Math.abs(dy))),
                t = 1 / N;
        float sx = dx * t, sy = dy * t,sz = dy * t;
        for (int i = 0; i <= N; i++) {
            mp.setPixel(bmp,(int)x,(int)y,(int)z,color);
            x += sx;
            y += sy;
            z += sz;
        }
    }
}
