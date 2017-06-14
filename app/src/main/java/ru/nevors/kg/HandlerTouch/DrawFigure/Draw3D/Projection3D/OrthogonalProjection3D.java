package ru.nevors.kg.HandlerTouch.DrawFigure.Draw3D.Projection3D;

import ru.nevors.kg.OBJ.Point;

public class OrthogonalProjection3D implements IProjection {
    @Override
    public void projection(int[] result, float[] points,Point center) {
        for(int i = 0;i<points.length;i++){
            result[i] = (int)points[i];
        }
    }

    @Override
    public void action(Point vec) {

    }
}
