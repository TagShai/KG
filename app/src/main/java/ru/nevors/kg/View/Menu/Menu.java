package ru.nevors.kg.View.Menu;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.PopupMenu;
import android.view.View;

import ru.nevors.kg.HandlerTouch.OnTouch;
import ru.nevors.kg.View.PD2;

public abstract class Menu extends PopupMenu implements PopupMenu.OnMenuItemClickListener {
    PD2 _pd;
    OnTouch _onTouch;
    Activity _activity;
    public Menu(@NonNull Context context, @NonNull View anchor, PD2 pd, OnTouch onTouch) {
        super(context, anchor);
        _activity = (Activity)context;
        _pd = pd;
        _onTouch = onTouch;
    }
}
