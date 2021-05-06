package com.example.todolist.util;

import org.litepal.crud.LitePalSupport;

public class DateWork extends LitePalSupport {
    private int day;
    private int month;
    private int year;
    private String content;
    private int hour;
    private int min;
    private int isFinish;

    public int getFinish() {
        return isFinish;
    }

    public void setFinish(int finish) {
        isFinish = finish;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public int getHour() {
        return hour;
    }

    public int getMin() {
        return min;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setMonth(int month) {
        this.month = month;
    }
}
