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
import java.util.ArrayList;
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
            pst.close();
            con.close();

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

    public static Quiz getLasCreated(int userId){
        String sql = "SELECT * FROM quizzes WHERE ownerId= ? ORDER BY id DESC LIMIT 1";
        Quiz result = new Quiz();
        try {
            Connection  con = connection();
            PreparedStatement pst=con.prepareStatement(sql);

            pst.setInt(1, userId);

            ResultSet rs = pst.executeQuery();
            if (rs.next()){
                result.ownerId = userId;
                result.id = rs.getInt("Id");
                result.name = rs.getString("Name");
                result.details = rs.getString("Description");
                System.out.println(result.id  + "|" + result.name  + "\t" + "- was selected");
            }
            pst.close();
            con.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            return result;
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
        String sql = "INSERT INTO quizzes ( Name, Description, OwnerId, IsDeleted) VALUES ( ?,  ?, ? , 0);";

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
                int quizId =  getLasCreated(ownerId).id;

                for (Question question: questions ) {
                    question.quizId = quizId;
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

    public static ArrayList<Quiz> GetAllQuizzes() {
        ArrayList<Quiz> quizzes = new ArrayList<>();
        String sql = "SELECT * FROM quizzes WHERE IsDeleted=false LIMIT 20";
        System.out.println( "Try get all quizzes LIMIT 20");

        try {
            Connection  con = connection();
            PreparedStatement pst=con.prepareStatement(sql);

            ResultSet rs = pst.executeQuery();

            while (rs.next()){
                Quiz temp = new Quiz();
                temp.id = rs.getInt("Id");
                temp.name =  rs.getString("Name");
                temp.details =  rs.getString("Description");
                temp.ownerId = rs.getInt("Id");

                System.out.println( temp.id  + "|" + rs.getString("Name")+ "|" + rs.getInt("OwnerId") + "\t" + "- was get from DB.");
                quizzes.add(temp);
            }
            pst.close();
            con.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            System.out.println( quizzes.size() + " quizzes was get from DB (MAX 20).");
            return  quizzes;
        }
    }


    public static ArrayList<Quiz> GetUserQuizzes(int userId) {
        ArrayList<Quiz> quizzes = new ArrayList<>();
        String sql = "SELECT * FROM quizzes WHERE OwnerId = ? AND IsDeleted=false LIMIT 20";

        try {
            Connection  con = connection();
            PreparedStatement pst=con.prepareStatement(sql);

            pst.setInt(1, userId);

            ResultSet rs = pst.executeQuery();
            while (rs.next()){
                Quiz temp = new Quiz();
                temp.id = rs.getInt("Id");
                temp.name =  rs.getString("Name");
                temp.details =  rs.getString("Description");
                temp.ownerId = rs.getInt("Id");

                System.out.println( temp.id  + "|" + rs.getString("Name")+ "|" + rs.getInt("OwnerId") + "\t" + " for User["+ userId +"]- was get from DB.");
                quizzes.add(temp);
            }
            pst.close();
            con.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            return  quizzes;
        }
    }


    /** Change DB Delete flag to true
     * @param quizId quiz ID to delete
     * @return Is deleted successfully = true
     */
    public static boolean deleteUserQuiz(int quizId){
        String sql = "UPDATE quizzes SET IsDeleted = 1 WHERE quizzes.id = ?";
        boolean result = false;
        try {
            Connection  con = connection();

            PreparedStatement pst=con.prepareStatement(sql);
            pst.setInt(1, quizId);

            int value = pst.executeUpdate();

            con.close();
            if(value>0){
                System.out.println(quizId + " was deleted successfully.");
                return true;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            return false;
        }
    }


    /** Update DB Quiz with edited values
     * @param quiz input data to Update (ID is important)
     * @return Is updated successfully = true
     */
    public static boolean updateUserQuize(Quiz quiz){
        boolean result = false;
        String sql = "UPDATE quizzes SET Name = ?, Description = ? WHERE quizzes.id = ?";

        try {
            Connection  con = connection();

            PreparedStatement pst=con.prepareStatement(sql);
            pst.setString(1, quiz.name);
            pst.setString(2, quiz.details);
            pst.setInt(3, quiz.id);

            int value = pst.executeUpdate();

            con.close();
            if(value>0){
                System.out.println(quiz.id + "|"+ quiz.name + " was updated successfully.");
                return true;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            return false;
        }

    }


}
