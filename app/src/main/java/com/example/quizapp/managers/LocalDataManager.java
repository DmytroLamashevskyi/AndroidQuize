package com.example.quizapp.managers;

import android.content.SharedPreferences;

import com.example.quizapp.models.Quiz;
import com.example.quizapp.models.User;

public class LocalDataManager {

    private static LocalDataManager _instance;

    private LocalDataManager()
    {

    }

    public static LocalDataManager getInstance()
    {
        if (_instance == null)
        {
            _instance = new LocalDataManager();
        }
        return _instance;
    }


    private User userData;
    public void setUserData(User user){
        userData = user;
    }
    public User getUserData(){

        return userData;
    }

    private Quiz currentQuiz;
    public Quiz getQuizData() {
        if(currentQuiz==null)
            currentQuiz = new Quiz();
        return currentQuiz;
    }
    public void setQuiz(Quiz quiz){
        currentQuiz = quiz;
    }
}
