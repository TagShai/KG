package ru.nevors.kg.View;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import java.util.Random;
import java.util.TreeMap;

import ru.nevors.kg.HandlerTouch.DrawFigure.DrawFigure;
import ru.nevors.kg.View.HandlerDraw.HandlerDraw;
import ru.nevors.kg.View.HandlerDraw.HandlerDrawModified;
import ru.nevors.kg.View.HandlerDraw.HandlerDrawStandart;
import ru.nevors.kg.View.MyPaint.MyPaint;


public class PD2 extends View {

    float _scale;
    int _nCol, _nRow;
    float _offsetX, _offsetY;

    HandlerDraw _handlerDraw;
    HandlerDrawModified _handlerDrawModified;
    HandlerDrawStandart _handlerStandart;

    MyPaint _paint;

    Bitmap _bmp;
    Bitmap _bmpBuf;
    Bitmap _layerBmp;

    Rect _src, _dst;

    TreeMap<Integer, Layer> _layers;

    public static PD2 inst;

    public PD2(Context context) {
        super(context);
        _src = new Rect();
        _dst = new Rect();

        _handlerDrawModified = new HandlerDrawModified(this, _src, _dst);
        _handlerStandart = new HandlerDrawStandart(this, _src, _dst);
        _handlerDraw = _handlerStandart;
        _paint = new MyPaint();
        _paint.setColor(Color.BLACK);
        _paint.setColor2(0xFFFFDD00);

        _layers = new TreeMap<>();

        inst = this;
    }


    public void set(float scale, int nCol, int nRow) {
        _scale = scale;
        _nCol = nCol;
        _nRow = nRow;
        build(nCol, nRow);
        updateLimits();
    }

    int _startI, _startJ;

    @Override
    protected void onDraw(Canvas canvas) {
        _handlerDraw.processing(canvas);
    }

    private void updateLimits() {
        int width = getWidth();
        int bufW = (int) (_nCol * _scale);
        if (width > bufW) {
            width = bufW;
        } else {
            if (_dst.left == 0) {
                width += _scale - width % _scale;
            }
        }
        int height = getHeight();
        int bufH = (int) (_nRow * _scale);
        if (height > bufH) {
            height = bufH;
        } else {
            if (_dst.top == 0) {
                height += _scale - height % _scale;
            }
        }

        _startI = (int) ((_offsetX + _dst.left) / _scale);
        _startJ = (int) ((_offsetY + _dst.top) / _scale);
        int endI = (int) (_startI + ((width - _dst.left) / _scale));
        int endJ = (int) (_startJ + ((height - _dst.top) / _scale));

        _src.set(_startI, _startJ, endI, endJ);
        _dst.right = width;
        _dst.bottom = height;

        _handlerDrawModified.updatePoint();
    }

    public void setOffset(float x, float y) {
        int col = (int) (_bmp.getWidth() * _scale);
        int row = (int) (_bmp.getHeight() * _scale);
        int width = getWidth();
        int height = getHeight();

        if (x >= 0 && width < col) {
            if (col - x >= width) {
                _offsetX = x;
                _dst.left = 0;
            } else {
                _offsetX = col - width;
                _dst.left = (int) (width % _scale - _scale);
            }
        } else {
            _offsetX = 0;
            _dst.left = 0;
        }
        if (y >= 0 && height < row) {
            if (row - y >= height) {
                _offsetY = y;
                _dst.top = 0;
            } else {
                _offsetY = row - height;
                _dst.top = (int) (height % _scale - _scale);
            }
        } else {
            _offsetY = 0;
            _dst.top = 0;
        }
        updateLimits();
    }


    public void fillRandomColor() {
        Random rnd = new Random();
        int size = (int) _scale;
        int size2 = size / 2;
        Canvas canvas = new Canvas(_bmp);
        Paint p = new Paint();
        p.setStrokeWidth(size);
        for (int i = size2; i < _nCol + size; i += size) {
            for (int j = size2; j < _nRow + size; j += size) {
                p.setColor(rnd.nextInt() % Color.rgb(125, 125, 125) + Color.rgb(125, 125, 125));
                canvas.drawPoint(i, j, p);
            }
        }
    }

    public void drawPoint(int x, int y) {
        if (x >= 0 && x < _nCol && y >= 0 && y < _nRow) {
            _bmp.setPixel(x, y, _paint.getColor());
        }
    }

    public int countFigure = 1;
    public int lastIdFigure = 0;

    public void draw(DrawFigure df) {
        _handlerDraw.draw(df, _paint, true);
    }

    public void draw(DrawFigure df, boolean save) {
        _handlerDraw.draw(df, _paint, save);
    }

    public float getOffsetY() {
        return _offsetY;
    }

    public float getOffsetX() {
        return _offsetX;
    }

    public int translateX(float x) {
        return (int) ((x - _dst.left) / _scale) + _startI;
    }

    public int translateY(float y) {
        return (int) ((y - _dst.top) / _scale) + _startJ;
    }

    private void build(int nCol, int nRow) {
        _bmp = Bitmap.createBitmap(nCol, nRow, Bitmap.Config.ARGB_8888);
        _bmpBuf = Bitmap.createBitmap(nCol, nRow, Bitmap.Config.ARGB_8888);
        _layerBmp = Bitmap.createBitmap(nCol, nRow, Bitmap.Config.ARGB_8888);
    }

