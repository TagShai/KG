package ru.nevors.kg.DrawMethod.FillMethod.FillMethodPolygon;

import android.graphics.Bitmap;
import android.graphics.Color;

import java.util.ArrayList;
import java.util.Comparator;

import ru.nevors.kg.DrawMethod.DrawMethod;
import ru.nevors.kg.OBJ.Point;
import ru.nevors.kg.View.MyPaint.MyPaint;

public class FillMethodPolygon extends DrawMethod {
    @Override
    public void draw(Bitmap bmp, int[] points, MyPaint paint) {
        int color = paint.getColor();
        int color2 = paint.getColor2();
        int a = Color.alpha(color);
        float r = Color.red(color), g = Color.green(color), b = Color.blue(color);
        float r2 = Color.red(color2), g2 = Color.green(color2), b2 = Color.blue(color2);

        Edge[] edges = new Edge[points.length / 2];
        int k = 0;
        int minY = points[1];
        int maxY = minY;
        for (int i = 0; i < points.length; i += 2) {
            if (points[i + 1] < minY) {
                minY = points[i + 1];
            } else {
                if (points[i + 1] > maxY) {
                    maxY = points[i + 1];
                }
            }

            int j = (i + 2) % points.length;
            if (points[i + 1] > points[j + 1]) {
                edges[k++] = new Edge(points[i], points[i + 1], points[j], points[j + 1]);
            } else {
                edges[k++] = new Edge(points[j], points[j + 1], points[i], points[i + 1]);
            }
        }
        int dy = maxY - minY;
        float dr = (r2 - r) / dy;
        float dg = (g2 - g) / dy;
        float db = (b2 - b) / dy;

        sort(edges);
        int[] xs = new int[points.length];
        int iXs = 0;
        int start = 0, end = 0;
        for (int i = maxY; i >= minY; i--) {
            while (end < edges.length && edges[end].getStartY() >= i) {
                end++;
            }

            iXs = 0;
            for (int j = start; j < end; j++) {
                if (edges[j].isNextY()) {
                    xs[iXs++] = edges[j].getX();
                    edges[j].nextY();
                }
            }
            sort(xs, iXs);

            for (int j = 0; j < iXs; j += 2) {
                for (int h = xs[j]; h <= xs[j + 1]; h++) {
                    if (checkLimits(bmp, h, i)) {
                        bmp.setPixel(h, i, Color.argb(a,(int) r, (int) g, (int) b));
                    }
                }
            }

            r += dr;
            g += dg;
            b += db;
        }

    }

    private Edge[] sort(Edge[] points) {
        int ii, jj, ll;
        Edge minY, kk;
        for (ii = 0; ii < points.length; ii++) {
            minY = points[ll = ii];
            for (jj = ii + 1; jj < points.length; jj++)
                if ((kk = points[jj]).getStartY() > minY.getStartY()) {
                    ll = jj;
                    minY = kk;
                }
            if (ll != ii) {
                points[ll] = points[ii];
                points[ii] = minY;
            }
        }
        return points;
    }

    private int[] sort(int[] points, int n) {
        int ii, jj, ll, minY, kk;
        for (ii = 0; ii < n; ii++) {
            minY = points[ll = ii];
            for (jj = ii + 1; jj < n; jj++)
                if ((kk = points[jj]) < minY) {
                    ll = jj;
                    minY = kk;
                }
            if (ll != ii) {
                points[ll] = points[ii];
                points[ii] = minY;
            }
        }
        return points;
    }
}
