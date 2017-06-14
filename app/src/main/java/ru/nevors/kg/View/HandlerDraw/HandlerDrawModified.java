package ru.nevors.kg.View.HandlerDraw;

import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;

import java.util.ArrayList;

import ru.nevors.kg.HandlerTouch.DrawFigure.DrawFigure;
import ru.nevors.kg.DrawMethod.DrawMethod;
import ru.nevors.kg.View.PD2;
import ru.nevors.kg.View.MyPaint.MyPaint;

public class HandlerDrawModified extends HandlerDraw {
    MyPaint _bufPaint;
    Path _path;
    int[] _points;
    DrawMethod _dm;

    public HandlerDrawModified(PD2 pd, Rect src, Rect dst) {
        super(pd, src, dst);
        _bufPaint = new MyPaint();
        _bufPaint.setColor(0);
        _bufPaint.setColor2(0);
        _points = new int[4];
        _path = new Path();
    }

    @Override
    public void processing(Canvas canvas) {
        canvas.drawBitmap(_pd.getBitMap(), _src, _dst, _paint);
        canvas.drawBitmap(_pd.getBufBitMap(), _src, _dst, _paint);
        canvas.drawPath(_path, _paint);
    }

    @Override
    public void draw(DrawFigure df, MyPaint paint,boolean overwrite) {
        try {
            if(overwrite){
                //_dm.draw(_pd.getBufBitMap(), _points, _bufPaint);
                _pd.getBufBitMap().eraseColor(0);
            }
        } catch (Exception e) {
        }
        _dm = df.getDrawMethod();
        copyCoords(df.getPoints());
        _dm.draw(_pd.getBufBitMap(), _points, paint);
    }

    private void copyCoords(int[] points) {
        if (_points.length != points.length) {
            _points = new int[points.length];
        }
        for (int i = 0; i < points.length; i ++) {
            _points[i] = points[i];
        }
    }

    ArrayList<Point> points = new ArrayList<>();
    ArrayList<Rect> lines = new ArrayList<>();
    int size;

    public void updatePoint() {
        _path.reset();
        float scale = _pd.getScale();
        for (Point p : points) {
            _addPoint((p.x - _pd.getStartI()) * scale + _dst.left, (p.y - _pd.getStartJ()) * scale + _dst.top, size * scale);
        }
    }

    public void addPoint(int x, int y, int size) {
        this.size = size;
        points.add(new Point(x, y));
        float scale = _pd.getScale();
        _addPoint((x - _pd.getStartI()) * scale + _dst.left, (y - _pd.getStartJ()) * scale + _dst.top, size * scale);
    }

    public void addLine(int x1, int y1, int x2, int y2) {
        lines.add(new Rect(x1, y1, x2, y2));
        float scale = _pd.getScale();
        _addLine((x1 - _pd.getStartI()) * scale + _dst.left,
                (y1 - _pd.getStartJ()) * scale + _dst.top,
                (x2 - _pd.getStartI()) * scale + _dst.left,
                (y2 - _pd.getStartJ()) * scale + _dst.top);
    }

    private void _addLine(float x1, float y1, float x2, float y2) {
        _path.moveTo(x1, y1);
        _path.lineTo(x2, y2);
        _path.close();
    }

    private void _addPoint(float x, float y, float size) {
        float x1 = x - size, x2 = x + size, y1 = y - size, y2 = y + size;
        _path.moveTo(x1, y1);
        _path.lineTo(x1, y2);
        _path.lineTo(x2, y2);
        _path.lineTo(x2, y1);
        _path.close();
    }

    public void clearPoint() {
        _path.reset();
        points.clear();
    }


}
