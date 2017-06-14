package ru.nevors.kg.DrawMethod.DrawMethodObj3D;

import android.graphics.Bitmap;

import ru.nevors.kg.DrawMethod.DrawMethod;
import ru.nevors.kg.OBJ.Point;
import ru.nevors.kg.View.MyPaint.MyPaint;

class FillMethodTriangle3D extends DrawMethod {
    IManagerPixel mp;

    Point t0 = new Point();
    Point t1 = new Point();
    Point t2 = new Point();
    Point A = new Point();
    Point B = new Point();
    Point P = new Point();
    Point lastA = new Point();
    Point lastB = new Point();

    public FillMethodTriangle3D(IManagerPixel mp){
        this.mp = mp;
    }

    public void draw(Bitmap bmp, int[] points, MyPaint paint) {
        int color = paint.getColor2();
        int color2 = paint.getColor();
        int k = 0;
        t0.set(points[k++], points[k++], points[k++]);
        t1.set(points[k++], points[k++], points[k++]);
        t2.set(points[k++], points[k++], points[k++]);

        if (t0.y == t1.y && t0.y == t2.y) return; // i dont care about degenerate triangles
        if (t0.y > t1.y) {
            Point buf = t0;
            t0 = t1;
            t1 = buf;
        }
        if (t0.y > t2.y) {
            Point buf = t0;
            t0 = t2;
            t2 = buf;
        }
        if (t1.y > t2.y) {
            Point buf = t2;
            t2 = t1;
            t1 = buf;
        }
        int total_height = (int) (t2.y - t0.y);

        int z;
        int y = (int) t0.y;

        boolean isFirs = true;
        for (int i = 0; i < total_height; i++) {
            boolean second_half = i > t1.y - t0.y || t1.y == t0.y;
            float segment_height = second_half ? t2.y - t1.y : t1.y - t0.y;
            float alpha = (float) i / total_height;
            float beta = (i - (second_half ? t1.y - t0.y : 0)) / segment_height; // be careful: with above conditions no division by zero here

            diff(t2, t0, A);
            mul(A, alpha);
            add(t0, A, A);
            if (second_half) {
                diff(t2, t1, B);
                mul(B, beta);
                add(B, t1, B);
            } else {
                diff(t1, t0, B);
                mul(B, beta);
                add(B, t0, B);
            }
            if (A.x > B.x) {
                Point buf = A;
                A = B;
                B = buf;
            }

            if (isFirs || i + 1 == total_height) {
                lastA.set(A);
                lastB.set(B);
                isFirs = false;
                for (int j = (int) A.x; j <= B.x; j++) {
                    z = computeZ(A,B,j);
                    mp.setPixel(bmp, j, y + i, z, color2);
                }
            }else{
                Point l = A,lA = lastA,rA = lastB,r = B;
                if(A.x>lastA.x){
                    l = lastA;
                    lA = A;
                }
                if(lastB.x > B.x){
                    rA = B;
                    r = lastB;
                }
                for (int j = (int) l.x; j <= lA.x; j++) {
                    z = computeZ(l,lA,j);
                    mp.setPixel(bmp, j, y + i, z, color2);
                }

                for (int j = (int) lA.x + 1; j < (int)rA.x; j++) {
                    z = computeZ(lA,rA,j);
                    mp.setPixel(bmp, j, y + i, z, color);
                }

                for (int j = (int) rA.x; j <= r.x; j++) {
                    z = computeZ(rA,r,j);
                    mp.setPixel(bmp, j, y + i, z, color2);
                }
            }
            lastA.set(A);
            lastB.set(B);
        }
    }
    private int computeZ(Point A,Point B,int j){
        float phi = B.x == A.x ? 1.F : (j - A.x) / (B.x - A.x);
        return (int) (A.z + (B.z-A.z)*phi);
    }

    private void diff(Point p1, Point p2, Point result) {
        result.set(p1.x - p2.x, p1.y - p2.y, p1.z - p2.z);
    }

    private void add(Point p1, Point p2, Point result) {
        result.set(p1.x + p2.x, p1.y + p2.y, p1.z + p2.z);
    }

    private void mul(Point p1, float f) {
        p1.set(p1.x * f, p1.y * f, p1.z * f);
    }

    public void swap(int[] points, int i1, int i2) {
        int t = points[i1];
        points[i1] = points[i2];
        points[i2] = t;
    }
}
