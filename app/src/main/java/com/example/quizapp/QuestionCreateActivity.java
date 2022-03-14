package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.quizapp.managers.LocalDataManager;
import com.example.quizapp.models.Question;

public class QuestionCreateActivity extends AppCompatActivity {
    private Question question;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_create);

        Button createQuestionButton = (Button) findViewById(R.id.createQuestionButton);
        TextView questionTextView = (TextView) findViewById(R.id.questionTextView);

        RadioButton isAnswerOneRadioButton = (RadioButton) findViewById(R.id.isAnswerOneRadioButton);
        RadioButton isAnswerTwoRadioButton = (RadioButton) findViewById(R.id.isAnswerTwoRadioButton);
        RadioButton isAnswerThreeRadioButton = (RadioButton) findViewById(R.id.isAnswerThreeRadioButton);
        RadioButton isAnswerFourRadioButton = (RadioButton) findViewById(R.id.isAnswerFourRadioButton);

        TextView answerOneTextView = (TextView) findViewById(R.id.answerOneTextView);
        TextView answerTwoTextView = (TextView) findViewById(R.id.answerTwoTextView);
        TextView answerThreeTextView = (TextView) findViewById(R.id.answerThreeTextView);
        TextView answerFourTextView = (TextView) findViewById(R.id.answerFourTextView);

        question = new Question();

        createQuestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                question.question = questionTextView.getText().toString();
                question.optionsArray.add(answerOneTextView.getText().toString());
                question.optionsArray.add(answerTwoTextView.getText().toString());
                question.optionsArray.add(answerThreeTextView.getText().toString());
                question.optionsArray.add(answerFourTextView.getText().toString());

                if(isAnswerOneRadioButton.isChecked()){
                    question.answer = answerOneTextView.getText().toString();
                }else if(isAnswerTwoRadioButton.isChecked()){
                    question.answer = answerTwoTextView.getText().toString();
                }else if(isAnswerThreeRadioButton.isChecked()){
                    question.answer = answerThreeTextView.getText().toString();
                }else if(isAnswerFourRadioButton.isChecked()){
                    question.answer = answerFourTextView.getText().toString();
                }


                if(question.isCorrect()){
                    LocalDataManager.getInstance().getQuizData().addQuestion(question);

                    Intent intent = new Intent(v.getContext(), QuizCreateActivity.class);
                    startActivity(intent);
                }else {
                    new AlertDialog.Builder(QuestionCreateActivity.this)
                            .setTitle("Error")
                            .setMessage("Can't create Question with this parameters.")
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).show();
                }
            }
        });
    }
}