package com.example.quizapp;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.quizapp.managers.DataBaseManager;
import com.example.quizapp.managers.LocalDataManager;
import com.example.quizapp.models.Question;
import com.example.quizapp.models.Quiz;
import com.example.quizapp.utiles.QuestionListAdapter;
import com.example.quizapp.utiles.QuestionTestListAdapter;

public class QuizGameActivity extends AppCompatActivity {
    QuestionTestListAdapter questionsListAdapter;
    Quiz quiz;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_game);
        quiz = LocalDataManager.getInstance().getQuizData();

        quiz.questions = DataBaseManager.GetQuizQuestions(quiz.id);
        AddQuestionsToLayout();
        Button endQUiz = (Button) findViewById(R.id.buttonFinish);
        endQUiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                new AlertDialog.Builder(QuizGameActivity.this)
                        .setTitle("Summary")
                        .setMessage(questionsListAdapter.getScore() + "/" + quiz.questions.size())

                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(v.getContext(), QuizzesMenuActivity.class);
                                startActivity(intent);
                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .show();
            }
        });
    }


    public void AddQuestionsToLayout() {
        ListView questTestListView = (ListView)  findViewById(R.id.questionGameListView);
        questionsListAdapter = new QuestionTestListAdapter(this, quiz);
        questTestListView.setAdapter(questionsListAdapter);
    }

}