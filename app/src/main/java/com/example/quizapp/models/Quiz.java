package com.example.quizapp.models;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;

public class Quiz {
    public  int id;
    public  String name;
    public  String details;
    public int ownerId;

    public Quiz(){
        System.out.println("Create without owner Id.");
        questions = new ArrayList<Question>();
    }
    public Quiz(int ownerId){
        System.out.println("Create with owner Id=["+ownerId+"].");
        this.ownerId = ownerId;
        questions = new ArrayList<Question>();
    }

    public ArrayList<Question> questions;

    public void addQuestion(Question question){
        if (question!=null)
            questions.add(question);
        else
            throw new IllegalArgumentException();
    }

    public void setName(String value){
        name = value;
    }
    public void setDetails(String value){
        details = value;
    }

    public boolean isCorrect(){
        if(name == null || name.length() == 0){
            System.out.println("No name.");
            return false;
        }
        if(details == null || details.length() == 0){
            System.out.println("No details.");
            return false;
        }
        if(questions == null || questions.isEmpty()){
            System.out.println("No questions in list.");
            return false;
        }

        System.out.println("Quiz is Correct.");
        return true;
    }

    public void removeQuestion(Question question) {
        questions.removeIf(q->q.id == question.id);
    }
}
