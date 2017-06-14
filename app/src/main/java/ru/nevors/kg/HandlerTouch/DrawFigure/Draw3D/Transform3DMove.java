package ru.nevors.kg.HandlerTouch.DrawFigure.Draw3D;

import ru.nevors.kg.OBJ.Point;

public class Transform3DMove implements Transform3D {
    @Override
    public void transform(float[] points, Point vec,Point center) {
        for (int i = 0; i < points.length; i += 3) {
            points[i] = points[i] + vec.x;
            points[i + 1] = points[i + 1] + vec.y;
        }
    }
}
