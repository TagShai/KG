package ru.nevors.kg.DrawMethod.TrimMethod;

import android.graphics.Bitmap;
import android.graphics.Point;

import java.util.ArrayList;

import ru.nevors.kg.DrawMethod.DrawMethod;
import ru.nevors.kg.DrawMethod.DrawMethodLine.DrawMethodLineBRH;
import ru.nevors.kg.DrawMethod.DrawMethodRectangle;
import ru.nevors.kg.DrawMethod.FillMethod.FillMethodPolygon.FillMethodPolygon;
import ru.nevors.kg.View.MyPaint.MyPaint;

public class TrimMethodSH extends DrawMethod {

    @Override
    public void draw(Bitmap bmp, int[] points, MyPaint paint) {
        DrawMethodRectangle m = new DrawMethodRectangle();
        FillMethodPolygon polygon = new FillMethodPolygon();
        ArrayList<Point> list;
        int temp[] = new int[4];

        int k = 0;

        //прорисовка окна
        initMas(k, 4, 0, 4, temp, points);
        m.draw(bmp, temp, paint);

        k += 4;

        //многоугольников
        int numPoints;
        while (k < points.length) {
            numPoints = points[k++];
            int size = numPoints * 2;
            if (numPoints > 1) {
                list = new ArrayList<>(numPoints);
                for (int i = k; i < k + size; i += 2) {
                    list.add(new Point(points[i], points[i + 1]));
                }
                list = trimX(points[0], false, list);
                list = trimX(points[2], true, list);
                list = trimY(points[1], false, list);
                list = trimY(points[3], true, list);
                int result[] = new int[list.size() * 2];
                int h = 0;
                for (Point p : list) {
                    result[h++] = p.x;
                    result[h++] = p.y;
                }
                if (result.length != 0) {
                    polygon.draw(bmp, result, paint);
                }
            }
            k += size;
        }

    }

    private void initMas(int startPos, int size, int n, int m, int[] temp, int[] points) {
        for (int i = n, k = 0; i < m; i++, k++) {
            int var = (i - startPos) % size;
            temp[k] = points[startPos + var];
        }
    }

    private ArrayList<Point> trimX(int x, boolean isLeft, ArrayList<Point> list) {
        ArrayList<Point> result = new ArrayList<>();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            int pre = (i + size - 1) % size;
            Point p1 = list.get(pre);
            Point p2 = list.get(i);
            if (x < p1.x ^ isLeft) {// первая точка в окне
                if (x < p2.x ^ isLeft) {//вторая точка в окне
                    result.add(p2);
                } else {//вторая точка вне окна
                    Point p3 = new Point();
                    p3.x = x;
                    p3.y = p2.y + (int) ((x - p2.x) * ((float) (p1.y - p2.y) / (p1.x - p2.x)));
                    result.add(p3);
                }
            } else {// первая точка вне окна
                if (x < p2.x ^ isLeft) {//вторая точка в окне
                    Point p3 = new Point();
                    p3.x = x;
                    p3.y = p1.y + (int) ((x - p1.x) * ((float) (p2.y - p1.y) / (p2.x - p1.x)));
                    result.add(p3);
                    result.add(p2);
                }
            }
        }
        return result;
    }

    private ArrayList<Point> trimY(int y, boolean isTop, ArrayList<Point> list) {
        ArrayList<Point> result = new ArrayList<>();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            int pre = (i + size - 1) % size;
            Point p1 = list.get(pre);
            Point p2 = list.get(i);
            if (y < p1.y ^ isTop) {// первая точка в окне
                if (y < p2.y ^ isTop) {//вторая точка в окне
                    result.add(p2);
                } else {//вторая точка вне окна
                    Point p3 = new Point();
                    p3.y = y;
                    p3.x = p2.x + (int) ((y - p2.y) * ((float) (p1.x - p2.x) / (p1.y - p2.y)));
                    result.add(p3);
                }
            } else {// первая точка вне окна
                if (y < p2.y ^ isTop) {//вторая точка в окне
                    Point p3 = new Point();
                    p3.y = y;
                    p3.x = p1.x + (int) ((y - p1.y) * ((float) (p2.x - p1.x) / (p2.y - p1.y)));
                    result.add(p3);
                    result.add(p2);
                }
            }
        }
        return result;
    }
}
