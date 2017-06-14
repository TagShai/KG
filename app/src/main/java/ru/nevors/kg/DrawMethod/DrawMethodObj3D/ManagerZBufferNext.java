package ru.nevors.kg.DrawMethod.DrawMethodObj3D;

import android.graphics.Bitmap;

public class ManagerZBufferNext implements IManagerPixel {
    protected int[]zBuf;
    int w = 100;
    int h = 100;
    int count;

    public ManagerZBufferNext() {
        count= w*h;
        zBuf = new int[count];
        for (int i = 0; i < count; i++) {
                zBuf[i] = Integer.MIN_VALUE;
        }
    }

    public void clearBufer() {
        for (int i = 0; i < count; i++) {
            zBuf[i] = Integer.MIN_VALUE;
        }
    }

    private void copyBuffer(int width, int height) {
        int newCount = width * height;
        int[]result = new int[newCount];

        for (int i = 0; i < newCount; i++) {
            result[i] = Integer.MIN_VALUE;
        }
        for (int i = 0; i < count; i++) {
            zBuf[i] = zBuf[i];
        }

        zBuf = result;
        w = width;
        h = height;
        count = newCount;
    }

    public void setPixel(Bitmap bmp, int x, int y, int z, int color) {
        int width = bmp.getWidth();
        int height = bmp.getHeight();
        if (x >= 0 && x < width && y >= 0 && y < height) {
            if (height != w || width != h) {
                copyBuffer(width, height);
            }
            int i = x + y * h;
            if (zBuf[i] < z) {
                zBuf[i] = z;
                bmp.setPixel(x, y, color);
            }
        }
    }
}
