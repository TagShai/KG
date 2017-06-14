package ru.nevors.kg.FileIO;

import android.graphics.Bitmap;
import android.graphics.PointF;
import android.graphics.RectF;
import android.nfc.FormatException;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ru.nevors.kg.FileIO.FileBitmapIO.BitMapException;
import ru.nevors.kg.FileIO.FileBitmapIO.FileBitmap24Reader;
import ru.nevors.kg.FileIO.FileBitmapIO.FileBitmap24Writer;
import ru.nevors.kg.OBJ.Triangle;
import ru.nevors.kg.OBJ.Point;

public class FileIO {
    final String LOG_TAG = "file";
    final String DIR_SD = "DRAW";
    final String FILENAME_SD = "test.txt";

    File _sdPath;

    public FileIO() {
        // проверяем доступность SD
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Log.d(LOG_TAG, "SD-карта не доступна: " + Environment.getExternalStorageState());
            return;
        }
        // получаем путь к SD
        _sdPath = Environment.getExternalStorageDirectory();
        // добавляем свой каталог к пути
        _sdPath = new File(_sdPath.getAbsolutePath() + "/" + DIR_SD);
        // создаем каталог
        if (_sdPath.mkdirs()) {
            Log.d(LOG_TAG, "Создал: " + _sdPath.getAbsolutePath());
        } else {
            Log.d(LOG_TAG, "не создал: " + _sdPath.getAbsolutePath());
        }
    }

    public String[] listFiles() {
        File[] files = _sdPath.listFiles();
        if (files == null) return null;
        String[] names = new String[files.length];
        for (int i = 0; i < files.length; i++) {
            names[i] = files[i].getName();
        }
        return names;
    }

    private File openFile(String fileName) throws IOException {
        File sdFile = new File(_sdPath, fileName);
        if (sdFile.exists()) {
            sdFile.createNewFile();
        }
        return sdFile;
    }

    public boolean writeBMP24(String fileName, Bitmap bmp) {
        // формируем объект File, который содержит путь к файлу
        boolean check = false;
        try {
            File sdFile = openFile(fileName);
            FileBitmap24Writer fbw = new FileBitmap24Writer(sdFile);
            check = fbw.write(bmp);
            Log.d(LOG_TAG, "Файл записан на SD: " + sdFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return check;
    }


    public Bitmap readBMP24(String fileName) throws BitMapException {
        // формируем объект File, который содержит путь к файлу
        Bitmap bmp = null;
        try {

            File sdFile = openFile(fileName);
            FileBitmap24Reader fbr = new FileBitmap24Reader(sdFile);
            bmp = fbr.read();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmp;
    }

    final String _patPoint = "v\\s+([^\\s]+)*\\s+([^\\s]+)*\\s+([^\\s]+)*";
    final Pattern _point = Pattern.compile(_patPoint);
    final String _patTreangl = "f\\s+([^/]+)/?([^/]+)?/?([^\\s]+)?\\s+([^/]+)/?([^/]+)?/?([^\\s]+)?\\s+([^/]+)/?([^/]+)?/?([^\\s]+)?";
    final Pattern _treangl = Pattern.compile(_patTreangl);

    public Collection<Triangle> readOBJ(String fileName) {
        // формируем объект File, который содержит путь к файлу
        ArrayList<Triangle> lines = null;
        try {
            ArrayList<Point> points = new ArrayList<>();
            lines = new ArrayList<>();
            File sdFile = openFile(fileName);
            // открываем поток для чтения
            BufferedReader br = new BufferedReader(new FileReader(sdFile));
            String str = "";
            // читаем содержимое
            while ((str = br.readLine()) != null) {
                Matcher temp = _point.matcher(str);
                if (temp.find()) {
                    Point point = new Point();
                    point.x = Float.valueOf(temp.group(1));
                    point.y = (-1.F) * Float.valueOf(temp.group(2));
                    point.z = Float.valueOf(temp.group(3));
                    points.add(point);
                    //Log.d(LOG_TAG, temp.group(1) + " " + temp.group(2));
                }
                temp = _treangl.matcher(str);
                if (temp.find()) {
                    int p[] = new int[3];
                    p[0] = Integer.valueOf(temp.group(1)) - 1;
                    p[1] = Integer.valueOf(temp.group(4)) - 1;
                    p[2] = Integer.valueOf(temp.group(7)) - 1;

                    Point p1[] = new Point[3];
                    p1[0] = points.get(p[0]);
                    p1[1] = points.get(p[1]);
                    p1[2] = points.get(p[2]);
                    Triangle tr = new Triangle(p1[0],p1[1],p1[2]);
                    lines.add(tr);

                    // Log.d(LOG_TAG, p[0] + " " +  p[1] + " " + p[2]);
                }

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }
}
