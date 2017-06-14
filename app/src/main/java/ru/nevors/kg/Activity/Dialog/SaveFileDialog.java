package ru.nevors.kg.Activity.Dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.RectF;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Collection;

import ru.nevors.kg.FileIO.FileIO;

public class SaveFileDialog extends AlertDialog.Builder {

    public interface OnFileNameSetListener {
        void onSet(String fileName);
    }

    OnFileNameSetListener _mListener;

    public SaveFileDialog(Activity activity, OnFileNameSetListener l,String startFileName) {
        super(activity);

        _mListener = l;

        setTitle("Имя файла");
        final EditText et = new EditText(activity);
        et.setText(startFileName);
        setView(et);

        setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String fileName = et.getText().toString();
                        _mListener.onSet(fileName);
                        dialog.dismiss();
                    }
                });
        create();
    }
}
