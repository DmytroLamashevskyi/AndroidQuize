package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.quizapp.managers.LocalDataManager;

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        TextView userNameView = (TextView) findViewById(R.id.userNameTextView);
        TextView userIdView = (TextView) findViewById(R.id.userIdTextView);

        userNameView.setText(LocalDataManager.getInstance().getUserData().Login);
        userIdView.setText("ID: " + LocalDataManager.getInstance().getUserData().Id);

        Button exitButton = (Button) findViewById(R.id.exitButton);
        Button quizListButton = (Button) findViewById(R.id.quizListButton);
        Button settingsButton = (Button) findViewById(R.id.settingsButton);
        Button ratingButton = (Button) findViewById(R.id.ratingButton);

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                LocalDataManager.getInstance().setUserData(null);
                startActivity(intent);
            }
        });

        quizListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), QuizzesMenuActivity.class);
                startActivity(intent);
            }
        });

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        ratingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), RatingQuizActivity.class);
                startActivity(intent);
            }
        });

    }
}