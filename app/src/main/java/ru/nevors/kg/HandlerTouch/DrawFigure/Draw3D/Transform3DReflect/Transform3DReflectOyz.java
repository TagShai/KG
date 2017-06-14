package ru.nevors.kg.HandlerTouch.DrawFigure.Draw3D.Transform3DReflect;

import ru.nevors.kg.HandlerTouch.DrawFigure.Draw3D.Transform3D;
import ru.nevors.kg.OBJ.Point;

public class Transform3DReflectOyz implements Transform3D {
    @Override
    public void transform(float[] points, Point vec, Point center) {
        for (int i = 0; i < points.length; i += 3) {
            points[i] -= center.x;
            points[i] *= -1;
            points[i] += center.x;
        }
    }
}
