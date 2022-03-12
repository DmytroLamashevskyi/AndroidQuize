package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        Intent intent = getIntent();
        int userId = intent.getIntExtra(MainActivity.USER_ID,0);
        String userName = intent.getStringExtra(MainActivity.USER_NAME);

        TextView userNameView = (TextView) findViewById(R.id.userNameTextView);
        TextView userIdView = (TextView) findViewById(R.id.userIdTextView);
        userNameView.setText(userName);
        userIdView.setText("ID: " + userId);

        Button exitButton = (Button) findViewById(R.id.exitButton);
        Button quizListButton = (Button) findViewById(R.id.quizListButton);
        Button settingsButton = (Button) findViewById(R.id.settingsButton);
        Button ratingButton = (Button) findViewById(R.id.ratingButton);

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
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