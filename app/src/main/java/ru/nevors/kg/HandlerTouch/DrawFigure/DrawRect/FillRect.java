package ru.nevors.kg.HandlerTouch.DrawFigure.DrawRect;

import ru.nevors.kg.DrawMethod.DrawMethod;
import ru.nevors.kg.DrawMethod.FillMethod.FillMethodRect;
import ru.nevors.kg.View.PD2;

public class FillRect extends DrawRect {
    public FillRect(PD2 pd) {
        super(pd, new FillMethodRect());
    }
}
