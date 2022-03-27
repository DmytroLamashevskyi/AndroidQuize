package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.quizapp.managers.DataBaseManager;
import com.example.quizapp.managers.LocalDataManager;
import com.example.quizapp.models.Question;
import com.example.quizapp.models.Quiz;
import com.example.quizapp.utiles.QuestionListAdapter;
import com.example.quizapp.utiles.QuizListAdapter;

import java.util.ArrayList;

public class QuizCreateActivity extends AppCompatActivity {

    Quiz quiz;
    ListView questionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_create);

        Button createQuizButton = (Button) findViewById(R.id.createQuizButton);
        Button addQuestionButton = (Button) findViewById(R.id.addQuestionButton);

        EditText quizName = (EditText) findViewById(R.id.quizNameEditText);
        EditText quizDetails = (EditText) findViewById(R.id.quizEditTextView);
        quiz = LocalDataManager.getInstance().getQuizData();
        quizName.setText(quiz.name);
        quizDetails.setText(quiz.details);
        AddQuestionsToLayout();

        createQuizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(LocalDataManager.getInstance().getQuizData().isCorrect()){
                    if(quiz.id ==0){
                        DataBaseManager.createQuiz(quiz);
                    }else{
                        DataBaseManager.updateUserQuize(quiz);
                        DataBaseManager.updateQuizQuestions(quiz);
                    }

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
                quiz.setName(quizName.getText().toString());
                quiz.setDetails(quizDetails.getText().toString());
                LocalDataManager.getInstance().setQuiz(quiz);
                Intent intent = new Intent(v.getContext(), QuestionCreateActivity.class);
                startActivity(intent);
            }
        });

    }

    public void AddQuestionsToLayout() {
        ListView questListView = (ListView)  findViewById(R.id.questListView);
        // Create the adapter to convert the array to views
        QuestionListAdapter questionsListAdapter = new QuestionListAdapter(this, quiz);
        // Attach the adapter to a ListView
        questListView.setAdapter(questionsListAdapter);
    }
}