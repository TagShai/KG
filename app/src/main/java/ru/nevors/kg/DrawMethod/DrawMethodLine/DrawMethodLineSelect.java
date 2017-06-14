package ru.nevors.kg.DrawMethod.DrawMethodLine;

import android.graphics.Bitmap;
import android.graphics.Color;

import ru.nevors.kg.DrawMethod.DrawMethod;
import ru.nevors.kg.View.MyPaint.MyPaint;

public class DrawMethodLineSelect extends DrawMethod {
    Bitmap buf;
    DrawMethodLineBRH brh;

    public DrawMethodLineSelect() {
        brh = new DrawMethodLineBRH();
    }

    @Override
    public void draw(Bitmap bmp, int[] points, MyPaint paint) {
        if (buf == null || (bmp.getHeight() != buf.getHeight() * 2 || bmp.getWidth() != buf.getWidth() * 2)) {
            buf = Bitmap.createBitmap(bmp.getWidth() * 2, bmp.getHeight() * 2, Bitmap.Config.ARGB_8888);
        }
        int color = paint.getColor();
        brh.draw(buf, new int[]{points[0] * 2, points[1] * 2, points[2] * 2, points[3] * 2}, paint);
        int x1, y1, x2, y2;
        if (points[2] > points[0]) {
            x1 = points[0];
            x2 = points[2];
        } else {
            x1 = points[2];
            x2 = points[0];
        }
        if (points[3] > points[1]) {
            y1 = points[1];
            y2 = points[3];
        } else {
            y1 = points[3];
            y2 = points[1];
        }
        for (int i = x1; i <= x2; i++) {
            for (int j = y1; j <= y2; j++) {
                setPixel(buf, bmp, i, j,color);
            }
        }
    }

    private void setPixel(Bitmap buf, Bitmap bitmap, int x, int y, int color) {
        int bufX = x * 2;
        int bufY = y * 2;
        int alpha = (
                Color.alpha(getPixel(buf, bufX, bufY))
                        + Color.alpha(getPixel(buf, bufX, bufY + 1))
                        + Color.alpha(getPixel(buf, bufX + 1, bufY))
                        + Color.alpha(getPixel(buf, bufX + 1, bufY + 1))
        ) / 4;
        setPixel(bitmap, x, y, color, alpha);
    }

    private int getPixel(Bitmap bmp, int x, int y) {
        if (checkLimits(bmp, x, y)) {
            return bmp.getPixel(x, y);
        }
        return 0;
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
