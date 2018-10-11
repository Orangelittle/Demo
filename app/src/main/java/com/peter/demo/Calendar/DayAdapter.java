package com.peter.demo.Calendar;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.peter.demo.R;
import java.util.ArrayList;

/**
 * Created by Peter on 2018/10/9.
 */
public class DayAdapter extends RecyclerView.Adapter<DayAdapter.DayTimeViewHolder> {
    private static final String TAG = "DayAdapter";
    private ArrayList<DayEntity> days;
    private Context context;
    private int chooseType;//日期选择单日还是多日

    public DayAdapter(ArrayList<DayEntity> days, Context context,int chooseType) {
        this.days = days;
        this.context = context;
        this.chooseType = chooseType;
    }

    @NonNull
    @Override
    public DayTimeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup,  int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_recycler_selectday, viewGroup, false);
        return  new DayTimeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DayTimeViewHolder holder, final int position) {
        final DayEntity dayTimeEntity = days.get(position);
        //显示日期
        if (dayTimeEntity.getDay()!=0) {
            holder.select_txt_day.setText(dayTimeEntity.getDay() + "");
            holder.select_ly_day.setEnabled(true);
        }else{
            holder.select_ly_day.setEnabled(false);
        }

       if (chooseType == CalendarChooseDialog.MULTI_TYPE){
           multiday(holder, position, dayTimeEntity);
       }else if (chooseType == CalendarChooseDialog.SINGLE_TYPE){
            singleday(holder, position, dayTimeEntity);
       }


        if (CalendarChooseDialog.startDay.getYear()== dayTimeEntity.getYear() && CalendarChooseDialog.startDay.getMonth() == dayTimeEntity.getMonth() && CalendarChooseDialog.startDay.getDay() == dayTimeEntity.getDay()
                && CalendarChooseDialog.stopDay.getYear()== dayTimeEntity.getYear() && CalendarChooseDialog.stopDay.getMonth() == dayTimeEntity.getMonth() && CalendarChooseDialog.stopDay.getDay() == dayTimeEntity.getDay() ){
            //开始和结束同一天
            holder.select_ly_day.setBackgroundResource(R.drawable.bg_time_start);
            holder.select_txt_day.setTextColor(context.getResources().getColor(R.color.white));
        }
        else if (CalendarChooseDialog.startDay.getYear()== dayTimeEntity.getYear() && CalendarChooseDialog.startDay.getMonth() == dayTimeEntity.getMonth() && CalendarChooseDialog.startDay.getDay() == dayTimeEntity.getDay()){
            //该item是 开始日期
            if (CalendarChooseDialog.stopDay.getDay() !=-1)
                holder.select_ly_day.setBackgroundResource(R.drawable.bg_time_start_end);
            else
                holder.select_ly_day.setBackgroundResource(R.drawable.bg_time_start);
            holder.select_txt_day.setTextColor(context.getResources().getColor(R.color.white));
        }else if(CalendarChooseDialog.stopDay.getYear()== dayTimeEntity.getYear() && CalendarChooseDialog.stopDay.getMonth() == dayTimeEntity.getMonth() && CalendarChooseDialog.stopDay.getDay() == dayTimeEntity.getDay()){
            //该item是 结束日期
            holder.select_ly_day.setBackgroundResource(R.drawable.bg_time_stop);
            holder.select_txt_day.setTextColor(context.getResources().getColor(R.color.white));
        }else if(dayTimeEntity.getMonthPosition()>= CalendarChooseDialog.startDay.getMonthPosition() && dayTimeEntity.getMonthPosition()<= CalendarChooseDialog.stopDay.getMonthPosition()){
            //处于开始和结束之间的点
            if (dayTimeEntity.getMonthPosition()== CalendarChooseDialog.startDay.getMonthPosition()&& dayTimeEntity.getMonthPosition()== CalendarChooseDialog.stopDay.getMonthPosition()){
                //开始和结束是一个月份
                if (dayTimeEntity.getDay()>= CalendarChooseDialog.startDay.getDay() && dayTimeEntity.getDay() <= CalendarChooseDialog.stopDay.getDay()) {
                    if (dayTimeEntity.getDayofweek().equals("周日")) {
                        holder.select_ly_day.setBackgroundResource(R.drawable.bg_time_left);
                    }else if (dayTimeEntity.getDayofweek().equals("周六")) {
                        holder.select_ly_day.setBackgroundResource(R.drawable.bg_time_right);
                    }else {
                        holder.select_ly_day.setBackgroundResource(R.color.calendarchoose_color);
                    }
                    holder.select_txt_day.setTextColor(context.getResources().getColor(R.color.white));
                }else{
                    holder.select_ly_day.setBackgroundResource(R.color.white);
                    holder.select_txt_day.setTextColor(context.getResources().getColor(R.color.txtColor));
                }
            }else if(CalendarChooseDialog.startDay.getMonthPosition() != CalendarChooseDialog.stopDay.getMonthPosition()){
                // 日期和 开始 不是一个月份
                if (dayTimeEntity.getMonthPosition()== CalendarChooseDialog.startDay.getMonthPosition() && dayTimeEntity.getDay()> CalendarChooseDialog.startDay.getDay()){
                    //和初始相同月  天数往后
                    if (dayTimeEntity.getDayofweek().equals("周日") && dayTimeEntity.isLastDayOfMonth()){
                        holder.select_ly_day.setBackgroundResource(R.drawable.bg_time_special);
                    }else if (dayTimeEntity.getDayofweek().equals("周六") && dayTimeEntity.isFirstDayOfMonth()){
                        holder.select_ly_day.setBackgroundResource(R.drawable.bg_time_special);
                    }else if (dayTimeEntity.getDayofweek().equals("周日") ||dayTimeEntity.isFirstDayOfMonth()) {
                        holder.select_ly_day.setBackgroundResource(R.drawable.bg_time_left);
                    }else if (dayTimeEntity.getDayofweek().equals("周六")||dayTimeEntity.isLastDayOfMonth()) {
                        holder.select_ly_day.setBackgroundResource(R.drawable.bg_time_right);
                    }else {
                        holder.select_ly_day.setBackgroundResource(R.color.calendarchoose_color);
                    }
                    holder.select_txt_day.setTextColor(context.getResources().getColor(R.color.white));
                }else if(dayTimeEntity.getMonthPosition()== CalendarChooseDialog.stopDay.getMonthPosition() && dayTimeEntity.getDay()< CalendarChooseDialog.stopDay.getDay()){
                    //和结束相同月   天数往前
                    if (dayTimeEntity.getDay()<=0){
                        holder.select_ly_day.setBackgroundResource(R.color.white);
                        holder.select_txt_day.setTextColor(context.getResources().getColor(R.color.txtColor));
                    }else {
                        if (dayTimeEntity.getDayofweek().equals("周日") && dayTimeEntity.isLastDayOfMonth()){
                            holder.select_ly_day.setBackgroundResource(R.drawable.bg_time_special);
                        }else if (dayTimeEntity.getDayofweek().equals("周六") && dayTimeEntity.isFirstDayOfMonth()){
                            holder.select_ly_day.setBackgroundResource(R.drawable.bg_time_special);
                        }else if (dayTimeEntity.getDayofweek().equals("周日")||dayTimeEntity.isFirstDayOfMonth()) {
                            holder.select_ly_day.setBackgroundResource(R.drawable.bg_time_left);
                        }else if (dayTimeEntity.getDayofweek().equals("周六")||dayTimeEntity.isLastDayOfMonth()) {
                            holder.select_ly_day.setBackgroundResource(R.drawable.bg_time_right);
                        }else {
                            holder.select_ly_day.setBackgroundResource(R.color.calendarchoose_color);
                        }
                        holder.select_txt_day.setTextColor(context.getResources().getColor(R.color.white));
                    }
                }else if(dayTimeEntity.getMonthPosition()!= CalendarChooseDialog.startDay.getMonthPosition() && dayTimeEntity.getMonthPosition()!= CalendarChooseDialog.stopDay.getMonthPosition()){
                    // 开始结束都不是同一个月
                    if (dayTimeEntity.getDay()<=0){
                        holder.select_ly_day.setBackgroundResource(R.color.white);
                    } else{
                        if (dayTimeEntity.getDayofweek().equals("周日") && dayTimeEntity.isLastDayOfMonth()){
                            holder.select_ly_day.setBackgroundResource(R.drawable.bg_time_special);
                        }else if (dayTimeEntity.getDayofweek().equals("周六") && dayTimeEntity.isFirstDayOfMonth()){
                            holder.select_ly_day.setBackgroundResource(R.drawable.bg_time_special);
                        }else if (dayTimeEntity.getDayofweek().equals("周日") || dayTimeEntity.isFirstDayOfMonth()) {
                            holder.select_ly_day.setBackgroundResource(R.drawable.bg_time_left);
                        }else if (dayTimeEntity.getDayofweek().equals("周六") || dayTimeEntity.isFirstDayOfMonth()) {
                            holder.select_ly_day.setBackgroundResource(R.drawable.bg_time_right);
                        }else {
                            holder.select_ly_day.setBackgroundResource(R.color.calendarchoose_color);
                        }
                    }
                    holder.select_txt_day.setTextColor(context.getResources().getColor(R.color.white));
                }else{
                    holder.select_ly_day.setBackgroundResource(R.color.white);
                    holder.select_txt_day.setTextColor(context.getResources().getColor(R.color.txtColor));
                }
            }

        }else{
            holder.select_ly_day.setBackgroundResource(R.color.white);
            holder.select_txt_day.setTextColor(context.getResources().getColor(R.color.txtColor));
        }
    }

    private void multiday(@NonNull DayTimeViewHolder holder, final int position, final DayEntity dayTimeEntity) {
        holder.select_ly_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CalendarChooseDialog.startDay.getYear() == 0 ){          // 第一次点击开始的位置，因为开始默认参数是 0,0,0,0
                    CalendarChooseDialog.startDay.setDay(dayTimeEntity.getDay());           // 该item 天数的 年月日等信息  赋给  开始日期
                    CalendarChooseDialog.startDay.setMonth(dayTimeEntity.getMonth());
                    CalendarChooseDialog.startDay.setYear(dayTimeEntity.getYear());
                    CalendarChooseDialog.startDay.setDayofweek(dayTimeEntity.getDayofweek());
                    CalendarChooseDialog.startDay.setMonthPosition(dayTimeEntity.getMonthPosition());
                    CalendarChooseDialog.startDay.setDayPosition(position);
                }else if(CalendarChooseDialog.startDay.getYear()>0 && CalendarChooseDialog.stopDay.getYear() ==-1){      //已经点击了开始 ，点击结束位置，（默认结束位置-1,-1,-1,-1 说明还没有点击结束位置）
                    if (dayTimeEntity.getYear()> CalendarChooseDialog.startDay.getYear()) {
                        //如果选中的年份大于开始的年份，说明结束日期肯定大于开始日期 ，合法的 ，将该item的天数的 信息  赋给 结束日期
                        CalendarChooseDialog.stopDay.setDay(dayTimeEntity.getDay());
                        CalendarChooseDialog.stopDay.setMonth(dayTimeEntity.getMonth());
                        CalendarChooseDialog.stopDay.setYear(dayTimeEntity.getYear());
                        CalendarChooseDialog.stopDay.setDayofweek(dayTimeEntity.getDayofweek());
                        CalendarChooseDialog.stopDay.setMonthPosition(dayTimeEntity.getMonthPosition());
                        CalendarChooseDialog.stopDay.setDayPosition(position);
                    }else if (dayTimeEntity.getYear() == CalendarChooseDialog.startDay.getYear()){
                        //如果选中的年份 等于 选中的年份
                        if (dayTimeEntity.getMonth()> CalendarChooseDialog.startDay.getMonth()){
                            //如果改item的天数的月份大于开始日期的月份，说明结束日期肯定大于开始日期 ，合法的 ，将该item的天数的 信息  赋给 结束日期
                            CalendarChooseDialog.stopDay.setDay(dayTimeEntity.getDay());
                            CalendarChooseDialog.stopDay.setMonth(dayTimeEntity.getMonth());
                            CalendarChooseDialog.stopDay.setYear(dayTimeEntity.getYear());
                            CalendarChooseDialog.stopDay.setDayofweek(dayTimeEntity.getDayofweek());
                            CalendarChooseDialog.stopDay.setMonthPosition(dayTimeEntity.getMonthPosition());
                            CalendarChooseDialog.stopDay.setDayPosition(position);
                        }else if(dayTimeEntity.getMonth() == CalendarChooseDialog.startDay.getMonth()){
                            //年份月份 都相等
                            if (dayTimeEntity.getDay() >= CalendarChooseDialog.startDay.getDay()){
                                //判断天数 ，如果 该item的天数的 日子大于等于 开始日期的 日子 ，说明结束日期合法的 ，将该item的天数的 信息  赋给 结束日期
                                CalendarChooseDialog.stopDay.setDay(dayTimeEntity.getDay());
                                CalendarChooseDialog.stopDay.setMonth(dayTimeEntity.getMonth());
                                CalendarChooseDialog.stopDay.setYear(dayTimeEntity.getYear());
                                CalendarChooseDialog.stopDay.setDayofweek(dayTimeEntity.getDayofweek());
                                CalendarChooseDialog.stopDay.setMonthPosition(dayTimeEntity.getMonthPosition());
                                CalendarChooseDialog.stopDay.setDayPosition(position);
                            }else{
                                //天数小与初始  从新选择开始  ，结束日期重置，开始日期为当前的位置的天数的信息
                                CalendarChooseDialog.startDay.setDay(dayTimeEntity.getDay());
                                CalendarChooseDialog.startDay.setMonth(dayTimeEntity.getMonth());
                                CalendarChooseDialog.startDay.setYear(dayTimeEntity.getYear());
                                CalendarChooseDialog.startDay.setDayofweek(dayTimeEntity.getDayofweek());
                                CalendarChooseDialog.startDay.setMonthPosition(dayTimeEntity.getMonthPosition());
                                CalendarChooseDialog.startDay.setDayPosition(position);
                                CalendarChooseDialog.stopDay.setDay(-1);
                                CalendarChooseDialog.stopDay.setMonth(-1);
                                CalendarChooseDialog.stopDay.setYear(-1);
                                CalendarChooseDialog.stopDay.setMonthPosition(-1);
                                CalendarChooseDialog.stopDay.setDayPosition(-1);
                            }
                        }else {
                            //选中的月份 比开始日期的月份还小，说明 结束位置不合法，结束日期重置，开始日期为当前的位置的天数的信息
                            CalendarChooseDialog.startDay.setDay(dayTimeEntity.getDay());
                            CalendarChooseDialog.startDay.setMonth(dayTimeEntity.getMonth());
                            CalendarChooseDialog.startDay.setYear(dayTimeEntity.getYear());
                            CalendarChooseDialog.startDay.setDayofweek(dayTimeEntity.getDayofweek());
                            CalendarChooseDialog.startDay.setMonthPosition(dayTimeEntity.getMonthPosition());
                            CalendarChooseDialog.startDay.setDayPosition(position);
                            CalendarChooseDialog.stopDay.setDay(-1);
                            CalendarChooseDialog.stopDay.setMonth(-1);
                            CalendarChooseDialog.stopDay.setYear(-1);
                            CalendarChooseDialog.stopDay.setMonthPosition(-1);
                            CalendarChooseDialog.stopDay.setDayPosition(-1);
                        }

                    }else{
                        //选中的年份 比开始日期的年份还小，说明 结束位置不合法，结束日期重置，开始日期为当前的位置的天数的信息
                        CalendarChooseDialog.startDay.setDay(dayTimeEntity.getDay());
                        CalendarChooseDialog.startDay.setMonth(dayTimeEntity.getMonth());
                        CalendarChooseDialog.startDay.setYear(dayTimeEntity.getYear());
                        CalendarChooseDialog.startDay.setDayofweek(dayTimeEntity.getDayofweek());
                        CalendarChooseDialog.startDay.setMonthPosition(dayTimeEntity.getMonthPosition());
                        CalendarChooseDialog.startDay.setDayPosition(position);
                        CalendarChooseDialog.stopDay.setDay(-1);
                        CalendarChooseDialog.stopDay.setMonth(-1);
                        CalendarChooseDialog.stopDay.setYear(-1);
                        CalendarChooseDialog.stopDay.setMonthPosition(-1);
                        CalendarChooseDialog.stopDay.setDayPosition(-1);
                    }
                }else if(CalendarChooseDialog.startDay.getYear()>0 && CalendarChooseDialog.startDay.getYear()>1){      //已经点击开始和结束   第三次点击 ，重新点击开始
                    CalendarChooseDialog.startDay.setDay(dayTimeEntity.getDay());
                    CalendarChooseDialog.startDay.setMonth(dayTimeEntity.getMonth());
                    CalendarChooseDialog.startDay.setYear(dayTimeEntity.getYear());
                    CalendarChooseDialog.startDay.setDayofweek(dayTimeEntity.getDayofweek());
                    CalendarChooseDialog.startDay.setMonthPosition(dayTimeEntity.getMonthPosition());
                    CalendarChooseDialog.startDay.setDayPosition(position);
                    CalendarChooseDialog.stopDay.setDay(-1);
                    CalendarChooseDialog.stopDay.setMonth(-1);
                    CalendarChooseDialog.stopDay.setYear(-1);
                    CalendarChooseDialog.stopDay.setMonthPosition(-1);
                    CalendarChooseDialog.stopDay.setDayPosition(-1);
                }
                // TODO: 2018/10/9 刷新ui 传值
                RxBus.getInstance().post(1);// 发消息刷新适配器，目的为了显示日历上各个日期的背景颜色
            }
        });
    }
    private void singleday(@NonNull DayTimeViewHolder holder, final int position, final DayEntity dayTimeEntity) {
        holder.select_ly_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    CalendarChooseDialog.startDay.setDay(dayTimeEntity.getDay());
                    CalendarChooseDialog.startDay.setMonth(dayTimeEntity.getMonth());
                    CalendarChooseDialog.startDay.setYear(dayTimeEntity.getYear());
                    CalendarChooseDialog.startDay.setDayofweek(dayTimeEntity.getDayofweek());
                    CalendarChooseDialog.startDay.setMonthPosition(dayTimeEntity.getMonthPosition());
                    CalendarChooseDialog.startDay.setDayPosition(position);
                    CalendarChooseDialog.stopDay = CalendarChooseDialog.startDay;
//                    CalendarChooseDialog.stopDay.setDay(dayTimeEntity.getDay());
//                    CalendarChooseDialog.stopDay.setMonth(dayTimeEntity.getMonth());
//                    CalendarChooseDialog.stopDay.setYear(dayTimeEntity.getYear());
//                    CalendarChooseDialog.stopDay.setDayofweek(dayTimeEntity.getDayofweek());
//                    CalendarChooseDialog.stopDay.setMonthPosition(dayTimeEntity.getMonthPosition());
//                    CalendarChooseDialog.stopDay.setDayPosition(position);


                // TODO: 2018/10/9 刷新ui 传值
                RxBus.getInstance().post(1);// 发消息刷新适配器，目的为了显示日历上各个日期的背景颜色
            }
        });
    }

    @Override
    public int getItemCount() {
        return days == null ? 0 : days.size();
    }

    public static class DayTimeViewHolder extends RecyclerView.ViewHolder{
        public TextView select_txt_day;      //日期文本
        public LinearLayout select_ly_day;       //父容器 ， 用于点击日期
        public DayTimeViewHolder(@NonNull View itemView) {
            super(itemView);
            select_ly_day = (LinearLayout) itemView.findViewById(R.id.select_ly_day);
            select_txt_day = (TextView) itemView.findViewById(R.id.select_txt_day);
        }
    }
}
