package com.example.quizapp.models;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class Question {
    public  int id;
    public int quizId;
    public String question;
    public String options;
    public String answer;
    public ArrayList<String> optionsArray;

    public Question(){
        optionsArray =new ArrayList<String>();
    }

    public String getOptionsJson(){
        Gson gson = new Gson();
        return gson.toJson(optionsArray);
    }

    public void setOptionsFromJson(String json){
        Gson gson = new Gson();
        optionsArray =  gson.fromJson(json, new TypeToken<ArrayList<String>>(){}.getType());
    }

    public boolean isCorrect() {



        return  true;
    }
}
