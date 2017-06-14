package ru.nevors.kg.HandlerTouch.DrawFigure.Draw3D.Projection3D;

import ru.nevors.kg.OBJ.Point;

public interface IProjection {
    float angle = 0.05F;
    void projection(int []result,float [] points,Point center);
    void action(Point vec);
}
