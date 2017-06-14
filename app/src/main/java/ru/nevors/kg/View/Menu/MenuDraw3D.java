package ru.nevors.kg.View.Menu;

import android.content.Context;
import android.support.annotation.IntegerRes;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;

import ru.nevors.kg.Activity.Dialog.OpenFileDialog;
import ru.nevors.kg.Activity.Dialog.WHDialog;
import ru.nevors.kg.DrawMethod.DrawMethod;
import ru.nevors.kg.DrawMethod.DrawMethodObj3D.DrawMethodGraph;
import ru.nevors.kg.DrawMethod.DrawMethodObj3D.DrawMethodObj3D;
import ru.nevors.kg.DrawMethod.DrawMethodObj3D.ManagerZBuffer;
import ru.nevors.kg.DrawMethod.DrawMethodObj3D.ManagerZBufferNext;
import ru.nevors.kg.DrawMethod.DrawMethodObj3D.ManagerZBufferOut;
import ru.nevors.kg.DrawMethod.DrawMethodObj3DStandart;
import ru.nevors.kg.FileIO.FileIO;
import ru.nevors.kg.HandlerTouch.DrawFigure.Draw3D.Draw3D;
import ru.nevors.kg.HandlerTouch.DrawFigure.Draw3D.Projection3D.CenterProjection3D;
import ru.nevors.kg.HandlerTouch.DrawFigure.Draw3D.Projection3D.ObliqueAxonometricProjection3D;
import ru.nevors.kg.HandlerTouch.DrawFigure.Draw3D.Projection3D.OrthogonalProjection3D;
import ru.nevors.kg.HandlerTouch.DrawFigure.Draw3D.Projection3D.RectAxonometricProjection3D;
import ru.nevors.kg.HandlerTouch.DrawFigure.Draw3D.Transform3D;
import ru.nevors.kg.HandlerTouch.DrawFigure.Draw3D.Transform3DMove;
import ru.nevors.kg.HandlerTouch.DrawFigure.Draw3D.Transform3DReflect.Transform3DReflectOyz;
import ru.nevors.kg.HandlerTouch.DrawFigure.Draw3D.Transform3DReflect.Transform3DReflectOxz;
import ru.nevors.kg.HandlerTouch.DrawFigure.Draw3D.Transform3DReflect.Transform3DReflectOxy;
import ru.nevors.kg.HandlerTouch.DrawFigure.Draw3D.Transform3DRotate.Transform3DRotateOxOy;
import ru.nevors.kg.HandlerTouch.DrawFigure.Draw3D.Transform3DRotate.Transform3DRotateOx;
import ru.nevors.kg.HandlerTouch.DrawFigure.Draw3D.Transform3DRotate.Transform3DRotateOy;
import ru.nevors.kg.HandlerTouch.DrawFigure.Draw3D.Transform3DRotate.Transform3DRotateOz;
import ru.nevors.kg.HandlerTouch.DrawFigure.Draw3D.Transform3DScale;
import ru.nevors.kg.HandlerTouch.OnTouch;
import ru.nevors.kg.OBJ.Graph;
import ru.nevors.kg.OBJ.Point;
import ru.nevors.kg.OBJ.Triangle;
import ru.nevors.kg.R;
import ru.nevors.kg.View.PD2;

public class MenuDraw3D extends Menu {
    public MenuDraw3D(@NonNull Context context, @NonNull View anchor, PD2 pd, OnTouch onTouch) {
        super(context, anchor, pd, onTouch);
        inflate(R.menu.draw_3d);
        setOnMenuItemClickListener(this);
    }

