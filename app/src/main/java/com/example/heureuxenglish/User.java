package com.example.heureuxenglish;

import android.provider.ContactsContract;

import java.util.ArrayList;

public class User {
    private String name = "your name";      // default name
    private int age = 10;                   // default age
    private String email = "your email";    // default email
    private int level = 0;
    private int point = 0;
    private int countLearnedWord = 0;
    private int hardDay = 0;
    private int wordToday = 0;
    private int target = 15;
    private LatestDay lastTimeLearning =  new LatestDay();

    public User(){
    }

    public User(String name, String email){
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getHardDay() {
        return hardDay;
    }

    public void setHardDay(int hardDay) {
        this.hardDay = hardDay;
    }

    public int getWordToday() {
        return wordToday;
    }

    public void setWordToday(int wordToday) {
        this.wordToday = wordToday;
    }

    public int getTarget() {
        return target;
    }

    public void setTarget(int target) {
        this.target = target;
    }

    public int getCountLearnedWord() {
        return countLearnedWord;
    }

    public void setCountLearnedWord(int countLearnedWord) {
        this.countLearnedWord = countLearnedWord;
    }

    public LatestDay getLastTimeLearning() {
        return lastTimeLearning;
    }

    public void setLastTimeLearning(LatestDay lastTimeLearning) {
        this.lastTimeLearning = lastTimeLearning;
    }
}
