package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.quizapp.managers.DataBaseManager;
import com.example.quizapp.managers.LocalDataManager;
import com.example.quizapp.models.Quiz;

public class QuizCreateActivity extends AppCompatActivity {


    ListView questionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_create);

        Button createQuizButton = (Button) findViewById(R.id.createQuizButton);
        Button addQuestionButton = (Button) findViewById(R.id.addQuestionButton);
        LocalDataManager.getInstance().setQuiz(new Quiz(LocalDataManager.getInstance().getUserData().Id));


        ListView quizList = (ListView)  findViewById(R.id.questListView);

        createQuizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(LocalDataManager.getInstance().getQuizData().isCorrect()){
                    DataBaseManager.createQuiz(LocalDataManager.getInstance().getQuizData());

                    Intent intent = new Intent(v.getContext(), QuizzesMenuActivity.class);
                    startActivity(intent);
                }else {
                    new AlertDialog.Builder(QuizCreateActivity.this)
                        .setTitle("Error")
                        .setMessage("Can't create Quiz with this parameters.")
                        .setCancelable(false)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();
                }
            }
        });

        addQuestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), QuestionCreateActivity.class);
                startActivity(intent);
            }
        });

    }
}