    Draw3D draw3D = new Draw3D(_pd);
    DrawMethodGraph dmg = new DrawMethodGraph();

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        _onTouch.setHandler(draw3D);
        switch (item.getItemId()) {
            case R.id.transform_3d_move:
                draw3D.setTransform(new Transform3DMove());
                break;
            case R.id.transform_3d_refl_oxy:
                Transform3D tx = draw3D.getTransform();
                draw3D.setTransform(new Transform3DReflectOxy());
                draw3D.setTransform(tx);
                break;
            case R.id.transform_3d_refl_oyz:
                final Transform3D ty = draw3D.getTransform();
                draw3D.setTransform(new Transform3DReflectOyz());
                draw3D.setTransform(ty);
                break;
            case R.id.transform_3d_refl_oxz:
                Transform3D tz = draw3D.getTransform();
                draw3D.setTransform(new Transform3DReflectOxz());
                draw3D.setTransform(tz);
                break;
            case R.id.transform_3d_rotate:
                draw3D.setTransform(new Transform3DRotateOxOy());
                break;
            case R.id.transform_3d_rotate_ox:
                draw3D.setTransform(new Transform3DRotateOx());
                break;
            case R.id.transform_3d_rotate_oy:
                draw3D.setTransform(new Transform3DRotateOy());
                break;
            case R.id.transform_3d_rotate_oz:
                draw3D.setTransform(new Transform3DRotateOz());
                break;
            case R.id.transform_3d_scale:
                draw3D.setTransform(new Transform3DScale());
                break;
            case R.id.transform_3d_mode_standart:
                draw3D.setDrawMethod(new DrawMethodObj3DStandart());
                break;
            case R.id.transform_3d_mode_z_buffer:
                draw3D.setDrawMethod(new DrawMethodObj3D(new ManagerZBuffer()));
                break;
            case R.id.transform_3d_mode_z_buffer_out:
                draw3D.setDrawMethod(new DrawMethodObj3D(new ManagerZBufferOut()));
                break;
            case R.id.transform_3d_mode_horizont:
                draw3D.setDrawMethod(dmg);
                break;
            case R.id.transform_3d_projection_orto:
                draw3D.setProjection(new OrthogonalProjection3D());
                break;
            case R.id.transform_3d_projection_obliq_ax:
                draw3D.setProjection(new ObliqueAxonometricProjection3D());
                break;
            case R.id.transform_3d_projection_rect_ax:
                draw3D.setProjection(new RectAxonometricProjection3D());
                break;
            case R.id.transform_3d_projection_center:
                draw3D.setProjection(new CenterProjection3D());
                break;
            case R.id.transform_3d_reset:
                _pd.clear();
                _pd.invalidate();
                draw3D = new Draw3D(_pd);
                _onTouch.setHandler(draw3D);
                break;
            case R.id.select_3d_obj:
                OpenFileDialog ofd1 = new OpenFileDialog(_activity, new OpenFileDialog.OnFileNameSetListener() {
                    @Override
                    public void onSet(String fileName) {
                        Toast.makeText(_activity, "Начато считывание.", Toast.LENGTH_SHORT).show();
                        FileIO f = new FileIO();
                        Collection<Triangle> lines = f.readOBJ(fileName);
                        if (lines != null) {
                            Toast.makeText(_activity, "Считывание завершено.", Toast.LENGTH_SHORT).show();

                            for (Triangle tr : lines) {
                                draw3D.addTriangleOBJ(tr);
                            }
                        }
                    }
                });
                ofd1.show();
                break;
            case R.id.select_3d_gen_graf1:
                WHDialog whd = new WHDialog(_activity, new WHDialog.OnWHSetListener() {
                    int rw, lw, uh, dh, lz, rz;

                    @Override
                    public void onSet(int width, int height) {

                        rw = width / 2;
                        lw = -rw;
                        uh = height / 2;
                        dh = -uh;
                        lz = -height;
                        rz = height;

                        // int step = (int) (_pd.getScale() * 2);
                        int step = 1;

                        ArrayList<Point> list = new ArrayList<>();

                        for (int k = lz; k < rz; k++) {
                            for (int i = lw; i < rw; i += step) {
                                for (int j = dh; j < uh; j += step) {
                                    if (func(i, j, k)) {
                                        Point p = new Point(i,j,k);
                                        list.add(p);
                                    }
                                }
                            }
                        }

                        float[] result = new float[list.size() * 3];

                        int h = 0;
                        for (Point p : list) {
                            result[h++] = p.x;
                            result[h++] = p.y;
                            result[h++] = p.z;
                        }
                        draw3D.setDrawMethod(dmg);
                        draw3D.addPoints(result);
                    }

                    boolean func(float x, float y, float z) {
                        //return Math.abs(Math.sin(x)*Math.cos(y)-(1.5F * Math.cos(y) * Math.exp(-y)) - z) <= 5;
                        return Math.abs(x*x / 50 + y*y / 50 - z) <= 5;
                        //return (float) (Math.sin(x)*Math.cos(y)-(1.5F * Math.cos(y) * Math.exp(-y)));
                    }
                }, 100, 100);
                whd.show();
                break;
            case R.id.select_3d_gen_graf2:
                WHDialog whd2 = new WHDialog(_activity, new WHDialog.OnWHSetListener() {

                    @Override
                    public void onSet(int width, int height) {
                        Graph g = new Graph(_pd,draw3D);
                        g.onSet(width,height);
                    }
                },100,100);
                whd2.show();
                break;
        }
        return true;
    }
}
