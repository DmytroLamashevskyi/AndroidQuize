package com.example.quizapp.utiles;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.quizapp.R;
import com.example.quizapp.managers.LocalDataManager;
import com.example.quizapp.models.Question;
import com.example.quizapp.models.Quiz;

import java.util.ArrayList;

public class QuestionListAdapter extends ArrayAdapter<Question> {

    private  Quiz quiz;
    public QuestionListAdapter(Context context, Quiz quiz) {
        super(context, 0, quiz.questions);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Question question = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.question_list_item, parent, false);
        }
        // Lookup view for data population
        TextView questionTextView = (TextView) convertView.findViewById(R.id.questionTextView);
        ImageButton questionDeleteButton = (ImageButton) convertView.findViewById(R.id.questionDeleteButton);
        ImageButton questionEditButton = (ImageButton) convertView.findViewById(R.id.questionEditButton);

        // Populate the data into the template view using the data object
        questionTextView.setText(question.question);
        questionDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LocalDataManager.getInstance().getQuizData().removeQuestion(question);
            }
        });
        questionEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        // Return the completed view to render on screen
        return convertView;
    }

}
