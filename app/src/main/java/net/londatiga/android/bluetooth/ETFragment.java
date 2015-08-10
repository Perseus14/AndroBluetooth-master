package net.londatiga.android.bluetooth;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ETFragment extends Fragment {
    public Long temp;
    private Speedometer speedometer;

    public ETFragment()
    {

    }
    public static final ETFragment newInstance(Long temp) {
        ETFragment fragment = new ETFragment();

        final Bundle args = new Bundle(1);
        args.putLong("temp", temp);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_temp, container, false);
        temp = getArguments().getLong("fuel");
        speedometer = (Speedometer) rootView.findViewById(R.id.Speedometer4);
        speedometer.onSpeedChanged(temp);

        return rootView;
    }
}
