package com.example.novelsproject.dao;

import com.example.novelsproject.Model.StoriesDetail;
import com.example.novelsproject.db.ConnectionDB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class StoriesDetailDao {
    static ConnectionDB connectionDB = new ConnectionDB();
    static Connection con = connectionDB.CONN();

    public static List<StoriesDetail> getChapterByComicId(String comicId) {
        StoriesDetail storiesDetail;
        String mg;
        List<StoriesDetail> list = new ArrayList<>();
        try {
            if (con == null) {
                mg = "Không thể kết nối được dữ liệu";
            } else {
                String sql = "select * from ComicDetail where ComicId='" + comicId + "'";
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(sql);

                while (rs.next()) {
                    storiesDetail = new StoriesDetail();
                    storiesDetail.setChapters(rs.getInt("Chapters"));
                    storiesDetail.setTitle(rs.getString("title"));
                    storiesDetail.setComicDetailID(rs.getString("ComicDetailId"));
                    list.add(storiesDetail);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            mg = "Exception";
        }
        return list;
    }

    public static List<StoriesDetail> getTruyenByComicDetailId(String comicDetailID) {
        StoriesDetail storiesDetail;
        String msg;
        List<StoriesDetail> list = new ArrayList<>();
        try {
            if (con == null) {
                msg = "Không thể kết nối được dữ liệu";
            } else {
                String sql = "select * from ComicDetail where comicDetailId='" + comicDetailID + "'";
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(sql);

                while (rs.next()) {
                    storiesDetail = new StoriesDetail();
                    storiesDetail.setContent(rs.getString("Content"));
                    list.add(storiesDetail);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            msg = "Exception";
        }
        return list;
    }
}
