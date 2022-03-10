package com.example.quizapp.managers;

import android.content.SharedPreferences;

import com.example.quizapp.models.User;

public class LocalDataManager {

    private static LocalDataManager _instance;

    private LocalDataManager()
    {

    }

    private SharedPreferences sharedPref;
    public  void  InitPref(SharedPreferences sharedPreferences){
        SharedPreferences sharedPref = sharedPreferences;

        String UserId = sharedPref.getString("UserId", null);
    }

    private User userData;

    public static LocalDataManager getInstance()
    {
        if (_instance == null)
        {
            _instance = new LocalDataManager();
        }
        return _instance;
    }


    public void SaveData() {


    }
}
