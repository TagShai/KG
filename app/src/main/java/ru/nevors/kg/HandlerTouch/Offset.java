package ru.nevors.kg.HandlerTouch;

import android.view.MotionEvent;

import ru.nevors.kg.View.PD2;

public class Offset extends HandlerTouch {
    float _lastOffsetX, _lastOffsetY;
    float startX, startY;

    static private Offset offset;
    static private PD2 pd;

    private Offset(PD2 pd) {
        super(pd);
    }

    static public Offset getInstance(){
        if(offset==null){
            offset = new Offset(pd);
        }
        return offset;
    }

    static public Offset init(PD2 pd){
        return offset = new Offset(Offset.pd = pd);
    }

    float _startDis, _endDis;
    boolean _scale;

    @Override
    public void processing(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN: // нажатие
                startX = x;
                startY = y;
                break;
            /*case MotionEvent.ACTION_POINTER_DOWN:
                _pd.setOffset(_lastOffsetX + startX - x, _lastOffsetY + startY - y);
                _startDis = distance(x, y, event.getX(1), event.getY(1));
                _scale = true;
                break;
            case MotionEvent.ACTION_POINTER_UP:
                _scale = false;
                break;*/
            case MotionEvent.ACTION_MOVE: // движение
                /*if (_scale) {
                    float endDis = distance(event.getX(0), event.getY(0), event.getX(1), event.getY(1));
                    _endDis = endDis < _endDis?-2 * endDis:endDis/2;
                    int newScale = (int)(_endDis / _startDis + _pd.getScale());
                    _pd.setScale(newScale <= 0 ? 1 : newScale);
                } else {*/
                    _pd.setOffset(_lastOffsetX + startX - x, _lastOffsetY + startY - y);
                //}
                break;
            case MotionEvent.ACTION_UP: // отпускание
            case MotionEvent.ACTION_CANCEL:
                update();
                break;
        }
        _pd.invalidate();
    }

    public void update() {
        _lastOffsetX = _pd.getOffsetX();
        _lastOffsetY = _pd.getOffsetY();
    }

    private float distance(float x1, float y1, float x2, float y2) {
        return (float) Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
    }
}
