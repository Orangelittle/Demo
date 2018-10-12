package com.peter.demo;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.peter.demo.ActivityUtils;
import com.peter.demo.Calendar.CalendarChooseDialog;
import com.peter.demo.Calendar.RxBus;
import com.peter.demo.MainActivity;
import com.peter.demo.R;
import com.squareup.leakcanary.RefWatcher;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;


public class HistoryTempetureFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "HistoryTempetureFragmen";

    private RadioGroup history_choose_group;
    private RadioGroup date_radiogroup;
    private TextView end_date;
    private TextView start_date;
    private TextView start_week;
    private TextView end_week;

    private int CHOOSETYPE =2;//单日还是多日类型
    private Disposable subscribe;


    public HistoryTempetureFragment() {
        // Required empty public constructor
    }
    public static HistoryTempetureFragment newInstance() {
        return new HistoryTempetureFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history_temperature, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        history_choose_group = ((RadioGroup) view.findViewById(R.id.history_temperature_group));
        date_radiogroup = ((RadioGroup) view.findViewById(R.id.date_radiogroup));
        end_date = ((TextView) view.findViewById(R.id.end_date));
        start_date = ((TextView) view.findViewById(R.id.start_date));
        end_week = ((TextView) view.findViewById(R.id.end_weekday));
        start_week = ((TextView) view.findViewById(R.id.start_weekday));
       view.findViewById(R.id.clear_choose).setOnClickListener(this);
        end_date.setOnClickListener(this);
        start_date.setOnClickListener(this);

        Bundle arguments = getArguments();
        int argumentsInt = arguments.getInt("tag", -1);

        switch (argumentsInt) {
            case 1:
                ((RadioButton) view.findViewById(R.id.bluetoothtemperature_btn)).setChecked(true);
                break;
            case 2:
                ((RadioButton) view.findViewById(R.id.bodytemperature_btn)).setChecked(true);
                break;
        }

        history_choose_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.bluetoothtemperature_btn:
                        Toast.makeText(getActivity(), "蓝牙表格", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.bodytemperature_btn:
                        Toast.makeText(getActivity(), "体温表格", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

        date_radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.singledate:
                        CHOOSETYPE = CalendarChooseDialog.SINGLE_TYPE;
                        break;
                    case R.id.multidate:
                        CHOOSETYPE = CalendarChooseDialog.MULTI_TYPE;
                        break;
                }
            }
        });


//        TemperatureNoticeDialog.getInstance(getContext()).show();
        subscribe = RxBus.getInstance().register(String.class).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.d(TAG, "accept: " + s);
                String[] strings = s.split("#");
                start_date.setText(strings[0]);
                start_week.setText(strings[1]);
                end_date.setText(strings[2]);
                end_week.setText(strings[3]);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().unregister(subscribe);
        RefWatcher refWatcher = App.getRefWatcher(getActivity());
        refWatcher.watch(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.end_date:
            case R.id.start_date:
                CalendarChooseDialog.getInstance(getContext(),CHOOSETYPE).show();
                break;
            case R.id.clear_choose:
                resetChoose();
                break;
        }
    }

    private void resetChoose(){
        start_date.setText("选择日期");
        start_week.setText("");
        end_date.setText("选择日期");
        end_week.setText("");
        date_radiogroup.check(R.id.multidate);
    }
}
