package ru.nevors.kg.HandlerTouch.DrawFigure.Draw3D.Projection3D;

import ru.nevors.kg.OBJ.Point;

public class ObliqueAxonometricProjection3D implements  IProjection{

    float B = 0, A = 0;

    @Override
    public void projection(int[] result, float[] points, Point center) {
        float x, y, z;
        float bx, by, bz;
        float cosA, cosB, sinA, sinB;
        cosA = (float) Math.cos(A);
        cosB = (float) Math.cos(B);
        sinA = (float) Math.sin(A);
        sinB = (float) Math.sin(B);
        for (int i = 0; i < points.length; i += 3) {
            x = points[i] - center.x;
            y = points[i + 1] - center.y;
            z = points[i + 2] - center.z;

            by = y * cosA - z * sinA;
            bz = y * sinA + z * cosA;

            bx = x * cosB - bz * sinB;
            bz = x * sinB + bz * cosB;

            result[i] = (int) (bx + center.x);
            result[i + 1] = (int) (by + center.y);
            result[i + 2] = (int) (bz + center.z);
        }
    }

    @Override
    public void action(Point vec) {
        if (vec.y < 0) {
            A += angle;
        } else {
            if (vec.y > 0) {
                A -= angle;
            }
        }
        if (vec.x < 0) {
            B += angle;
        } else {
            if (vec.x > 0) {
                B -= angle;
            }
        }
    }
}
