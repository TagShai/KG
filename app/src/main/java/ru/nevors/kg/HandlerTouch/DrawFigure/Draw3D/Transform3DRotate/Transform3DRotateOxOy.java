package ru.nevors.kg.HandlerTouch.DrawFigure.Draw3D.Transform3DRotate;

import ru.nevors.kg.HandlerTouch.DrawFigure.Draw3D.Transform3D;
import ru.nevors.kg.OBJ.Point;

public class Transform3DRotateOxOy extends Transform3DRotate implements Transform3D {
    @Override
    public void transform(float[] points, Point vec, Point center) {
        float angleX, angleY;
        if (vec.x > 0) {
            angleY = angleDown;
        } else {
            if (vec.x < 0) {
                angleY = angleUp;
            } else {
                angleY = 0;
            }
        }

        if (vec.y > 0) {
            angleX = angleDown;
        } else {
            if (vec.y < 0) {
                angleX = angleUp;
            } else {
                angleX = 0;
            }
        }
        float cosX, sinX, cosY, sinY;
        cosX = (float) Math.cos(angleX);
        sinX = (float) Math.sin(angleX);
        cosY = (float) Math.cos(angleY);
        sinY = (float) Math.sin(angleY);
        float x,y,z;
        for (int i = 0; i < points.length; i += 3) {
            points[i] -= center.x;
            points[i + 1] -= center.y;
            points[i + 2] -= center.z;

            //вокруг oX
            y = points[i + 1];
            z = points[i + 2];
            points[i + 1] = y * cosX - z * sinX;
            points[i + 2] = y * sinX + z * cosX;

            //вокруг oY
            x = points[i];
            z = points[i + 2];
            points[i] = x * cosY - z * sinY;
            points[i + 2] = x * sinY + z * cosY;

            points[i] += center.x;
            points[i + 1] += center.y;
            points[i + 2] += center.z;
        }
    }
}
