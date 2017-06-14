package ru.nevors.kg.View.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import ru.nevors.kg.HandlerTouch.Offset;
import ru.nevors.kg.HandlerTouch.OnTouch;
import ru.nevors.kg.R;
import ru.nevors.kg.View.Menu.MenuDraw;
import ru.nevors.kg.View.Menu.MenuDraw3D;
import ru.nevors.kg.View.Menu.MenuFile;
import ru.nevors.kg.View.Menu.MenuFractals;
import ru.nevors.kg.View.Menu.MenuTransform2D;
import ru.nevors.kg.View.Menu.MenuTrim;
import ru.nevors.kg.View.PD2;

public class Tools extends Fragment {
    PD2 _pd;
    OnTouch _onTouch;


    public Tools(PD2 pd, OnTouch onTouch) {
        _pd = pd;
        _onTouch = onTouch;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = inflater.inflate(R.layout.tools, container, false);
        Button draw = (Button) v.findViewById(R.id.b_draw);
        draw.setOnClickListener(new ButtonListener(new MenuDraw(getActivity(), draw, _pd, _onTouch)));

        Button file = (Button) v.findViewById(R.id.b_file);
        file.setOnClickListener(new ButtonListener(new MenuFile(getActivity(), file, _pd, _onTouch)));

        Button trim = (Button) v.findViewById(R.id.trim);
        trim.setOnClickListener(new ButtonListener(new MenuTrim(getActivity(), trim, _pd, _onTouch)));

        Button transform2D = (Button) v.findViewById(R.id.b_transform_2d);
        transform2D.setOnClickListener(new ButtonListener(new MenuTransform2D(getActivity(), transform2D, _pd, _onTouch)));

        Button draw3D = (Button) v.findViewById(R.id.b_draw_3d);
        draw3D.setOnClickListener(new ButtonListener(new MenuDraw3D(getActivity(), draw3D, _pd, _onTouch)));

        Button fractals = (Button) v.findViewById(R.id.b_fractals);
        fractals.setOnClickListener(new ButtonListener(new MenuFractals(getActivity(), fractals, _pd, _onTouch)));

        Button onTouch = (Button) v.findViewById(R.id.b_toch);
        onTouch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    _onTouch.update();
                    _onTouch.setHandler(Offset.getInstance());
                    Toast.makeText(getActivity(), "Скролл вкл.", Toast.LENGTH_SHORT).show();

            }
        });

        Button clear = (Button) v.findViewById(R.id.b_clear);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _onTouch.update();
                _pd.clear();
                _pd.invalidate();
            }
        });
        return v;
    }

}
