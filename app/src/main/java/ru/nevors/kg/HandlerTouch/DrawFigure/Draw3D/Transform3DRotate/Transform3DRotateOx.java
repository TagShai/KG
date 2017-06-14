package ru.nevors.kg.HandlerTouch.DrawFigure.Draw3D.Transform3DRotate;

import ru.nevors.kg.HandlerTouch.DrawFigure.Draw3D.Transform3D;
import ru.nevors.kg.OBJ.Point;

public class Transform3DRotateOx  extends Transform3DRotate implements Transform3D {
    @Override
    public void transform(float[] points, Point vec, Point center) {
        float angleX;

        if (vec.y < 0) {
            angleX = angleUp;
        } else {
            if (vec.y > 0) {
                angleX = angleDown;
            } else {
                angleX = 0;
            }
        }
        float cosX, sinX;
        cosX = (float) Math.cos(angleX);
        sinX = (float) Math.sin(angleX);
        float y,z;
        for (int i = 0; i < points.length; i += 3) {
            points[i + 1] -= center.y;
            points[i + 2] -= center.z;

            //вокруг oX
            y = points[i + 1];
            z = points[i + 2];
            points[i + 1] = y * cosX - z * sinX;
            points[i + 2] = y * sinX + z * cosX;

            points[i + 1] += center.y;
            points[i + 2] += center.z;
        }
    }
}
