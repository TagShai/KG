package ru.nevors.kg.HandlerTouch.DrawFigure.DrawRect.DrawCurve;

import android.graphics.Bitmap;

import ru.nevors.kg.HandlerTouch.DrawFigure.DrawFigure;
import ru.nevors.kg.HandlerTouch.DrawFigure.DrawRect.DrawRect;
import ru.nevors.kg.DrawMethod.DrawMethod;
import ru.nevors.kg.DrawMethod.DrawMethodCurve.DrawMethodErmit;
import ru.nevors.kg.View.PD2;

public class DrawErmit extends DrawRect {
    public DrawErmit(PD2 pd) {
        super(pd, new DrawMethodErmit());
    }

    protected DrawErmit(PD2 pd, DrawMethod dw, int[] points, boolean up) {
        super(pd, dw, points, up);
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
                            int[] points = new int[_points.length + 4];
                            int n = _points.length - 4;
                            float min;
                            int iMin = 0;
                            int g = 0;
                            min = distance(_x, _y, _points[g + 4], _points[g + 5]);
                            min += distance(_x, _y, _points[g], _points[g + 1]);
                            min /= distance(_points[g], _points[g + 1], _points[g + 4], _points[g + 5]);
                            g += 4;
                            while (g < n) {
                                float tMin = distance(_x, _y, _points[g + 4], _points[g + 5]);
                                tMin += distance(_x, _y, _points[g], _points[g + 1]);
                                tMin /= distance(_points[g], _points[g + 1], _points[g + 4], _points[g + 5]);
                                if (min > tMin) {
                                    min = tMin;
                                    iMin = g;
                                }
                                g += 4;
                            }
                            int k = 0;
                            while (k < iMin + 4) {
                                points[k] = _points[k];
                                k++;
                            }
                            points[k++] = _x;
                            points[k++] = _y;
                            points[k++] = _x;
                            points[k++] = _y;
                            while (k < points.length) {
                                points[k] = _points[k - 4];
                                k++;
                            }
                            _points = points;
                            _k = iMin + 6;

                            return;
                        }
                    } catch (Exception e) {
                    }
                }
            }
            update();
        }
        _pd.activateModify();
        _points = new int[8];
        for (int i = 0; i < _points.length; i += 2) {
            _points[i] = _x;
            _points[i + 1] = _y;
        }
        _k = 4;
    }

    @Override
    public DrawFigure clone() {
        return new DrawErmit(_pd, _dm, _points.clone(), true);
    }

    @Override
    protected void drawManagement() {
        for (int i = 0; i < _points.length; i += 2) {
            _pd.addPointBuf(_points[i], _points[i + 1], _accuracy);
        }
        for (int i = 0; i < _points.length; i += 4) {
            _pd.addLineBuf(_points[i], _points[i + 1],_points[i + 2], _points[i + 3]);
        }
    }
}
