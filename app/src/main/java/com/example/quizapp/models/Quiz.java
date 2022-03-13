package com.example.quizapp.models;

import java.util.ArrayList;
import java.util.List;

public class Quiz {
    public  int id;
    public  String name;
    public  String details;
    public int ownerId;

    public Quiz(){
        questions = new ArrayList<Question>();
    }
    public ArrayList<Question> questions;
}
