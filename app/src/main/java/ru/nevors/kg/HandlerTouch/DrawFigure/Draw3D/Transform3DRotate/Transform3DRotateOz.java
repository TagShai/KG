package ru.nevors.kg.HandlerTouch.DrawFigure.Draw3D.Transform3DRotate;

import ru.nevors.kg.HandlerTouch.DrawFigure.Draw3D.Transform3D;
import ru.nevors.kg.OBJ.Point;

public class Transform3DRotateOz extends Transform3DRotate implements Transform3D {
    @Override
    public void transform(float[] points, Point vec, Point center) {
        float angleZ;

        if (vec.y < 0) {
            angleZ = angleDown;
        } else {
            if (vec.y > 0) {
                angleZ = angleUp;
            } else {
                angleZ = 0;
            }
        }
        float cosZ, sinZ;
        cosZ = (float) Math.cos(angleZ);
        sinZ = (float) Math.sin(angleZ);
        float x,y;
        for (int i = 0; i < points.length; i += 3) {
            points[i] -= center.x;
            points[i + 1] -= center.y;

            //вокруг oZ
            x = points[i];
            y = points[i + 1];
            points[i] = x * cosZ - y * sinZ;
            points[i + 1] = x * sinZ + y * cosZ;

            points[i] += center.x;
            points[i + 1] += center.y;
        }
    }
}
