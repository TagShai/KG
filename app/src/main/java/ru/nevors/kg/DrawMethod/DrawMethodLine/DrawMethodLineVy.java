package ru.nevors.kg.DrawMethod.DrawMethodLine;

import android.graphics.Bitmap;
import android.graphics.Color;

import ru.nevors.kg.DrawMethod.DrawMethod;
import ru.nevors.kg.View.MyPaint.MyPaint;

public class DrawMethodLineVy extends DrawMethod {
    @Override
    public void draw(Bitmap bmp, int[] points, MyPaint paint) {
        int color = paint.getColor();
        if (color == 0) {
            bmp.eraseColor(0);
            return;
        }
        int x1, y1, x2, y2;
        x1 = points[0];
        y1 = points[1];
        x2 = points[2];
        y2 = points[3];
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        //Горизонтальные и вертикальные линии не нуждаются в сглаживании
        if (dx == 0) {
            while (y1 < y2) setPixel(bmp, x1, y1++, color,255);
            return;
        }
        if (dy == 0) {
            while (x1 < x2) setPixel(bmp, x1++, y1, color,255);
            return;
        }
        float gradient = 0;
        if (dx > dy) {
            gradient = (float) dy / dx;

            if (x2 < x1){
                x2 += x1;
                x1 = x2 - x1;
                x2 -= x1;
                y2 += y1;
                y1 = y2 - y1;
                y2 -= y1;
            }
            if(y2 < y1)
                gradient=-gradient;

            float intery = y1 + gradient;
            setPixel(bmp, x1, y1, color,255);
            for (int x = x1; x < x2; ++x) {
                setPixel(bmp, x, (int) intery, color, 255 - fpart(intery) * 255);
                setPixel(bmp, x, (int) intery + 1, color, fpart(intery) * 255);
                intery += gradient;
            }
            setPixel(bmp, x2, y2, color,255);
        } else {
            gradient = (float) dx / dy;

            if (y2 < y1){
                x2 += x1;
                x1 = x2 - x1;
                x2 -= x1;
                y2 += y1;
                y1 = y2 - y1;
                y2 -= y1;
            }
            if(x2 < x1)
                gradient=-gradient;

            float interx = x1 + gradient;
            setPixel(bmp, x1, y1, color,255);
            for (int y = y1; y < y2; ++y) {
                setPixel(bmp, (int) interx, y, color, 255 - fpart(interx) * 255);
                setPixel(bmp, (int) interx + 1, y, color, fpart(interx) * 255);
                interx += gradient;
            }
            setPixel(bmp, x2, y2, color,255);
        }

    }

    private float fpart(float x) {
        int tmp = (int) x;
        return x - tmp; //вернёт дробную часть числа
    }

    private void setPixel(Bitmap bmp, int x, int y, int color, float grad) {
        if (checkLimits(bmp, x, y)) {
            bmp.setPixel(x, y,
                    Color.argb(
                            (int) grad,
                            Color.red(color),
                            Color.green(color),
                            Color.blue(color)
                    )
            );
        }
    }
}
