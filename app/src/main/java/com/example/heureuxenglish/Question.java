package com.example.heureuxenglish;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class Question {
    private String question;
    private ArrayList<Answer> listAnswer = new ArrayList<Answer>();

    public Question() {
    }

    public Question(String question, ArrayList<Answer> listAnswer) {
        this.question = question;
        this.listAnswer = listAnswer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public ArrayList<Answer> getListAnswer() {
        return listAnswer;
    }

    public void setListAnswer(ArrayList<Answer> listAnswer) {
        this.listAnswer = listAnswer;
    }
}
