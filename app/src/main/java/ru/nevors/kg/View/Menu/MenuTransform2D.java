package ru.nevors.kg.View.Menu;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.view.View;

import ru.nevors.kg.HandlerTouch.DrawFigure.DrawRect.Transform2D.Transform2DMove;
import ru.nevors.kg.HandlerTouch.DrawFigure.DrawRect.Transform2D.Transform2DReflect;
import ru.nevors.kg.HandlerTouch.DrawFigure.DrawRect.Transform2D.Transform2DRotate;
import ru.nevors.kg.HandlerTouch.DrawFigure.DrawRect.Transform2D.Transform2DScale;
import ru.nevors.kg.HandlerTouch.OnTouch;
import ru.nevors.kg.R;
import ru.nevors.kg.View.PD2;

public class MenuTransform2D extends Menu {
    public MenuTransform2D(@NonNull Context context, @NonNull View anchor, PD2 pd, OnTouch onTouch) {
        super(context, anchor, pd, onTouch);
        inflate(R.menu.transform_2d);
        setOnMenuItemClickListener(this);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        _onTouch.update();
        switch (item.getItemId()) {
            case R.id.transform_2d_move:
                _onTouch.setHandler(new Transform2DMove(_pd));
                break;
            case R.id.transform_2d_scale:
                _onTouch.setHandler(new Transform2DScale(_pd));
                break;
            case R.id.transform_2d_rotate:
                _onTouch.setHandler(new Transform2DRotate(_pd));
                break;
            case R.id.transform_2d_refl:
                _onTouch.setHandler(new Transform2DReflect(_pd));
                break;
        }
        return true;
    }
}
