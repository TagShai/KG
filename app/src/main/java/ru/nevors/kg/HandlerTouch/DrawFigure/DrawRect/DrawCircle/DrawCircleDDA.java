package ru.nevors.kg.HandlerTouch.DrawFigure.DrawRect.DrawCircle;

import ru.nevors.kg.HandlerTouch.DrawFigure.DrawRect.DrawRect;
import ru.nevors.kg.DrawMethod.DrawMethodCircle.DrawMethodCircleDDA;
import ru.nevors.kg.View.PD2;

public class DrawCircleDDA extends DrawRect {
    public DrawCircleDDA(PD2 pd) {
        super(pd,new DrawMethodCircleDDA());
    }
}