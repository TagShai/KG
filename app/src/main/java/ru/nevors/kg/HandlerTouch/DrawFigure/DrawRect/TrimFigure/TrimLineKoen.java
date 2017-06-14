package ru.nevors.kg.HandlerTouch.DrawFigure.DrawRect.TrimFigure;

import ru.nevors.kg.DrawMethod.DrawMethod;
import ru.nevors.kg.DrawMethod.TrimMethod.TrimMethodKoen;
import ru.nevors.kg.HandlerTouch.DrawFigure.DrawFigure;
import ru.nevors.kg.HandlerTouch.DrawFigure.DrawRect.DrawRect;
import ru.nevors.kg.View.PD2;

public class TrimLineKoen extends DrawRect {
    int state = -2;//1-edit line 2-edit window -1 -new line -2-new window

    public TrimLineKoen(PD2 pd) {
        super(pd, new TrimMethodKoen());
    }

    protected TrimLineKoen(PD2 pd, DrawMethod dm, int[] points, boolean up) {
        super(pd, dm, points, up);
    }

    public DrawFigure clone() {
        return new TrimLineKoen(_pd, _dm, _points.clone(), true);
    }

    int _startX, _startY;
    int _diffX, _diffY;

    @Override
    protected void actionDown() {
        _startX = _x;
        _startY = _y;

        int l = state == -1 ? 4 : 0;
        int r = state == -1 ? _points.length : 4;

        _k = -1;
        _pd.clearPointBuf();
        for (int i = l; i < r; i += 2) {
            if (Math.abs(_points[i] - _x) < _accuracy && Math.abs(_points[i + 1] - _y) < _accuracy) {
                _k = i;
                return;
            }
        }

        switch (state) {
            case -2:
                _pd.activateModify();
                _points = new int[4];
                for (int i = 0; i < _points.length; i += 2) {
                    _points[i] = _x;
                    _points[i + 1] = _y;
                }
                state = 2;
                _k=2;
                break;
            case -1:
                int i = _points.length;
                addSpaceForPoint(4);
                for (int j = i; j < _points.length; j += 2) {
                    _points[j] = _x;
                    _points[j + 1] = _y;
                }
                _k = i + 2;
                state = -1;
                break;
        }
    }
    @Override
    public void restore(){
        super.restore();
        state = 2;
    }

    @Override
    protected void actionMove() {
        _diffX = _x - _startX;
        _diffY = _y - _startY;

        switch (state) {
            case 2:
                if(_k!=-1){
                    _points[_k] = _x;
                    _points[_k + 1] = _y;
                }else{
                    for(int i=0;i<4;i+=2){
                        _points[i] +=_diffX;
                        _points[i + 1] += _diffY;
                        _startX = _x;
                        _startY = _y;
                    }
                }
                break;
            case -1:
                _points[_k] = _x;
                _points[_k + 1] = _y;
                break;
        }
        draw(this);
    }

    @Override
    protected void actionUp() {
        draw(this);
        //прорисовка квандратиков
        drawManagement();
    }

    private void addSpaceForPoint(int size) {
        int[] points = new int[_points.length + size];
        for (int i = 0; i < _points.length; i++) {
            points[i] = _points[i];
        }
        _points = points;
    }

    @Override
    protected void drawManagement() {
        int l = state == -1 ? 4 : 0;
        int r = state == -1 ? _points.length : 4;
        for (int i = l; i < r; i += 2) {
            _pd.addPointBuf(_points[i], _points[i + 1], _accuracy);
        }
    }

    public void setEditLineState(){
        state = -1;
        _pd.clearPointBuf();
        drawManagement();
        draw(this);
        _pd.invalidate();
    }
    public void setEditWindowState(){
        state = 2;
        _pd.clearPointBuf();
        drawManagement();
        draw(this);
        _pd.invalidate();
    }
    public void addLine(int points[]){
        int i = _points.length;
        int k = 0;
        addSpaceForPoint(4);
        for (int j = i; j < _points.length; j++) {
            _points[j] = points[k++];
        }
    }
}
