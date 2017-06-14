package ru.nevors.kg.DrawMethod.DrawMethodObj3D;

import android.graphics.Bitmap;

import ru.nevors.kg.DrawMethod.DrawMethod;
import ru.nevors.kg.View.MyPaint.MyPaint;

public class DrawMethodObj3D extends DrawMethod {
    IManagerPixel mp;
    DrawMethod dm ;
    public DrawMethodObj3D(IManagerPixel mp){
        this.mp = mp;
        dm = new FillMethodTriangle3D(mp);
    }

    public void draw(Bitmap bmp, int[] points, MyPaint paint) {
        mp.clearBufer();
        int temp[] = new int[9];
        int k;
        for (int i = 0; i < points.length; i += 9) {
            k = 0;
            for (int j = 0; j < 9; j++) {
                temp[k] = points[i + k];
                k++;
            }
            dm.draw(bmp, temp, paint);
        }
    }
}
