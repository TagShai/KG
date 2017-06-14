package ru.nevors.kg.View.HandlerDraw;

import android.graphics.Canvas;
import android.graphics.Rect;

import ru.nevors.kg.HandlerTouch.DrawFigure.DrawFigure;
import ru.nevors.kg.DrawMethod.DrawMethod;
import ru.nevors.kg.View.Layer;
import ru.nevors.kg.View.MyPaint.MyPaint;
import ru.nevors.kg.View.MyPaint.MyPaintId;
import ru.nevors.kg.View.PD2;

public class HandlerDrawStandart extends HandlerDraw {
    MyPaintId _pId;

    public HandlerDrawStandart(PD2 pd, Rect src, Rect dst) {
        super(pd, src, dst);
    }

    @Override
    public void processing(Canvas canvas) {
        canvas.drawBitmap(_pd.getBitMap(), _src, _dst, _paint);
        _pId = new MyPaintId();
    }

    private boolean _isUpdate = false;

    @Override
    public void draw(DrawFigure df, MyPaint paint, boolean overwrite) {
        if (df != null) {
            DrawMethod dm = df.getDrawMethod();
            int[] points = df.getPoints();

            if (!_isUpdate) {
                int id = _pd.lastIdFigure;
                if (id == 0) {
                    id = _pd.countFigure;
                    _pd.countFigure++;
                } else {
                    _pd.lastIdFigure = 0;
                }
                id |= 0xFF << 24;

                paint.setId(id);
                _pd.getLayers().put(id, new Layer(df, paint.clone()));

                _pId.setId(id);
            } else {
                _pId.setId(paint.getId());
            }
            dm.draw(_pd.getLayerBmp(), points, _pId);
            dm.draw(_pd.getBitMap(), points, paint);
        }
    }

    public void setUpdate(boolean isUpdate) {
        _isUpdate = isUpdate;
    }
}
