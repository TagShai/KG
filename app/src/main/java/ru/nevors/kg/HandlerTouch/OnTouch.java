package ru.nevors.kg.HandlerTouch;

import android.view.MotionEvent;
import android.view.View;

import ru.nevors.kg.View.HandlerDraw.HandlerDraw;
import ru.nevors.kg.View.PD2;

public class OnTouch extends HandlerTouch  implements View.OnTouchListener  {

    Offset _handlerOffset;
    HandlerTouch _handler;

    public OnTouch(PD2 pd) {
        super(pd);
        _handlerOffset = Offset.init(pd);
        _handler = _handlerOffset;
    }

    @Override
    public void processing(MotionEvent event) {
        _handler.processing(event);
    }

    public void setHandler(HandlerTouch handler){
        _handler = handler;
    }

    @Override
    public void update() {
        _handler.update();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        _handler.processing(event);
        return true;
    }

    static private OnTouch onTouch;

    static public OnTouch getInstance(){
        return onTouch;
    }

    public HandlerTouch getCurrent(){
        return _handler;
    }

    static public OnTouch init(PD2 pd){
        return onTouch = new OnTouch(pd);
    }
}
