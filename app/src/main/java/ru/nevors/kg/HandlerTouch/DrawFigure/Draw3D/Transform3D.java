package ru.nevors.kg.HandlerTouch.DrawFigure.Draw3D;


import ru.nevors.kg.OBJ.Point;

public interface Transform3D {
    public void transform(float []points,Point vec,Point center);
}
