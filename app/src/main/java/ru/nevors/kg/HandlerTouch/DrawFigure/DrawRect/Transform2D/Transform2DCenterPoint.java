package ru.nevors.kg.HandlerTouch.DrawFigure.DrawRect.Transform2D;

import android.graphics.Point;

import ru.nevors.kg.View.PD2;

public abstract class Transform2DCenterPoint extends Transform2D {
    Point center;
    boolean isComputeCenter = true;
    boolean isChacngeCenter = false;

    //state 3 move center point
    public Transform2DCenterPoint(PD2 pd) {
        super(pd);
        center = new Point();
    }

    @Override
    protected void actionDown() {
        super.actionDown();
        if (isComputeCenter) {
            computeBorder();
            center.x = minX + (maxX - minX) / 2;
            center.y = minY + (maxY - minY) / 2;
            isComputeCenter = false;
        }
        if (Math.abs(center.x - _x) < _accuracy && Math.abs(center.y - _y) < _accuracy) {
            state = 3;
        } else {
            state = 1;
        }
    }

    @Override
    protected void actionMove() {
        super.actionMove();
        if (state == 3) {
            isChacngeCenter = true;
            center.x += _x - _start.x;
            center.y += _y - _start.y;
            _start.x = _x;
            _start.y = _y;
        }
        /*if (!isChacngeCenter) {
            computeBorder();
            center.x = (int)(minX + (maxX - minX) / 2.0F);
            center.y = (int)(minY + (maxY - minY) / 2.0F);
        }*/
    }

    @Override
    protected int[] actionPoint(int[] points, Point start, Point cur) {
        return actionPoint(points, start, cur, center);
    }

    protected abstract int[] actionPoint(int[] points, Point start, Point cur, Point center);

    @Override
    protected void actionUp() {
        state = 1;
        super.actionUp();
    }


    @Override
    protected void drawManagement() {
        super.drawManagement();
        _pd.addPointBuf(center.x, center.y, _accuracy);
    }
}
