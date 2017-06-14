package ru.nevors.kg.HandlerTouch.DrawFigure.DrawRect.TrimFigure;

import ru.nevors.kg.DrawMethod.DrawMethod;
import ru.nevors.kg.DrawMethod.TrimMethod.TrimMethodVA.TrimMethodVA;
import ru.nevors.kg.HandlerTouch.DrawFigure.DrawFigure;
import ru.nevors.kg.HandlerTouch.DrawFigure.DrawRect.DrawRect;
import ru.nevors.kg.View.PD2;

public class TrimPolygonVA extends DrawRect {
    int state = -3;
    int iSizePolygon;
    // -3 new window
    // -2 add point window
    // 2 move window
    // 1 edit lines
    // 4 add new polygon

    public TrimPolygonVA(PD2 pd) {
        super(pd, new TrimMethodVA());
    }

    protected TrimPolygonVA(PD2 pd, DrawMethod dm, int[] points, boolean up) {
        super(pd, dm, points, up);
    }

    public DrawFigure clone() {
        return new TrimPolygonVA(_pd, _dm, _points.clone(), true);
    }

    int _startX, _startY;
    int _diffX, _diffY;


    @Override
    protected void actionDown() {
        _startX = _x;
        _startY = _y;

        _k = -1;
        int cond = _points[0] * 2 + 1;
        _pd.clearPointBuf();

        switch (state) {
            case 2:
                for (int i = 1; i < cond; i += 2) {
                    if (Math.abs(_points[i] - _x) < _accuracy && Math.abs(_points[i + 1] - _y) < _accuracy) {
                        _k = i;
                        return;
                    }
                }
                break;
            case 1:
                int numPoints;
                int k = cond;
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
            case -3:
                _pd.activateModify();
                _points = new int[3];

                i = 0;
                iSizePolygon = i;
                _points[i++] = 1;
                _points[i++] = _x;
                _points[i] = _y;

                state = -2;
                _k = 0;
                break;
            case -2:
                i = _points[0] * 2 + 1;
                _points[0]++;
                _k = i;
                addSpaceForMas(i, 2);
                _points[i++] = _x;
                _points[i] = _y;
                break;
            case 1:
                if (iSizePolygon != 0) {
                    i = _points.length;
                    addPoint(_x, _y);
                    _k = i;
                }
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
                    int cond = _points[0] * 2;
                    for (int i = 1; i <= cond; i += 2) {
                        _points[i] += _diffX;
                        _points[i + 1] += _diffY;
                    }
                    _startX = _x;
                    _startY = _y;
                }
                break;
            default:
                if (_k != -1) {
                    _points[_k] = _x;
                    _points[_k + 1] = _y;
                }
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

    protected void addSpaceForMas(int index, int size) {
        int[] points = new int[_points.length + size];
        int k = 0;
        for (int i = 0; i < index; i++) {
            points[i] = _points[k++];
        }
        for (int i = index + size; i < points.length; i++) {
            points[i] = _points[k++];
        }
        _points = points;
    }

    @Override
    protected void drawManagement() {
        int cond = _points[0] * 2 + 1;
        switch (state) {
            case -2:
            case 2:
                for (int i = 1; i < cond; i += 2) {
                    _pd.addPointBuf(_points[i], _points[i + 1], _accuracy);
                }
                break;
            case 1:
                int numPoints;
                int k = cond;
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

    private void setState() {
        _pd.clearPointBuf();
        drawManagement();
        draw(this);
        _pd.invalidate();
    }

    public void setEditLineState() {
        state = 1;
        setState();
    }

    public void setEditWindowState() {
        state = 2;
        setState();
    }

    public void setAddPointsWindowState() {
        state = -2;
        setState();
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
