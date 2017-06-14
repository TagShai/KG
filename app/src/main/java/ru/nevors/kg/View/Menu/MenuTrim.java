package ru.nevors.kg.View.Menu;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.Collection;

import ru.nevors.kg.Activity.Dialog.OpenFileDialog;
import ru.nevors.kg.FileIO.FileIO;
import ru.nevors.kg.HandlerTouch.DrawFigure.DrawRect.TrimFigure.TrimLineKoen;
import ru.nevors.kg.HandlerTouch.DrawFigure.DrawRect.TrimFigure.TrimPolygonSH;
import ru.nevors.kg.HandlerTouch.DrawFigure.DrawRect.TrimFigure.TrimPolygonVA;
import ru.nevors.kg.HandlerTouch.OnTouch;
import ru.nevors.kg.OBJ.Triangle;
import ru.nevors.kg.R;
import ru.nevors.kg.View.PD2;

public class MenuTrim extends Menu {
    public MenuTrim(@NonNull Context context, @NonNull View anchor, PD2 pd, OnTouch onTouch) {
        super(context, anchor, pd, onTouch);
        inflate(R.menu.trim);
        setOnMenuItemClickListener(this);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        TrimLineKoen tlk = null;
        TrimPolygonSH tpsh = null;
        TrimPolygonVA tpva = null;
        switch (item.getItemId()) {
            case R.id.run_sh:
                tpsh = new TrimPolygonSH(_pd);
                _onTouch.setHandler(tpsh);
                break;
            case R.id.run_va:
                tpva = new TrimPolygonVA(_pd);
                _onTouch.setHandler(tpva);
                break;
            case R.id.run_koen:
                tlk = new TrimLineKoen(_pd);
                _onTouch.setHandler(tlk);
                break;
            case R.id.edit_line_koen:
                try {
                    tlk = (TrimLineKoen) _onTouch.getCurrent();
                } catch (Exception e) {
                }
                if (tlk != null) {
                    tlk.setEditLineState();
                }
                break;
            case R.id.edit_line_va:
                try {
                    tpva = (TrimPolygonVA) _onTouch.getCurrent();
                } catch (Exception e) {
                }
                if (tpva != null) {
                    tpva.setEditLineState();
                }
                break;
            case R.id.edit_line_sh:
                try {
                    tpsh = (TrimPolygonSH) _onTouch.getCurrent();
                } catch (Exception e) {
                }
                if (tpsh != null) {
                    tpsh.setEditLineState();
                }
                break;
            case R.id.edit_window_koen:
                try {
                    tlk = (TrimLineKoen) _onTouch.getCurrent();
                    tlk.setEditWindowState();
                } catch (Exception e) {
                }
                break;
            case R.id.edit_window_va:
                try {
                    tpva = (TrimPolygonVA) _onTouch.getCurrent();
                    tpva.setEditWindowState();
                } catch (Exception e) {
                }
                break;
            case R.id.edit_window_sh:
                try {
                    tpsh = (TrimPolygonSH) _onTouch.getCurrent();
                } catch (Exception e) {
                }
                if (tpsh != null) {
                    tpsh.setEditWindowState();
                }
                break;
            case R.id.add_polygon_sh:
                try {
                    tpsh = (TrimPolygonSH) _onTouch.getCurrent();
                    tpsh.addPolygon();
                } catch (Exception e) {
                }
                break;
            case R.id.add_polygon_va:
                try {
                    tpva = (TrimPolygonVA) _onTouch.getCurrent();
                    tpva.addPolygon();
                    tpva.setEditLineState();
                } catch (Exception e) {
                }
                break;
            case R.id.add_line_obj_koen:
                try {
                    tlk = (TrimLineKoen) _onTouch.getCurrent();
                } catch (Exception e) {
                }
                if (tlk != null) {
                    final TrimLineKoen finalTlk = tlk;
                    OpenFileDialog ofd2 = new OpenFileDialog(_activity, new OpenFileDialog.OnFileNameSetListener() {
                        @Override
                        public void onSet(String fileName) {
                            Toast.makeText(_activity, "Начато считывание.", Toast.LENGTH_SHORT).show();
                            FileIO f = new FileIO();
                            Collection<Triangle> lines = f.readOBJ(fileName);
                            if (lines != null) {
                                Toast.makeText(_activity, "Считывание завершено.\nНачата отрисовка.", Toast.LENGTH_SHORT).show();
                                long start = System.currentTimeMillis();
                                int width = _pd.getBitMap().getWidth();
                                int height = _pd.getBitMap().getHeight();

                                int[] points = new int[4];
                                for (Triangle tr : lines) {
                                    points[0] = (int) ((tr.p1.x + 1.F) * width / 2.F);
                                    points[1] = (int) ((tr.p1.y + 1.F) * height / 2.F);
                                    points[2] = (int) ((tr.p2.x + 1.F) * width / 2.F);
                                    points[3] = (int) ((tr.p2.y + 1.F) * height / 2.F);
                                    finalTlk.addLine(points);
                                    points[0] = (int) ((tr.p2.x + 1.F) * width / 2.F);
                                    points[1] = (int) ((tr.p2.y + 1.F) * height / 2.F);
                                    points[2] = (int) ((tr.p3.x + 1.F) * width / 2.F);
                                    points[3] = (int) ((tr.p3.y + 1.F) * height / 2.F);
                                    finalTlk.addLine(points);
                                    points[0] = (int) ((tr.p3.x + 1.F) * width / 2.F);
                                    points[1] = (int) ((tr.p3.y + 1.F) * height / 2.F);
                                    points[2] = (int) ((tr.p1.x + 1.F) * width / 2.F);
                                    points[3] = (int) ((tr.p1.y + 1.F) * height / 2.F);
                                    finalTlk.addLine(points);
                                }
                                long end = System.currentTimeMillis() - start;
                                _pd.draw(finalTlk.clone());
                                _pd.invalidate();

                                Toast.makeText(_activity, "Готово.Время: " + end + " ms", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(_activity, "Не удалось считать файл.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    ofd2.show();
                }
                break;
            case R.id.add_line_obj_va:
                try {
                    tpva = (TrimPolygonVA) _onTouch.getCurrent();
                } catch (Exception e) {
                }
                if (tpva != null) {
                    final TrimPolygonVA finalTpva = tpva;
                    OpenFileDialog ofd2 = new OpenFileDialog(_activity, new OpenFileDialog.OnFileNameSetListener() {
                        @Override
                        public void onSet(String fileName) {
                            Toast.makeText(_activity, "Начато считывание.", Toast.LENGTH_SHORT).show();
                            FileIO f = new FileIO();
                            Collection<Triangle> lines = f.readOBJ(fileName);
                            if (lines != null) {
                                Toast.makeText(_activity, "Считывание завершено.\nНачата отрисовка.", Toast.LENGTH_SHORT).show();
                                long start = System.currentTimeMillis();
                                int width = _pd.getBitMap().getWidth();
                                int height = _pd.getBitMap().getHeight();

                                int x ,y;
                                for (Triangle tr : lines) {
                                    finalTpva.addPolygon();
                                    x = (int) ((tr.p1.x + 1.F) * width / 2.F);
                                    y = (int) ((tr.p1.y + 1.F) * height / 2.F);
                                    finalTpva.addPoint(x,y);
                                    x = (int) ((tr.p2.x + 1.F) * width / 2.F);
                                    y = (int) ((tr.p2.y + 1.F) * height / 2.F);
                                    finalTpva.addPoint(x,y);
                                    x = (int) ((tr.p3.x + 1.F) * width / 2.F);
                                    y = (int) ((tr.p3.y + 1.F) * height / 2.F);
                                    finalTpva.addPoint(x,y);
                                }
                                long end = System.currentTimeMillis() - start;
                                _pd.draw(finalTpva.clone());
                                _pd.invalidate();

                                Toast.makeText(_activity, "Готово.Время: " + end + " ms", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(_activity, "Не удалось считать файл.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    ofd2.show();
                }
                break;
            case R.id.add_line_obj_sh:
                try {
                    tpsh = (TrimPolygonSH) _onTouch.getCurrent();
                } catch (Exception e) {
                }
                if (tpsh != null) {
                    final TrimPolygonSH finalTpsh = tpsh;
                    OpenFileDialog ofd2 = new OpenFileDialog(_activity, new OpenFileDialog.OnFileNameSetListener() {
                        @Override
                        public void onSet(String fileName) {
                            Toast.makeText(_activity, "Начато считывание.", Toast.LENGTH_SHORT).show();
                            FileIO f = new FileIO();
                            Collection<Triangle> lines = f.readOBJ(fileName);
                            if (lines != null) {
                                Toast.makeText(_activity, "Считывание завершено.\nНачата отрисовка.", Toast.LENGTH_SHORT).show();
                                long start = System.currentTimeMillis();
                                int width = _pd.getBitMap().getWidth();
                                int height = _pd.getBitMap().getHeight();

                                int x ,y;
                                for (Triangle tr : lines) {
                                    finalTpsh.addPolygon();
                                    x = (int) ((tr.p1.x + 1.F) * width / 2.F);
                                    y = (int) ((tr.p1.y + 1.F) * height / 2.F);
                                    finalTpsh.addPoint(x,y);
                                    x = (int) ((tr.p2.x + 1.F) * width / 2.F);
                                    y = (int) ((tr.p2.y + 1.F) * height / 2.F);
                                    finalTpsh.addPoint(x,y);
                                    x = (int) ((tr.p3.x + 1.F) * width / 2.F);
                                    y = (int) ((tr.p3.y + 1.F) * height / 2.F);
                                    finalTpsh.addPoint(x,y);
                                }
                                long end = System.currentTimeMillis() - start;
                                _pd.draw(finalTpsh.clone());
                                _pd.invalidate();

                                Toast.makeText(_activity, "Готово.Время: " + end + " ms", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(_activity, "Не удалось считать файл.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    ofd2.show();
                }
                break;
            case R.id.add_point_window_va:
                try {
                    tpva = (TrimPolygonVA) _onTouch.getCurrent();
                    tpva.setAddPointsWindowState();
                } catch (Exception e) {
                }
                break;
        }
        return true;
    }
}
