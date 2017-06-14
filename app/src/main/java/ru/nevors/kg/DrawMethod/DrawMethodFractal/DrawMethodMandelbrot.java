package ru.nevors.kg.DrawMethod.DrawMethodFractal;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PointF;

import ru.nevors.kg.DrawMethod.DrawMethod;
import ru.nevors.kg.View.MyPaint.MyPaint;

public class DrawMethodMandelbrot extends DrawMethod {
    float startX;
    float startY;
    private int iter;
    private float zoom;
    private int width;
    private int height;

    public DrawMethodMandelbrot(int width, int height, int iter, float zoom) {
        this.iter = iter;
        this.zoom = zoom;
        startX = 1;
        startY = 1;

        this.height = height;
        this.width = width;
    }

    @Override
    public void draw(Bitmap bmp, int[] points, MyPaint paint) {
        int w2 = points[0] - width / 2;
        int h2 = points[1] - height / 2;
        startX = (1 / width / startX) - (zoom * width / 2);
        startY = (1 / height / startY) - (zoom * height / 2);
        PointF c = new PointF();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int n = 0;
                c.x = x * zoom + startX;
                c.y = y * zoom + startY;
                PointF z = new PointF(0, 0);
                while (z.x * z.x + z.y * z.y < 4 && n < iter) {
                    PointF t = new PointF(z.x, z.y);
                    z.x = t.x * t.x - t.y * t.y + c.x;
                    z.y = 2 * t.x * t.y + c.y;
                    n++;
                }
                if (n < iter) {
                    double r = (0.1 + n * 0.06) * 100; //расчет
                    double g = (0.2 + n * 0.09) * 100; //значений
                    double b = (0.3 + n * 0.03) * 100; //для раскраски множества
                    r = (r) % 255;
                    g = (g) % 255;
                    b = (b) % 255;
                    setPixel(bmp, y + w2, x + h2, Color.rgb((int) (r), (int) (g), (int) (b)));

                }
            }
        }
    }
}
