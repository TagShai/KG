package ru.nevors.kg.DrawMethod.DrawMethodLine;

import android.graphics.Bitmap;
import android.util.Log;

import ru.nevors.kg.DrawMethod.DrawMethod;
import ru.nevors.kg.View.MyPaint.MyPaint;

public class DrawMethodLineBRH extends DrawMethod {
    @Override
    public void draw(Bitmap bmp, int[] points, MyPaint paint) {
        if ((points[2] - points[0] != 0 || points[3] - points[1] != 0)) {
            int color = paint.getColor();
            int x = points[0], y = points[1];
            int tx = points[2] - x, ty = points[3] - y;
            int dx, dy, s1 = 1, s2 = 1;
            if (tx < 0) {
                dx = -tx;
                s1 = -1;
            } else {
                if (tx == 0) {
                    s1 = 0;
                }
                dx = tx;
            }
            if (ty < 0) {
                dy = -ty;
                s2 = -1;
            } else {
                if (ty == 0) {
                    s2 = 0;
                }
                dy = ty;
            }

            boolean swap = false;
            if (dy > dx) {
                int temp = dx;
                dx = dy;
                dy = temp;
                swap = true;
            }
            int f = 2 * dy - dx, f1 = 2 * dy, f2 = 2 * dx;
            int i = 0;
            while (i <= dx) {
                setPixel(bmp,x, y, color);
                while (f >= 0) {
                    if (swap) {
                        x += s1;
                    } else {
                        y += s2;
                    }
                    f -= f2;
                }
                if (swap) {
                    y += s2;
                } else {
                    x += s1;
                }
                f += f1;
                i++;
            }
        }
    }
}
