package com.example.heureuxenglish;

import java.io.Serializable;
import java.util.ArrayList;

public class Word {
    private String wordInEnglish;
    private String wordInVietnamese;
    private Question mcQuestion;
    private String exampleInEnglish;
    private String exampleInVietnamese;

    public Word(){
    }

    public Word(String wordInEnglish, String wordInVietnamese, Question mcQuestion,
                String exampleInEnglish, String exampleInVietnamese) {
        this.wordInEnglish = wordInEnglish;
        this.wordInVietnamese = wordInVietnamese;
        this.mcQuestion = mcQuestion;
        this.exampleInEnglish = exampleInEnglish;
        this.exampleInVietnamese = exampleInVietnamese;
    }

    public String getWordInEnglish() {
        return wordInEnglish;
    }

    public void setWordInEnglish(String wordInEnglish) {
        this.wordInEnglish = wordInEnglish;
    }

    public String getWordInVietnamese() {
        return wordInVietnamese;
    }

    public void setWordInVietnamese(String wordInVietnamese) {
        this.wordInVietnamese = wordInVietnamese;
    }

    public Question getMcQuestion() {
        return mcQuestion;
    }

    public void setMcQuestion(Question mcQuestion) {
        this.mcQuestion = mcQuestion;
    }

    public String getExampleInEnglish() {
        return exampleInEnglish;
    }

    public void setExampleInEnglish(String exampleInEnglish) {
        this.exampleInEnglish = exampleInEnglish;
    }

    public String getExampleInVietnamese() {
        return exampleInVietnamese;
    }

    public void setExampleInVietnamese(String exampleInVietnamese) {
        this.exampleInVietnamese = exampleInVietnamese;
    }
}
