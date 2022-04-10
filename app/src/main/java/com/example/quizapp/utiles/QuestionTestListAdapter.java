package com.example.quizapp.utiles;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.quizapp.R;
import com.example.quizapp.models.Question;
import com.example.quizapp.models.Quiz;

import java.util.ArrayList;
import java.util.List;

public class QuestionTestListAdapter extends ArrayAdapter<Question> {

    public static ArrayList<String> selectedAnswers;
    private ViewGroup parent;
    private Quiz quiz;
    public QuestionTestListAdapter(Context context, Quiz quiz) {
        super(context, 0, quiz.questions);
        this.quiz = quiz;
        selectedAnswers = new ArrayList<>();
        for (int i = 0; i < quiz.questions.size(); i++) {
            selectedAnswers.add("Not Attempted");
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Question question = getItem(position);
        this.parent = parent;
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.question_test_item, parent, false);
        }
        // Lookup view for data population
        TextView questionLabel = (TextView) convertView.findViewById(R.id.questionLabel);
        RadioButton answerOneQuestion = (RadioButton) convertView.findViewById(R.id.answerOneQuestion);
        RadioButton answerTwoQuestion = (RadioButton) convertView.findViewById(R.id.answerTwoQuestion);
        RadioButton answerThreeQuestion = (RadioButton) convertView.findViewById(R.id.answerThreeQuestion);
        RadioButton answerFourQuestion = (RadioButton) convertView.findViewById(R.id.answerFourQuestion);
        // Populate the data into the template view using the data object
        questionLabel.setText(question.question);
        answerOneQuestion.setText(question.getOptionsArray().get(0));
        answerTwoQuestion.setText(question.getOptionsArray().get(1));
        answerThreeQuestion.setText(question.getOptionsArray().get(2));
        answerFourQuestion.setText(question.getOptionsArray().get(3));

        answerOneQuestion.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    selectedAnswers.set(position, buttonView.getText().toString());
            }
        });
        answerTwoQuestion.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    selectedAnswers.set(position, buttonView.getText().toString());
            }
        });
        answerThreeQuestion.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    selectedAnswers.set(position, buttonView.getText().toString());
            }
        });
        answerFourQuestion.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    selectedAnswers.set(position, buttonView.getText().toString());
            }
        });

        // Return the completed view to render on screen
        return convertView;
    }

    public int getScore(){
        int score = 0;
        for (int i=0;i<quiz.questions.size();i++){
            if (quiz.questions.get(i).answer.equals(selectedAnswers.get(i))){
                score++;
            }
        }

        return score;
    }


}


