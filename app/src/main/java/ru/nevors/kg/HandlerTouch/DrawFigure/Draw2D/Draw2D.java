package ru.nevors.kg.HandlerTouch.DrawFigure.Draw2D;

import ru.nevors.kg.DrawMethod.DrawMethod;
import ru.nevors.kg.DrawMethod.DrawMethodObj2D;
import ru.nevors.kg.HandlerTouch.DrawFigure.DrawFigure;
import ru.nevors.kg.HandlerTouch.DrawFigure.DrawRect.DrawLine.DrawLineBRH;
import ru.nevors.kg.OBJ.Triangle;
import ru.nevors.kg.View.PD2;

public class Draw2D extends DrawFigure {
    public Draw2D(PD2 pd) {
        super(pd, new DrawMethodObj2D());
        _points = new int[0];
    }

    public Draw2D(PD2 pd, DrawMethod dm, int[] points) {
        super(pd, dm, points);
    }

    public void addTriangle(Triangle tr) {
        int width = (int) (_pd.getBitMap().getWidth() / _pd.getScale());
        int height = (int) (_pd.getBitMap().getHeight() / _pd.getScale());

        int i = _points.length;
        addSpaceForMas(6);
        _points[i++] = (int) ((tr.p1.x + 1.F) * width / 2.F - width / 2.F);
        _points[i++] = (int) ((tr.p1.y + 1.F) * height / 2.F - height / 2.F);
        _points[i++] = (int) ((tr.p2.x + 1.F) * width / 2.F - width / 2.F);
        _points[i++] = (int) ((tr.p2.y + 1.F) * height / 2.F - height / 2.F);
        _points[i++] = (int) ((tr.p3.x + 1.F) * width / 2.F - width / 2.F);
        _points[i] = (int) ((tr.p3.y + 1.F) * height / 2.F - height / 2.F);
    }

    @Override
    protected void actionDown() {
        int points[] = new int[+_points.length];
        for (int i = 0; i < _points.length; i += 2) {
            points[i] = _points[i] + _x;
            points[i + 1] = _points[i + 1] + _y;
        }
        draw(new Draw2D(_pd,_dm,points));
    }

    @Override
    protected void actionMove() {

    }

    @Override
    protected void actionUp() {

    }

    @Override
    public DrawFigure clone() {
        return new Draw2D(_pd, _dm, _points.clone());
    }

    @Override
    public void restore() {
        //actionDown();
    }
}
