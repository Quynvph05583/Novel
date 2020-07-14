package com.example.novelsproject.dao;

import com.example.novelsproject.db.ConnectionDB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class UsersDao {
    ConnectionDB connectionDB = new ConnectionDB();
    String mg = "";
    String user = "";
    Connection con = connectionDB.CONN();

    public String Login(String userid, String password) {
        if (userid.isEmpty()) {
            mg = "Vui lòng nhập Tài khoản";
        } else if (password.isEmpty()) {
            mg = "Vui lòng nhập Mật khẩu";
        } else {
            try {
                if (con == null) {
                    mg = "Không thể kết nối được dữ liệu";
                } else {
                    String sql = "select * from Users where userid='" + userid + "' and pass='" + password + "'";
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(sql);
                    if (rs.next()) {
                        user = rs.getString("userid");
                        mg = "Xin chào: " + user;
                    } else {
                        mg = "Tài khoản hoặc mật khẩu không đúng";
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                mg = "Exception";
            }
        }
        return mg;
    }
}
