package net.londatiga.android.bluetooth;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FuelFragment extends Fragment {
    public Long fuel;
    private Speedometer speedometer;

    public FuelFragment()
    {

    }
    public static final FuelFragment newInstance(Long fuel) {
        FuelFragment fragment = new FuelFragment();

        final Bundle args = new Bundle(1);
        args.putLong("fuel", fuel);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_fuel, container, false);
        fuel = getArguments().getLong("fuel");
        speedometer = (Speedometer) rootView.findViewById(R.id.Speedometer2);
        speedometer.onSpeedChanged(fuel);

        return rootView;
    }
}
