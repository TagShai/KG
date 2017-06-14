package ru.nevors.kg.DrawMethod.DrawMethodObj3D;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import ru.nevors.kg.DrawMethod.DrawMethod;
import ru.nevors.kg.DrawMethod.DrawMethodLine.DrawMethodLineBRH;
import ru.nevors.kg.OBJ.Point;
import ru.nevors.kg.View.MyPaint.MyPaint;

public class DrawMethodGraph extends DrawMethod {
    int w = 100;
    int buffer[] = new int[100];

    ArrayList<Point> list = new ArrayList<>();
    Compare my = new Compare();

    @Override
    public void draw(Bitmap bmp, int[] points, MyPaint paint) {

        int color = paint.getColor();
        list = new ArrayList<>();
        for (int i = 0; i < points.length; ) {
            Point p = new Point();
            p.x = points[i++];
            p.y = points[i++];
            p.z = points[i++];
            list.add(p);
        }
        Collections.sort(list, my);

        for(int i=0;i<buffer.length;i++){
            buffer[i] = Integer.MIN_VALUE;
        }

        int width = bmp.getWidth();
        int height = bmp.getHeight();
        for (Point p : list) {
            int x = (int) p.x;
            int y = (int )p.y;
            if (x >= 0 && x < width && y >= 0 && y < height) {
                if (height != w) {
                    buffer = new int[width];
                    w = width;
                }
                if (buffer[x] < y) {
                    buffer[x] = y;
                    bmp.setPixel(x, y, color);
                }
            }
        }
    }

    class Compare implements Comparator<Point> {
        @Override
        public int compare(Point o1, Point o2) {
            int dz = (int) (o1.z - o2.z);
            if (dz == 0) {
                return (int) (o1.x - o2.x);
            }
            return dz;
        }
    }
}
