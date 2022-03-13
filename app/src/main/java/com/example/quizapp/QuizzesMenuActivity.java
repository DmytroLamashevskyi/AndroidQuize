package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.quizapp.managers.DataBaseManager;
import com.example.quizapp.models.Quiz;
import com.example.quizapp.utiles.QuizListAdapter;

import java.util.ArrayList;
import java.util.List;


public class QuizzesMenuActivity extends AppCompatActivity {

    ListView quizList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizzes_menu);

        quizList = (ListView)  findViewById(R.id.quizzesListLayout);
        AddQuizzesToLayout(quizList);


    }

    private void AddQuizzesToLayout(ListView quizList) {
        // Construct the data source
        ArrayList<Quiz> quizzes = new ArrayList<>();
        // Create the adapter to convert the array to views
        QuizListAdapter quizListAdapter = new QuizListAdapter(this,quizzes);
        // Attach the adapter to a ListView
        quizList.setAdapter(quizListAdapter);
        // Get data from DB
        quizzes = DataBaseManager.GetAllQuizzes();
        // Add items to adapter
        quizListAdapter.addAll(quizzes);
    }
}