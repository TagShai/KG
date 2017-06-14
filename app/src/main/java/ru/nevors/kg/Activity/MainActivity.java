package ru.nevors.kg.Activity;

import android.app.FragmentTransaction;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.LinearLayout;

import ru.nevors.kg.Activity.Dialog.ColorPickerDialog;
import ru.nevors.kg.Activity.Dialog.SetValueDialog;
import ru.nevors.kg.Activity.Dialog.WHDialog;
import ru.nevors.kg.DrawMethod.DrawMethodCurve.DrawMethodBezierErmit;
import ru.nevors.kg.DrawMethod.TrimMethod.TrimMethodVA.TrimMethodVA;
import ru.nevors.kg.HandlerTouch.DrawFigure.DrawFigure;
import ru.nevors.kg.HandlerTouch.DrawFigure.DrawRect.DrawRectImp;
import ru.nevors.kg.HandlerTouch.Offset;
import ru.nevors.kg.HandlerTouch.OnTouch;
import ru.nevors.kg.HandlerTouch.SelectFigure;
import ru.nevors.kg.R;
import ru.nevors.kg.View.PD2;
import ru.nevors.kg.View.Fragments.Tools;

public class MainActivity extends AppCompatActivity {

    PD2 _pd;

    OnTouch _onTouch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);

        LinearLayout ll = (LinearLayout) findViewById(R.id.lin_l);
        ll.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        lp.weight = 1;

        float scale = 3.F;
        int nCol = 500;
        int nRow = 500;
        //_pd = new PlaceDraw(this);
        _pd = new PD2(this);

        _onTouch = OnTouch.init(_pd);

        _pd.set(scale, nCol, nRow);
        _pd.setOnTouchListener(_onTouch);

        ll.addView(_pd, lp);

        setContentView(ll);

        Tools tools = new Tools(_pd,_onTouch);

        FragmentTransaction fTrans = getFragmentManager().beginTransaction();

        fTrans.add(R.id.lin_l,tools);

        fTrans.commit();/**/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // TODO Auto-generated method stub
        // добавляем пункты меню
        menu.add(3, 1, 0, "Выбрать элемент");
        menu.add(3, 3, 0, "Рандомно зарисовать");
        menu.add(3, 41, 0, "Цвет 1");
        menu.add(3, 42, 0, "Цвет 2");
        menu.add(3, 4, 0, "Масштаб");
        menu.add(3, 5, 0, "Размер холста");
        menu.add(3, 97, 0, "Размер кв. редактирования");
        menu.add(3, 100, 0, "Перерисовать");
        menu.add(3,150,0,"Безье+Эрмит");
        return super.onCreateOptionsMenu(menu);
    }
    // обработка нажатий

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        _onTouch.update();
        // TODO Auto-generated method stub
        switch (itemId) {
            case 150:
                _onTouch.setHandler(new DrawRectImp(_pd,new DrawMethodBezierErmit()));
                break;
            case 3:
                _pd.fillRandomColor();

                _pd.invalidate();
                break;
            case 4:
                SetValueDialog ssd = new SetValueDialog(this, new SetValueDialog.OnScaleSetListener() {
                    @Override
                    public void onSet(float scale) {
                        _pd.setScale(scale);
                        _pd.invalidate();
                        Offset.getInstance().update();
                    }
                },"Масштаб!", _pd.getScale(),.5F,50);
                ssd.show();
                break;
            case 97:
                SetValueDialog ssd1 = new SetValueDialog(this, new SetValueDialog.OnScaleSetListener() {
                    @Override
                    public void onSet(float scale) {
                        DrawFigure.setAccuracy((int) scale);
                    }
                },"Размер кв. ред.", DrawFigure.getAccuracy(),1,100);
                ssd1.show();
                break;
            case 5:
                WHDialog whd = new WHDialog(this, new WHDialog.OnWHSetListener() {
                    @Override
                    public void onSet(int width, int height) {
                        _pd.resize(width, height);
                        Offset.getInstance().update();

                        _pd.invalidate();
                    }
                }, _pd.getBitMap().getWidth(), _pd.getBitMap().getHeight());
                whd.show();
                break;
            case 41:
                ColorPickerDialog cpd = new ColorPickerDialog(this, new ColorPickerDialog.OnColorChangedListener() {
                    @Override
                    public void colorChanged(int color) {
                        _pd.getPaint().setColor(color);
                    }
                }, _pd.getPaint().getColor());
                cpd.show();
                break;
            case 42:
                ColorPickerDialog cp1 = new ColorPickerDialog(this, new ColorPickerDialog.OnColorChangedListener() {
                    @Override
                    public void colorChanged(int color) {
                        _pd.getPaint().setColor2(color);
                    }
                }, _pd.getPaint().getColor2());
                cp1.show();
                break;
            case 1:
                _onTouch.setHandler(new SelectFigure(_pd));
                break;
            case 100:
                _pd.update();
                _pd.invalidate();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}