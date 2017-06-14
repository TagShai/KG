package ru.nevors.kg.View.MyPaint;

public class MyPaint {
    int _color1, _color2,_id;

    public void setColor(int color) {
        _color1 = color;
    }

    public void setColor2(int color) {
        _color2 = color;
    }

    public int getColor() {
        return _color1;
    }

    public int getColor2() {
        return _color2;
    }

    public int getId() {
        return _id;
    }

    public void setId(int id) {
        _id = id;
    }

    public MyPaint clone() {
        MyPaint p = new MyPaint();
        p.setColor(_color1);
        p.setColor2(_color2);
        p.setId(_id);
        return p;
    }
}