package com.peter.demo;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;


public class BluetoothTempetureFragment extends Fragment {


    private RadioButton radiobodyButton;
    private PetalBackGroundView petalBackGroundView;

    public BluetoothTempetureFragment() {
        // Required empty public constructor
    }
    public static BluetoothTempetureFragment newInstance() {
        return new BluetoothTempetureFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bluetooth_temperature, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        radiobodyButton = ((RadioButton) view.findViewById(R.id.button_body));

        petalBackGroundView = ((PetalBackGroundView) view.findViewById(R.id.view));
//        petalBackGroundView.startAnim();

        view.findViewById(R.id.history_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.loadFragment(getActivity().getSupportFragmentManager(),
                        HistoryTempetureFragment.newInstance(),R.id.tempeturecontainer,true,1);

            }
        });
        radiobodyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.loadFragment(getActivity().getSupportFragmentManager(),
                        ((MainActivity) getActivity()).bodyTempetureFragment,R.id.tempeturecontainer);
            }
        });

        TemperatureDialog.getInstance(getActivity()).show();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        petalBackGroundView.stopAnim();
    }
}
