package ru.nevors.kg.DrawMethod.DrawMethodCurve;

import android.graphics.Bitmap;

import ru.nevors.kg.DrawMethod.DrawMethod;
import ru.nevors.kg.View.MyPaint.MyPaint;

public class DrawMethodErmit extends DrawMethod {
    @Override
    public void draw(Bitmap bmp, int[] points, MyPaint paint) {
        int color = paint.getColor();
        float step = .001F;
        float t;
        int x, y;
        for (int i = 0; i < points.length - 4; i += 4) {
            int xv1,yv1,xv2,yv2;
            xv1 = 3*(points[i + 2] - points[i]);
            xv2 = 3*(points[i + 6] - points[4]);
            yv1 = 3*(points[i + 3] - points[i + 1]);
            yv2 = 3*(points[i + 7] - points[i + 5]);
            t = .0F;
            while (t <= 1) {
                x = (int) ((1 - 3 * t * t + 2 * t * t * t) * points[i]
                        + t * t * (3 - 2 * t) * points[i + 4]
                        + t * (1 - 2 * t + t * t) * xv1
                        - t * t * (1 - t) * xv2);

                y = (int) ((1 - 3 * t * t + 2 * t * t * t) * points[i + 1]
                        + t * t * (3 - 2 * t) * points[i + 5]
                        + t * (1 - 2 * t + t * t) * yv1
                        - t * t * (1 - t) * yv2);
                try {
                    bmp.setPixel(x, y, color);
                } catch (Exception e) {
                }

                t += step;
            }
        }
    }
}
