package ru.nevors.kg.DrawMethod;

import android.graphics.Bitmap;

import java.util.HashMap;

import ru.nevors.kg.View.MyPaint.MyPaint;

public abstract class DrawMethod {
    public abstract void draw(Bitmap bmp, int[] points, MyPaint paint);
    public void setPixel(Bitmap bmp, int x, int y, int color){
        if(checkLimits(bmp,x,y)){
            bmp.setPixel(x,y,color);
        }
    }

    protected boolean checkLimits(Bitmap bmp, int x, int y) {
        int width = bmp.getWidth();
        int heigth = bmp.getHeight();
        return (x >= 0 && x < width
                && y >= 0 && y < heigth);
    }
}
