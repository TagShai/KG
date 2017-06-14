package ru.nevors.kg.HandlerTouch.DrawFigure.DrawRect;

import ru.nevors.kg.DrawMethod.DrawMethod;
import ru.nevors.kg.View.PD2;
import ru.nevors.kg.HandlerTouch.DrawFigure.DrawFigure;

public abstract class DrawRect extends DrawFigure {
    protected int _k;
    protected boolean _up;

    public DrawRect(PD2 pd, DrawMethod dw) {
        super(pd, dw);
        _k = 0;
        _up = false;
    }

    protected DrawRect(PD2 pd, DrawMethod dw,int[] points ,boolean up) {
        super(pd, dw,points);
        _up = up;
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
        _k = 2;
    }

    @Override
    protected void actionMove() {
        _points[_k] = _x;
        _points[_k + 1] = _y;
        draw(this);

        /*String str = "";
        for (int g = 0; g < _points.length; g++) {
            str += _points[g] + " ";
        }
        Log.d("points", str);*/
    }

    @Override
    protected void actionUp() {
        _up = true;
        //прорисовка квандратиков
        drawManagement();
    }

    @Override
    public void update() {
        _up = false;
        if (_pd.deActivateModify()) {
            draw(clone());
            _points = new int[4];
        }
    }

    @Override
    public DrawFigure clone() {
        return new DrawRect(_pd, _dm, _points.clone(), true) {};
    }

    @Override
    public void restore() {
            _pd.activateModify();
            draw(this);
            drawManagement();
    }

    protected float distance(int x1, int y1, int x2, int y2) {
        return (float) Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
    }
}
