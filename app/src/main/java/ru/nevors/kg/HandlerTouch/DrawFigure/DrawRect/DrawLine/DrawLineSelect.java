package ru.nevors.kg.HandlerTouch.DrawFigure.DrawRect.DrawLine;

import ru.nevors.kg.DrawMethod.DrawMethod;
import ru.nevors.kg.DrawMethod.DrawMethodLine.DrawMethodLineSelect;
import ru.nevors.kg.HandlerTouch.DrawFigure.DrawRect.DrawRect;
import ru.nevors.kg.View.PD2;

public class DrawLineSelect extends DrawRect {
    public DrawLineSelect(PD2 pd) {
        super(pd, new DrawMethodLineSelect());
    }
}
