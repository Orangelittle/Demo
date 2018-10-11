package com.peter.demo;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.peter.demo.ActivityUtils;
import com.peter.demo.MainActivity;
import com.peter.demo.R;


public class BodyTempetureFragment extends Fragment {
    RadioButton bluetoothButton;
    RadioGroup timeChoose;

    public BodyTempetureFragment() {
        // Required empty public constructor
    }

    public static BodyTempetureFragment newInstance() {
        return new BodyTempetureFragment();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_body_temperature, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        bluetoothButton = ((RadioButton) view.findViewById(R.id.button_bluetooth));
        timeChoose = ((RadioGroup) view.findViewById(R.id.time_choose_group));

        timeChoose.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.body_one_hour:

                        break;
                    case R.id.body_two_hour:

                        break;
                }
            }
        });

        view.findViewById(R.id.history_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.loadFragment(getActivity().getSupportFragmentManager(),
                        HistoryTempetureFragment.newInstance(),R.id.tempeturecontainer,true,2);

            }
        });

        bluetoothButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.loadFragment(getActivity().getSupportFragmentManager(),
                        ((MainActivity) getActivity()).bluetoothTempetureFragment,R.id.tempeturecontainer);

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        MainActivity.popFragmentStack();
    }
}
