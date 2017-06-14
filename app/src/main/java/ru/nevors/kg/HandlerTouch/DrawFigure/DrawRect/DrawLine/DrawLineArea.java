package ru.nevors.kg.HandlerTouch.DrawFigure.DrawRect.DrawLine;
import ru.nevors.kg.DrawMethod.DrawMethodLine.DrawMethodLineArea;
import ru.nevors.kg.HandlerTouch.DrawFigure.DrawRect.DrawRect;
import ru.nevors.kg.View.PD2;

public class DrawLineArea extends DrawRect {
    public DrawLineArea(PD2 pd) {
        super(pd, new DrawMethodLineArea());
    }
}
