package ru.nevors.kg.View;

import ru.nevors.kg.HandlerTouch.DrawFigure.DrawFigure;
import ru.nevors.kg.View.MyPaint.MyPaint;

public class Layer {
    DrawFigure _df;
    MyPaint _paint;

    public Layer(DrawFigure df, MyPaint paint) {
        _df = df;
        _paint = paint;
    }

    public DrawFigure getDrawFigure() {
        return _df;
    }

    public MyPaint getPaint() {
        return _paint;
    }
}
