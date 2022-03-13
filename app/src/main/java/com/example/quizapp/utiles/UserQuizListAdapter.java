package com.example.quizapp.utiles;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.quizapp.R;
import com.example.quizapp.managers.DataBaseManager;
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
        quizDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Delete quiz?")
                        .setMessage("Are you sure you want to delete this quiz?")

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                DataBaseManager.deleteUserQuiz(quiz.id);
                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                notifyDataSetChanged();
            }
        });

        quizEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        quizStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        // Return the completed view to render on screen
        return convertView;
    }

}
