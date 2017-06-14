package ru.nevors.kg.DrawMethod.DrawMethodCircle;

import android.graphics.Bitmap;

import ru.nevors.kg.DrawMethod.DrawMethod;

public abstract class DrawMethodCircle extends DrawMethod {

    protected void _setPixel8(Bitmap bmp, int x0, int y0, int x, int y, int color) {
        if (checkLimits(bmp, x0 + x, y0 + y)) bmp.setPixel(x0 + x, y0 + y, color);
        if (checkLimits(bmp, x0 - x, y0 + y)) bmp.setPixel(x0 - x, y0 + y, color);

        if (checkLimits(bmp, x0 + y, y0 + x)) bmp.setPixel(x0 + y, y0 + x, color);
        if (checkLimits(bmp, x0 - y, y0 + x)) bmp.setPixel(x0 - y, y0 + x, color);

        if (checkLimits(bmp, x0 + y, y0 - x)) bmp.setPixel(x0 + y, y0 - x, color);
        if (checkLimits(bmp, x0 - y, y0 - x)) bmp.setPixel(x0 - y, y0 - x, color);

        if (checkLimits(bmp, x0 + x, y0 - y)) bmp.setPixel(x0 + x, y0 - y, color);
        if (checkLimits(bmp, x0 - x, y0 - y)) bmp.setPixel(x0 - x, y0 - y, color);



    }
}
