package com.example.novelsproject.Model;

import java.util.List;

public class StoriesDetail {
    private String ComicDetailID, Content, ComicId, title;
    private int Chapters;

    public StoriesDetail() {
    }


    public void setChapters(String content) {
        this.Content = content;
    }

    public StoriesDetail(String comicDetailID, String content, String comicId, String title, int chapters) {
        this.ComicDetailID = comicDetailID;
        this.Content = content;
        this.ComicId = comicId;
        this.title = title;
        this.Chapters = chapters;
    }

    public StoriesDetail(String content) {
        this.Content = content;
    }


    public String getComicDetailID() {
        return ComicDetailID;
    }

    public void setComicDetailID(String comicDetailID) {
        ComicDetailID = comicDetailID;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getComicId() {
        return ComicId;
    }

    public void setComicId(String comicId) {
        ComicId = comicId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getChapters() {
        return Chapters;
    }

    public void setChapters(int chapters) {
        Chapters = chapters;
    }

}
