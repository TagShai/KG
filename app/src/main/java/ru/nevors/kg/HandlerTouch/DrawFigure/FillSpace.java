package ru.nevors.kg.HandlerTouch.DrawFigure;

import ru.nevors.kg.DrawMethod.DrawMethod;
import ru.nevors.kg.DrawMethod.FillMethod.FillMethodSpace;
import ru.nevors.kg.View.PD2;

public class FillSpace extends DrawFigure {

    public FillSpace(PD2 pd) {
        super(pd,new FillMethodSpace());
        _points= new int[2];
    }

    protected FillSpace(PD2 pd, DrawMethod dm, int[] points) {
        super(pd, dm, points);
    }

    @Override
    protected void actionDown() {
        _points[0] = _x;
        _points[1] = _y;
        draw(clone());
    }

    @Override
    protected void actionMove() {

    }

    @Override
    protected void actionUp() {

    }

    @Override
    public DrawFigure clone() {
        return new FillSpace(_pd,_dm, _points.clone());
    }

    @Override
    public void restore() {
        draw(this);
    }
}
