package ru.nevors.kg.FileIO.FileBitmapIO;

import android.graphics.Bitmap;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileBitmap24Writer {
    FileOutputStream _fs;
    byte[]_mas;
    int _pos;

    public FileBitmap24Writer(File file) throws FileNotFoundException {
        _fs = new FileOutputStream(file);
    }

    public boolean write(Bitmap bmp) throws IOException {
        int width = bmp.getWidth();
        int height = bmp.getHeight();
        int spacerSize = (width % 4);
        int lenght = width * height * 3 + height * spacerSize;
        int size = lenght + 54;

        _mas = new byte[size];
        _pos = 0;


        //тип файла
        writeBytes(0x4D42, 2);
        //размер
        writeBytes(size, 4);
        //пустые байты
        writeBytes(0, 4);
        //адрес начала массива цветов
        writeBytes(54, 4);
        //размер структуры
        writeBytes(40, 4);
        //ширина
        writeBytes(width, 4);
        //высота
        writeBytes(height, 4);
        //количество плоскостей
        writeBytes(1, 2);
        //количество бит на пиксель
        writeBytes(24, 2);
        //метод сжатия
        writeBytes(0, 4);
        //длинна растрового массива
        writeBytes(0, 4);
        //горизонтальное разрешения
        writeBytes(0, 4);
        //вертикальное разрешения
        writeBytes(0, 4);
        //количество цветов изображения
        writeBytes(0, 4);
        //количество основных цветов изображения
        writeBytes(0, 4);

        for (int i = height - 1; i >= 0; i--) {
            for (int j = 0; j < width; j++) {
                int color = bmp.getPixel(j, i);
                if (color == 0) {
                    writeBytes(0xFFFFFF, 3);
                } else {
                    writeBytes(color & 0xFF, 1);
                    writeBytes((color >> 8) & 0xFF, 1);
                    writeBytes((color >> 16) & 0xFF, 1);
                }
            }
            writeBytes(0, spacerSize);
        }
        _fs.write(_mas);
        _fs.close();
        return true;
    }

    private void writeBytes(int value, int n) throws IOException {
        for (int i = 0; i < n; i++) {
            _mas[_pos++]= (byte)(value & 0xFF);
            value >>= 8;
        }
    }
}
