package ru.nevors.kg.HandlerTouch.DrawFigure.Draw3D.Projection3D;

import ru.nevors.kg.OBJ.Point;

public class CenterProjection3D implements IProjection {
    int dk = 1;
    int k = -150;

    @Override
    public void projection(int[] result, float[] points, Point center) {
        for (int i = 0; i < points.length; i += 3) {

            float k2 = k / 2.F;
            float a = points[i] - center.x;
            float b = (points[i + 2] - (center.z + k)) / k2;


            result[i] = (int) ((b * center.x + a) / b);

            a = points[i + 1] - center.y;

            result[i + 1] = (int) ((b * center.y + a) / b);
            result[i + 2] = (int) (points[i + 2] / b);

            /*points[i]-=center.x;
            points[i+1]-=center.y;
            points[i+2]-=center.z;*/

           /* result[i] = (int) (points[i] * k / (points[i+2] + k));
            result[i+1] = (int) (points[i+1] * k / (points[i+2] + k));
            result[i+2] = (int) (points[i+2] * k / (points[i+2] + k));*/

           /* points[i]+=center.x;
            points[i+1]+=center.y;
            points[i+2]+=center.z;*/


        }
    }

    @Override
    public void action(Point vec) {
        if (vec.y < 0) {
            k += dk;
        } else {
            if (vec.y > 0) {
                k -= dk;
            }
        }
    }
}
