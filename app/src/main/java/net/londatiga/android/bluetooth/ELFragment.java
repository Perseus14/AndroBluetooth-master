package net.londatiga.android.bluetooth;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class ELFragment extends Fragment {
    public Double load;
    private Speedometer speedometer;
    private Button cancel;
    private TextView tv;
    public ELFragment()
    {

    }
    public static final ELFragment newInstance(Double load) {
        ELFragment fragment = new ELFragment();

        final Bundle args = new Bundle(1);
        args.putDouble("load", load);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_load, container, false);
        load = getArguments().getDouble("load");
        tv = (TextView) rootView.findViewById(R.id.textView6);
        speedometer = (Speedometer) rootView.findViewById(R.id.Speedometer5);
        float f = Float.valueOf(String.valueOf(load));
        speedometer.onSpeedChanged(f);
        tv.setText(String.valueOf(f));
        return rootView;
    }
}
