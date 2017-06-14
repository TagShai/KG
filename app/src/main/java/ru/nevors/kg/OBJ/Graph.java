package ru.nevors.kg.OBJ;

import ru.nevors.kg.HandlerTouch.DrawFigure.Draw3D.Draw3D;
import ru.nevors.kg.View.PD2;

public class Graph {
    PD2 _pd;
    Draw3D draw3D;

    public Graph(PD2 pd, Draw3D draw3D) {
        _pd = pd;
        this.draw3D = draw3D;
    }

    int rw, lw, uh, dh, lz, rz;
    float[] temp = new float[9];
    int step;

    public void onSet(int width, int height) {
        rw = width / 2;
        lw = -rw;
        uh = height / 2;
        dh = -uh;
        lz = -height;
        rz = height;
        step = (int) _pd.getScale() * 2;

        type(new Point(lw, dh, lz), 1);
    }

    void type(Point last, int way) {
        if (checkPoint(last)) {
            float x, y;
            x = last.x;
            y = last.y;
            Point p1 = new Point(x, y, func(x, y));
            x += step;
            Point p2 = new Point(x, y, func(x, y));
            y += step;
            Point p3 = new Point(x, y, func(x, y));
            x -= step;
            Point p4 = new Point(x, y, func(x, y));

            addPoints(p4, p1, p2);
            addPoints(p2, p3, p4);

            switch (way) {
                case 1:
                    type(p3, 1);
                    type(p4, 2);
                    type(p2, 3);
                    break;
                case 2:
                    type(p4, 2);
                    break;
                case 3:
                    type(p2, 3);
                    break;
            }
        }
    }

    boolean checkPoint(Point p) {
        return p.x <= rw && p.x >= lw
                && p.y <= uh && p.y >= dh;
    }

    boolean checkPointZ(Point p) {
        return p.x <= rw && p.x >= lw
                && p.y <= uh && p.y >= dh
                && p.z <= rz && p.z >= lz;
    }

    void addPoints(Point p1, Point p2, Point p3) {
        if (checkPointZ(p1) && checkPointZ(p2) && checkPointZ(p3)) {
            int k = 0;
            temp[k++] = p1.x;
            temp[k++] = p1.y;
            temp[k++] = p1.z;

            temp[k++] = p2.x;
            temp[k++] = p2.y;
            temp[k++] = p2.z;

            temp[k++] = p3.x;
            temp[k++] = p3.y;
            temp[k++] = p3.z;

            draw3D.addPoints(temp);
        }
    }

    float func(float x, float y) {
        return (x*x / 50 + y*y / 50);
        //return (float) (Math.sin(x)*Math.cos(y)-(1.5F * Math.cos(y) * Math.exp(-y)));
    }
}
