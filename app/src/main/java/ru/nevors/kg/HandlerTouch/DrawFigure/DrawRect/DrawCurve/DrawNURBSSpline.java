package ru.nevors.kg.HandlerTouch.DrawFigure.DrawRect.DrawCurve;

import android.graphics.Bitmap;

import ru.nevors.kg.HandlerTouch.DrawFigure.DrawFigure;
import ru.nevors.kg.DrawMethod.DrawMethod;
import ru.nevors.kg.DrawMethod.DrawMethodCurve.DrawMethodNURBSSpline;
import ru.nevors.kg.View.PD2;

public class DrawNURBSSpline extends DrawBezier {

    int _rank;

    public DrawNURBSSpline(PD2 pd, int rank) {
        super(pd, new DrawMethodNURBSSpline(rank));
        _rank = rank;
    }

    protected DrawNURBSSpline(PD2 pd, DrawMethod dm, int[] points, boolean up, int rank) {
        super(pd, dm, points, up);
        _rank = rank;
    }

    @Override
    protected void actionDown() {
        if (_up) {
            _pd.clearPointBuf();
            for (int i = 0; i < _points.length; i += 3) {
                if (Math.abs(_points[i] - _x) < _accuracy && Math.abs(_points[i + 1] - _y) < _accuracy) {
                    _k = i;
                    return;
                }
            }
            int xmc = _x - _accuracy;
            int xpc = _x + _accuracy;
            int ymc = _y - _accuracy;
            int ypc = _y + _accuracy;
            Bitmap bmp = _pd.getBufBitMap();
            for (int i = xmc; i < xpc; i++) {
                for (int j = ymc; j < ypc; j++) {
                    try {
                        if (bmp.getPixel(i, j) != 0) {
                            int[] points = new int[_points.length + 3];
                            int n = _points.length - 3;
                            float min;
                            int iMin = 0;
                            int g = 0;
                            min = distance(_x, _y, _points[g + 3], _points[g + 4]);
                            min += distance(_x, _y, _points[g], _points[g + 1]);
                            min /= distance(_points[g], _points[g + 1], _points[g + 3], _points[g + 4]);
                            g += 3;
                            while (g < n) {
                                float tMin = distance(_x, _y, _points[g + 3], _points[g + 4]);
                                tMin += distance(_x, _y, _points[g], _points[g + 1]);
                                tMin /= distance(_points[g], _points[g + 1], _points[g + 3], _points[g + 4]);
                                if (min > tMin) {
                                    min = tMin;
                                    iMin = g;
                                }
                                g += 3;
                            }
                            int k = 0;
                            while (k < iMin + 3) {
                                points[k] = _points[k];
                                k++;
                            }
                            points[k++] = _x;
                            points[k++] = _y;
                            while (k < points.length) {
                                points[k] = _points[k - 3];
                                k++;
                            }
                            _points = points;
                            _k = iMin + 3;
                            draw(this);
                            return;
                        }
                    } catch (Exception e) {
                    }
                }
            }
            update();
        }
        _pd.activateModify();
        _points = new int[(_rank + 1) * 3];
        for (int i = 0; i < _points.length; i += 3) {
            _points[i] = _x;
            _points[i + 1] = _y;
        }
        _k = _rank * 3;
    }

    @Override
    public DrawFigure clone() {
        return new DrawNURBSSpline(_pd, _dm, _points.clone(), true, _rank);
    }

    @Override
    protected void drawManagement() {
        for (int i = 0; i < _points.length; i += 3) {
            _pd.addPointBuf(_points[i], _points[i + 1], _accuracy);
        }
        for (int i = 0; i < _points.length - 3; i += 3) {
            _pd.addLineBuf(_points[i], _points[i + 1], _points[i + 3], _points[i + 4]);
        }
    }
}
