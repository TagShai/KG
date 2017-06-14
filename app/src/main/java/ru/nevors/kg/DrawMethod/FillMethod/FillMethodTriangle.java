package ru.nevors.kg.DrawMethod.FillMethod;

import android.graphics.Bitmap;

import ru.nevors.kg.DrawMethod.DrawMethod;
import ru.nevors.kg.DrawMethod.DrawMethodLine.DrawMethodLineBRH;
import ru.nevors.kg.View.MyPaint.MyPaint;

public class FillMethodTriangle extends DrawMethod {
    DrawMethod dm = new DrawMethodLineBRH();

    @Override
    public void draw(Bitmap bmp, int[] points, MyPaint paint) {
        points = points.clone();
        int color = paint.getColor2();
        if (points[1] > points[3]) {
            swap(points, 0, 2);
            swap(points, 1, 3);
        }
        if (points[1] > points[5]) {
            swap(points, 0, 4);
            swap(points, 1, 5);
        }
        if (points[3] > points[5]) {
            swap(points, 2, 4);
            swap(points, 3, 5);
        }

        int total_height = points[5] - points[1];

        for (int i = 0; i < total_height; i++) {
            boolean second_half = i > points[3] - points[1] || points[3] == points[1];
            int segment_height = second_half ? points[5] - points[3] : points[3] - points[1];
            float alpha = (float) i / total_height;
            float beta = (float) (i - (second_half ? points[3] - points[1] : 0)) / segment_height; // be careful: with above conditions no division by zero here
            int A = (int) (points[0] + (points[4] - points[0]) * alpha);
            int B = second_half ? (int) (points[2] + (points[4] - points[2]) * beta) : (int) (points[0] + (points[2] - points[0]) * beta);
            if (A > B) {
                int t = A;
                A = B;
                B = t;
            }
            for (int j = A + 1; j <= B; j++) {
                setPixel(bmp, j, points[1] + i, color);
            }
        }

        int[] temp = new int[4];
        for (int i = 0; i < points.length; i += 2) {
            int k = 0;
            temp[k++] = points[i];
            temp[k++] = points[i + 1];

            int j = (i + 2) % points.length;
            temp[k++] = points[j];
            temp[k++] = points[j + 1];
            dm.draw(bmp, temp, paint);
        }
    }

    public void swap(int[] points, int i1, int i2) {
        int t = points[i1];
        points[i1] = points[i2];
        points[i2] = t;
    }
}
