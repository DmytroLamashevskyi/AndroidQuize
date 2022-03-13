package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizapp.managers.DataBaseManager;
import com.example.quizapp.managers.LocalDataManager;
import com.example.quizapp.models.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {

    public static final String USER_ID = "USER_ID";
    public static final String USER_NAME = "USER_NAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button clickButton = (Button) findViewById(R.id.SignInButton);
        EditText loginText = (EditText) findViewById(R.id.editTextPassword);
        EditText passwordText = (EditText) findViewById(R.id.editTextPassword);
        clickButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView logText = (TextView ) findViewById(R.id.ErrorLoginText);

                User user = null;
                if(DataBaseManager.isUserRegistered(loginText.getText().toString()))
                {
                    user =  DataBaseManager.LogIn(loginText.getText().toString(),passwordText.getText().toString());


                    if (user == null) {
                        logText.setText("This user already exist, wrong password");
                        return;
                    }else{
                        Intent intent = new Intent(v.getContext(), MainMenuActivity.class);
                        LocalDataManager.getInstance().setUserData(user);
                        startActivity(intent);
                    }

                }else{
                    DataBaseManager.register(loginText.getText().toString(),passwordText.getText().toString());
                    user = DataBaseManager.LogIn(loginText.getText().toString(),passwordText.getText().toString());
                    logText.setText("User " + user.Id +"|"+user.Login +" created successfully");

                    Intent intent = new Intent(v.getContext(), MainMenuActivity.class);
                    intent.getIntExtra(USER_ID, user.Id);
                    intent.putExtra(USER_NAME, user.Login);
                    startActivity(intent);
                }


            }
        });
    }



}