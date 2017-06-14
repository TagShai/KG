package ru.nevors.kg.HandlerTouch.DrawFigure.DrawRect.DrawLine;

import ru.nevors.kg.HandlerTouch.DrawFigure.DrawRect.DrawRect;
import ru.nevors.kg.DrawMethod.DrawMethodLine.DrawMethodLineDDA;
import ru.nevors.kg.View.PD2;

public class DrawLineDDA extends DrawRect {
    public DrawLineDDA(PD2 pd) {
        super(pd,new DrawMethodLineDDA());
    }
}
