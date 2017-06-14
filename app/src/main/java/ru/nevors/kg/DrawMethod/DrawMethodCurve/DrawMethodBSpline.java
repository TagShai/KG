package ru.nevors.kg.DrawMethod.DrawMethodCurve;

import android.graphics.Bitmap;

import ru.nevors.kg.DrawMethod.DrawMethod;
import ru.nevors.kg.View.MyPaint.MyPaint;

/**
 * Created by W11B on 07.03.2017.
 */
public class DrawMethodBSpline extends DrawMethod {
    int _rank;
    int _n;
    double _step;
    int _size;
    double[] _N;

    public DrawMethodBSpline(int rank) {
        _step = 0.001F;
        _rank = rank;
        _n = 0;
    }

    private double[] reCompute() {
        int vt[] = new int[_n + _rank + 1];

        int k = 0;
        while (k <= _rank) {
            vt[k++] = 0;
        }
        while (k < _n) {
            vt[k] = k - _rank + 1;
            k++;
        }
        int temp = _n - _rank + 2;
        while (k <= _n + _rank) {
            vt[k] = temp;
            k++;
        }

        float t = .0F;
        _size = (int)Math.round(temp / _step) + 1;
        double N[] = new double[_size * _n];

        int i = 0;
        while (t <= temp) {
            for (int j = 0; j < _n; j++) {
                N[i++] = N(j, _rank, t, vt);
            }
            t += _step;
        }
        return N;
    }

    @Override
    public void draw(Bitmap bmp, int[] points, MyPaint paint) {
        int color = paint.getColor();
        int n = points.length / 2;
        if (n != _n) {
            _n = n;
            _N = reCompute();
        }

        float resX, resY;
        int k = 0;
        for (int i = 0; i < _size; i++) {
            resX = 0;
            resY = 0;
            for (int j = 0,h = 0; j < _n; j++,h+=2) {
                resX += points[h] * _N[k];
                resY += points[h + 1] * _N[k];
                k++;
            }
            try {
                bmp.setPixel((int) resX, (int) resY, color);
            } catch (Exception e) {
            }
        }/**/

    }


    private float N(int i, int k, float t, int[] vt) {
        if (k == 0) {
            if (t >= vt[i] && t < vt[i + 1]) {
                return 1;
            } else {
                return 0;
            }
        } else {
            float ch1 = (t - vt[i]) * N(i, k - 1, t, vt);
            int znam1 = (vt[i + k] - vt[i]);

            if (znam1 == 0) {
                ch1 = 0;
            } else {
                ch1 /= znam1;
            }

            float ch2 = (vt[i + k + 1] - t) * N(i + 1, k - 1, t, vt);
            int znam2 = (vt[i + k + 1] - vt[i + 1]);

            if (znam2 == 0) {
                ch2 = 0;
            } else {
                ch2 /= znam2;
            }

            return ch1 + ch2;
        }
    }

}
