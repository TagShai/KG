package ru.nevors.kg.DrawMethod.FillMethod;

import android.graphics.Bitmap;

import ru.nevors.kg.DrawMethod.DrawMethodCircle.DrawMethodCircle;
import ru.nevors.kg.View.MyPaint.MyPaint;

public class FillMethodCircle extends DrawMethodCircle {

    @Override
    public void draw(Bitmap bmp, int[] points, MyPaint paint) {
        int color = paint.getColor();
        int color2 = paint.getColor2();
        int r = (int) Math.sqrt((points[2] - points[0]) * (points[2] - points[0]) + (points[3] - points[1]) * (points[3] - points[1]));
        int x = 0, y = r, f = 1 - r;
        fill4Lines(bmp, points[0], points[1], x, y, color2);
        while (x <= y) {
            if (f > 0) {
                y--;
                f += 2 * (x - y) + 5;
            } else {
                f += 2 * x + 3;
            }
            x++;
            fill4Lines(bmp, points[0], points[1], x, y, color2);
        }

        x = 0;
        y = r;
        f = 1 - r;
        _setPixel8(bmp, points[0], points[1], x, y, color);
        while (x <= y) {
            if (f > 0) {
                y--;
                f += 2 * (x - y) + 5;
            } else {
                f += 2 * x + 3;
            }
            x++;
            _setPixel8(bmp, points[0], points[1], x, y, color);
        }
    }

    private void fillLine(Bitmap bmp, int x1, int x2, int y, int color) {
        for (int i = x1 + 1; i < x2; i++) {
            if (checkLimits(bmp, i, y)) {
                bmp.setPixel(i, y, color);
            }
        }
    }

    private void fill4Lines(Bitmap bmp, int x0, int y0, int x, int y, int color) {
        fillLine(bmp, x0 - x, x0 + x, y0 + y, color);

        fillLine(bmp, x0 - y, x0 + y, y0 + x, color);

        fillLine(bmp, x0 - y, x0 + y, y0 - x, color);

        fillLine(bmp, x0 - x, x0 + x, y0 - y, color);
    }
}
