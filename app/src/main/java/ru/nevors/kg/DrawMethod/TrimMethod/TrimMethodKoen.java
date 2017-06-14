package ru.nevors.kg.DrawMethod.TrimMethod;

import android.graphics.Bitmap;

import ru.nevors.kg.DrawMethod.DrawMethod;
import ru.nevors.kg.DrawMethod.DrawMethodLine.DrawMethodLineBRH;
import ru.nevors.kg.DrawMethod.DrawMethodRectangle;
import ru.nevors.kg.View.MyPaint.MyPaint;

public class TrimMethodKoen extends DrawMethod {
    @Override
    public void draw(Bitmap bmp, int[] points, MyPaint paint) {
        DrawMethodRectangle dr = new DrawMethodRectangle();
        dr.draw(bmp, points, paint);

        DrawMethodLineBRH brh = new DrawMethodLineBRH();
        int[] temp = new int[4];
        for (int i = 4; i <= points.length - 4; i += 4) {
            for (int j = 0, k = i; j < 4; j++, k++) {
                temp[j] = points[k];
            }

            int codeA, codeB, code;
            int tempX, tempY;

            codeA = vCode(points, temp[0], temp[1]);
            codeB = vCode(points, temp[2], temp[3]);

            boolean check = true;
            while ((codeA | codeB) != 0) {
                if ((codeA & codeB) != 0) {
                    check = false;
                    break;
                }
                if (codeA != 0) {
                    code = codeA;
                    tempX = temp[0];
                    tempY = temp[1];
                } else {
                    code = codeB;
                    tempX = temp[2];
                    tempY = temp[3];
                }

                if ((code & LEFT) != 0) {
                    tempY += (temp[1] - temp[3]) * (points[0] - tempX) / (temp[0] - temp[2]);
                    tempX = points[0];
                } else if ((code & RIGHT) != 0) {
                    tempY += (temp[1] - temp[3]) * (points[2] - tempX) / (temp[0] - temp[2]);
                    tempX = points[2];
                }// если c ниже r, то передвигаем c на прямую y = r->y_min если c выше r, то передвигаем c на прямую y = r->y_max
                else if ((code & BOT) != 0) {
                    tempX += (temp[0] - temp[2]) * (points[1] - tempY) / (temp[1] - temp[3]);
                    tempY = points[1];
                } else if ((code & TOP) != 0) {
                    tempX += (temp[0] - temp[2]) * (points[3] - tempY) / (temp[1] - temp[3]);
                    tempY = points[3];
                }

                    /* обновляем код */
                if (code == codeA) {
                    temp[0] = tempX;
                    temp[1] = tempY;
                    codeA = vCode(points, tempX, tempY);
                } else {
                    temp[2] = tempX;
                    temp[3] = tempY;
                    codeB = vCode(points, tempX, tempY);
                }
            }
            if (check)
                brh.draw(bmp, temp, paint);
        }
    }

    private static final int LEFT = 1; /* двоичное 0001 */
    private static final int RIGHT = 2; /* двоичное 0010 */
    private static final int BOT = 4; /* двоичное 0100 */
    private static final int TOP = 8;  /* двоичное 1000 */

    private int vCode(int points[], int x, int y) {
        int result = 0;

        result += x < points[0] ? LEFT : 0;
        result += x > points[2] ? RIGHT : 0;
        result += y < points[1] ? BOT: 0;
        result += y > points[3] ? TOP : 0;

        return result;
    }
}
