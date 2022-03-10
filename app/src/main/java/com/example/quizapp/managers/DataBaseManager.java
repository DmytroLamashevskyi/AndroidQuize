package com.example.quizapp.managers;

import android.os.StrictMode;
import android.util.Log;

import com.example.quizapp.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;

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
        Connection con = connection();
        User result = new User();
        try{
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


    public static boolean register(User user){

        String sql = "insert into users(Name,Password) values (?,?)";
        Connection  con = connection();

        try {
            PreparedStatement pst=con.prepareStatement(sql);

            pst.setString(1, user.Login);
            pst.setString(2, user.Password);

            int value = pst.executeUpdate();

            con.close();
            if(value>0){
                return true;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            return false;
        }
    }

}
