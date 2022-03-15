package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.quizapp.managers.DataBaseManager;
import com.example.quizapp.managers.LocalDataManager;
import com.example.quizapp.models.Quiz;
import com.example.quizapp.utiles.QuizListAdapter;
import com.example.quizapp.utiles.UserQuizListAdapter;

import java.util.ArrayList;
import java.util.List;


public class QuizzesMenuActivity extends AppCompatActivity {

    ListView quizList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizzes_menu);

        quizList = (ListView)  findViewById(R.id.quizzesListLayout);
        AddQuizzesToLayout();

        Button myQuizButton = (Button) findViewById(R.id.myQuizButton);
        Button allQuizzesButton = (Button) findViewById(R.id.allQuizzesButton);
        Button createButton = (Button) findViewById(R.id.createButton);

        allQuizzesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddQuizzesToLayout();
            }
        });

        myQuizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddUserQuizzesToLayout(1);
            }
        });

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    LocalDataManager.getInstance().setQuiz(new Quiz(LocalDataManager.getInstance().getUserData().Id));
                    Intent intent = new Intent(view.getContext(), QuizCreateActivity.class);
                    startActivity(intent);
            }
        });
    }

    public void AddQuizzesToLayout() {
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

    public void AddUserQuizzesToLayout(int ownerId) {
        // Construct the data source
        ArrayList<Quiz> quizzes = new ArrayList<>();
        // Create the adapter to convert the array to views
        UserQuizListAdapter userQuizListAdapter = new UserQuizListAdapter(this,quizzes);
        // Attach the adapter to a ListView
        quizList.setAdapter(userQuizListAdapter);
        // Get data from DB
        quizzes = DataBaseManager.GetUserQuizzes(ownerId);
        // Add items to adapter
        userQuizListAdapter.addAll(quizzes);
    }
}