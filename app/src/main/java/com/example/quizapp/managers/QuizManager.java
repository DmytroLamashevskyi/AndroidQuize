package com.example.quizapp.managers;

import com.example.quizapp.models.Quiz;

public class QuizManager {
    private static QuizManager _instance;

    private QuizManager()
    {

    }

    public static QuizManager getInstance()
    {
        if (_instance == null)
        {
            _instance = new QuizManager();
        }
        return _instance;
    }

    private Quiz quizData;
    public void setQuizData(Quiz quiz){
        quizData = quiz;
    }
    public Quiz getQuizData(){
        return quizData;
    }




}
