package com.example.quizapp.utiles;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.quizapp.R;
import com.example.quizapp.models.Question;
import com.example.quizapp.models.Quiz;

import java.util.ArrayList;

public class QuestionTestListAdapter extends ArrayAdapter<Question> {

    public QuestionTestListAdapter(Context context, Quiz quiz) {
        super(context, 0, quiz.questions);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Question question = getItem(position);
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
        // Return the completed view to render on screen
        return convertView;
    }

}


