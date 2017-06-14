package ru.nevors.kg.HandlerTouch.DrawFigure.DrawRect.DrawCircle;

import ru.nevors.kg.HandlerTouch.DrawFigure.DrawRect.DrawRect;
import ru.nevors.kg.DrawMethod.DrawMethodCircle.DrawMethodCircleBRH;
import ru.nevors.kg.View.PD2;

public class DrawCircleBRH extends DrawRect {

    public DrawCircleBRH(PD2 pd) {
        super(pd,new DrawMethodCircleBRH());
    }
}