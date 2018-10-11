package com.peter.demo.Calendar;

/**
 * 日历中每一个月中的 每一个天数
 */
public class DayEntity {
    private int day ;           //日期，几号
    private int month;           //属于的月份
    private int year;           //属于的年份
    private String dayofweek;   //周几
    private boolean isFirstDayOfMonth; //是否是每个月的第一天
    private boolean isLastDayOfMonth; //是否是每个月的最后一天
    private int monthPosition;    //属于的月份位置，注意是该日期属于的月份在外层列表中的position，不是月份
    private int dayPosition;      //属于的日期位置，注意是该日期在每个月（内层列表）中的位置


    public String getDayofweek() {
        return dayofweek;
    }

    public void setDayofweek(String dayofweek) {
        this.dayofweek = dayofweek;
    }

    public DayEntity(int day, int month, int year, int monthPosition, String dayofweek,
                     boolean isFirstDayOfMonth, boolean isLastDayOfMonth) {
        this.day = day;
        this.month = month;
        this.year = year;
        this.monthPosition = monthPosition;
        this.dayofweek = dayofweek;
        this.isFirstDayOfMonth = isFirstDayOfMonth;
        this.isLastDayOfMonth = isLastDayOfMonth;
    }



    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {

        this.year = year;
    }

    public int getMonthPosition() {
        return monthPosition;
    }

    public void setMonthPosition(int monthPosition) {
        this.monthPosition = monthPosition;
    }

    public int getDayPosition() {
        return dayPosition;
    }

    public void setDayPosition(int dayPosition) {
        this.dayPosition = dayPosition;
    }

    public boolean isFirstDayOfMonth() {
        return isFirstDayOfMonth;
    }

    public void setFirstDayOfMonth(boolean firstDayOfMonth) {
        isFirstDayOfMonth = firstDayOfMonth;
    }

    public boolean isLastDayOfMonth() {
        return isLastDayOfMonth;
    }

    public void setLastDayOfMonth(boolean lastDayOfMonth) {
        isLastDayOfMonth = lastDayOfMonth;
    }
}
