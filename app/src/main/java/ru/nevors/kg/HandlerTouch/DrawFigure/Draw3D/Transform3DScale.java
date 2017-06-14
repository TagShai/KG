package ru.nevors.kg.HandlerTouch.DrawFigure.Draw3D;

import ru.nevors.kg.OBJ.Point;

public class Transform3DScale implements Transform3D {
    float k = 0.05F;
    float kUp = 1 + k;
    float kDown = 1 - k;

    @Override
    public void transform(float[] points, Point vec, Point center) {
        float kX, kY;
        if (vec.x < 0) {
            kX = kDown;
        } else {
            if (vec.x > 0) {
                kX = kUp;
            } else {
                kX = 1;
            }
        }

        if (vec.y < 0) {
            kY = kUp;
        } else {
            if (vec.y > 0) {
                kY = kDown;
            } else {
                kY = 1;
            }
        }
        for (int i = 0; i < points.length; i += 3) {
            points[i] -= center.x;
            points[i + 1] -= center.y;
            points[i + 2] -= center.z;

            points[i] *= kX;
            points[i + 1] *= kY;

            points[i] += center.x;
            points[i + 1] += center.y;
            points[i + 2] += center.z;
        }
    }
}
