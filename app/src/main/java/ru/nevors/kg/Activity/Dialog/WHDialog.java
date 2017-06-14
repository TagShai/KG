package ru.nevors.kg.Activity.Dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;

import ru.nevors.kg.R;


public class WHDialog extends AlertDialog.Builder {

    public interface OnWHSetListener {
        void onSet(int width,int height);
    }

    int _width;
    int _height;
    OnWHSetListener _mListener;

    public WHDialog(Activity activity, OnWHSetListener l, int startWidth,int startHeight) {
        super(activity);

        _mListener = l;
        _width = startWidth;
        _height = startHeight;

        setTitle("Размер холста");

        View linearlayout = activity.getLayoutInflater().inflate(R.layout.dialog_wh, null);
        setView(linearlayout);

        final EditText w = (EditText) linearlayout.findViewById(R.id.dialog_w);
        w.setText(String.valueOf(_width));
        final EditText h = (EditText) linearlayout.findViewById(R.id.dialog_h);
        h.setText(String.valueOf(_height));

        setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        _width = Integer.valueOf(w.getText().toString());
                        _height = Integer.valueOf(h.getText().toString());
                        _mListener.onSet(_width,_height);
                        dialog.dismiss();
                    }
                });
        create();
    }
}