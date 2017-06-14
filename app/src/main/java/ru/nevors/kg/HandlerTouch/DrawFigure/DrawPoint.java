package ru.nevors.kg.HandlerTouch.DrawFigure;

import ru.nevors.kg.DrawMethod.DrawMethodLine.DrawMethodLineBRH;
import ru.nevors.kg.View.PD2;

public class DrawPoint extends DrawFigure {
    int _startX,_startY;

    public DrawPoint(PD2 pd) {
        super(pd,new DrawMethodLineBRH());
    }

    @Override
    protected void actionDown() {
        _points[0] = _x;
        _points[1] = _y;
    }

    @Override
    protected void actionMove() {
        _points[2] = _x;
        _points[3] = _y;
        _pd.draw(this);
        _points[0] = _x;
        _points[1] = _y;
    }

    @Override
    protected void actionUp() {
        _pd.drawPoint(_x, _y);
    }

    @Override
    public DrawFigure clone() {
        return null;
    }

    @Override
    public void restore() {

    }
}
