package net.londatiga.android.bluetooth;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SpeedFragment extends Fragment {
    public Long speed;
    private Speedometer speedometer;
    private TextView tv;
    public SpeedFragment()
    {

    }
    public static final SpeedFragment newInstance(Long speed) {
        SpeedFragment fragment = new SpeedFragment();

        final Bundle args = new Bundle(1);
        args.putLong("speed", speed);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_speed, container, false);
        speed = getArguments().getLong("speed");
        speedometer = (Speedometer) rootView.findViewById(R.id.Speedometer);
        tv=(TextView) rootView.findViewById(R.id.textView1);
        speedometer.onSpeedChanged(speed);
        tv.setText(String.valueOf(speed));
        return rootView;
    }
}
