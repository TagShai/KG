package ru.nevors.kg.View.Fragments;

import android.support.v7.widget.PopupMenu;
import android.view.View;

public class ButtonListener implements View.OnClickListener {
    PopupMenu _menu;
    public ButtonListener(PopupMenu menu){
        _menu = menu;
    }
    @Override
    public void onClick(View v) {
        _menu.show();
    }
}
