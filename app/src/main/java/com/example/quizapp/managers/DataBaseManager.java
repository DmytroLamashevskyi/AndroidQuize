package com.example.quizapp.managers;

import android.os.StrictMode;
import android.util.Log;

import com.example.quizapp.models.Question;
import com.example.quizapp.models.Quiz;
import com.example.quizapp.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

/*
    Example: https://www.codeproject.com/Questions/1251805/Android-studio-plus-SQL-server-database
    Class for DB connection and SQL queries
*/
public class DataBaseManager {

    private static final String LOG = "DEBUG";
    private static String classjdbcDriver = "net.sourceforge.jtds.jdbc.Driver";

    private static String url = "jdbc:mysql://192.168.1.20/android_quiz";
    private static String username = "java";
    private static String password = "password";
    String res = "";
    private static Connection connection() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver loaded!");
            Connection connection = DriverManager.getConnection(url, username, password);
            System.out.println("Database connected!");
            return connection;
        } catch (SQLException | ClassNotFoundException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

    public static User LogIn( String login, String password){

        try{
            Connection con = connection();
            User result = new User();
            String sql;
            sql  = "SELECT Id,Name FROM users WHERE Name =? And Password = ?";
            PreparedStatement prest = con.prepareStatement(sql);
            prest.setString(1,login);
            prest.setString(2,password);

            ResultSet rs = prest.executeQuery();
            if (rs.next()){
                result.Id = rs.getInt("Id");
                result.Login = rs.getString("Name");

                System.out.println(result.Id + "\t" + "- " + result.Login);

            }
            prest.close();
            con.close();
            return result;
        }
        catch (SQLException s){
            System.out.println("SQL statement is not executed!");
            System.out.println(s.getMessage());

        }

        return null;
    }


    public static boolean register(String login, String password){

        String sql = "insert into users(Name,Password) values (?,?)";

        try {
            Connection  con = connection();

            PreparedStatement pst=con.prepareStatement(sql);

            pst.setString(1, login);
            pst.setString(2, password);

            int value = pst.executeUpdate();

            con.close();
            if(value>0){
                System.out.println(login + " was created successfully.");
                return true;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            return false;
        }
    }


    public static boolean isUserRegistered(String login){

        String sql = "SELECT Id,Name FROM users WHERE Name =? ";
        boolean result = false;
        try {
            Connection  con = connection();
            PreparedStatement pst=con.prepareStatement(sql);

            pst.setString(1, login);

            ResultSet rs = pst.executeQuery();
            if (rs.next()){
                System.out.println( rs.getInt("Id") + "|" + rs.getString("Name")+ "\t" + "- Already exist");

                pst.close();
                con.close();
                result = true;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            result = false;
        }finally {
            return  result;
        }
    }

    public static void createQuiz(Quiz quiz){
        if(quiz !=null){
            createQuiz(quiz.ownerId,quiz.name,quiz.details,quiz.questions);
        }
    }

    public static String createQuiz(int ownerId, String name, String details, List<Question> questions){
        String message = "Error";
        if(name == null || name.isEmpty() || name.trim().isEmpty()){
            message = "Quiz name is not correct.";
            System.out.println(message);
            return message;
        }
        if(details == null || details.isEmpty() || details.trim().isEmpty()){
            message = "Quiz details is not correct";
            System.out.println(message);
            return message;
        }
        if(questions == null || questions.isEmpty() || questions.size() < 1){
            message = "Quiz questions is not correct.";
            System.out.println(message);
            return message;
        }

        System.out.println("Start creating Quiz ownerId =" + ownerId +"\t| name = " + name);
        String sql = "INSERT INTO quizzes ( Name, Description, OwnerId) VALUES ( ?,  ?, ?);";

        try {
            Connection  con = connection();
            PreparedStatement pst=con.prepareStatement(sql);

            pst.setString(1, name);
            pst.setString(2, details);
            pst.setInt(3, ownerId);

            int value = pst.executeUpdate();
            if (value > 0){
                message = "Quiz" + name + " was created successfully.";
                System.out.println(message);
                pst.close();
                con.close();

                for (Question question: questions ) {
                    if(tryAddQuestion(question)){
                        message = "Question [" + question.question + "] was created successfully.";
                        System.out.println(message);
                    }else {
                        System.out.println("Question was skiped");
                    }
                }
                message = "Created successfully";
            }else {
                message = "Quiz" + name + " was NOT created successfully.";
                System.out.println(message);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return message;
    }

    private static boolean tryAddQuestion(Question question) {
        boolean result = false;
        String sql = "INSERT INTO questions (`quizId`, `question`, `options`, `answer`) VALUES ( ?, ?, ?, ?);";

        if(question !=null){
            System.out.println("Question equals null.");
            return  false;
        }
        if(!question.options.contains(question.answer)){
            System.out.println("Question not contains correct answer.");
            return  false;
        }

        try {
            Connection  con = connection();

            PreparedStatement pst=con.prepareStatement(sql);

            pst.setInt(1, question.quizId);
            pst.setString(2, question.question);
            pst.setString(3, question.options);
            pst.setString(4, question.answer);

            int value = pst.executeUpdate();

            con.close();
            if(value>0){
                System.out.println("Question [" + question.question + "] was created successfully.");
                return true;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            return result;
        }
    }

}
