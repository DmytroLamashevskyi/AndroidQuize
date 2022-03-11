package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LocalDataManager.getInstance().InitPref(getSharedPreferences("UserId", MODE_PRIVATE));

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

                    if (user != null) {
                        logText.setText("This user already exist, wrong password");
                        return;
                    }
                }else{
                    DataBaseManager.register(loginText.getText().toString(),passwordText.getText().toString());
                    user = DataBaseManager.LogIn(loginText.getText().toString(),passwordText.getText().toString());
                    logText.setText("User " + user.Id +"|"+user.Login +" created successfully");
                }


            }
        });
    }



}