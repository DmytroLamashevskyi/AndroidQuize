package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.quizapp.managers.DataBaseManager;
import com.example.quizapp.managers.LocalDataManager;
import com.example.quizapp.models.Quiz;
import com.example.quizapp.utiles.QuestionListAdapter;
import com.example.quizapp.utiles.QuestionTestListAdapter;

public class QuizGameActivity extends AppCompatActivity {

    Quiz quiz;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_game);
        quiz = LocalDataManager.getInstance().getQuizData();

        quiz.questions = DataBaseManager.GetQuizQuestions(quiz.id);
        AddQuestionsToLayout();
    }


    public void AddQuestionsToLayout() {
        ListView questTestListView = (ListView)  findViewById(R.id.questionGameListView);
        // Create the adapter to convert the array to views
        QuestionTestListAdapter questionsListAdapter = new QuestionTestListAdapter(this, quiz);
        // Attach the adapter to a ListView
        questTestListView.setAdapter(questionsListAdapter);
    }

}