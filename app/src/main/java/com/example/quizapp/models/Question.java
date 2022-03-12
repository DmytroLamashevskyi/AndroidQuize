package com.example.quizapp.models;

import java.util.List;

public class Question {
    public  int id;
    public int quizId;
    public String question;
    public String options; //DB value in Forma  {option1},{option2},{option3},{option4}
    public String answer;
}
