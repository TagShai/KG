package ru.nevors.kg.View.Menu;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.view.View;

import ru.nevors.kg.Activity.Dialog.SetValueDialog;
import ru.nevors.kg.Activity.Dialog.WHDialog;
import ru.nevors.kg.DrawMethod.DrawMethodFractal.DrawMethodDragon;
import ru.nevors.kg.DrawMethod.DrawMethodFractal.DrawMethodFerm;
import ru.nevors.kg.DrawMethod.DrawMethodFractal.DrawMethodMandelbrot;
import ru.nevors.kg.DrawMethod.DrawMethodFractal.DrawMethodPlasma;
import ru.nevors.kg.DrawMethod.DrawMethodFractal.DrawMethodTreePifagor;
import ru.nevors.kg.HandlerTouch.DrawFigure.DrawFigureParam;
import ru.nevors.kg.HandlerTouch.DrawFigure.DrawRect.DrawRectImpl;
import ru.nevors.kg.HandlerTouch.OnTouch;
import ru.nevors.kg.R;
import ru.nevors.kg.View.PD2;

public class MenuFractals extends Menu {
    public MenuFractals(@NonNull Context context, @NonNull View anchor, PD2 pd, OnTouch onTouch) {
        super(context, anchor, pd, onTouch);
        inflate(R.menu.fractals);
        setOnMenuItemClickListener(this);
    }

    SetValueDialog svd;
    WHDialog whd;

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        _onTouch.update();
        switch (item.getItemId()) {
            case R.id.fractal_dragon:
                svd = new SetValueDialog(_activity, new SetValueDialog.OnScaleSetListener() {
                    @Override
                    public void onSet(float scale) {
                        _onTouch.setHandler(new DrawRectImpl(_pd, new DrawMethodDragon((int) scale)));
                    }
                }, "Итерации", 9, 1, 50);
                svd.show();
                break;
            case R.id.fractal_plasma:
                whd = new WHDialog(_activity, new WHDialog.OnWHSetListener() {
                    @Override
                    public void onSet(int width, int height) {
                        _onTouch.setHandler(new DrawFigureParam(_pd, new DrawMethodPlasma(width, height)));
                    }
                }, 100, 100);
                whd.show();
                break;
            case R.id.fractal_tree_pifagor:
                svd = new SetValueDialog(_activity, new SetValueDialog.OnScaleSetListener() {
                    @Override
                    public void onSet(float scale) {
                        _onTouch.setHandler(new DrawRectImpl(_pd, new DrawMethodTreePifagor((int) scale)));
                    }
                }, "Итерации", 9, 1, 50);
                svd.show();
                break;
            case R.id.fractal_mandelbrot:
                whd = new WHDialog(_activity, new WHDialog.OnWHSetListener() {
                    @Override
                    public void onSet(final int width, final int height) {
                        svd = new SetValueDialog(_activity, new SetValueDialog.OnScaleSetListener() {
                            @Override
                            public void onSet(float scale) {
                                final float iter = scale;
                                svd = new SetValueDialog(_activity, new SetValueDialog.OnScaleSetListener() {
                                    @Override
                                    public void onSet(float scale) {
                                        _onTouch.setHandler(new DrawFigureParam(_pd, new DrawMethodMandelbrot(width, height, (int) iter, 1/scale)));
                                    }
                                }, "Масштаб 1/scale", 100, 1F, 10000);
                                svd.show();
                            }
                        }, "Итерации", 9, 1, 50);
                        svd.show();
                    }
                }, 500, 500);
                whd.show();
                break;
            case R.id.fractal_fern:
                whd = new WHDialog(_activity, new WHDialog.OnWHSetListener() {
                    @Override
                    public void onSet(final int width, final int height) {
                        svd = new SetValueDialog(_activity, new SetValueDialog.OnScaleSetListener() {
                            @Override
                            public void onSet(float scale) {
                                _onTouch.setHandler(new DrawFigureParam(_pd, new DrawMethodFerm(width, height, (int) scale)));
                            }
                        }, "Итерации", 10000, 1, 50000);
                        svd.show();
                    }
                }, 100, 100);
                whd.show();
                break;
        }
        return true;
    }
}
