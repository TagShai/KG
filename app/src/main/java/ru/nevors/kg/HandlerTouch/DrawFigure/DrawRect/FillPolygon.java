package ru.nevors.kg.HandlerTouch.DrawFigure.DrawRect;

import android.graphics.Bitmap;

import ru.nevors.kg.DrawMethod.DrawMethod;
import ru.nevors.kg.DrawMethod.FillMethod.FillMethodPolygon.FillMethodPolygon;
import ru.nevors.kg.HandlerTouch.DrawFigure.DrawFigure;
import ru.nevors.kg.View.PD2;

public class FillPolygon extends DrawRect {

    public FillPolygon(PD2 pd) {
        super(pd, new FillMethodPolygon());
    }

    protected FillPolygon(PD2 pd, DrawMethod dw, int[] points, boolean up) {
        super(pd, dw);
        _points = points;
        _up = up;
    }

    @Override
    protected void actionDown() {
        if (_up) {
            _pd.clearPointBuf();
            for (int i = 0; i < _points.length; i += 2) {
                if (Math.abs(_points[i] - _x) < _accuracy && Math.abs(_points[i + 1] - _y) < _accuracy) {
                    _k = i;
                    draw(this);
                    return;
                }
            }

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

            return;
        }

        _pd.activateModify();
        for (int i = 0; i < _points.length; i += 2) {
            _points[i] = _x;
            _points[i + 1] = _y;
        }

        _k = 2;
    }

    @Override
    public DrawFigure clone() {
        return new FillPolygon(_pd, _dm, _points.clone(), true);
    }
}
