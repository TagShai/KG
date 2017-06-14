package ru.nevors.kg.DrawMethod.DrawMethodObj3D;

import android.graphics.Bitmap;

public interface IManagerPixel {
    void setPixel(Bitmap bmp, int x, int y, int z, int color);
    void clearBufer();
}
