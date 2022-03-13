package com.example.quizapp.utiles;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.quizapp.R;
import com.example.quizapp.models.Quiz;

import java.util.ArrayList;

public class UserQuizListAdapter extends ArrayAdapter<Quiz> {

    public UserQuizListAdapter(Context context, ArrayList<Quiz> quizzes) {
        super(context, 0, quizzes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Quiz quiz = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.user_quiz_list_item, parent, false);
        }
        // Lookup view for data population
        TextView quizNameTextView = (TextView) convertView.findViewById(R.id.quizNameTextView);
        ImageButton quizStartButton = (ImageButton) convertView.findViewById(R.id.quizStartButton);
        ImageButton quizEditButton = (ImageButton) convertView.findViewById(R.id.quizEditButton);
        ImageButton quizDeleteButton = (ImageButton) convertView.findViewById(R.id.quizDeleteButton);
        // Populate the data into the template view using the data object
        quizNameTextView.setText(quiz.name);
        quizStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        // Return the completed view to render on screen
        return convertView;
    }

}
