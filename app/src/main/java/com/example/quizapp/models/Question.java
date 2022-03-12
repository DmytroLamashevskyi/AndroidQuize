package com.example.quizapp.models;

import java.util.List;

public class Question {
    public  int id;
    public int quizId;
    public String question;
        public String options; //DB value in Format  {"One","Two","Three","Four"}
    public String answer;
}
