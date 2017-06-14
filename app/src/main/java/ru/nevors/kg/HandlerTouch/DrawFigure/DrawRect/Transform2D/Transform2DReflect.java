package ru.nevors.kg.HandlerTouch.DrawFigure.DrawRect.Transform2D;

import android.graphics.Point;

import ru.nevors.kg.HandlerTouch.DrawFigure.DrawFigure;
import ru.nevors.kg.View.PD2;

public class Transform2DReflect extends Transform2D {
    DrawFigure clone;
    Point p1, p2;
    boolean isCompute = true;

    public Transform2DReflect(PD2 pd) {
        super(pd);
        p1 = new Point();
        p2 = new Point();
    }

    @Override
    protected void actionDown() {
        super.actionDown();
        drawManagement();
        if (isCompute) {
            computeBorder();
            p1.x = maxX;
            p1.y = minY;
            p2.x = maxX;
            p2.y = maxY;

            clone = df.clone();

            reflectClone();

            draw(df);
            draw(clone, false);

            isCompute = false;
        }
        if (Math.abs(p1.x - _x) < _accuracy && Math.abs(p1.y - _y) < _accuracy) {
            state = 3;
        } else {
            if (Math.abs(p2.x - _x) < _accuracy && Math.abs(p2.y - _y) < _accuracy) {
                state = 4;
            } else {
                state = 1;
            }
        }
    }

    private void reflectClone() {
        float a, b, c, d, e, f, x, y, x0, y0;
        //коэф прямой отражения
        a = p1.y - p2.y;
        b = p2.x - p1.x;
        c = p2.x * p1.y - p1.x * p2.y;//*-1

        float a2 = a*a;
        float b2 = b*b;
        float a2b2 = a2+b2;

        if(a==0)return;
        //перпендикуляр
        d = -b;
        e = a;

        int points[] = df.getPoints();
        int pointsC[] = clone.getPoints();
        for (int i = 0; i < points.length; i += 2) {
            x = points[i];
            y = points[i + 1];
            f = a * y - b * x;//*-1

            //точка пересечения перпендикуляра и прямой
            y0 = (-c * d + f * a) / (-b * d + e * a);
            x0 = (c - b * y0) / a;

            //симметричная точка
            pointsC[i] = (int)(2 * x0 - x);
            pointsC[i + 1] = (int)(2 * y0 - y);

            //тоже работает нужно умножить вектор С на -1
            //pointsC[i] = (int)(points[i] * ((b2 - a2) / a2b2) + points[i + 1] * (-2 * a * b / a2b2) + (-2 * a * c / a2b2));
            //pointsC[i + 1] = (int)(points[i] * (-2 * a * b / a2b2) + points[i + 1] * ((a2 - b2) / a2b2) + (-2 * b * c / a2b2));
        }
    }

    @Override
    public void update() {
        if (_pd.deActivateModify()) {
            draw(df);
            draw(clone);
        }
    }

    @Override
    protected void actionMove() {
        switch (state) {
            case 3:
                p1.x = _x;
                p1.y = _y;
                break;
            case 4:
                p2.x = _x;
                p2.y = _y;
                break;
        }
        reflectClone();
        draw(df);
        draw(clone, false);
        drawManagement();
    }

    @Override
    protected int[] actionPoint(int[] points, Point start, Point cur) {
        return null;
    }

    @Override
    protected void actionUp() {
        state = 1;
        draw(df);
        draw(clone, false);
        drawManagement();
    }

    @Override
    protected void drawManagement() {
        _pd.clearPointBuf();
        _pd.addPointBuf(p1.x, p1.y, _accuracy);
        _pd.addPointBuf(p2.x, p2.y, _accuracy);
        _pd.addLineBuf(p1.x, p1.y, p2.x, p2.y);
    }
}
