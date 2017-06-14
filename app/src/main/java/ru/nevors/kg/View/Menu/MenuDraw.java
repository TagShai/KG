package ru.nevors.kg.View.Menu;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.Collection;

import ru.nevors.kg.Activity.Dialog.OpenFileDialog;
import ru.nevors.kg.Activity.Dialog.SetValueDialog;
import ru.nevors.kg.FileIO.FileIO;
import ru.nevors.kg.HandlerTouch.DrawFigure.Draw2D.Draw2D;
import ru.nevors.kg.HandlerTouch.DrawFigure.DrawFigure;
import ru.nevors.kg.HandlerTouch.DrawFigure.DrawPoint;
import ru.nevors.kg.HandlerTouch.DrawFigure.DrawRect.DrawCurve.DrawBSpline;
import ru.nevors.kg.HandlerTouch.DrawFigure.DrawRect.DrawCurve.DrawBezier;
import ru.nevors.kg.HandlerTouch.DrawFigure.DrawRect.DrawCircle.DrawCircleBRH;
import ru.nevors.kg.HandlerTouch.DrawFigure.DrawRect.DrawCircle.DrawCircleDDA;
import ru.nevors.kg.HandlerTouch.DrawFigure.DrawRect.DrawCurve.DrawErmit;
import ru.nevors.kg.HandlerTouch.DrawFigure.DrawRect.DrawLine.DrawLineArea;
import ru.nevors.kg.HandlerTouch.DrawFigure.DrawRect.DrawLine.DrawLineBRH;
import ru.nevors.kg.HandlerTouch.DrawFigure.DrawRect.DrawLine.DrawLineDDA;
import ru.nevors.kg.HandlerTouch.DrawFigure.DrawRect.DrawCurve.DrawNURBSSpline;
import ru.nevors.kg.HandlerTouch.DrawFigure.DrawRect.DrawLine.DrawLineSelect;
import ru.nevors.kg.HandlerTouch.DrawFigure.DrawRect.DrawLine.DrawLineVy;
import ru.nevors.kg.HandlerTouch.DrawFigure.DrawRect.DrawRectangle;
import ru.nevors.kg.HandlerTouch.DrawFigure.DrawRect.FillCircle;
import ru.nevors.kg.HandlerTouch.DrawFigure.DrawRect.FillPolygon;
import ru.nevors.kg.HandlerTouch.DrawFigure.DrawRect.FillRect;
import ru.nevors.kg.HandlerTouch.DrawFigure.DrawRect.FillTriangle;
import ru.nevors.kg.HandlerTouch.DrawFigure.FillSpace;
import ru.nevors.kg.HandlerTouch.OnTouch;
import ru.nevors.kg.OBJ.Triangle;
import ru.nevors.kg.R;
import ru.nevors.kg.View.PD2;

public class MenuDraw extends Menu {

    public MenuDraw(@NonNull Context context, @NonNull View anchor, PD2 pd, OnTouch onTouch) {
        super(context, anchor, pd, onTouch);
        inflate(R.menu.draw);
        setOnMenuItemClickListener(this);
    }

    DrawPoint dp = new DrawPoint(_pd);

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        _onTouch.update();
        switch (item.getItemId()) {
            case R.id.draw_line_dda:
                _onTouch.setHandler(new DrawLineDDA(_pd));
                break;
            case R.id.draw_line_brh:
                _onTouch.setHandler(new DrawLineBRH(_pd));
                break;
            case R.id.draw_circle_dda:
                _onTouch.setHandler(new DrawCircleDDA(_pd));
                break;
            case R.id.draw_circle_brh:
                _onTouch.setHandler(new DrawCircleBRH(_pd));
                break;
            case R.id.draw_line_bezier:
                _onTouch.setHandler(new DrawBezier(_pd));
                break;
            case R.id.draw_line_ermit:
                _onTouch.setHandler(new DrawErmit(_pd));
                break;
            case R.id.draw_b_spline:
                SetValueDialog ssd1 = new SetValueDialog(_activity, new SetValueDialog.OnScaleSetListener() {
                    @Override
                    public void onSet(float scale) {
                        _onTouch.setHandler(new DrawBSpline(_pd, (int) scale));
                    }
                }, "Порядок сплайна", 3, 2, 10);
                ssd1.show();
                break;
            case R.id.draw_nurbs_spline:
                SetValueDialog ssd2 = new SetValueDialog(_activity, new SetValueDialog.OnScaleSetListener() {
                    @Override
                    public void onSet(float scale) {
                        _onTouch.setHandler(new DrawNURBSSpline(_pd, (int) scale));
                    }
                }, "Порядок NURBS сплайна", 3, 2, 10);
                ssd2.show();
                break;
            case R.id.fill_circle:
                _onTouch.setHandler(new FillCircle(_pd));
                break;
            case R.id.fill_rect:
                _onTouch.setHandler(new FillRect(_pd));
                break;
            case R.id.fill_polygon:
                _onTouch.setHandler(new FillPolygon(_pd));
                break;
            case R.id.draw_polygon:
                break;
            case R.id.fill_seed:
                _onTouch.setHandler(new FillSpace(_pd));
                break;
            case R.id.fill_triangle:
                _onTouch.setHandler(new FillTriangle(_pd));
                break;
            case R.id.draw_line_area:
                _onTouch.setHandler(new DrawLineArea(_pd));
                break;
            case R.id.draw_line_vy:
                _onTouch.setHandler(new DrawLineVy(_pd));
                break;
            case R.id.draw_line_select:
                _onTouch.setHandler(new DrawLineSelect(_pd));
                break;
            case R.id.draw_rect:
                _onTouch.setHandler(new DrawRectangle(_pd));
                break;
            case R.id.draw_obj_2d:
                final Draw2D draw2D = new Draw2D(_pd);
                _onTouch.setHandler(draw2D);
                OpenFileDialog ofd1 = new OpenFileDialog(_activity, new OpenFileDialog.OnFileNameSetListener() {
                    @Override
                    public void onSet(String fileName) {
                        Toast.makeText(_activity, "Начато считывание.", Toast.LENGTH_SHORT).show();
                        FileIO f = new FileIO();
                        Collection<Triangle> lines = f.readOBJ(fileName);
                        if (lines != null) {
                            Toast.makeText(_activity, "Считывание завершено.", Toast.LENGTH_SHORT).show();

                            for (Triangle tr : lines) {
                                draw2D.addTriangle(tr);
                            }
                        }
                    }
                });
                ofd1.show();
                break;
        }
        return true;
    }
}
