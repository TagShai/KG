package ru.nevors.kg.HandlerTouch.DrawFigure.Draw3D.Transform3DRotate;

import ru.nevors.kg.HandlerTouch.DrawFigure.Draw3D.Transform3D;
import ru.nevors.kg.OBJ.Point;

public class Transform3DRotateOy  extends Transform3DRotate implements Transform3D {
    @Override
    public void transform(float[] points, Point vec, Point center) {
        float angleY;
        if (vec.x < 0) {
            angleY = angleUp;
        } else {
            if (vec.x > 0) {
                angleY = angleDown;
            } else {
                angleY = 0;
            }
        }
        float cosY, sinY;
        cosY = (float) Math.cos(angleY);
        sinY = (float) Math.sin(angleY);
        float x,z;
        for (int i = 0; i < points.length; i += 3) {
            points[i] -= center.x;
            points[i + 2] -= center.z;

            //вокруг oY
            x = points[i];
            z = points[i + 2];
            points[i] = x * cosY - z * sinY;
            points[i + 2] = x * sinY + z * cosY;

            points[i] += center.x;
            points[i + 2] += center.z;
        }
    }
}
