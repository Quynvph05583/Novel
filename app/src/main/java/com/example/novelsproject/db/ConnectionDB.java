package com.example.novelsproject.db;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnectionDB {
    String ip = "10.221.74.180";
    String classs = "net.sourceforge.jtds.jdbc.Driver";
    String db = "NovelsLife";
    String un = "sa";
    String password = "123";

    @SuppressLint("NewApi")
    public Connection CONN() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection conn = null;
        String ConnURL = null;
        try {
            Class.forName(classs);
            ConnURL = "jdbc:jtds:sqlserver://" + ip + ";"
                    + "databaseName=" + db + ";user=" + un + ";password="
                    + password + ";";
            conn = DriverManager.getConnection(ConnURL);
        } catch (SQLException se) {
            Log.e("Error", se.getMessage());
        } catch (ClassNotFoundException e) {
            Log.e("Error", e.getMessage());
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
        }
        return conn;
    }
}
