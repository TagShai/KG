package ru.nevors.kg.HandlerTouch.DrawFigure.DrawRect;

import ru.nevors.kg.DrawMethod.DrawMethod;
import ru.nevors.kg.DrawMethod.FillMethod.FillMethodCircle;
import ru.nevors.kg.View.PD2;

public class FillCircle extends  DrawRect{
    public FillCircle(PD2 pd) {
        super(pd, new FillMethodCircle());
    }
}
