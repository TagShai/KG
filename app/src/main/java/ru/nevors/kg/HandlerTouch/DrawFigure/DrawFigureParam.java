package ru.nevors.kg.HandlerTouch.DrawFigure;

import ru.nevors.kg.DrawMethod.DrawMethod;
import ru.nevors.kg.HandlerTouch.DrawFigure.DrawFigure;
import ru.nevors.kg.View.PD2;

public class DrawFigureParam extends DrawFigure {
    public DrawFigureParam(PD2 pd, DrawMethod dm) {
        super(pd, dm);
        _points = new int[2];
    }

    protected DrawFigureParam(PD2 pd, DrawMethod dm, int[] points) {
        super(pd, dm, points);
    }

    public void addParam(int param) {
        int i = _points.length;
        addSpaceForMas(1);
        _points[i] = param;
    }

    boolean isMove = false;
    boolean isPaint = false;

    @Override
    protected void actionDown() {
        if(!isPaint){
            _pd.activateModify();
            _points[0] = _x;
            _points[1] = _y;
            isPaint = true;
        }
        draw(this);
        _pd.clearPointBuf();
        if (Math.abs(_points[0] - _x) < _accuracy && Math.abs(_points[1] - _y) < _accuracy) {
            isMove = true;
        }
    }

    @Override
    protected void actionMove() {
        if (isMove) {
            _points[0] = _x;
            _points[1] = _y;
        }
        draw(this);
    }

    @Override
    protected void actionUp() {
        isMove = false;
        drawManagement();
    }

    @Override
    public DrawFigure clone() {
        return new DrawFigureParam(_pd, _dm, _points.clone());
    }

    @Override
    public void restore() {
        _pd.activateModify();
        draw(this);
        drawManagement();
    }

    @Override
    public void update() {
        if (_pd.deActivateModify()) {
            draw(clone());
        }
    }

    @Override
    protected void drawManagement() {
        _pd.addPointBuf(_points[0], _points[1], getAccuracy());
    }
}
