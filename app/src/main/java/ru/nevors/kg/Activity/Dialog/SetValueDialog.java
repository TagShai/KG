package ru.nevors.kg.Activity.Dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

import ru.nevors.kg.R;

public class SetValueDialog extends AlertDialog.Builder {

    public interface OnScaleSetListener {
        void onSet(float scale);
    }

    float _value;
    float _maxVal,_minVal;
    OnScaleSetListener _mListener;

    public SetValueDialog(Activity activity, OnScaleSetListener l, String title, float startValue, float minValue, float maxValue) {
        super(activity);
        _value = startValue;
        _maxVal = maxValue;
        _minVal = minValue;
        _mListener = l;
        setTitle(title);

        View linearlayout = activity.getLayoutInflater().inflate(R.layout.dialog_value, null);
        setView(linearlayout);

        final EditText et = (EditText) linearlayout.findViewById(R.id.size_pixel_et);
        final SeekBar sk = (SeekBar) linearlayout.findViewById(R.id.size_pixel_sk);
        sk.setProgress((int) (_value));
        et.setText(String.valueOf(_value));
        sk.setMax((int)maxValue);

        sk.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                et.setText(progress < _minVal ? String.valueOf(_minVal) : String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String sEt = et.getText().toString();
                        if (sEt.length() != 0) {
                            _value = Float.valueOf(sEt);
                            if(_value>=_minVal){
                                _mListener.onSet(_value);
                                dialog.dismiss();
                            }else{
                                Toast.makeText(getContext(), "Не удалось установить значение.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
        create();
    }
}