    private void reBuild(int nCol, int nRow, int nOldCol, int nOldRow) {
        Bitmap bmp1 = Bitmap.createBitmap(nCol, nRow, Bitmap.Config.ARGB_8888);
        Bitmap bmp2 = Bitmap.createBitmap(nCol, nRow, Bitmap.Config.ARGB_8888);
        _bmpBuf = Bitmap.createBitmap(nCol, nRow, Bitmap.Config.ARGB_8888);

        nCol = nOldCol < nCol ? nOldCol : nCol;
        nRow = nOldRow < nRow ? nOldRow : nRow;
        int[] pixels = new int[nCol * nRow];

        _bmp.getPixels(pixels, 0, nCol, 0, 0, nCol, nRow);
        bmp1.setPixels(pixels, 0, nCol, 0, 0, nCol, nRow);
        _bmp.recycle();
        _bmp = bmp1;

        _layerBmp.getPixels(pixels, 0, nCol, 0, 0, nCol, nRow);
        bmp2.setPixels(pixels, 0, nCol, 0, 0, nCol, nRow);
        _layerBmp.recycle();
        _layerBmp = bmp2;
    }

    public void resize(int nCol, int nRow) {
        reBuild(nCol, nRow, _nCol, _nRow);
        _nCol = nCol;
        _nRow = nRow;
        setOffset(_offsetX, _offsetY);
        updateLimits();
    }

    public void setScale(float scale) {
        _scale = scale;

        _offsetX = _scale * _startI;
        _offsetY = _scale * _startJ;

        setOffset(_offsetX, _offsetY);

        updateLimits();
    }

    public void setBitmap(Bitmap bitmap) {
        _bmp.recycle();
        _bmp = bitmap;
        _nCol = _bmp.getWidth();
        _nRow = _bmp.getHeight();
        setOffset(0, 0);
        updateLimits();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        updateLimits();
    }

    public Bitmap getBitMap() {
        return _bmp;
    }

    public Bitmap getBufBitMap() {
        return _bmpBuf;
    }

    public Bitmap getLayerBmp() {
        return _layerBmp;
    }

    public TreeMap<Integer, Layer> getLayers() {
        return _layers;
    }

    public void setPaint(MyPaint paint) {
        _paint = paint;
    }

    public MyPaint getPaint() {
        return _paint;
    }

    public float getScale() {
        return _scale;
    }

    public int getStartI() {
        return _startI;
    }

    public int getStartJ() {
        return _startJ;
    }

    public void clear() {
        _bmp.eraseColor(0);
        _layerBmp.eraseColor(0);
        _layers.clear();
        lastIdFigure = 0;
        countFigure = 1;
    }

    boolean _activate;

    public void activateModify() {
        _activate = true;
        _handlerDraw = _handlerDrawModified;
    }

    public boolean deActivateModify() {
        if (_activate) {
            _activate = false;
            _bmpBuf.eraseColor(0);
            _handlerDrawModified.clearPoint();
            _handlerDraw = _handlerStandart;
            invalidate();
            return true;
        }
        return false;
    }

    public void addPointBuf(int x, int y, int size) {
        _handlerDrawModified.addPoint(x, y, size);
    }

    public void addLineBuf(int x1, int y1, int x2, int y2) {
        _handlerDrawModified.addLine(x1, y1, x2, y2);
    }

    public void clearPointBuf() {
        _handlerDrawModified.clearPoint();
    }

    public void update() {
        _bmp.eraseColor(0);
        _layerBmp.eraseColor(0);
        _handlerStandart.setUpdate(true);
        for (Layer l : _layers.values()) {
            _handlerStandart.draw(l.getDrawFigure(), l.getPaint(), false);
        }
        _handlerStandart.setUpdate(false);
    }

    public DrawFigure getFigure2(int x, int y, int accuracy) {
        int xmc = x - accuracy;
        int xpc = x + accuracy;
        int ymc = y - accuracy;
        int ypc = y + accuracy;
        for (int i = xmc; i < xpc; i++) {
            for (int j = ymc; j < ypc; j++) {
                try {
                    if (_layerBmp.getPixel(i, j) != 0) {
                        lastIdFigure = _layerBmp.getPixel(i, j);
                        DrawFigure result = _layers.get(lastIdFigure).getDrawFigure();
                        _layers.remove(lastIdFigure);
                        update();
                        return result;
                    }
                } catch (Exception e) {
                }
            }
        }
        return null;
    }

    public DrawFigure getFigure(int x, int y, int accuracy) {
        x -= accuracy;
        y -= accuracy;
        int n = accuracy * 2;  // размер матрицы
        // центр
        int i = n / 2;
        int j = n / 2;
        // задаем границы движения
        int min_i = i;
        int max_i = i; // влево вправо
        int min_j = j;
        int max_j = j; // вверх вниз
        int d = 0; // сначала пойдем влево
        for (int a = 1; a < n * n; a++) {

            //try {
            if (_layerBmp.getPixel(x + i, y + j) != 0) {
                lastIdFigure = _layerBmp.getPixel(x + i, y + j);
                Layer layer = _layers.get(lastIdFigure);
                DrawFigure result = null;
                if (layer != null) {
                    result = layer.getDrawFigure();
                    _paint = layer.getPaint();
                    _layers.remove(lastIdFigure);
                    update();
                }
                return result;
            }
            /*} catch (Exception e) {
            }*/

            switch (d) {
                case 0:
                    i -= 1;  // движение влево
                    if (i < min_i) { // проверка выхода за заполненную центральную часть слева
                        d = 1; // меняем направление
                        min_i = i; // увеличиваем заполненную часть влево
                    }
                    break;
                case 1:  // движение вверх проверка сверху
                    j -= 1;
                    if (j < min_j) {
                        d = 2;
                        min_j = j;
                    }
                    break;
                case 2:  // движение вправо проверка справа
                    i += 1;
                    if (i > max_i) {
                        d = 3;
                        max_i = i;
                    }
                    break;
                case 3:  // движение вниз проверка снизу
                    j += 1;
                    if (j > max_j) {
                        d = 0;
                        max_j = j;
                    }
            }
        }
        return null;
    }
}

