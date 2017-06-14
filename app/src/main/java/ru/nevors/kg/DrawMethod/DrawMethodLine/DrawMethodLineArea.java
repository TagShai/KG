package ru.nevors.kg.DrawMethod.DrawMethodLine;

import android.graphics.Bitmap;
import android.graphics.Color;

import ru.nevors.kg.DrawMethod.DrawMethod;
import ru.nevors.kg.View.MyPaint.MyPaint;

public class DrawMethodLineArea extends DrawMethod {
    private static final int Pix_V= 255;
    @Override
    public void draw(Bitmap bmp, int[] points, MyPaint paint) {
        int color = paint.getColor();
        int xn, yn, xk, yk;
        xn = points[0];
        yn = points[1];
        xk = points[2];
        yk = points[3];
        int dx, dy, sx, sy, kl, swap;
        long incr1, incr2;
        long s;              /* Текущее значение ошибки  */
        long s_max;          /* Макс значение ошибки     */
        int color_tek;      /* Текущий номеp оттенка    */
        int xt;

/* Вычисление приращений и шагов */
        sx = 0;
        if ((dx = xk - xn) < 0) {
            dx = -dx;
            --sx;
        } else if (dx > 0) ++sx;
        sy = 0;
        if ((dy = yk - yn) < 0) {
            dy = -dy;
            --sy;
        } else if (dy > 0) ++sy;
/* Учет наклона */
        swap = 0;
        if ((kl = dx) < (s = dy)) {
            dx = (int)s;
            dy = kl;
            kl = (int)s;
            ++swap;
        }
        s = (long) dx * (long) Pix_V; /* Hачальное значение ошибки    */
        incr1 = 2l * (long) dy       /* Конст. перевычисления ошибки */
                * (long) Pix_V;   /* если текущее s < s_max       */
        incr2 = 2l * s;             /* Конст. перевычисления ошибки */
                            /* если текущее s >= s_max      */
        s_max = incr2 - incr1;    /* Максимальное значение ошибки */
        color_tek = Pix_V;        /* Яpкость стаpтового пиксела   */
        if (dx!=0) color_tek = (int) ((((long) Pix_V * (long) dy) / (long) dx));

        setPixel(bmp,xn, yn, color,color_tek);      /* 1-й пиксел */
        while (--kl >= 0) {
            if (s >= s_max) {
                if (swap!=0) xn += sx;
                else yn += sy;
                s -= incr2;
            }
            if (swap!=0) yn += sy;
            else xn += sx;
            s += incr1;
            color_tek = Pix_V;
            if (dx!=0) color_tek = (int)(s / dx / 2);
            setPixel(bmp,xn, yn, color,color_tek);     /* Тек.пиксел */
/* Однотонная закраска строки многоугольника макс цветом  */
            xt = xn;
            while (++xt <= xk) setPixel(bmp,xt, yn, color,Pix_V);
        }

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
