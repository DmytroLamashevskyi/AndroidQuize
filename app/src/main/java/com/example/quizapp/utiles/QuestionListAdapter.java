package com.example.quizapp.utiles;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.quizapp.R;
import com.example.quizapp.models.Question;
import com.example.quizapp.models.Quiz;

import java.util.ArrayList;

public class QuestionListAdapter extends ArrayAdapter<Question> {

    public QuestionListAdapter(Context context, ArrayList<Question> questions) {
        super(context, 0, questions);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Question question = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.quiz_list_item, parent, false);
        }
        // Lookup view for data population
        TextView quizNameTextView = (TextView) convertView.findViewById(R.id.quizNameTextView);
        ImageButton quizStartButton = (ImageButton) convertView.findViewById(R.id.quizStartButton);
        // Populate the data into the template view using the data object
        quizNameTextView.setText(question.question);
        quizStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        // Return the completed view to render on screen
        return convertView;
    }

}
