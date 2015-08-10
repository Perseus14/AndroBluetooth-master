package net.londatiga.android.bluetooth;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

public class RPMFragment extends Fragment {
    public Double rpm;
    private Speedometer speedometer;
    private TextView tv;
    public RPMFragment()
    {

    }
    public static final RPMFragment newInstance(Double rpm) {
        RPMFragment fragment = new RPMFragment();

        final Bundle args = new Bundle(1);
        args.putDouble("rpm", rpm);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_rpm, container, false);
        rpm = getArguments().getDouble("rpm");
        speedometer = (Speedometer) rootView.findViewById(R.id.Speedometer1);
        tv = (TextView) rootView.findViewById(R.id.textView2);
        float f = Float.valueOf(String.valueOf(rpm));
        speedometer.onSpeedChanged(f);
        tv.setText(String.valueOf(f));

        return rootView;
    }
}
