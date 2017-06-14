package ru.nevors.kg.HandlerTouch.DrawFigure.DrawRect.DrawCurve;

import android.graphics.Bitmap;

import ru.nevors.kg.HandlerTouch.DrawFigure.DrawFigure;
import ru.nevors.kg.DrawMethod.DrawMethod;
import ru.nevors.kg.DrawMethod.DrawMethodCurve.DrawMethodBSpline;
import ru.nevors.kg.View.PD2;

public class DrawBSpline extends DrawBezier {

    int _rank;

    public DrawBSpline(PD2 pd, int rank) {
        super(pd, new DrawMethodBSpline(rank));
        _rank = rank;
    }

    protected DrawBSpline(PD2 pd, DrawMethod dm, int[] points, boolean up, int rank) {
        super(pd, dm, points, up);
        _rank = rank;
    }

    @Override
    protected void actionDown() {
        if (_up) {
            _pd.clearPointBuf();
            for (int i = 0; i < _points.length; i += 2) {
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
                            int[] points = new int[_points.length + 2];
                            int n = _points.length - 2;
                            float min;
                            int iMin = 0;
                            int g = 0;
                            min = distance(_x, _y, _points[g + 2], _points[g + 3]);
                            min += distance(_x, _y, _points[g], _points[g + 1]);
                            min /= distance(_points[g], _points[g + 1], _points[g + 2], _points[g + 3]);
                            g += 2;
                            while (g < n) {
                                float tMin = distance(_x, _y, _points[g + 2], _points[g + 3]);
                                tMin += distance(_x, _y, _points[g], _points[g + 1]);
                                tMin /= distance(_points[g], _points[g + 1], _points[g + 2], _points[g + 3]);
                                if (min > tMin) {
                                    min = tMin;
                                    iMin = g;
                                }
                                g += 2;
                            }
                            int k = 0;
                            while (k < iMin + 2) {
                                points[k] = _points[k];
                                k++;
                            }
                            points[k++] = _x;
                            points[k++] = _y;
                            while (k < points.length) {
                                points[k] = _points[k - 2];
                                k++;
                            }
                            _points = points;
                            _k = iMin + 2;
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
        _points = new int[(_rank + 1) * 2];
        for (int i = 0; i < _points.length; i += 2) {
            _points[i] = _x;
            _points[i + 1] = _y;
        }
        _k = _rank * 2;
    }

    @Override
    public DrawFigure clone() {
        return new DrawBSpline(_pd, _dm, _points.clone(), true, _rank);
    }
}
