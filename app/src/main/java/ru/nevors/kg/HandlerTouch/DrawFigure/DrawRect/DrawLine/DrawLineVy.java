package ru.nevors.kg.HandlerTouch.DrawFigure.DrawRect.DrawLine;

import ru.nevors.kg.DrawMethod.DrawMethod;
import ru.nevors.kg.DrawMethod.DrawMethodLine.DrawMethodLineVy;
import ru.nevors.kg.HandlerTouch.DrawFigure.DrawRect.DrawRect;
import ru.nevors.kg.View.PD2;

public class DrawLineVy extends DrawRect {
    public DrawLineVy(PD2 pd) {
        super(pd, new DrawMethodLineVy());
    }
}
