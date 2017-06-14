package ru.nevors.kg.HandlerTouch.DrawFigure.DrawRect;

import ru.nevors.kg.DrawMethod.DrawMethod;
import ru.nevors.kg.DrawMethod.FillMethod.FillMethodTriangle;
import ru.nevors.kg.View.PD2;

public class FillTriangle extends DrawRect {
    public FillTriangle(PD2 pd) {
        super(pd, new FillMethodTriangle());
        _points = new int[6];
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
            update();
        }
        _pd.activateModify();
        for (int i = 0; i < _points.length; i += 2) {
            _points[i] = _x;
            _points[i + 1] = _y;
        }
        _k = 4;
    }

    public void update() {
        _up = false;
        if (_pd.deActivateModify()) {
            draw(clone());
            _points = new int[6];
        }
    }
}
