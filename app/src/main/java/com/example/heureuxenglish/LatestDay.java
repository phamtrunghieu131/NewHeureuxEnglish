package com.example.heureuxenglish;

import java.util.Objects;

public class LatestDay {
    int day = 0;
    int month = 0;
    int year = 0;

    public LatestDay() {
    }

    public LatestDay(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LatestDay latestDay = (LatestDay) o;
        return day == latestDay.day &&
                month == latestDay.month &&
                year == latestDay.year;
    }
}
