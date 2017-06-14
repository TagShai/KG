package ru.nevors.kg.DrawMethod.DrawMethodCurve;

import android.graphics.Bitmap;

import ru.nevors.kg.DrawMethod.DrawMethod;
import ru.nevors.kg.View.MyPaint.MyPaint;

public class DrawMethodBezier extends DrawMethod {
    @Override
    public void draw(Bitmap bmp, int[] points, MyPaint paint) {
        int color = paint.getColor();
        float step = 0.001F;
        float r[] = new float[points.length];
        float t = step;
        while (t <= 1) {
            for (int i = 0; i < r.length; i++) {
                r[i] = points[i];
            }
            for (int i = r.length - 2; i >= 0; i -= 2) {
                for (int j = 0; j < i; j++) {
                    r[j] += t * (r[j + 2] - r[j]);
                    j++;
                    r[j] += t * (r[j + 2] - r[j]);
                }
            }
            //if (checkLimits(bmp, (int) r[0], (int) r[1]))
            try {
                bmp.setPixel((int) r[0], (int) r[1], color);
            }catch (Exception e){}
            t += step;
        }
    }
}
