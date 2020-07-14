package com.example.novelsproject.dao;

import android.content.Context;

import com.example.novelsproject.Model.Category;
import com.example.novelsproject.db.ConnectionDB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CategoryDao {
    static ConnectionDB connectionDB = new ConnectionDB();
    static Connection con = connectionDB.CONN();

    public CategoryDao(Context context) {

    }

    public static List<Category> getListCategory() {
        Category category;
        String mg;
        List<Category> list = new ArrayList<>();
        try {
            if (con == null) {
                mg = "Không thể kết nối được dữ liệu";
            } else {
                String sql = "select * from Category";
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(sql);

                while (rs.next()) {
                    category = new Category();
                    category.setName(rs.getString("Name"));
                    category.setCategoryID(rs.getString("CategoryId"));
                    list.add(category);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            mg = "Exception";
        }
        return list;
    }


}
