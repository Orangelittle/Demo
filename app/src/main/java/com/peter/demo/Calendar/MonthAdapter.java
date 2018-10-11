package com.peter.demo.Calendar;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.peter.demo.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Peter on 2018/10/9.
 */
public class MonthAdapter extends RecyclerView.Adapter<MonthAdapter.MonthTimeViewHolder> {
    private static final String TAG = "MonthAdapter";
    private ArrayList<MonthEntity> datas;
    private Context context;
    private int chooseType;

    public MonthAdapter(ArrayList<MonthEntity> datas, Context context,int chooseType) {
        this.datas = datas;
        this.context = context;
        this.chooseType = chooseType;
    }
    @NonNull
    @Override
    public MonthTimeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_recycler_timeplan, viewGroup, false);
        return new MonthTimeViewHolder(v,context);

    }

    @Override
    public void onBindViewHolder(@NonNull MonthTimeViewHolder holder, int position) {
        MonthEntity monthTimeEntity = datas.get(position);
        holder.plan_time_txt_month.setText(monthTimeEntity.getYear()+"年"+ monthTimeEntity.getMonth()+"月");  //显示 几年几月
        //得到该月份的第一天
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, monthTimeEntity.getYear());          //指定年份
        calendar.set(Calendar.MONTH, monthTimeEntity.getMonth() - 1);        //指定月份 Java月份从0开始算
        calendar.set(Calendar.DAY_OF_MONTH,1);                           // 指定天数 ，这三行是为了得到 这一年这一月的第一天

        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);             //得到该月份第一天 是星期几
        ArrayList<DayEntity> days = new ArrayList<DayEntity>();
        for (int i = 0; i < dayOfWeek-1; i++) {                          //空白间隔
            days.add(new DayEntity(0, monthTimeEntity.getMonth(), monthTimeEntity.getYear(),position,"",false,false));
        }
        calendar.add(Calendar.DATE, -1);// 减去一天，变为当月最后一天
        int monthofdays = calendar.get(Calendar.DAY_OF_MONTH);
        for (int i = 1; i <= monthofdays; i++) {     //添加 该月份的天数   一号 到 该月的最后一天
            String dayofweek = caculateWeekDay(monthTimeEntity.getYear(),monthTimeEntity.getMonth(),i);
           if (i == 1){
               days.add(new DayEntity(i, monthTimeEntity.getMonth(), monthTimeEntity.getYear(),position,dayofweek,true,false));
           }else if (i == monthofdays){
               days.add(new DayEntity(i, monthTimeEntity.getMonth(), monthTimeEntity.getYear(),position,dayofweek,false,true));
           }else {
               days.add(new DayEntity(i, monthTimeEntity.getMonth(), monthTimeEntity.getYear(),position,dayofweek,false,false));
           }
        }

        DayAdapter adapter = new DayAdapter(days,context,chooseType);
        holder.plan_time_recycler_content.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0: datas.size();
    }

    public static class MonthTimeViewHolder extends RecyclerView.ViewHolder{
        public TextView plan_time_txt_month;                         //文本 title
        public RecyclerView plan_time_recycler_content ;            //月份里面详细日期的列表

        public MonthTimeViewHolder(@NonNull View itemView,Context context) {
            super(itemView);
            plan_time_recycler_content = (RecyclerView) itemView.findViewById(R.id.plan_time_recycler_content);
            plan_time_txt_month = (TextView) itemView.findViewById(R.id.plan_time_txt_month);

            RecyclerView.LayoutManager layoutManager =
                    new GridLayoutManager(context,7, GridLayoutManager.VERTICAL,false);
            plan_time_recycler_content.setLayoutManager(layoutManager);
        }
    }

    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");// 定义日期格式
    Calendar calendar_choose = Calendar.getInstance();

    /**
     * 筛选当前日期为周几
     * @param year
     * @param month
     * @param day
     * @return
     */
    private String caculateWeekDay(int year,int month,int day){
        String strDate = year+"-"+month+"-"+day;// 定义日期字符串

        Date date = null;
        try {
            date = format.parse(strDate);// 将字符串转换为日期
        }catch (ParseException e) {
        }
        calendar_choose.setTime(date);
        int dayForWeek =calendar_choose.get(Calendar.DAY_OF_WEEK);
        String dayofweek = "";
        switch (dayForWeek){
            case 1:dayofweek = "周日";break;
            case 2:dayofweek = "周一";break;
            case 3:dayofweek = "周二";break;
            case 4:dayofweek = "周三";break;
            case 5:dayofweek = "周四";break;
            case 6:dayofweek = "周五";break;
            case 7:dayofweek = "周六";break;
        }
        return dayofweek;
    }
}
