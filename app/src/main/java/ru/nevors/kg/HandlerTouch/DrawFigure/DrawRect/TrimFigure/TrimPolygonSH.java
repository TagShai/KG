package ru.nevors.kg.HandlerTouch.DrawFigure.DrawRect.TrimFigure;

import ru.nevors.kg.DrawMethod.DrawMethod;
import ru.nevors.kg.DrawMethod.DrawMethodRectangle;
import ru.nevors.kg.DrawMethod.TrimMethod.TrimMethodSH;
import ru.nevors.kg.HandlerTouch.DrawFigure.DrawFigure;
import ru.nevors.kg.HandlerTouch.DrawFigure.DrawRect.DrawRect;
import ru.nevors.kg.View.PD2;

public class TrimPolygonSH extends DrawRect {

    int state = -2;
    int iSizePolygon;
    // -2 new window
    // 2 edit  window
    // 1 line
    // 4 add new polygon

    public TrimPolygonSH(PD2 pd) {
        super(pd, new TrimMethodSH());
    }

    protected TrimPolygonSH(PD2 pd, DrawMethod dm, int[] points, boolean up) {
        super(pd, dm, points, up);
    }

    public DrawFigure clone() {
        return new TrimPolygonSH(_pd, _dm, _points.clone(), true);
    }

    int _startX, _startY;
    int _diffX, _diffY;


    @Override
    protected void actionDown() {
        _startX = _x;
        _startY = _y;

        _k = -1;
        _pd.clearPointBuf();

        switch (state) {
            case 2:
                for (int i = 0; i < 4; i += 2) {
                    if (Math.abs(_points[i] - _x) < _accuracy && Math.abs(_points[i + 1] - _y) < _accuracy) {
                        _k = i;
                        return;
                    }
                }
                break;
            case 1:
                int numPoints;
                int k = 4;
                while (k < _points.length) {
                    numPoints = _points[k++];
                    int size = numPoints * 2;
                    for (int i = k; i < k + size; i += 2) {
                        if (Math.abs(_points[i] - _x) < _accuracy && Math.abs(_points[i + 1] - _y) < _accuracy) {
                            _k = i;
                            return;
                        }
                    }
                    k += size;
                }
                break;
        }


        int i;
        switch (state) {
            case -2:
                _pd.activateModify();
                _points = new int[5];

                i = 0;
                _points[i++] = _x;
                _points[i++] = _y;
                _points[i++] = _x + 100;
                _points[i++] = _y + 100;
                iSizePolygon = i;

                state = 2;
                _k = 0;
                break;
            case 1:
                _points[iSizePolygon]++;

                i = _points.length;

                addSpaceForMas(2);

                _points[i] = _x;
                _points[i + 1] = _y;
                _k = i;
                break;
        }
    }

    @Override
    public void restore() {
        super.restore();
        state = 2;
    }

    @Override
    protected void actionMove() {
        _diffX = _x - _startX;
        _diffY = _y - _startY;
        switch (state) {
            case 2:
                if (_k != -1) {
                    _points[_k] = _x;
                    _points[_k + 1] = _y;
                } else {
                    for (int i = 0; i < 4; i += 2) {
                        _points[i] += _diffX;
                        _points[i + 1] += _diffY;
                        _startX = _x;
                        _startY = _y;
                    }
                }
                break;
            default:
                _points[_k] = _x;
                _points[_k + 1] = _y;
                break;
        }
        draw(this);
    }

    @Override
    protected void actionUp() {
        draw(this);
        //прорисовка квандратиков
        drawManagement();
    }

    protected void addSpaceForMas(int size) {
        int[] points = new int[_points.length + size];
        for (int i = 0; i < _points.length; i++) {
            points[i] = _points[i];
        }
        _points = points;
    }

    @Override
    protected void drawManagement() {
        switch (state) {
            case 2:
                for (int i = 0; i < 4; i += 2) {
                    _pd.addPointBuf(_points[i], _points[i + 1], _accuracy);
                }
                break;
            case 1:
                int numPoints;
                int k = 4;
                while (k < _points.length) {
                    numPoints = _points[k++];
                    int size = numPoints * 2;
                    for (int i = k; i < k + size; i += 2) {
                        _pd.addPointBuf(_points[i], _points[i + 1], _accuracy);
                    }
                    k += size;
                }
                break;
        }
    }

    public void setEditLineState() {
        state = 1;
        _pd.clearPointBuf();
        drawManagement();
        draw(this);
        _pd.invalidate();
    }

    public void setEditWindowState() {
        state = 2;
        _pd.clearPointBuf();
        drawManagement();
        draw(this);
        _pd.invalidate();
    }

    public void addPoint(int x, int y) {
        int i = _points.length;

        _points[iSizePolygon] += 1;

        addSpaceForMas(2);
        _points[i] = x;
        _points[i + 1] = y;
    }

    public void addPolygon() {
        int i = _points.length;
        iSizePolygon = i;
        addSpaceForMas(1);
    }
}
