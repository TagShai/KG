package ru.nevors.kg.View.Menu;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.Collection;
import java.util.Random;

import ru.nevors.kg.Activity.Dialog.OpenFileDialog;
import ru.nevors.kg.Activity.Dialog.SaveFileDialog;
import ru.nevors.kg.DrawMethod.DrawMethod;
import ru.nevors.kg.DrawMethod.DrawMethodLine.DrawMethodLineDDA;
import ru.nevors.kg.DrawMethod.FillMethod.FillMethodTriangle;
import ru.nevors.kg.HandlerTouch.DrawFigure.DrawFigure;
import ru.nevors.kg.HandlerTouch.DrawFigure.DrawRect.DrawLine.DrawLineBRH;
import ru.nevors.kg.HandlerTouch.DrawFigure.DrawRect.DrawLine.DrawLineDDA;
import ru.nevors.kg.HandlerTouch.DrawFigure.DrawRect.FillTriangle;
import ru.nevors.kg.HandlerTouch.Offset;
import ru.nevors.kg.HandlerTouch.OnTouch;
import ru.nevors.kg.DrawMethod.DrawMethodLine.DrawMethodLineBRH;
import ru.nevors.kg.FileIO.FileBitmapIO.BitMapException;
import ru.nevors.kg.FileIO.FileIO;
import ru.nevors.kg.OBJ.Triangle;
import ru.nevors.kg.R;
import ru.nevors.kg.View.PD2;

