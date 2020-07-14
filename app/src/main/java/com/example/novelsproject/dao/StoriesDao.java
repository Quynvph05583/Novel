package com.example.novelsproject.dao;

import android.util.Log;

import com.example.novelsproject.Model.Stories;
import com.example.novelsproject.db.ConnectionDB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class StoriesDao {
    static ConnectionDB connectionDB = new ConnectionDB();
    static Connection con = connectionDB.CONN();

    public static List<Stories> getComicByCategoryId(String categoryId) {
        Stories stories;
        String mg;
        List<Stories> list = new ArrayList<>();
        try {
            if (con == null) {
                mg = "Không thể kết nối được dữ liệu";
            } else {
                String sql = "select * from Comic where CategoryId='" + categoryId + "'";
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(sql);

                while (rs.next()) {
                    stories = new Stories();
                    stories.setThumbnail(rs.getString("Thumbnail"));
                    stories.setName(rs.getString("Name"));
                    stories.setComicId(rs.getString("ComicId"));
                    stories.setViews(rs.getString("Views"));
                    list.add(stories);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            mg = "Exception";
        }
        return list;
    }

    public static List<Stories> getComicByComicId(String comicId) {
        Stories stories;
        String mg;
        List<Stories> list = new ArrayList<>();
        try {
            if (con == null) {
                mg = "Không thể kết nối được dữ liệu";
            } else {
                String sql = "select * from Comic where ComicId='" + comicId + "'";
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(sql);

                while (rs.next()) {
                    stories = new Stories();
                    stories.setThumbnail(rs.getString("Thumbnail"));
                    stories.setName(rs.getString("Name"));
                    list.add(stories);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            mg = "Exception";
        }
        return list;
    }

    public static List<Stories> getAllStories() {
        Stories stories;
        String mg;
        List<Stories> list = new ArrayList<>();
        try {
            if (con == null) {
                mg = "Không thể kết nối dữ liệu AllStories";
            } else {
                String sql = "Select * from Comic";
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(sql);

                while (rs.next()) {
                    stories = new Stories();
                    stories.setThumbnail(rs.getString("Thumbnail"));
                    stories.setName(rs.getString("Name"));
                    stories.setViews(rs.getString("Views"));
                    stories.setComicId(rs.getString("ComicId"));
                    list.add(stories);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d("Exception: ", ex.toString());
        }
        return list;
    }
}
