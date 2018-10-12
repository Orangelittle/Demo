package com.peter.demo.Calendar;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.peter.demo.R;
import java.util.ArrayList;
import java.util.Calendar;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by Peter on 2018/10/9.
 */
public class CalendarChooseDialog extends Dialog implements View.OnClickListener{
    private static final String TAG = "CalendarChooseDialog";
    private Disposable subscribe;
    private TextView startTime;          //开始时间
    private TextView stopTime;           //结束时间

    private String resultTime;  //最终结果

    private RecyclerView reycycler;
    private MonthAdapter adapter;
    private ArrayList<MonthEntity> datas;

    private Context mContext;
    private static CalendarChooseDialog calendarChooseDialog;

    public static DayEntity startDay;
    public static DayEntity stopDay;

    public static int SINGLE_TYPE=1; //区分日期选择单日还是多日
    public static int MULTI_TYPE=2;
    private static int choose_type;


    public CalendarChooseDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_choose_dialog);

        subscribe = RxBus.getInstance().register(Integer.class).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.d(TAG, "RxBus accept: ------------------------" + integer);
                if (integer == 1) {
                    adapter.notifyDataSetChanged();
                    startTime.setText(startDay.getMonth() + "月" + startDay.getDay() + "日 " + startDay.getDayofweek());
                    if (stopDay.getDay() == -1) {
                        stopTime.setText("");
                    } else {
                        stopTime.setText(stopDay.getMonth() + "月" + stopDay.getDay() + "日 " + stopDay.getDayofweek());
                    }
                    resultTime = startDay.getMonth() + "月" + startDay.getDay() + "日#" + startDay.getDayofweek() + "#" + stopDay.getMonth() + "月" + stopDay.getDay() + "日#" + stopDay.getDayofweek();
                }
            }
        });


        mContext = getContext();
        initView();
        initData();
    }

    public static CalendarChooseDialog getInstance(Context context,int type){
        calendarChooseDialog = new CalendarChooseDialog(context,R.style.temperaturePromptDialog);
        choose_type = type;
        return calendarChooseDialog;
    }

    private void initData() {
        startDay = new DayEntity(0,0,0,0,"",false,false);
        stopDay = new DayEntity(-1,-1,-1,-1,"",false,false);
        datas = new ArrayList<>();

        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH)+1;
        for (int i = 0; i < 12; i++) {
            int month1 = i+1;
            datas.add(new MonthEntity(year,month1));
        }
        adapter = new MonthAdapter(datas, mContext,choose_type);
        reycycler.setAdapter(adapter);
        reycycler.scrollToPosition(month-1);

    }

    private void initView() {
        startTime = (TextView) findViewById(R.id.plan_time_txt_start);
        stopTime = (TextView) findViewById(R.id.plan_time_txt_stop);
        findViewById(R.id.dialog_close_btn).setOnClickListener(this);
        findViewById(R.id.dialog_sure_btn).setOnClickListener(this);

        reycycler = (RecyclerView) findViewById(R.id.plan_time_calender);
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(mContext, LinearLayout.VERTICAL,false);
        reycycler.setLayoutManager(layoutManager);
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        RxBus.getInstance().unregister(subscribe);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_close_btn:
                    dismiss();
                break;
            case  R.id.dialog_sure_btn:
               if( stopDay.getDay()!=-1 && startDay.getDay()!=-1){
                   RxBus.getInstance().post(resultTime);
                   dismiss();
               }else if (stopDay.getDay()==-1 && startDay.getDay()!=-1){
                   Toast.makeText(mContext, "请选择结束日期", Toast.LENGTH_SHORT).show();
               }else {
                   Toast.makeText(mContext, "请选择日期", Toast.LENGTH_SHORT).show();
               }
                break;
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (calendarChooseDialog != null) {
            calendarChooseDialog = null;
        }
    }
}
