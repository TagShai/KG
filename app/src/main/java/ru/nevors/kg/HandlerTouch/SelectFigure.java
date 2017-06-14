package ru.nevors.kg.HandlerTouch;

import android.view.MotionEvent;

import ru.nevors.kg.HandlerTouch.DrawFigure.DrawFigure;
import ru.nevors.kg.View.PD2;

public class SelectFigure extends HandlerTouch {
    public SelectFigure(PD2 pd) {
        super(pd);
    }

    @Override
    public void processing(MotionEvent event) {
        int x = _pd.translateX(event.getX());
        int y = _pd.translateY(event.getY());
        if (event.getAction() == MotionEvent.ACTION_UP) {
            DrawFigure df = _pd.getFigure(x, y, DrawFigure.getAccuracy());
            if(df != null){
                OnTouch.getInstance().setHandler(df);
                df.restore();
                _pd.invalidate();
            }
        }
    }
}
