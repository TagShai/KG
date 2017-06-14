package ru.nevors.kg.HandlerTouch.DrawFigure;

import android.view.MotionEvent;

import ru.nevors.kg.HandlerTouch.HandlerTouch;
import ru.nevors.kg.DrawMethod.DrawMethod;
import ru.nevors.kg.View.PD2;

public abstract class DrawFigure extends HandlerTouch {
    protected DrawMethod _dm;
    protected int[] _points;
    protected int _x, _y;
    static protected int _accuracy;
    int _lastX, _lastY;

    static {
        _accuracy = 15;
    }

    public DrawFigure(PD2 pd, DrawMethod dm) {
        super(pd);
        _dm = dm;
        _points = new int[4];
    }

    protected DrawFigure(PD2 pd, DrawMethod dm, int[] points) {
        super(pd);
        _dm = dm;
        _points = points;
    }

    @Override
    public void processing(MotionEvent event) {

        float x = event.getX();
        float y = event.getY();
        _x = _pd.translateX(x);
        _y = _pd.translateY(y);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: // нажатие
                actionDown();
                break;
            case MotionEvent.ACTION_MOVE: // движение
                if (_x != _lastX || _y != _lastY) {
                    actionMove();
                    _lastX = _x;
                    _lastY = _y;
                } else {
                    return;
                }
                break;
            case MotionEvent.ACTION_UP: // отпускание
            case MotionEvent.ACTION_CANCEL:
                actionUp();
                break;
        }
        _pd.invalidate();
    }

    protected abstract void actionDown();

    protected abstract void actionMove();

    protected abstract void actionUp();

    public static int getAccuracy() {
        return _accuracy;
    }

    public static void setAccuracy(int accuracy) {
        DrawFigure._accuracy = accuracy;
    }

    public DrawMethod getDrawMethod() {
        return _dm;
    }

    public int[] getPoints() {
        return _points;
    }

    public void setPoints(int[] points) {
        _points = points;
    }

    protected void draw(DrawFigure df) {
        _pd.draw(df);
    }

    protected void draw(DrawFigure df,boolean overwrite) {
        _pd.draw(df,overwrite);
    }

    public abstract DrawFigure clone();

    public abstract void restore();

    protected void drawManagement() {
        for (int i = 0; i < _points.length; i += 2) {
            _pd.addPointBuf(_points[i], _points[i + 1], _accuracy);
        }
    }
    protected void addSpaceForMas(int size) {
        int[] points = new int[_points.length + size];
        for (int i = 0; i < _points.length; i++) {
            points[i] = _points[i];
        }
        _points = points;
    }
}
