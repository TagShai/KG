package ru.nevors.kg.View.HandlerDraw;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import ru.nevors.kg.HandlerTouch.DrawFigure.DrawFigure;
import ru.nevors.kg.View.MyPaint.MyPaint;
import ru.nevors.kg.View.PD2;

public abstract class HandlerDraw {
    Paint _paint;
    PD2 _pd;
    Rect _src, _dst;

    public HandlerDraw(PD2 pd,Rect src, Rect dst) {
        _paint = new Paint();
        _paint.setStrokeWidth(3);
        _paint.setStyle(Paint.Style.STROKE);
        _paint.setColor(Color.GREEN);

        _pd = pd;
        _src = src;
        _dst = dst;
    }

    public abstract void processing( Canvas canvas);

    public abstract void draw(DrawFigure df, MyPaint paint,boolean save);
}
