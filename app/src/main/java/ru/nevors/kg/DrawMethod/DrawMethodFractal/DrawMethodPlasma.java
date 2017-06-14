package ru.nevors.kg.DrawMethod.DrawMethodFractal;

import android.graphics.Bitmap;
import android.graphics.Color;

import java.util.Random;

import ru.nevors.kg.DrawMethod.DrawMethod;
import ru.nevors.kg.View.MyPaint.MyPaint;

public class DrawMethodPlasma extends DrawMethod {
    int width, height;

    public DrawMethodPlasma(int width, int height) {
        this.width = width;
        this.height = height;
    }

    Random random = new Random();

    @Override
    public void draw(Bitmap bmp, int[] points, MyPaint paint) {
        float c1 = random.nextFloat();
        float c2 = random.nextFloat();
        float c3 = random.nextFloat();
        float c4 = random.nextFloat();
        draw(bmp, points[0] - width/ 2, points[1] - height / 2, width / 2, height / 2, height / 2, 1, c1, c2, c3, c4);
    }

    public void draw(Bitmap bmp, int cx, int cy, float x, float y, float size, float stddev, float c1, float c2, float c3, float c4) {

        if (size < 0.2) {
            return;
        }
        float cM = ((c1 + c2 + c3 + c4) / 4.0F + stddev * random.nextFloat());
        int color = Color.HSVToColor(new float[]{(cM * 360), 0.8f, 0.8f});
        setPixel(bmp, (int) x + cx, (int) y + cy, color);

        float cT = ((c1 + c2) / 2.0F);    // top
        float cB = ((c3 + c4) / 2.0F);    // bottom
        float cL = ((c1 + c3) / 2.0F);    // left
        float cR = ((c2 + c4) / 2.0F);    // right

        draw(bmp, cx, cy, x - size / 2, y - size / 2, size / 2, stddev / 2, cL, cM, c3, cB);
        draw(bmp, cx, cy, x + size / 2, y - size / 2, size / 2, stddev / 2, cM, cR, cB, c4);
        draw(bmp, cx, cy, x - size / 2, y + size / 2, size / 2, stddev / 2, c1, cT, cL, cM);
        draw(bmp, cx, cy, x + size / 2, y + size / 2, size / 2, stddev / 2, cT, c2, cM, cR);
    }
}
