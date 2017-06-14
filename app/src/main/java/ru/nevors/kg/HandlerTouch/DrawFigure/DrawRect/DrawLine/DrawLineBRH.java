package ru.nevors.kg.HandlerTouch.DrawFigure.DrawRect.DrawLine;

import ru.nevors.kg.HandlerTouch.DrawFigure.DrawRect.DrawRect;
import ru.nevors.kg.DrawMethod.DrawMethodLine.DrawMethodLineBRH;
import ru.nevors.kg.View.PD2;

public class DrawLineBRH extends DrawRect {
    public DrawLineBRH(PD2 pd) {
        super(pd,new DrawMethodLineBRH());
    }

}