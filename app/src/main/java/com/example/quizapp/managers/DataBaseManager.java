package com.example.quizapp.managers;

import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
    Example: https://www.codeproject.com/Questions/1251805/Android-studio-plus-SQL-server-database
    Class for DB connection and SQL queries
*/
public class DataBaseManager {

    private static final String LOG = "DEBUG";
    private static String ip = "*****";
    private static String port = "*****";
    private static String classjdbcDriver = "net.sourceforge.jtds.jdbc.Driver";
    private static String db = "*****";
    private static String un = "*****";
    private static String password = "*****";

    public static Connection connection() {
        String ConnURL = null;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            Class.forName(classjdbcDriver);
            ConnURL = "jdbc:jtds:sqlserver://" + ip +":"+port+";" + "databaseName=" + db + ";user=" + un + ";password=" + password + ";";
            return DriverManager.getConnection(ConnURL);
        } catch (SQLException e) {
            Log.d(LOG, e.getMessage());
        } catch (ClassNotFoundException e) {
            Log.d(LOG, e.getMessage());
        }
        return null;
    }

}
