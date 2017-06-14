package ru.nevors.kg.Activity.Dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.RectF;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Collection;

import ru.nevors.kg.FileIO.FileIO;

public class OpenFileDialog extends AlertDialog.Builder {

    public interface OnFileNameSetListener {
        void onSet(String fileName);
    }

    String[] _names;
    OnFileNameSetListener _mListener;

    public OpenFileDialog(Activity activity, OnFileNameSetListener l) {
        super(activity);

        _mListener = l;

        setTitle("Имя файла");
        FileIO f = new FileIO();
        _names = f.listFiles();
        setSingleChoiceItems(_names, -1, null);

        setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ListView lv = ((AlertDialog) dialog).getListView();
                        if (lv != null) {
                            try {
                                String fileName = _names[lv.getCheckedItemPosition()];
                                _mListener.onSet(fileName);
                                dialog.dismiss();
                            } catch (Exception e) {
                                Toast.makeText(getContext(), "Выберете файл.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
        create();
    }
}
