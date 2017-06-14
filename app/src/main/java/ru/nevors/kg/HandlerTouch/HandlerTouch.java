package ru.nevors.kg.HandlerTouch;

import android.view.MotionEvent;

import ru.nevors.kg.View.PD2;

public abstract class HandlerTouch {
    protected PD2 _pd;

    public HandlerTouch(PD2 pd) {
        _pd = pd;
    }

    public abstract void processing(MotionEvent event);

    public void update() {
    }
}
