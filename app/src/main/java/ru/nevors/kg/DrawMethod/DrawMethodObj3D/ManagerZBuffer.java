package ru.nevors.kg.DrawMethod.DrawMethodObj3D;

import android.graphics.Bitmap;

public class ManagerZBuffer implements IManagerPixel{
    protected int[][] zBuf;
    int w = 100;
    int h = 100;

    public ManagerZBuffer() {
        zBuf = new int[w][w];
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                zBuf[i][j] = Integer.MIN_VALUE;
            }
        }
    }

    public void clearBufer() {
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                zBuf[i][j] = Integer.MIN_VALUE;
            }
        }
    }

    private void copyBuffer(int width, int height) {
        int[][] result = new int[width][height];

        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                result[i][j] = zBuf[i][j];
            }
        }

        for (int i = w; i < width; i++) {
            for (int j = 0; j < height; j++) {
                result[i][j] = Integer.MIN_VALUE;
            }
        }
        for (int i = 0; i < w; i++) {
            for (int j = h; j < height; j++) {
                result[i][j] = Integer.MIN_VALUE;
            }
        }

        zBuf = result;
        w = width;
        h = height;
    }

    public void setPixel(Bitmap bmp, int x, int y, int z, int color) {
        int width = bmp.getWidth();
        int height = bmp.getHeight();
        if (x >= 0 && x < width && y >= 0 && y < height) {
            if (height != w || width != h) {
                copyBuffer(width, height);
            }
            if (zBuf[x][y] < z) {
                zBuf[x][y] = z;
                bmp.setPixel(x, y, color);
            }
        }
    }
}