public class MenuFile extends Menu {
    public MenuFile(@NonNull Context context, @NonNull View anchor, PD2 pd, OnTouch onTouch) {
        super(context, anchor, pd, onTouch);
        inflate(R.menu.file);
        setOnMenuItemClickListener(this);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        _onTouch.update();
        switch (item.getItemId()){
            case R.id.draw_obj_dda:
                OpenFileDialog ofd1 = new OpenFileDialog(_activity, new OpenFileDialog.OnFileNameSetListener() {
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
                            DrawMethod dm = new DrawMethodLineDDA();
                            int[] points = new int[4];
                            DrawFigure df = new DrawLineBRH(_pd);
                            for (Triangle tr : lines) {
                                points[0] = (int) ((tr.p1.x + 1.F) * width / 2.F);
                                points[1] = (int) ((tr.p1.y + 1.F) * height / 2.F);
                                points[2] = (int) ((tr.p2.x+ 1.F) * width / 2.F);
                                points[3] = (int) ((tr.p2.y + 1.F) * height / 2.F);
                                df.setPoints(points.clone());
                                DrawFigure t = df.clone();
                                _pd.draw(t);
                                points[0] = (int) ((tr.p2.x + 1.F) * width / 2.F);
                                points[1] = (int) ((tr.p2.y + 1.F) * height / 2.F);
                                points[2] = (int) ((tr.p3.x+ 1.F) * width / 2.F);
                                points[3] = (int) ((tr.p3.y + 1.F) * height / 2.F);
                                df.setPoints(points.clone());
                                t = df.clone();
                                _pd.draw(t);
                                points[0] = (int) ((tr.p3.x + 1.F) * width / 2.F);
                                points[1] = (int) ((tr.p3.y + 1.F) * height / 2.F);
                                points[2] = (int) ((tr.p1.x+ 1.F) * width / 2.F);
                                points[3] = (int) ((tr.p1.y + 1.F) * height / 2.F);
                                df.setPoints(points.clone());
                                t = df.clone();
                                _pd.draw(t);
                            }
                            long end = System.currentTimeMillis() - start;
                            _pd.invalidate();

                            Toast.makeText(_activity, "Готово.Время: " + end + " ms", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(_activity, "Не удалось считать файл.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                ofd1.show();
                break;
            case R.id.draw_obj_brh:
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
                            DrawMethodLineBRH dm = new DrawMethodLineBRH();
                            int[] points = new int[4];
                            DrawFigure df = new DrawLineBRH(_pd);
                            for (Triangle tr : lines) {
                                points[0] = (int) ((tr.p1.x + 1.F) * width / 2.F);
                                points[1] = (int) ((tr.p1.y + 1.F) * height / 2.F);
                                points[2] = (int) ((tr.p2.x+ 1.F) * width / 2.F);
                                points[3] = (int) ((tr.p2.y + 1.F) * height / 2.F);
                                df.setPoints(points.clone());
                                DrawFigure t = df.clone();
                                _pd.draw(t);
                                points[0] = (int) ((tr.p2.x + 1.F) * width / 2.F);
                                points[1] = (int) ((tr.p2.y + 1.F) * height / 2.F);
                                points[2] = (int) ((tr.p3.x+ 1.F) * width / 2.F);
                                points[3] = (int) ((tr.p3.y + 1.F) * height / 2.F);
                                df.setPoints(points.clone());
                                t = df.clone();
                                _pd.draw(t);
                                points[0] = (int) ((tr.p3.x + 1.F) * width / 2.F);
                                points[1] = (int) ((tr.p3.y + 1.F) * height / 2.F);
                                points[2] = (int) ((tr.p1.x+ 1.F) * width / 2.F);
                                points[3] = (int) ((tr.p1.y + 1.F) * height / 2.F);
                                df.setPoints(points.clone());
                                t = df.clone();
                                _pd.draw(t);
                            }
                            long end = System.currentTimeMillis() - start;
                            _pd.invalidate();

                            Toast.makeText(_activity, "Готово.Время: " + end + " ms", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(_activity, "Не удалось считать файл.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                ofd2.show();
                break;
            case R.id.save_file:
                SaveFileDialog sfd1 = new SaveFileDialog(_activity, new SaveFileDialog.OnFileNameSetListener() {
                    @Override
                    public void onSet(String fileName) {
                        Toast.makeText(_activity, "Запись на файл начата.", Toast.LENGTH_SHORT).show();
                        FileIO f = new FileIO();
                        f.writeBMP24(fileName, _pd.getBitMap());
                        Toast.makeText(_activity, "Запись завершена.", Toast.LENGTH_SHORT).show();
                    }
                }, "1234.bmp");
                sfd1.show();
                break;
            case R.id.open_file:
                OpenFileDialog ofd3 = new OpenFileDialog(_activity, new OpenFileDialog.OnFileNameSetListener() {
                    @Override
                    public void onSet(String fileName) {
                        Toast.makeText(_activity, "Начато считывание.", Toast.LENGTH_SHORT).show();
                        FileIO f = new FileIO();
                        Bitmap bmp = null;
                        try {
                            bmp = f.readBMP24(fileName);
                        } catch (BitMapException e) {
                            Toast.makeText(_activity, e.getMessage(), Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                        if (bmp != null) {
                            Toast.makeText(_activity, "Считывание завершено.\nНачата отрисовка.", Toast.LENGTH_SHORT).show();
                            _pd.setBitmap(bmp);
                            _pd.invalidate();

                            Offset.getInstance().update();
                            Toast.makeText(_activity, "Готово.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(_activity, "Не удалось считать файл.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                ofd3.show();
                break;
            case R.id.draw_obj_rgb:
                OpenFileDialog ofd4 = new OpenFileDialog(_activity, new OpenFileDialog.OnFileNameSetListener() {
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
                            DrawMethod dm = new FillMethodTriangle();
                            int[] points = new int[6];
                            DrawFigure df = new FillTriangle(_pd);
                            Random rnd = new Random();
                            for (Triangle tr : lines) {
                                points[0] = (int) ((tr.p1.x + 1.F) * width / 2.F);
                                points[1] = (int) ((tr.p1.y + 1.F) * height / 2.F);
                                points[2] = (int) ((tr.p2.x+ 1.F) * width / 2.F);
                                points[3] = (int) ((tr.p2.y + 1.F) * height / 2.F);
                                points[4] = (int) ((tr.p3.x+ 1.F) * width / 2.F);
                                points[5] = (int) ((tr.p3.y + 1.F) * height / 2.F);
                                df.setPoints(points.clone());
                                DrawFigure t = df.clone();
                                _pd.getPaint().setColor2(rnd.nextInt() % Color.rgb(125, 125, 125) + Color.rgb(125, 125, 125));
                                _pd.draw(t);
                            }
                            long end = System.currentTimeMillis() - start;
                            _pd.invalidate();

                            Toast.makeText(_activity, "Готово.Время: " + end + " ms", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(_activity, "Не удалось считать файл.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                ofd4.show();
                break;
            case R.id.draw_obj_color:
                OpenFileDialog ofd5 = new OpenFileDialog(_activity, new OpenFileDialog.OnFileNameSetListener() {
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
                            DrawMethod dm = new FillMethodTriangle();
                            int[] points = new int[6];
                            DrawFigure df = new FillTriangle(_pd);
                            Random rnd = new Random();
                            for (Triangle tr : lines) {
                                points[0] = (int) ((tr.p1.x + 1.F) * width / 2.F);
                                points[1] = (int) ((tr.p1.y + 1.F) * height / 2.F);
                                points[2] = (int) ((tr.p2.x+ 1.F) * width / 2.F);
                                points[3] = (int) ((tr.p2.y + 1.F) * height / 2.F);
                                points[4] = (int) ((tr.p3.x+ 1.F) * width / 2.F);
                                points[5] = (int) ((tr.p3.y + 1.F) * height / 2.F);
                                df.setPoints(points.clone());
                                DrawFigure t = df.clone();
                                _pd.draw(t);
                            }
                            long end = System.currentTimeMillis() - start;
                            _pd.invalidate();

                            Toast.makeText(_activity, "Готово.Время: " + end + " ms", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(_activity, "Не удалось считать файл.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                ofd5.show();
                break;
        }
        return true;
    }
}
