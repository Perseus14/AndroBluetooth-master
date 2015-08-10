package net.londatiga.android.bluetooth;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AccelerationFragment extends Fragment {
    public Double acc;
    private Speedometer speedometer;
    private TextView tv;
    public AccelerationFragment()
    {

    }
    public static final AccelerationFragment newInstance(Double acc) {
        AccelerationFragment fragment = new AccelerationFragment();

        final Bundle args = new Bundle(1);
        args.putDouble("acc", acc);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_acc, container, false);
        acc = getArguments().getDouble("acc");
        speedometer = (Speedometer) rootView.findViewById(R.id.Speedometer3);
        tv = (TextView) rootView.findViewById(R.id.textView4);
        float f = Float.valueOf(String.valueOf(acc));

        speedometer.onSpeedChanged(f);
        tv.setText(String.valueOf(f));
        return rootView;
    }
}
