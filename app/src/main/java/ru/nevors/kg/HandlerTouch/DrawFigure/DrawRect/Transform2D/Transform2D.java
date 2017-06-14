package ru.nevors.kg.HandlerTouch.DrawFigure.DrawRect.Transform2D;

import android.graphics.Point;

import ru.nevors.kg.HandlerTouch.DrawFigure.DrawFigure;
import ru.nevors.kg.HandlerTouch.DrawFigure.DrawRect.DrawRect;
import ru.nevors.kg.View.PD2;

public abstract class Transform2D extends DrawRect {
    DrawFigure df;
    int[]bufPoints;
    int state = 0;//0 select figure, 1 transform

    Point _start;

    public Transform2D(PD2 pd) {
        super(pd, null);
        _start = new Point();
    }

    @Override
    protected void actionDown() {
        _start.x = _x;
        _start.y = _y;
        _pd.clearPointBuf();
        if (state == 0) {
            DrawFigure df = _pd.getFigure(_x, _y, DrawFigure.getAccuracy());
            if (df != null) {
                _pd.activateModify();
                draw(df);
                this.df = df;
                bufPoints = df.getPoints();
                state = 1;
            }
        }
    }

    Point p = new Point();
    Point cur = new Point();

    @Override
    protected void actionMove() {
        if (state == 1) {
            cur.x = _x;
            cur.y = _y;

            df.setPoints(actionPoint(bufPoints, _start, cur));

            _start.x = _x;
            _start.y = _y;
        }
        draw(df);
    }

    protected abstract int[] actionPoint(int[] points, Point start, Point cur);

    @Override
    protected void actionUp() {
        draw(df);
        drawManagement();
    }

    int minX, minY, maxX, maxY;

    protected void computeBorder() {
        if (df != null) {
            int points[] = df.getPoints();

            minX = maxX = points[0];
            minY = maxY = points[1];
            for (int i = 2; i < points.length; i += 2) {
                if (points[i] < minX) {
                    minX = points[i];
                } else {
                    if (points[i] > maxX) {
                        maxX = points[i];
                    }
                }

                if (points[i + 1] < minY) {
                    minY = points[i + 1];
                } else {
                    if (points[i + 1] > maxY) {
                        maxY = points[i + 1];
                    }
                }
            }
        }
    }

    protected void drawManagement() {
        if (state == 1) {
            computeBorder();
            _pd.addLineBuf(minX, minY, maxX, minY);
            _pd.addLineBuf(minX, maxY, maxX, maxY);
            _pd.addLineBuf(maxX, minY, maxX, maxY);
            _pd.addLineBuf(minX, minY, minX, maxY);
        }
    }

    @Override
    public void update() {
        if (_pd.deActivateModify()) {
            draw(df);
        }
    }
}
